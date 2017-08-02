package design.hustlelikeaboss.customr.models;

import design.hustlelikeaboss.customr.models.stats.CustomerStats;
import design.hustlelikeaboss.customr.models.stats.ProjectStats;
import design.hustlelikeaboss.customr.models.stats.SalesStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by quanjin on 7/26/17.
 */
@Component
public class StatsMapping {

    @Autowired
    private EntityManager entityManager;

    public List<ProjectStats> getPercentageByUserAndMonth(int userId, LocalDate startOfMonth, LocalDate startOfNextMonth) {
        Query q = entityManager.createNativeQuery("SELECT project_status.id as projectStatusId, \n" +
                "(count(project.id) / (select count(*) from project join customer on customer_id = customer.id " +
                "WHERE customer.user_id = ?1 AND project.updated >= ?2 AND project.updated < ?3)) * 100 " +
                "as projectStatusPercentage \n" +
                "from project_status \n" +
                "left join project on project_status_id = project_status.id\n" +
                "join customer on customer_id = customer.id WHERE customer.user_id = ?1\n" +
                "group by project_status.id", "ProjectStatsMapping");
        q.setParameter(1, userId);
        q.setParameter(2, startOfMonth.format(DateTimeFormatter.BASIC_ISO_DATE));
        q.setParameter(3, startOfNextMonth.format(DateTimeFormatter.BASIC_ISO_DATE));
        List<ProjectStats> projectStatsInPercentage = q.getResultList();

        return projectStatsInPercentage;
    }

    public List<SalesStats> getSalesStatsByUserAndMonth(int userId, LocalDate startOfMonth, LocalDate startOfNextMonth) {
        Query q = entityManager.createNativeQuery("SELECT project_type.id as projectTypeId, \n" +
                "SUM(project.sales) as salesTotal \n" +
                "from project_type \n" +
                "left join project on project_type_id = project_type.id\n" +
                "join customer on customer_id = customer.id \n" +
                "WHERE project.created >= ?1 AND project.created < ?2 AND customer.user_id = ?3\n" +
                "group by project_type.id", "SalesMapping");
        q.setParameter(1, startOfMonth.format(DateTimeFormatter.BASIC_ISO_DATE));
        q.setParameter(2, startOfNextMonth.format(DateTimeFormatter.BASIC_ISO_DATE));
        q.setParameter(3, userId);
        List<SalesStats> monthlySalesStats = q.getResultList();

        return monthlySalesStats;
    }

    public List<CustomerStats> getCustomerStatsByUserAndMonth(int userId, LocalDate startOfMonth, LocalDate startOfNextMonth) {
        Query q = entityManager.createNativeQuery("SELECT customer_status.id as customerStatusId,\n" +
                "COUNT(customer.id) as customerCounts\n" +
                "FROM customer_status\n" +
                "LEFT JOIN customer on customer.customer_status_id = customer_status.id\n" +
                "WHERE customer.user_id = ?1 AND customer.updated >= ?2 AND customer.updated < ?3 \n" +
                "GROUP by customer_status.id", "CustomerStatsMapping");
        q.setParameter(1, userId);
        q.setParameter(2, startOfMonth.format(DateTimeFormatter.BASIC_ISO_DATE));
        q.setParameter(3, startOfNextMonth.format(DateTimeFormatter.BASIC_ISO_DATE));
        List<CustomerStats> monthlyCustomerStats = q.getResultList();

        return monthlyCustomerStats;
    }




}
