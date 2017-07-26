package design.hustlelikeaboss.customr.controllers;

import design.hustlelikeaboss.customr.models.ProjectStats;
import design.hustlelikeaboss.customr.models.ProjectStatus;
import design.hustlelikeaboss.customr.models.UseProjectStatsMapping;
import design.hustlelikeaboss.customr.models.data.ProjectDao;
import design.hustlelikeaboss.customr.models.data.ProjectStatusDao;
import design.hustlelikeaboss.customr.models.data.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by quanjin on 6/20/17.
 */
@Controller
@RequestMapping("dashboard")
public class DashboardController {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectStatusDao projectStatusDao;

    @Autowired
    UserService userService;

    @Autowired
    UseProjectStatsMapping useProjectStatsMapping;

    @RequestMapping(value="")
    public String dashboard(Model model) {

//        LocalDate currentDate = LocalDate.now();
//        LocalDate startOfMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 0);

        int userId = userService.retrieveUser().getId();
        List<ProjectStats> projectStatsInPercentage = useProjectStatsMapping.getPercentageByUserStartOfMonth(userId);

        List<Integer> series = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        Iterable<ProjectStatus> projectStatuses = projectStatusDao.findAll();

        for (ProjectStatus p : projectStatuses) {
            int roundedPercentage = getPercentageByStatus(p, projectStatsInPercentage);

            labels.add(Integer.toString(roundedPercentage) + "%");
            series.add(roundedPercentage);
        }

        model.addAttribute("title", "Dashboard");
        model.addAttribute("series", series);
        model.addAttribute("labels", labels);

        return "dashboard";
    }


//
// helper method:
//
    private int getPercentageByStatus(ProjectStatus status, List<ProjectStats> statsList) {

        int roundedPercentage = 0;

        for (ProjectStats s : statsList) {
            if (status.getId() == s.getProjectStatusId()) {
                roundedPercentage = Math.round(s.getProjectStatusPercentage());
            }
        }

        return roundedPercentage;
    }




}
