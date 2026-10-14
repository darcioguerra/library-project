import java.time.*;

/**
 *	Subclasse da classe "Client" onde contém os atributos unicos da classe "Author"
 *	@author Dárcio Guerra e Rodrigo Pereira
 */
public class Author extends Client {  //Must register on the platform
	private String literaryStyle;
	private LocalDate initialDate;  //Of type yyyymmddhhmmss
	
	public Author(String type) {
		super(type);
	}
	
	public String getLiteraryStyle() {
		return literaryStyle;
	}
	
	public void setLiteraryStyle(String aLiteraryStyle) {
		this.literaryStyle = aLiteraryStyle;
	}
	
	public LocalDate getInitialDate() {
		return initialDate;
	}
	
	public void setInitialDate(LocalDate aInitialDate) {
		this.initialDate = aInitialDate;
	}
	
}
