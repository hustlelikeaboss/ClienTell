package design.hustlelikeaboss.customr.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by quanjin on 6/23/17.
 */
@Controller
@RequestMapping("to-do")
public class TodoController {

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("title", "To-dos");

        return "to-do/index";
    }
}
