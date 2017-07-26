package design.hustlelikeaboss.customr.models;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;

/**
 * Created by quanjin on 6/20/17.
 */

// mapping query results to class ProjectStats
@SqlResultSetMapping(
        name = "ProjectStatsMapping",
        classes = @ConstructorResult(
                targetClass = ProjectStats.class,
                columns = {
                        @ColumnResult(name="projectStatusId"),
                        @ColumnResult(name="projectStatusPercentage", type=Float.class)
                }
        )
)
@Entity
public class Project {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="project_type_id")
    private ProjectType projectType;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="project_status_id")
    private ProjectStatus projectStatus;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="customer_id")
    private Customer customer;

    private Date lastUpdated;

    // constructors
    public Project(String name, ProjectType projectType, ProjectStatus projectStatus, Customer customer) {
        this.name = name;
        this.projectType = projectType;
        this.projectStatus = projectStatus;
        this.customer = customer;
    }

    public Project() {
    }

    // getters & setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
