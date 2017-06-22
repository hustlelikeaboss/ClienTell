package design.hustlelikeaboss.customr.models.data;

import design.hustlelikeaboss.customr.models.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by quanjin on 6/21/17.
 */
@Repository
@Transactional
public interface CustomerDao extends CrudRepository<Customer, Integer> {
}
