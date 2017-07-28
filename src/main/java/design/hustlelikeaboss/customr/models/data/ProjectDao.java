package design.hustlelikeaboss.customr.models.data;

import design.hustlelikeaboss.customr.models.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by quanjin on 6/23/17.
 */

@Repository
@Transactional
public interface ProjectDao extends CrudRepository<Project, Integer>{

    List<Project> findByCustomerId(int customerId);

    @Query("from Project p where p.customer.user.id = ?1")
    List<Project> findByUserId(int id);

////  fetch a list of current projects by month
//    @Query("select count(p) from Project p where p.lastUpdated > ?1 and p.projectStatus = ?2")
//    int fetchCurrentProjectsWithStatus(LocalDate startOfMonth, ProjectStatus status);

}
