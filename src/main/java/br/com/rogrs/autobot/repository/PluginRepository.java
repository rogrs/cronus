package br.com.rogrs.autobot.repository;

import br.com.rogrs.autobot.domain.Plugin;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Plugin entity.
 */
@SuppressWarnings("unused")
public interface PluginRepository extends JpaRepository<Plugin,Long> {

}
