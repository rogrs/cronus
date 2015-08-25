package br.com.autobot.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.autobot.model.Plan;

@RepositoryRestResource(collectionResourceRel = "planRel", path = "plan")
public interface PlanRepository extends PagingAndSortingRepository<Plan, Long> {



}
