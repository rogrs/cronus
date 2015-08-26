package br.com.autobot.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.autobot.model.Project;

@RepositoryRestResource
public interface ProjectRepository extends CrudRepository<Project, Long> {
    List<Project> findByTaskArchived(@Param("archivedfalse") int taskArchivedFalse);
    List<Project> findByEnabled(@Param("enabled") int enabled);
}
