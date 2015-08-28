package com.mycompany.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.myapp.domain.Plano;

/**
 * Spring Data JPA repository for the Plano entity.
 */
public interface PlanoRepository extends JpaRepository<Plano,Long> {

}
