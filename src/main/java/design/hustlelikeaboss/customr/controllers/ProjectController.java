package design.hustlelikeaboss.customr.controllers;

import design.hustlelikeaboss.customr.models.Customer;
import design.hustlelikeaboss.customr.models.Project;
import design.hustlelikeaboss.customr.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;

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

    // retrieve customers under user
    @Autowired
    private UserService userService;

// display all existing projects

    @RequestMapping(value = "")
    public String index(Model model) {
        int id = userService.retrieveUser().getId();

        model.addAttribute("title", "Projects");
        model.addAttribute("projects", projectDao.findByUserId(id));

        return "project/index";
    }

// add project
    @GetMapping(value="add")
    public String add(Model model) {
        Set<Customer> customers = userService.retrieveUser().getCustomers();

        model.addAttribute("title", "Add Projects");
        model.addAttribute(new Project());
        model.addAttribute("projectStatuses", projectStatusDao.findAll());
        model.addAttribute("projectTypes", projectTypeDao.findAll());
        model.addAttribute("customers", customers);

        return "project/add";
    }

    @PostMapping(value="add")
    public String add(Model model, @ModelAttribute Project project, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Projects");
            return "project/add";
        }

        LocalDate currentDate = LocalDate.now();

        project.setCreated(currentDate);
        project.setUpdated(currentDate);
        projectDao.save(project);

        return "redirect:";
    }


// edit project

    @GetMapping("edit/{projectId}")
    public String edit(Model model, @PathVariable("projectId") int id) {
        Set<Customer> customers = userService.retrieveUser().getCustomers();

        model.addAttribute("title", "Edit Project");
        model.addAttribute("project", projectDao.findOne(id));
        model.addAttribute("projectStatuses", projectStatusDao.findAll());
        model.addAttribute("projectTypes", projectTypeDao.findAll());
        model.addAttribute("customers", customers);

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

        if (!project.getProjectStatus().equals(p.getProjectStatus())) {
            p.setProjectStatus(project.getProjectStatus());
            p.setUpdated(LocalDate.now());
        }
        p.setProjectType(project.getProjectType());
        p.setCustomer(project.getCustomer());
        p.setSales(project.getSales());

        projectDao.save(p);

        return "redirect:";
    }

}
