package br.com.rogrs.autobot.repository;

import br.com.rogrs.autobot.domain.Projeto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Projeto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

}
