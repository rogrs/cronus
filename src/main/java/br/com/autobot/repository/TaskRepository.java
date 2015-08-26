package br.com.autobot.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.autobot.model.Task;

@RepositoryRestResource
public interface TaskRepository extends CrudRepository<Task, Long> {

        List<Task> findByTaskArchived(@Param("archivedfalse") int taskArchivedFalse);
        List<Task> findByTaskStatus(@Param("status") String taskStatus);

}