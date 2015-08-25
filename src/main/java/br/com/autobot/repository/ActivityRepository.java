package br.com.autobot.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.autobot.model.Activity;


@RepositoryRestResource(collectionResourceRel = "activityRel", path = "activities")
public interface ActivityRepository extends PagingAndSortingRepository<Activity, Long>{

}
