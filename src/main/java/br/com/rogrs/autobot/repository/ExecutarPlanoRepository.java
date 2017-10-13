package br.com.rogrs.autobot.repository;

import br.com.rogrs.autobot.domain.ExecutarPlano;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ExecutarPlano entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExecutarPlanoRepository extends JpaRepository<ExecutarPlano, Long> {

}
