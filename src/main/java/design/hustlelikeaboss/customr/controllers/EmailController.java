package design.hustlelikeaboss.customr.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by quanjin on 6/23/17.
 */
@Controller
@RequestMapping("email")
public class EmailController {

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("title", "Emails");

        return "email/index";
    }
}
