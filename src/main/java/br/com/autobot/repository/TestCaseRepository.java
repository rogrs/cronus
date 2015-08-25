package br.com.autobot.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.autobot.model.TestCases;

@RepositoryRestResource(collectionResourceRel = "testcaseRel", path = "testcase")
public interface TestCaseRepository extends PagingAndSortingRepository<TestCases, Long> {

	
}
