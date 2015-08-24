package br.com.autobot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.autobot.model.Plugins;

@RepositoryRestResource(collectionResourceRel = "pluginsRel", path = "plugins")
public interface PluginsRepository extends JpaRepository<Plugins, Long> {

}
