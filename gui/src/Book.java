import java.time.*;
import java.io.Serializable;

/**
 * Classe onde irão ser guardadas as informações de um livro
 * @author Dárcio Guerra e Rodrigo Pereira
 */
public class Book implements Comparable<Book>, Serializable{
    private Author author;
    private String title;
    private String subtitle;
    private String style; // 1 = Action   /   2 = Comedy   /   3 = Horror  /   4 = Romance   /   5 = Fiction
    private String type; // 1 = 
    private int pages;
    private int words;
    private Long isbn;
    private int edition;
    private LocalDate submissionDate;
    private LocalDate aprovationDate;

    public Book() {
        LocalDate date = LocalDate.of(1970, 01, 01);
        this.setAprovationDate(date);
    }

    public void setAuthor(Author aAuthor) {
        this.author = aAuthor;
    }

    public Author getAuthor() {
        return this.author;
    }

    public void setTitle(String aTitle) {
        this.title = aTitle;
    }

    public String getTitle() {
        return this.title;
    }

    public void setSubtitle(String aSubtitle) {
        this.subtitle = aSubtitle;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public void setStyle(String aStyle) {
        this.style = aStyle;
    }

    public String getStyle() {
        return this.style;
    }

    public void setType(String aType) {
        this.type = aType;
    }

    public String getType() {
        return this.type;
    }

    public void setPages(int aPages) {
        this.pages = aPages;
    }

    public int getPages() {
        return this.pages;
    }

    public void setWords(int aWords) {
        this.words = aWords;
    }

    public int getWords() {
        return this.words;
    }

    public void setIsbn(Long aIsbn) {
        this.isbn = aIsbn;
    }

    public Long getIsbn() {
        return this.isbn;
    }

    public void setEdition(int aEdition) {
        this.edition = aEdition;
    }

    public int getEdition() {
        return this.edition;
    }

    public void setSubmissionDate(LocalDate aSubmissionDate) {
        this.submissionDate = aSubmissionDate;
    }

    public LocalDate getSubmissionDate() {
        return this.submissionDate;
    }

    public void setAprovationDate(LocalDate aAprovationDate) {
        this.aprovationDate = aAprovationDate;
    }

    public LocalDate getAprovationDate() {
        return this.aprovationDate;
    }

    @Override
    public int compareTo(Book aBook) {
        return this.getTitle().compareTo(aBook.getTitle());
    }
}
