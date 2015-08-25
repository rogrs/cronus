package br.com.autobot.rest.controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.autobot.model.TestCase;
import br.com.autobot.repository.TestCaseRepository;

@RestController
@RequestMapping("/testcase")
public class TestCaseController {

	private final TestCaseRepository repository;

	@Autowired
	TestCaseController(TestCaseRepository repository) {
		this.repository = repository;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	Collection<TestCase> readTestCases() {	
		return this.repository.findAll();
	}
		
	@RequestMapping(value = "/{testcaseId}", method = RequestMethod.GET)
	TestCase readTestCase(@PathVariable Long testcaseId) {
		return this.repository.findOne(testcaseId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?>  create(@RequestBody @Valid TestCase create) {
		TestCase result = repository.save(create);
		 
		 HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setLocation(ServletUriComponentsBuilder
					.fromCurrentRequest().path("/{id}")
					.buildAndExpand(result.getId()).toUri());
		 
		 return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
	}
	

	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public void delete(@PathVariable Long id) {
		repository.delete(id);
	}

}
