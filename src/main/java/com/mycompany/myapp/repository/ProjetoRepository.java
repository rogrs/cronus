package com.mycompany.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.myapp.domain.Projeto;

/**
 * Spring Data JPA repository for the Projeto entity.
 */
public interface ProjetoRepository extends JpaRepository<Projeto,Long> {

}
