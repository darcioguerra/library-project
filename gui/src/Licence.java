import java.time.*;
import java.util.*;

import javax.swing.table.DefaultTableModel;

import java.io.Serializable;

/**
 * Classe que contem a informacao de uma licenca
 * @author Dárcio Guerra
 */
public class Licence implements Serializable {
    private int id;
    private String name;
    private String description;
    private LocalDate date;
    private boolean state; // true - valid licence     / false - invalid licence
    
    public Licence() {
    	Searches newSearch = new Searches();
    	int lastID = newSearch.searchLicenceID();
    	this.setId(lastID + 1);
    }

	public int getId() {
		return id;
	}

	public void setId(int aId) {
		this.id = aId;
	}

	public String getName() {
		return name;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String aDescription) {
		this.description = aDescription;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate aDate) {
		this.date = aDate;
	}
    
	public boolean getState() {
		return this.state;
	}
	
    public void setState(boolean aState) {
    	this.state = aState;
    }

	/**
	 * Metodo para listar todas as licenças e a adicionar a um tablemodel
	 * @author Rodrigo Pereira
	 */
	public void listLicences(DefaultTableModel aTableModel) {

		Searches newSearch = new Searches();

		ArrayList<Licence> licenceList = newSearch.searchLicences();
		
		for (int i = 0; i < licenceList.size(); i++) {
			int id = licenceList.get(i).getId();
			String name = licenceList.get(i).getName();
    		String description = licenceList.get(i).getDescription();
    		LocalDate date = licenceList.get(i).getDate();
    		String stateString;
			if (state)
				stateString = "Yes";
			else
				stateString = "No";

			Object[] data = {id, name, description, date, stateString};

			aTableModel.addRow(data);
		}
	}
}
