package br.com.rogrs.autobot.repository.search;

import br.com.rogrs.autobot.domain.ExecutarPlano;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ExecutarPlano entity.
 */
public interface ExecutarPlanoSearchRepository extends ElasticsearchRepository<ExecutarPlano, Long> {
}
