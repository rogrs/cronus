package br.com.autobot.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.autobot.model.Plugins;

@RepositoryRestResource(collectionResourceRel = "pluginsRel", path = "plugins")
public interface PluginsRepository extends PagingAndSortingRepository<Plugins, Long> {

}
