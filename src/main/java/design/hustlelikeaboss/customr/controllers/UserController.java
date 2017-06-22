package design.hustlelikeaboss.customr.controllers;

import design.hustlelikeaboss.customr.models.User;
import design.hustlelikeaboss.customr.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by quanjin on 6/20/17.
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserDao userDao;

// register user
    @RequestMapping(value="register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute(new User());

        return "user/register";
    }

    @RequestMapping(value="register", method = RequestMethod.POST)
    public String register(Model model, @ModelAttribute @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Register");
            return "user/register";
        }

        userDao.save(user);
        return "dashboard";
    }

// user login
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("title", "Login");
        model.addAttribute(new User());

        return "user/login";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String login(Model model, @ModelAttribute @Valid User user, Errors errors) {

        return "dashboard";
    }

// user logout

}
