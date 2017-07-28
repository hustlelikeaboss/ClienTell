package design.hustlelikeaboss.customr.models.data;

import design.hustlelikeaboss.customr.models.CustomerStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by quanjin on 7/28/17.
 */
@Repository
@Transactional
public interface CustomerStatusDao extends CrudRepository<CustomerStatus, Integer>{
}
