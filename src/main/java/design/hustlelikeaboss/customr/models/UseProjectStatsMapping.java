package design.hustlelikeaboss.customr.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by quanjin on 7/26/17.
 */
@Component
public class UseProjectStatsMapping{

    @Autowired
    private EntityManager entityManager;

    public List<ProjectStats> getPercentageByUserStartOfMonth (int userId) {

        Query q = entityManager.createNativeQuery("SELECT project_status.id as projectStatusId,\n" +
                "(count(project.id) / (select count(*) from project join customer on customer_id = customer.id\n" +
                "WHERE customer.user_id=?1)) * 100 as projectStatusPercentage from project_status \n" +
                "left join project on project_status_id = project_status.id\n" +
                "join customer on customer_id = customer.id\n" +
                "WHERE customer.user_id=?1\n" +
                "group by project_status.id", "ProjectStatsMapping");
        q.setParameter(1, userId);
        List<ProjectStats> projectStatsInPercentage = q.getResultList();

        return projectStatsInPercentage;
    }


}
