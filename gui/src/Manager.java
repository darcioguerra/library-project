import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.time.*;

/**
 * subclasse da classe "User" para gerir gestores e as suas acoes
 * @author Dárcio Guerra e Rodrigo Pereira
 */
public class Manager extends User {  //Are created by other Managers
	
    public Manager (String type) {
		super(type);
	}

	/**
	 * Método para listar os utilizadores inativos e acrescentá-los ao table model
	 * @author Rodrigo Pereira
	 */
	public void requestList(DefaultTableModel aTableModel) {
		
		Searches s = new Searches();
		ArrayList <User> userInfo = s.searchUsersByState("Inactive");
		
		for (int i = 0; i < userInfo.size(); i++){
			String name = userInfo.get(i).getName();
			String login = userInfo.get(i).getLogin();
			String email  = userInfo.get(i).getEmail();
			String type = userInfo.get(i).getType();
			
			Object[] data = {name, login, email, type};
		 
			aTableModel.addRow(data);
		}
	}

	/**
	 * Método para listar os utilizadores ativos e acrescentá-los ao table model
	 * @author Rodrigo Pereira
	 */
	public void activeUserList(DefaultTableModel aTableModel) {
		
		Searches s = new Searches();
		ArrayList <User> userInfo = s.searchUsersByState("Active");
		
		for (int i = 0; i < userInfo.size(); i++){
			String name = userInfo.get(i).getName();
			String login = userInfo.get(i).getLogin();
			String email  = userInfo.get(i).getEmail();
			String type = userInfo.get(i).getType();
			
			Object[] data = {name, login, email, type};
		 
			aTableModel.addRow(data);
		}
	}

	/**
	 * Metodo para aprovar uma pedido de registo
	 * @author Dárcio Guerra e Rodrigo Pereira
	 */
	public boolean approveRequest(String aLogin) {
		Updates update = new Updates();
		return update.updateUserState("Active", aLogin);
	}

	/**
	 * Metodo para reprovar um pedido de registo
	 * @author Dárcio Guerra e Rodrigo Pereira
	 */
	public boolean reproveRequest(String aLogin) {
		Updates update = new Updates();
		return update.updateUserState("Reproved", aLogin);
	}

	/**
	 * Metodo para desativar uma conta
	 * @author Dárcio Guerra e Rodrigo Pereira
	 */
	public boolean inactivateAccount(String aLogin) {
		Updates update = new Updates();
		return update.updateUserState("Inactive", aLogin);	
 	}

	/**
	 * Metodo para aprovar uma revisao
	 * @author Dárcio Guerra e Rodrigo Pereira
	 */
	public boolean approveReview(Manager aManager, String aBookName) {
		Updates update = new Updates();
		Searches s = new Searches();
		Review r = s.searchReviewByBook(aBookName);
		
		if (update.updateReviewState("Approved", r.getSerialNumber()))
			if (update.updateReviewManager(aManager, r.getBook().getTitle()))
				return true;
			
		return false;		
	}

	/**
	 * Metodo para reprovar uma revisao
	 * @author Dárcio Guerra e Rodrigo Pereira
	 */
	public boolean reproveReview(Manager aManager, String aBookName) {
		
		Searches s = new Searches();
		Updates update = new Updates();

		Review r = s.searchReviewByBook(aBookName);
		
		if (update.updateReviewState("Reproved", r.getSerialNumber())) {
			if (update.updateReviewManager(aManager, r.getBook().getTitle())) {
				Notification n = new Notification();
				n = n.informationReviewReprovedN(r);
				Inserts i = new Inserts();
				
				if (i.insertNotification(n.getTitle(), n.getContent(), n.getType(), false)) {

					int id = s.searchNotificationById();
	
					i.assignReviewNotification(id, r.getSerialNumber());
					i.assignUserNotification(id, r.getAuthor().getLogin());
	
					return true;

				}
			}
		}
		
		return false;
	}

	/**
	 * Metodo para notificar uma revisor chefe (revisor a cargo de uma revisao)
	 * @author Dárcio Guerra e Rodrigo Pereira
	 */
	public boolean notifyHeadReviewer(Long aSerialNumber, String aLogin) {

		Searches s = new Searches();

		Inserts i = new Inserts();
		Notification n = new Notification();
		n = n.choiceProposalHeadReviewerN(aSerialNumber);
		
		if (i.insertNotification(n.getTitle(), n.getContent(), n.getType(), false)) {

			int id = s.searchNotificationById();

			i.assignReviewNotification(id, aSerialNumber);
			i.assignUserNotification(id, aLogin);

			return true;
		} else
			return false;
	}

	/**
	 * Método para listar as revisões e acrescentá-las ao table model
	 * @author Rodrigo Pereira
	 */

	public void reviewsList(DefaultTableModel aTableModel, String aState) {
		Searches s = new Searches();
		ArrayList<Review> reviewList = s.searchReviews(aState);

		for (int i = 0; i < reviewList.size(); i++){
			String title = reviewList.get(i).getBook().getTitle();
			String name = reviewList.get(i).getAuthor().getName();
			Long serialNumber  = reviewList.get(i).getSerialNumber();
			String state = reviewList.get(i).getState();

			Object[] data = {title, name, serialNumber, state};
		 
			aTableModel.addRow(data);
		}
	}

	/**
	 * Método para listar as revisores e acrescentá-los ao table model
	 * @author Rodrigo Pereira
	 */

	public void reviewersList(DefaultTableModel aTableModel, Long aSerialNumber) {
		Searches s = new Searches();
		ArrayList<Reviewer> reviewerList = s.searchReviewers(aSerialNumber);

		for (int i = 0; i < reviewerList.size(); i++){
			String name = reviewerList.get(i).getName();
			String login = reviewerList.get(i).getLogin();
			String email = reviewerList.get(i).getEmail();
			int telephone = reviewerList.get(i).getTelephoneNumber();
			System.out.println(email);
			System.out.println(telephone);

			Object[] data = {name, login, email, telephone};
		 
			aTableModel.addRow(data);
		}
	}

	/**
	 * Metodo para criar uma licenca
	 * @author Dárcio Guerra e Rodrigo Pereira
	 */
	public boolean createLicence(Licence aLicence) {
		Inserts i = new Inserts();
		
		aLicence.setDate(LocalDate.now());
		aLicence.setState(true);
		
		if (i.insertLicence(aLicence))
			return true;
		else
			return false;
	}
}