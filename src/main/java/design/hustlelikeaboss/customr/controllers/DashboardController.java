package design.hustlelikeaboss.customr.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by quanjin on 6/20/17.
 */
@Controller
@RequestMapping("dashboard")
public class DashboardController {

    @RequestMapping(value="")
    public String dashboard() {
        return "dashboard";
    }

}
