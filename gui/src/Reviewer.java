import java.util.*;

import javax.swing.table.DefaultTableModel;

/**
 *	Subclasse da classe "Client" onde contém os atributos unicos da classe "Reviewer"
 *	@author Dárcio Guerra e Rodrigo Pereira
 */
public class Reviewer extends Client{  //Must register on the platform
	private String areaOfEspecialization;
	private String academics;
	
	public Reviewer (String type) {
		super(type);
	}
	
	public String getAreaOfEspecialization() {
		return areaOfEspecialization;
	}
	
	public void setAreaOfEspecialization(String aAreaOfEspecialization) {
		this.areaOfEspecialization = aAreaOfEspecialization;
	}
	
	public String getAcademics() {
		return academics;
	}
	
	public void setAcademics(String aAcademics) {
		this.academics = aAcademics;
	}

	/**
	 * Método para adicionar um header reviewer
	 * @author Rodrigo Pereira
	 */
	
	public boolean addHeadReviewer(String aLogin, Long aSerialNumber) {
		Updates update = new Updates();

		if (update.updateHeadReviewer(aLogin, aSerialNumber))
			return true;
			
		return false;
	}

	/**
	 * Método para listar as reviews e acrescentar ao table model
	 * @author Rodrigo Pereira
	 */

	public void listReviews(DefaultTableModel aTableModel, String aState) {
		
		Searches newSearch = new Searches();
		
		ArrayList<Review> reviewList = newSearch.searchReviews(aState);

		for (int i = 0; i < reviewList.size(); i++){
			if (this.getLogin().equals(reviewList.get(i).getHeadReviewer())) {
				String title = reviewList.get(i).getBook().getTitle();
				String name = reviewList.get(i).getAuthor().getName();
				Long serialNumber  = reviewList.get(i).getSerialNumber();
				String state = reviewList.get(i).getState();

				Object[] data = {title, name, serialNumber, state};
			
				aTableModel.addRow(data);
			}
		}
	}


	/**
	 * Metodo onde se da inicio a uma revisao
	 * @author Dárcio Guerra e Rodrigo Pereira
	 */
	public boolean startReview(Long aSerialNumber) {
		Updates newUpdates = new Updates();
		if (newUpdates.updateReviewState("Ongoing", aSerialNumber))
			return true;
		else
			return false;
	}

	/**
	 * Metodo onde se finaliza uma revisao
	 * @author Rodrigo Pereira
	 */
	public boolean finishReview(Long aSerialNumber) {
		Updates newUpdates = new Updates();
		if (newUpdates.updateReviewState("Finished", aSerialNumber))
			return true;
		else
			return false;
	}

	/**
	 * Metodo para adicionar licencas numa revisao
	 * @author Dárcio Guerra e Rodrigo Pereira
	 */
	public boolean addLicences(Long aSerialNumber, int aId) {

		Inserts newInsert = new Inserts();

		System.out.println(aSerialNumber);
		System.out.println(aId);
		
		if (newInsert.insertReviewLicence(aSerialNumber, aId))
			return true;
		else
			return false;
	}

	/**
	 * Metodo para adicionar anotacoes numa revisao
	 * @author Dárcio Guerra e Rodrigo Pereira
	 */
	public boolean addAnnotations(Long aSerialNumber, Annotation aAnnotation) {
		Inserts newInsert = new Inserts();

		if (newInsert.insertAnnotation(aSerialNumber, aAnnotation))
			return true;
		else 
			return false;
		
	}
}
