package design.hustlelikeaboss.customr.models.data;

import design.hustlelikeaboss.customr.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by quanjin on 6/20/17.
 */
@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Integer>{
}
