import java.time.*;
import java.io.Serializable;

/** 
 *   Classe que representa as anotações feitas pelos revisores sobre as revisões dos livros
 *   @author Dárci Guerra e Rodrigo Pereira
 */
public class Annotation implements Serializable{
    private String description;
    private int page;
    private int paragraph;
    private LocalDate date;
    private int id;

    public Annotation() {
        Searches newSearch = new Searches();
        this.setId(newSearch.searchAnotationID() + 1);
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String aDescription) {
        this.description = aDescription;
    }
    
    public int getPage() {
        return this.page;
    }

    public void setPage(int aPage) {
        this.page = aPage;
    }

    public int getParagraph() {
        return this.paragraph;
    }

    public void setParagraph(int aParagraph) {
        this.paragraph = aParagraph;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate aDate) {
        this.date = aDate;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int aId) {
        this.id = aId;
    }
}