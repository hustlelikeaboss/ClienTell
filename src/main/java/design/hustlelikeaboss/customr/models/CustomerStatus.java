package design.hustlelikeaboss.customr.models;

import design.hustlelikeaboss.customr.models.stats.CustomerStats;

import javax.persistence.*;
import java.util.List;

/**
 * Created by quanjin on 7/26/17.
 */
@SqlResultSetMapping(
        name = "CustomerStatsMapping",
        classes = @ConstructorResult(
                targetClass = CustomerStats.class,
                columns = {
                        @ColumnResult(name="customerStatusId", type=Integer.class),
                        @ColumnResult(name="customerCounts", type=Integer.class)
                }
        )
)
@Entity
public class CustomerStatus {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @OneToMany
    private List<Customer> customers;

    public CustomerStatus(String name) {
        this.name = name;
    }

    public CustomerStatus() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
