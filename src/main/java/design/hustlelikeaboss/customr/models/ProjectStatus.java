package design.hustlelikeaboss.customr.models;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quanjin on 6/23/17.
 */
@Entity
public class ProjectStatus {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @OneToMany
    private List<Project> projects = new ArrayList<>();

    public ProjectStatus(String name) {
        this.name = name;
    }

    public ProjectStatus() {
    }

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
