public class ExperienceEntry extends Entry {

    private String dates;
    private String description;
    private String learningOutcomes;

    public ExperienceEntry(String userEmail, String experienceName, String dates, String description, String learningOutcomes) {
        super(userEmail, experienceName);
        this.dates = dates;
        this.description = description;
        this.learningOutcomes = learningOutcomes;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getDates() {
        return dates;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setLearningOutcomes(String learningOutcomes) {
        this.learningOutcomes = learningOutcomes;
    }

    public String getLearningOutcomes() {
        return learningOutcomes;
    }

}
