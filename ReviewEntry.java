public class ReviewEntry extends Entry {

    private String opinionOnSuccess;
    private String additionalComments;

    public ReviewEntry(String userEmail, String reviewName, String opinionOnSuccess, String additionalComments) {
        super(userEmail, reviewName);
        this.opinionOnSuccess = opinionOnSuccess;
        this.additionalComments = additionalComments;
    }

    public void setOpinionOnSuccess(String opinionOnSuccess) {
        this.opinionOnSuccess = opinionOnSuccess;
    }

    public String getOpinionOnSuccess() {
        return opinionOnSuccess;
    }

    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }

    public String getAdditionalComments() {
        return additionalComments;
    }

}
