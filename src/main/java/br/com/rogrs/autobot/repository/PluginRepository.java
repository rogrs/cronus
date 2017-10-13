package br.com.rogrs.autobot.repository;

import br.com.rogrs.autobot.domain.Plugin;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Plugin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PluginRepository extends JpaRepository<Plugin, Long> {

}
