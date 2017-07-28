package design.hustlelikeaboss.customr.models.stats;

/**
 * Created by quanjin on 7/26/17.
 */
public class ProjectStats {

    private Integer projectStatusId;
    private Float projectStatusPercentage;

    public ProjectStats(Integer projectStatusId, Float projectStatusPercentage) {
        this.projectStatusId = projectStatusId;
        this.projectStatusPercentage = projectStatusPercentage;
    }

    public Integer getProjectStatusId() {
        return projectStatusId;
    }
    public void setProjectStatusId(Integer projectStatusId) {
        this.projectStatusId = projectStatusId;
    }
    public Float getProjectStatusPercentage() {
        return projectStatusPercentage;
    }
    public void setProjectStatusPercentage(Float projectStatusPercentage) {
        this.projectStatusPercentage = projectStatusPercentage;
    }
}
