package com.mycompany.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.myapp.domain.Script;

/**
 * Spring Data JPA repository for the Script entity.
 */
@Transactional
public interface ScriptRepository extends JpaRepository<Script,Long> {
    
  //  Collection<Script> findByPlano(Long  idPlano);
}
