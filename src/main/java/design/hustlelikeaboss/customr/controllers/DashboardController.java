package design.hustlelikeaboss.customr.controllers;

import design.hustlelikeaboss.customr.models.ProjectStatus;
import design.hustlelikeaboss.customr.models.ProjectType;
import design.hustlelikeaboss.customr.models.StatsMapping;
import design.hustlelikeaboss.customr.models.data.ProjectDao;
import design.hustlelikeaboss.customr.models.data.ProjectStatusDao;
import design.hustlelikeaboss.customr.models.data.ProjectTypeDao;
import design.hustlelikeaboss.customr.models.data.UserService;
import design.hustlelikeaboss.customr.models.stats.ProjectStats;
import design.hustlelikeaboss.customr.models.stats.SalesStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private ProjectTypeDao projectTypeDao;

    @Autowired
    UserService userService;

    @Autowired
    StatsMapping statsMapping;

    private final static Float ZERO = new Float(0.0);

    @RequestMapping(value="")
    public String dashboard(Model model) {



        int userId = userService.retrieveUser().getId();

        // TODO #1: display project stats by month & status
        // get projectStatusSeries & projectStatusLabels in a list
        List<List> projectStats = getProjectStats(userId);

        // TODO #2: display sales stats by month & project type
        // 1. add sales & updated fields to the Project class
        // 2. update project form
        // 3. write query
        // 4. query result mapping

        // run the query for each month --> a list of SalesStats for each project type
        // for each month, add a SalesStats to its respective list (project type) for the year
        // after each list of project type sales stats is completed, add it to the salesSeries master list

        List<List> salesSeries = getSalesStats(userId);




        // TODO #3: display customer stats by month & status
        // 1. add status & updated fields to the Customer class
        // 2. automatically update customer to lead when first added; update edit form
        // 3. write query
        // 4. query result mapping



        ////////////////////////////////////////////////////////////////////////////////////////

        model.addAttribute("title", "Dashboard");
        model.addAttribute("projectStatusSeries", projectStats.get(0));
        model.addAttribute("projectStatusLabels", projectStats.get(1));
        model.addAttribute("salesSeries", salesSeries);

        return "dashboard";
    }
///////////////////////////////////////////////////////////////////
// get stats by project status
///////////////////////////////////////////////////////////////////
//
    public List<List> getProjectStats(int userId) {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate startOfNextMonth = LocalDate.of(year, month + 1, 1);

        List<ProjectStats> projectStatsInPercentage = statsMapping.getPercentageByUserAndMonth(userId, startOfMonth, startOfNextMonth);

        List<Integer> projectStatusSeries = new ArrayList<>();
        List<String> projectStatusLabels = new ArrayList<>();
        Iterable<ProjectStatus> projectStatuses = projectStatusDao.findAll();

        for (ProjectStatus p : projectStatuses) {
            int roundedPercentage = getPercentageByStatus(p, projectStatsInPercentage);

            projectStatusLabels.add(Integer.toString(roundedPercentage) + "%");
            projectStatusSeries.add(roundedPercentage);
        }

        List<List> projectStats = new ArrayList<>();
        projectStats.add(projectStatusSeries);
        projectStats.add(projectStatusLabels);

        return projectStats;
    }

//
// helper method: get percentage by project status
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



///////////////////////////////////////////////////////////////////
// get sales stats
///////////////////////////////////////////////////////////////////
//
// process query results:
//
    public List<List> getSalesStats(int userId) {

        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int months = currentDate.getMonthValue();


        // master list A: list of lists of monthly sales stats
        List<List> yearlySalesStats = new ArrayList<>();

        // master list B: list of lists of monthly sales (Float) by project type
        List<List> salesSeries = new ArrayList<>();

        for (int m = 1; m <= months; m++) {
            LocalDate startOfMonth = LocalDate.of(year, m, 1);
            LocalDate startOfNextMonth = LocalDate.of(year, m + 1, 1);

            List<SalesStats> monthlySalesStats = statsMapping.getSalesStatsByUserAndMonth(userId, startOfMonth, startOfNextMonth);
            yearlySalesStats.add(monthlySalesStats);
        }

        // convert A into B:
        int numberOfProjectTypes = 0;
        for (ProjectType p : projectTypeDao.findAll()) {numberOfProjectTypes += 1;}

        for (int projectType = 0; projectType < numberOfProjectTypes; projectType++) {

            List<Float> monthlySalesStatsByProjectType = getMonthlySalesStatsByProjectType(projectType, months, yearlySalesStats);
            salesSeries.add(monthlySalesStatsByProjectType);
        }

        return salesSeries;
    }

//
// helper method: get monthly sales stats by project type
//
    public List<Float> getMonthlySalesStatsByProjectType (int projectType, int months, List<List> yearlySalesStats) {
        List<Float> monthlySalesStatsByProjectType = new ArrayList<>();

        for (int month = 0; month < months; month++) {
            List<SalesStats> salesStatsByMonth = yearlySalesStats.get(month);

            if (! salesStatsByMonth.isEmpty() ) {
                monthlySalesStatsByProjectType.add( salesStatsByMonth.get(projectType).getSalesTotal() );
            } else {
                monthlySalesStatsByProjectType.add(ZERO);
            }
        }

        return monthlySalesStatsByProjectType;
    }




}
