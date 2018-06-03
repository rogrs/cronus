package br.com.rogrs.repository;

import br.com.rogrs.domain.Project;
import br.com.rogrs.domain.Step;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Step entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StepRepository extends JpaRepository<Step, Long> {
	
	public List<Step> findByProject(Project project);

}
