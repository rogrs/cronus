package com.mycompany.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.myapp.domain.ExecutarPlano;

/**
 * Spring Data JPA repository for the ExecutarPlano entity.
 */
public interface ExecutarPlanoRepository extends JpaRepository<ExecutarPlano,Long> {

}
