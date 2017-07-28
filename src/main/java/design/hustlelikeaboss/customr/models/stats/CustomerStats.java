package design.hustlelikeaboss.customr.models.stats;

/**
 * Created by quanjin on 7/27/17.
 */
public class CustomerStats {

    private Integer customerStatusId, customerCounts;

    public CustomerStats(Integer customerStatusId, Integer customerCounts) {
        this.customerStatusId = customerStatusId;
        this.customerCounts = customerCounts;
    }

    public Integer getCustomerStatusId() {
        return customerStatusId;
    }

    public void setCustomerStatusId(Integer customerStatusId) {
        this.customerStatusId = customerStatusId;
    }

    public Integer getCustomerCounts() {
        return customerCounts;
    }

    public void setCustomerCounts(Integer customerCounts) {
        this.customerCounts = customerCounts;
    }
}
