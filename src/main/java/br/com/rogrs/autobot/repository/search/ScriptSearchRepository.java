package br.com.rogrs.autobot.repository.search;

import br.com.rogrs.autobot.domain.Script;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Script entity.
 */
public interface ScriptSearchRepository extends ElasticsearchRepository<Script, Long> {
}
