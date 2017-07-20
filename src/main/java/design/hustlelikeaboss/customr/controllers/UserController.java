package design.hustlelikeaboss.customr.controllers;

import design.hustlelikeaboss.customr.models.User;
import design.hustlelikeaboss.customr.models.data.UserDao;
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
        return "redirect:/login";
    }

// TODO #1: user login
    @RequestMapping(value="login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("title", "Login");
        model.addAttribute(new User());

        return "user/login";
    }


// TODO #2: user logout
// user logout


// edit user profile
@RequestMapping(value="edit-profile/{userId}", method = RequestMethod.GET)
public String login(Model model, @PathVariable("userId") int userId) {
    model.addAttribute("title", "User Profile");
    model.addAttribute("user", userDao.findOne(userId));

    return "user/edit-profile";
}

    @RequestMapping(value="edit-profile", method = RequestMethod.POST)
    public String login(Model model, @ModelAttribute @Valid User user, Errors errors, @RequestParam int userId) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "User Profile");
            return "user/edit-profile";
        }

        User usr = userDao.findOne(userId);
        usr.setfName(user.getfName());
        usr.setlName(user.getlName());
        usr.setPhoneNumber(user.getPhoneNumber());
        usr.setCompany(user.getCompany());
        usr.setWebsite(user.getWebsite());
        userDao.save(usr);

        String message = " ";
        return "redirect:edit-profile/"+ userId + "?message=" + message;
    }

}
