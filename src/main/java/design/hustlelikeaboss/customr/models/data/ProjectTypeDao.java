package design.hustlelikeaboss.customr.models.data;

import design.hustlelikeaboss.customr.models.ProjectType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by quanjin on 6/23/17.
 */

@Repository
@Transactional
public interface ProjectTypeDao extends CrudRepository<ProjectType, Integer>{

    ProjectType findByName(String name);

}
