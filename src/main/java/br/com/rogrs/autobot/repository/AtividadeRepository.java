package br.com.rogrs.autobot.repository;

import br.com.rogrs.autobot.domain.Atividade;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Atividade entity.
 */
@SuppressWarnings("unused")
public interface AtividadeRepository extends JpaRepository<Atividade,Long> {

}
