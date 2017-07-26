package design.hustlelikeaboss.customr.models;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by quanjin on 7/26/17.
 */
public class ProjectStats {

    @Id
    @GeneratedValue
    private int id;

    private Integer projectStatusId;

    private Float projectStatusPercentage;

    public ProjectStats(Integer projectStatusId, Float projectStatusPercentage) {
        this.projectStatusId = projectStatusId;
        this.projectStatusPercentage = projectStatusPercentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
