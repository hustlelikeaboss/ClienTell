package design.hustlelikeaboss.customr.models.data;

import design.hustlelikeaboss.customr.models.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
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

    @Query("select sum(p.sales) from Project p where p.customer.user.id = ?1 and p.created >= ?2")
    Float fetchYearlyRevenue(int userId, LocalDate startOfYear);

}
