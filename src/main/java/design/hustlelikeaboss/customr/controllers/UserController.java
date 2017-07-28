package design.hustlelikeaboss.customr.controllers;

import design.hustlelikeaboss.customr.models.User;
import design.hustlelikeaboss.customr.models.data.UserDao;
import design.hustlelikeaboss.customr.models.data.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by quanjin on 6/20/17.
 */
@Controller
@RequestMapping("")
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

// register user
    @RequestMapping(value="register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute(new User());

        return "user/register";
    }

    @RequestMapping(value="register", method = RequestMethod.POST)
    public String register(Model model, @ModelAttribute @Valid User user, Errors errors,
                           @RequestParam("verify") String verify) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Register");
            model.addAttribute("user", user);
            model.addAttribute("errors", errors);
            return "user/register";
        }

        if (userService.findUserByEmail(user.getEmail()) != null) {
            model.addAttribute("title", "Register");
            model.addAttribute("user", user);
            model.addAttribute("isDuplicate", true);
            return "user/register";
        }

        if ( ! verify.equals(user.getPassword()) ) {
            model.addAttribute("title", "Register");
            model.addAttribute("user", user);
            model.addAttribute("isWrongPassword", true);
            return "user/register";
        }

        userService.saveUser(user);
        return "redirect:/login";
    }

// edit user profile
    @RequestMapping(value="user/edit", method = RequestMethod.GET)
    public String login(Model model) {
        User user = userService.retrieveUser();

        model.addAttribute("title", "User Profile");
        model.addAttribute("user", user);

        return "user/edit";
    }

    @RequestMapping(value="user/edit", method = RequestMethod.POST)
    public String edit(Model model, @ModelAttribute @Valid User user, Errors errors, @RequestParam("id") int id) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "User Profile");
//            model.addAttribute("errors", errors);
            return "user/edit";
        }

        User usr = userDao.findOne(id);
        usr.setFirstName(user.getFirstName());
        usr.setLastName(user.getLastName());
        usr.setPhoneNumber(user.getPhoneNumber());
        usr.setCompany(user.getCompany());
        usr.setWebsite(user.getWebsite());
        userDao.save(usr);

        String message = " ";
        return "redirect:/user/edit/" + "?message=" + message;
    }

}
