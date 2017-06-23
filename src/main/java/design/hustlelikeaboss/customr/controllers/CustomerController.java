package design.hustlelikeaboss.customr.controllers;

import design.hustlelikeaboss.customr.models.Customer;
import design.hustlelikeaboss.customr.models.data.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

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
            model.addAttribute("title", "Add New Customers");
            return "customer/add";
        }

        customerDao.save(customer);
        return "redirect:";
    }

// edit customer profile
    @RequestMapping(value="edit/{customerId}", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable("customerId") int customerId) {
        model.addAttribute("title", "Customer Profile");
        model.addAttribute("customer", customerDao.findOne(customerId));

        return "customer/edit";
    }

    @RequestMapping(value="edit", method = RequestMethod.POST)
    public String edit(Model model, @ModelAttribute @Valid Customer customer, Errors errors, @RequestParam("customerId") int customerId) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Customer Profile");
            return "customer/edit";
        }

        Customer c = customerDao.findOne(customerId);

        c.setfName(customer.getfName());
        c.setlName(customer.getlName());
        c.setEmail(customer.getEmail());
        c.setPhoneNumber(customer.getPhoneNumber());
        c.setCompany(customer.getCompany());
        c.setWebsite(customer.getWebsite());
        c.setStreetAddress(customer.getStreetAddress());
        c.setCity(customer.getCity());
        c.setState(customer.getState());
        c.setZipCode(customer.getZipCode());

        customerDao.save(c);

        String message = " ";

        return "redirect:edit/"+ customerId + "?message=" + message;
    }





}
