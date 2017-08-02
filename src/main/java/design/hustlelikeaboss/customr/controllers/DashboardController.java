package design.hustlelikeaboss.customr.controllers;

import design.hustlelikeaboss.customr.models.CustomerStatus;
import design.hustlelikeaboss.customr.models.ProjectStatus;
import design.hustlelikeaboss.customr.models.ProjectType;
import design.hustlelikeaboss.customr.models.StatsMapping;
import design.hustlelikeaboss.customr.models.data.*;
import design.hustlelikeaboss.customr.models.stats.CustomerStats;
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
    private CustomerStatusDao customerStatusDao;

    @Autowired
    private UserService userService;

    @Autowired
    private StatsMapping statsMapping;

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

        // run the query for each month --> a list of CustomerStats for each customer status
        // for each month, add a CustomerStats to its respective list (customer status) for the year
        // after each list of customer status stats is completed, add it to the salesSeries master list

        List<List> customerSeries = getCustomerStats(userId);

        // get yearly revenue up until today
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfYear = LocalDate.of(currentDate.getYear(), 01, 01);
        Float yearlyRevenue = projectDao.fetchYearlyRevenue(userId, startOfYear);


        ////////////////////////////////////////////////////////////////////////////////////////

        model.addAttribute("title", "Dashboard");

        model.addAttribute("yearlyRevenue", Math.round(yearlyRevenue));

        model.addAttribute("projectStatusSeries", projectStats.get(0));
        model.addAttribute("projectStatusLabels", projectStats.get(1));
        model.addAttribute("salesSeries", salesSeries);
        model.addAttribute("customerSeries", customerSeries);

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

            int projectStatusId = s.getProjectStatusId();

            if (status.getId() == projectStatusId) {


                if (s.getProjectStatusPercentage() != null) {
                    roundedPercentage = Math.round(s.getProjectStatusPercentage());
                } else {
                    roundedPercentage = 0;
                }

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


///////////////////////////////////////////////////////////////////
// get customer stats
///////////////////////////////////////////////////////////////////
//
// process query results:
//
    public List<List> getCustomerStats(int userId) {

        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int months = currentDate.getMonthValue();

        // master list A: list of lists of monthly customer stats by month
        List<List> yearlyCustomerStats = new ArrayList<>();

        // master list B: list of lists of monthly customer stats by customer stats
        List<List> customerSeries = new ArrayList<>();

        // count number of customer statuses
        int numberOfCustomerStatus = 0;
        for (CustomerStatus p : customerStatusDao.findAll()) {numberOfCustomerStatus += 1;}

        for (int m = 1; m <= months; m++) {
            LocalDate startOfMonth = LocalDate.of(year, m, 1);
            LocalDate startOfNextMonth = LocalDate.of(year, m + 1, 1);

            List<CustomerStats> monthlyCustomerStats = statsMapping.getCustomerStatsByUserAndMonth(userId, startOfMonth, startOfNextMonth);

            // check if the stats for a customerStatusId is missing (i.e.: no query result)
            for (int i = 1; i <= numberOfCustomerStatus; i++) {
                if ( ! containsCustomerStatus(i, monthlyCustomerStats) ) {
                    monthlyCustomerStats.add(i-1, new CustomerStats(i, 0));
                }
            }
            yearlyCustomerStats.add(monthlyCustomerStats);
        }

        // convert A into B:
        for (int customerStatus = 0; customerStatus < numberOfCustomerStatus; customerStatus++) {

            List<Integer> monthlyCustomerStatsByCustomerStatus = getMonthlyCustomerStatsByCustomerStatus(customerStatus, months, yearlyCustomerStats);
            customerSeries.add(monthlyCustomerStatsByCustomerStatus);
        }

        return customerSeries;
    }

//
// helper method #1: get monthly customer stats by customer status
//
    public List<Integer> getMonthlyCustomerStatsByCustomerStatus (int customerStatus, int months, List<List> yearlyCustomerStats) {
        List<Integer> monthlyCustomerStatsByCustomerStatus = new ArrayList<>();

        for (int month = 0; month < months; month++) {
            List<CustomerStats> customerStatsByMonth = yearlyCustomerStats.get(month);

            if (! customerStatsByMonth.isEmpty() ) {
                monthlyCustomerStatsByCustomerStatus.add( customerStatsByMonth.get(customerStatus).getCustomerCounts() );
            } else {
                monthlyCustomerStatsByCustomerStatus.add(0);
            }
        }

        return monthlyCustomerStatsByCustomerStatus;
    }

//
// helper method #2: check if a CustomerStats with a specific customerStatusId exists
//
    public Boolean containsCustomerStatus(int customerStatusId, List<CustomerStats> monthlyCustomerStats) {
        for (CustomerStats c : monthlyCustomerStats) {
            if (c.getCustomerStatusId().equals(customerStatusId)) {
                return true;
            }
        }
        return false;
    }


}
