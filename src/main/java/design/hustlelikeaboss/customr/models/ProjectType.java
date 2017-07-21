package design.hustlelikeaboss.customr.models;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quanjin on 6/23/17.
 */
@Entity
public class ProjectType {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @OneToMany
    private List<Project> projects = new ArrayList<>();

    // constructor
    public ProjectType(String name) {
        this.name = name;
    }

    public ProjectType() {
    }

    // setters & getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
