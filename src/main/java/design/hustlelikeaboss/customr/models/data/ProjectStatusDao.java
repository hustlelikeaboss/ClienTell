package design.hustlelikeaboss.customr.models.data;

import design.hustlelikeaboss.customr.models.ProjectStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by quanjin on 6/23/17.
 */
@Repository
@Transactional
public interface ProjectStatusDao extends CrudRepository<ProjectStatus, Integer>{
}
