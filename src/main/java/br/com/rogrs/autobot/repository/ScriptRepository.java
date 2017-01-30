package br.com.rogrs.autobot.repository;

import br.com.rogrs.autobot.domain.Script;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Script entity.
 */
@SuppressWarnings("unused")
public interface ScriptRepository extends JpaRepository<Script,Long> {

}
