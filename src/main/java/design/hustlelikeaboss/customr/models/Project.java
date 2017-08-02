package design.hustlelikeaboss.customr.models;
import design.hustlelikeaboss.customr.models.stats.ProjectStats;
import design.hustlelikeaboss.customr.models.stats.SalesStats;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by quanjin on 6/20/17.
 */

@SqlResultSetMappings({
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
    ),
    // mapping query results to class SalesStats
    @SqlResultSetMapping(
        name = "SalesMapping",
        classes = @ConstructorResult(
                targetClass = SalesStats.class,
                columns = {
                        @ColumnResult(name="projectTypeId"),
                        @ColumnResult(name="salesTotal", type=Float.class)
                }
        )
    )
})
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

    private Float sales;

    private LocalDate created, updated;

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

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public Float getSales() {
        return sales;
    }

    public void setSales(Float sales) {
        this.sales = sales;
    }
}
