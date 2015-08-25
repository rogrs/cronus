package br.com.autobot.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.autobot.model.Scripts;

@RepositoryRestResource(collectionResourceRel = "scriptsRel", path = "scripts")
public interface ScriptsRepository extends PagingAndSortingRepository<Scripts, Long> {
	

}
