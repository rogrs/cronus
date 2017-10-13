package br.com.rogrs.autobot.repository;

import br.com.rogrs.autobot.domain.LogExecutarPlano;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LogExecutarPlano entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogExecutarPlanoRepository extends JpaRepository<LogExecutarPlano, Long> {

}
