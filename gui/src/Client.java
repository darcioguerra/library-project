import java.util.*;

/**
 * Subclasse da classe "User" que contém atributos unicos das classes "Author" e "Reviewer"
 * @author Dárcio Guerra e Rodrigo Pereira
 */
public class Client extends User {
    private String address;
	private int nif;  //unique attribute with 9 digits
	private int telephoneNumber;  //Must have 9 digits and start with digits 9, 2 or 3

    public Client(String type) {
		super(type);
	}

    public String getAddress() {
		return address;
	}
	
	public void setAddress(String aAddress) {
		this.address = aAddress;
	}
	
	public int getNif() {
		return nif;
	}
	
	public void setNif(int aNif) {
		this.nif = aNif;
	}
	
	public int getTelephoneNumber() {
		return telephoneNumber;
	}
	
	public void setTelephoneNumber(int aTelephoneNumber) {
		this.telephoneNumber = aTelephoneNumber;
	}

	/**
	 * Metodo para pedir a remoção da conta
	 * @author Dárcio Guerra e Rodrigo Pereira
	 */
	public boolean requestAccountRemoval(User aUser) {
		Inserts i = new Inserts();
		Searches s = new Searches();
		
		Notification n = new Notification();
		n = n.choiceProposalOmitAccountDataN(aUser);

		if (i.insertNotification(n.getTitle(), n.getContent(), n.getType(), false)) {

			int id = s.searchNotificationById();
			ArrayList<User> managerList = s.searchUsersByType("Manager");

			if (i.assignUsersNotification(id, managerList)) {
				return true;
			}
		}

		return false;
	}
}
