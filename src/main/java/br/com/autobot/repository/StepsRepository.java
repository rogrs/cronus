package br.com.autobot.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.autobot.model.Steps;

@RepositoryRestResource(collectionResourceRel = "stepsRel", path = "steps")
public interface StepsRepository extends PagingAndSortingRepository<Steps, Long> {

	
}
