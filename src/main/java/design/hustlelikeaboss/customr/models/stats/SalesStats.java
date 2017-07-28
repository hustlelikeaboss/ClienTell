package design.hustlelikeaboss.customr.models.stats;

/**
 * Created by quanjin on 7/26/17.
 */
public class SalesStats {

    private Integer projectTypeId;
    private Float salesTotal;

    public SalesStats(Integer projectTypeId, Float salesTotal) {
        this.projectTypeId = projectTypeId;
        this.salesTotal = salesTotal;
    }

    public Integer getProjectTypeId() {
        return projectTypeId;
    }
    public void setProjectTypeId(Integer projectTypeId) {
        this.projectTypeId = projectTypeId;
    }
    public Float getSalesTotal() {
        return salesTotal;
    }
    public void setSalesTotal(Float salesTotal) {
        this.salesTotal = salesTotal;
    }
}
