package design.hustlelikeaboss.customr.controllers;

import design.hustlelikeaboss.customr.models.Project;
import design.hustlelikeaboss.customr.models.data.CustomerDao;
import design.hustlelikeaboss.customr.models.data.ProjectDao;
import design.hustlelikeaboss.customr.models.data.ProjectStatusDao;
import design.hustlelikeaboss.customr.models.data.ProjectTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

/**
 * Created by quanjin on 6/23/17.
 */
@Controller
@RequestMapping("project")
public class ProjectController {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ProjectTypeDao projectTypeDao;

    @Autowired
    private ProjectStatusDao projectStatusDao;


// display all existing projects

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("title", "Projects");
        model.addAttribute("projects", projectDao.findAll());

        return "project/index";
    }


// add project

    @GetMapping(value="add")
    public String add(Model model) {
        model.addAttribute("title", "Add Projects");
        model.addAttribute(new Project());
        model.addAttribute("projectStatuses", projectStatusDao.findAll());
        model.addAttribute("projectTypes", projectTypeDao.findAll());
        model.addAttribute("customers", customerDao.findAll());

        return "project/add";
    }

    @PostMapping(value="add")
    public String add(Model model, @ModelAttribute Project project, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Projects");
            return "project/add";
        }

        //projectTypeDao.save(project.getProjectType());
        //projectStatusDao.save(project.getProjectStatus());
        //customerDao.save(project.getCustomer());

        projectDao.save(project);

        return "redirect:";
    }


// edit project

    @GetMapping("edit/{projectId}")
    public String edit(Model model, @PathVariable("projectId") int id) {
        model.addAttribute("title", "Edit Project");
        model.addAttribute("project", projectDao.findOne(id));
        model.addAttribute("projectStatuses", projectStatusDao.findAll());
        model.addAttribute("projectTypes", projectTypeDao.findAll());
        model.addAttribute("customers", customerDao.findAll());

        return "project/edit";
    }

    @PostMapping("edit")
    public String edit(Model model, @ModelAttribute Project project, Errors errors,
                       @RequestParam("projectId") int projectId) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit Project");
            model.addAttribute("projectStatuses", projectStatusDao.findAll());
            model.addAttribute("projectTypes", projectTypeDao.findAll());
            return "project/edit";
        }

        Project p = projectDao.findOne(projectId);
        p.setName(project.getName());
        p.setProjectType(project.getProjectType());
        p.setProjectStatus(project.getProjectStatus());
        p.setCustomer(project.getCustomer());
        projectDao.save(p);

        return "redirect:";
    }

}
