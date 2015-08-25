package br.com.autobot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobot.model.TestCase;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {

	Optional<TestCase> findByTestCase(String username);
}
