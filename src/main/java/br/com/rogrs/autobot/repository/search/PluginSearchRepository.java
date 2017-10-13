package br.com.rogrs.autobot.repository.search;

import br.com.rogrs.autobot.domain.Plugin;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Plugin entity.
 */
public interface PluginSearchRepository extends ElasticsearchRepository<Plugin, Long> {
}
