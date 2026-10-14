/**
 * Classe para gerir notificacao
 * @author Rodrigo Pereira
 */
public class Notification implements Comparable<Notification>, SearchTerm{
    private int id;
    private String title;
    private String content;
    private String type;  // Information  /  Choice
    private boolean viewed;

    public Notification() {}
    
    public Notification(String aTitle, String aContent, String aType) {
        this.title = aTitle;
        this.content = aContent;
        this.type = aType;
        this.viewed = false;
    }

    public void setId(int aId) {
        this.id = aId;
    }

    public int getId() {
        return this.id;
    }

    public void setTitle(String aTitle) {
        this.title = aTitle;
    }

    public String getTitle() {
        return this.title;
    }

    public void setContent(String aContent) {
        this.content = aContent;
    }

    public String getContent() {
        return this.content;
    }

    public void setType(String aType) {
        this.type = aType;
    }

    public String getType() {
        return this.type;
    }

    public void setViewed(Boolean aViewed) {
        this.viewed = aViewed;
    }

    public boolean getViewed() {
        return this.viewed;
    }

    /**
     * Metodo para criar uma notificacao predefinida sobre uma proposta para ser o revisor chefe
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public Notification choiceProposalHeadReviewerN(Long aSerialNumber) {
        return new Notification("Head reviewer proposal", "A manager proposed you to be the head reviewer of the review with the code " + aSerialNumber + ".", "Choice");
    }

    /**
     * Metodo para criar uma notificacao predefinida sobre um pedido de omissao de conta
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public Notification choiceProposalOmitAccountDataN(User aUser) {
        return new Notification("Account data omission proposal", "The user with the login \"" + aUser.getLogin() + "\" is asking for their account data to be omitted from the system. This means the person no longer wants to use the account, acting as a \"soft deletion\".", "Choice");
    } 

    /**
     * Metodo para criar uma notificacao predefinida sobre um novo registo na aplicacao
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public Notification informationNewUserN(User aUser) {
        return new Notification("New user registered", "A new user with the login " + aUser.getLogin() + " has recently registered in the platform.", "Information");
    }

    /**
     * Metodo para criar uma notificacao predefinida sobre a criacao de uma nova revisao
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public Notification informationNewReviewN(User aUser, Review aReview) {
        return new Notification("New review created", "A new review with the code " + aReview.getSerialNumber() + " made by the author " + aUser.getLogin() + " has recently been created.", "Information");
    }

    /**
     * Metodo para criar uma notificacao predefinida sobre a reprovacao de uma revisao
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public Notification informationReviewReprovedN(Review aReview) {
        return new Notification("Review reproved", "Your review with the code " + aReview.getSerialNumber() + " was reproved by a manager.", "Information");
    }

    /**
     * Metodo para criar uma notificacao predefinida sobre a rejeicao de um pedido de se tornar revisor chefe de uma revisao
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public Notification informationReviewerRejectedProposalN(User aUser, Review aReview) {
        return new Notification("Proposal rejected", "The reviewer with the login " + aUser.getLogin() + " rejected the proposal to become the head reviewer of the review with the code " + aReview.getSerialNumber() + ". This reviewer will no longer be listed for this particular review going forward.", "Information");
    }

    /**
     * Metodo para criar uma notificacao predefinida sobre o pagamento de uma revisao
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public Notification informationReviewConcludedPaidN(User aUser, Review aReview) {
        return new Notification("Review paid", "The author with the login " + aUser.getLogin() + " paid the review, thus being concluded.", "Information");
    }

    /**
     * Metodo para criar uma notificacao predefinida sobre a rejeicao do pedido de omissao de conta
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public Notification informationAccountDataOmitProposalRejectedN() {
        return new Notification("Proposal rejected", "A manager rejected your proposal to omit your account data. No actions were conducted.", "Information");
    }

    @Override
    public int compareTo(Notification aNotification) {
        return Integer.compare(this.getId(), aNotification.getId());
    }

    @Override
    public String getSearchableAttribute() {
        return String.valueOf(id);
    }
}
