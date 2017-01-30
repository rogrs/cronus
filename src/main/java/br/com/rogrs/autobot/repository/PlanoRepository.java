package br.com.rogrs.autobot.repository;

import br.com.rogrs.autobot.domain.Plano;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Plano entity.
 */
@SuppressWarnings("unused")
public interface PlanoRepository extends JpaRepository<Plano,Long> {

}
