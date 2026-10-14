import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.io.Serializable;

/**
 * Classe para gerir utilizadores e as suas acoes
 * @author Dárcio Guerra e Rodrigo Pereira
 */
public class User implements Comparable<User>, SearchTerm, Serializable{
	private String name;
	private String login;  //unique attribute
	private String password;
	private String email;  //unique attribute of type [designation]@[entity].[domain]
	private String type; //1 = Manager  /   2 = Author   /   3 = Reviewers
	private String state; //inactive / active / reproved / omitted

	public User (String type) {
		this.setType(type);

		if (this.getType() == "Manager") {
			this.setState("Active");
		} else {
			this.setState("Inactive");
		}
	}

	public User() {}
	
	//region GETTERS/SETTERS
	public String getName() {
		return name;
	}
	
	public void setName(String aName) {
		this.name = aName;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String aLogin) {
		this.login = aLogin;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String aPassword) {
		this.password = aPassword;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String aEmail) {
		this.email = aEmail;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String aType) {
		this.type = aType;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String aState) {
		this.state = aState;
	}
	//endregion

	/**
	 * Método para listar todas as notificações de um utilizador
	 * @author Rodrigo Pereira
	 */
	public void listNotification(DefaultTableModel aTableModel, User aUser) {
		ArrayList<Notification> notificationList = aUser.checkNotifications(aUser);

		for (int i = 0; i < notificationList.size(); i++){
			int id = notificationList.get(i).getId();
			String title = notificationList.get(i).getTitle();
			String type  = notificationList.get(i).getType();
			boolean viewed = notificationList.get(i).getViewed();
			
			Object[] data = {id, title, type, viewed, "Details"};
		
			aTableModel.addRow(data);
		}
	}
	
	/**
	 * Metodo para verificar as notificacoes do utilizador
	 * @author Dárcio Guerra e Rodrigo Pereira
	 */
	public ArrayList<Notification> checkNotifications(User aUser) {
		Searches s = new Searches();
		ArrayList <Notification> notificationList = s.searchNotifications(aUser.getLogin());

		return notificationList;
	}
	
	/**
	 * Metodo para consultar o estado de uma revisao
	 * @author Dárcio Guerra e Rodrigo Pereira
	 */
	public void allReviewsList(DefaultTableModel aTableModel) {
		Searches s = new Searches();
		ArrayList<Review> reviewList = s.searchReviews("SearchAll");
		
		for (int i = 0; i < reviewList.size(); i++){
			String title = reviewList.get(i).getBook().getTitle();
			String name = reviewList.get(i).getAuthor().getName();
			Long serialNumber  = reviewList.get(i).getSerialNumber();
			String state = reviewList.get(i).getState();
			
			Object[] data = {title, name, serialNumber, state};
			
			aTableModel.addRow(data);
		}
	}

	@Override
	public int compareTo(User aUser) {
		return this.getLogin().compareTo(aUser.getLogin());
	}

	@Override
	public String getSearchableAttribute() {
		return login;
	}
}
