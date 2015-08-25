package br.com.autobot.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.autobot.model.TestCase;

@RepositoryRestResource(collectionResourceRel = "testcaseRel", path = "testcase")
public interface TestCaseRepository extends PagingAndSortingRepository<TestCase, Long> {

	
}
