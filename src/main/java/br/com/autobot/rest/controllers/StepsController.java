package br.com.autobot.rest.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.autobot.model.Steps;
import br.com.autobot.repository.StepsRepository;
import br.com.autobot.repository.TestCaseRepository;

@RestController
@RequestMapping("/{testcaseId}/steps")
class StepsRestController {

	private final StepsRepository stepsRepository;// BookmarkRepository

	private final TestCaseRepository testCaseRepository;// AccountRepository

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@PathVariable String testcaseId, @RequestBody Steps input) {
		this.validateTestCase(testcaseId);
		return this.testCaseRepository.findByTestCase(testcaseId).map(account -> {
			Steps result = stepsRepository.save(new Steps(account, input.uri, input.description));

			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setLocation(
					ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri());
			return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
		}).get();

	}

	@RequestMapping(value = "/{stepId}", method = RequestMethod.GET)
	Steps readStep(@PathVariable String testcaseId, @PathVariable Long stepId) {
		this.validateTestCase(testcaseId);
		return this.stepsRepository.findOne(stepId);
	}

	@RequestMapping(method = RequestMethod.GET)
	Collection<Steps> readSteps(@PathVariable String testcaseId) {
		this.validateTestCase(testcaseId);
		return this.stepsRepository.findByAccountUsername(testcaseId);
	}

	@Autowired
	StepsRestController(StepsRepository stepsRepository, TestCaseRepository testCaseRepository) {
		this.stepsRepository = stepsRepository;
		this.testCaseRepository = testCaseRepository;
	}

	private void validateTestCase(String testCaseId) {
		this.testCaseRepository.findByTestCase(testCaseId).orElseThrow(() -> new TestCaseNotFoundException(testCaseId));
	}
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class TestCaseNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2076091614507985789L;

	public TestCaseNotFoundException(String testCaseId) {
		super("could not find testcase '" + testCaseId + "'.");
	}
}