package br.com.autobot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobot.model.Steps;

public interface StepsRepository extends JpaRepository<Steps, Long> {
	
	
}
