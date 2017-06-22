package design.hustlelikeaboss.customr.controllers;

import design.hustlelikeaboss.customr.models.Customer;
import design.hustlelikeaboss.customr.models.data.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by quanjin on 6/21/17.
 */

@Controller
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerDao customerDao;

    // display all existing customers
    @RequestMapping(value="")
    public String index(Model model) {
        model.addAttribute("title", "Customers");
        model.addAttribute("customers", customerDao.findAll());

        return "customer/index";
    }

    // add new customers
    @RequestMapping(value="add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("title", "Add New Customers");
        model.addAttribute(new Customer());

        return "customer/add";
    }

    @RequestMapping(value="add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Customer customer, Errors errors) {
        if (errors.hasErrors()) {
            return "customer/add";
        }

        customerDao.save(customer);
        return "redirect:";
    }

}
