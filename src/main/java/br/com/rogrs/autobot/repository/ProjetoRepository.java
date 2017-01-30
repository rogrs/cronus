package br.com.rogrs.autobot.repository;

import br.com.rogrs.autobot.domain.Projeto;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Projeto entity.
 */
@SuppressWarnings("unused")
public interface ProjetoRepository extends JpaRepository<Projeto,Long> {

}
