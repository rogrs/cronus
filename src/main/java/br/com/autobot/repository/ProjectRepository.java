package br.com.autobot.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.autobot.model.Project;


@RepositoryRestResource(collectionResourceRel = "projectel", path = "project")
public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {

}
