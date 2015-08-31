package com.mycompany.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.myapp.domain.Script;

/**
 * Spring Data JPA repository for the Script entity.
 */
public interface ScriptRepository extends JpaRepository<Script,Long> {
	
}
