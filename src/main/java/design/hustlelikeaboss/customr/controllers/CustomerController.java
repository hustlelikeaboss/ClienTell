package design.hustlelikeaboss.customr.controllers;

import design.hustlelikeaboss.customr.utils.ProfileEnricher;
import design.hustlelikeaboss.customr.models.Customer;
import design.hustlelikeaboss.customr.models.User;
import design.hustlelikeaboss.customr.models.data.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.time.LocalDate;

/**
 * Created by quanjin on 6/21/17.
 */

@Controller
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CustomerStatusDao customerStatusDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileEnricher profileEnricher;

//
// display all existing customers
//
    @RequestMapping(value = "")
    public String index(Model model) {

        User user = userService.retrieveUser();

        model.addAttribute("title", "Customers");
        model.addAttribute("customers", user.getCustomers());

        return "customer/index";
    }
//
// add new customers
//
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("title", "Add New Customers");
        model.addAttribute(new Customer());

        return "customer/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Customer customer, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add New Customers");
            return "customer/add";
        }

        User user = userService.retrieveUser();
        LocalDate currentDate = LocalDate.now();

        customer.setUser(user);
        customer.setCreated(currentDate);
        customer.setUpdated(currentDate);
        customer.setCustomerStatus(customerStatusDao.findOne(1));
        customerDao.save(customer);

        return "redirect:edit/" + customer.getId();
    }

//
// edit customer profile
//
    @RequestMapping(value = "edit/{customerId}", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable("customerId") int customerId) throws UnsupportedEncodingException, MalformedURLException {
        Customer customer = customerDao.findOne(customerId);

        if (StringUtils.isEmpty(customer.getFacebook())) {
            profileEnricher.fetchFacebookPage(customer);
        }
        profileEnricher.parseFacebookPage(customer);

        model.addAttribute("title", "Customer Profile");
        model.addAttribute("customer", customerDao.findOne(customerId));
        model.addAttribute("projects", projectDao.findByCustomerId(customerId));
        model.addAttribute("customerStatuses", customerStatusDao.findAll());

        return "customer/edit";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String edit(Model model, @ModelAttribute @Valid Customer customer, Errors errors,
                       @RequestParam("customerId") int customerId){

        if (errors.hasErrors()) {
            model.addAttribute("title", "Customer Profile");
            return "customer/edit";
        }

        Customer c = customerDao.findOne(customerId);
        LocalDate currentDate = LocalDate.now();

        c.setfName(customer.getfName());
        c.setlName(customer.getlName());
        c.setEmail(customer.getEmail());
        c.setPhoneNumber(customer.getPhoneNumber());
        c.setCompany(customer.getCompany());
        c.setWebsite(customer.getWebsite());
        c.setFacebook(customer.getFacebook());
        c.setStreet(customer.getStreet());
        c.setCity(customer.getCity());
        c.setState(customer.getState());
        c.setZip(customer.getZip());


        if (c.getCustomerStatus() == null ) {
            c.setCustomerStatus(customer.getCustomerStatus());
            c.setUpdated(currentDate);
        } else if ( ! c.getCustomerStatus().equals(customer.getCustomerStatus())){
            c.setCustomerStatus(customer.getCustomerStatus());
            c.setUpdated(currentDate);
        }

        customerDao.save(c);

        String message = " ";

        return "redirect:edit/" + customerId + "?message=" + message;
    }
}
