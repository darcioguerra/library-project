import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

/**
 * Classe para gerir e criar revisoes
 * @author Dárcio Guerra e Rodrigo Pereira
 */
public class Review implements Comparable<Review>, SearchTerm, Serializable{
    private Book book;
    private Author author;
    private Manager manager;
    private String headReviewer;
    private ArrayList<Reviewer> reviewers;
    private long serialNumber;
    private LocalDate realizationDate;
    private LocalDate elapsedTime;
    private boolean paid;
    private String state;  // 1 = started   /   2 = approved   /   3 = ongoing   /   4 = finished   /   5 = filed
    private float paymentAmount;
    private ArrayList<Annotation> anotations;
    private ArrayList<Licence> licences;
    private int count = 0;

    /**
     * Construtor da classe "Review" 
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public Review() {
        this.setState("Started");
        this.setPaid(false);

        Searches newSearch = new Searches();
        
        LocalDate dateNow = LocalDate.now();
        LocalTime timeNow = LocalTime.now();

        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HHmmss");

        String dateFormated = dateNow.format(formatterDate);
        String timeFormated = timeNow.format(formatterTime);

        int count = newSearch.searchLastCount() + 1;

        this.setCount(count);

        String serialNumber = Integer.toString(count);
        serialNumber = serialNumber.concat(dateFormated);
        serialNumber = serialNumber.concat(timeFormated);

        this.setSerialNumber(Long.parseLong(serialNumber));
        
        this.setRealizationDate(LocalDate.now());
        LocalDate now = LocalDate.of(1970, 1, 1);
        this.setElapsedTime(now);
        this.setManager(null);
        this.setHeadReviewer(null);
    }

    public void setBook(Book aBook) {
        this.book = aBook;
    }

    public Book getBook() {
        return this.book;
    }

    public void setAuthor(Author aAuthor) {
        this.author = aAuthor;
    }

    public Author getAuthor() {
        return this.author;
    }

    public void setHeadReviewer(String aHeadReviewer) {
        this.headReviewer = aHeadReviewer;
    }

    public String getHeadReviewer() {
        return this.headReviewer;
    }

    public void setReviewers(ArrayList<Reviewer> aReviewers) {
        this.reviewers = aReviewers;
    }

    public ArrayList<Reviewer> getReviewers() {
        return this.reviewers;
    }

    public void setManager(Manager aManager) {
        this.manager = aManager;
    }

    public Manager getManager() {
        return this.manager;
    }

    public void setSerialNumber(long aSerialNumber) {
        this.serialNumber = aSerialNumber;
    }

    public long getSerialNumber() {
        return this.serialNumber;
    }

    public void setRealizationDate(LocalDate aRealizationDate) {
        this.realizationDate = aRealizationDate;
    }

    public LocalDate getRealizationDate() {
        return this.realizationDate;
    }

    public void setElapsedTime(LocalDate aElapsedTime) {
        this.elapsedTime = aElapsedTime;
    }

    public LocalDate getElapsedTime() {
        return this.elapsedTime;
    }

    public void setPaid(boolean aPaid) {
        this.paid = aPaid;
    }

    public boolean getPaid() {
        return this.paid;
    }

    public void setState(String aState) {
        this.state = aState;
    }

    public String getState() {
        return this.state;
    }

    public void setPaymentAmount(float aPaymentAmount) {
        this.paymentAmount = aPaymentAmount;
    }

    public float getPaymentAmount() {
        return this.paymentAmount;
    }

    public void setAnotations(ArrayList<Annotation> aAnotations) {
        this.anotations = aAnotations;
    }

    public ArrayList<Annotation> getAnotations() {
        return this.anotations;
    }

    public void setLicences(ArrayList<Licence> aLicences) {
        this.licences = aLicences;
    }

    public ArrayList<Licence> getLicences() {
        return this.licences;
    }

    public void setCount(int aCount) {
        this.count = aCount;
    }

    public int getCount() {
        return this.count;
    }

    @Override
    public int compareTo(Review aReview) {
        return this.getBook().getTitle().compareTo(aReview.getBook().getTitle());
    }

    @Override
    public String getSearchableAttribute() {
        return book.getTitle();
    }
}
