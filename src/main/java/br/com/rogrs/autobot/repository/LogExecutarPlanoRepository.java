package br.com.rogrs.autobot.repository;

import br.com.rogrs.autobot.domain.LogExecutarPlano;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LogExecutarPlano entity.
 */
@SuppressWarnings("unused")
public interface LogExecutarPlanoRepository extends JpaRepository<LogExecutarPlano,Long> {

}
