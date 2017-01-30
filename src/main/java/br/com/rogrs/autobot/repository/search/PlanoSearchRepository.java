package br.com.rogrs.autobot.repository.search;

import br.com.rogrs.autobot.domain.Plano;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Plano entity.
 */
public interface PlanoSearchRepository extends ElasticsearchRepository<Plano, Long> {
}
