package br.com.rogrs.autobot.repository.search;

import br.com.rogrs.autobot.domain.LogExecutarPlano;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LogExecutarPlano entity.
 */
public interface LogExecutarPlanoSearchRepository extends ElasticsearchRepository<LogExecutarPlano, Long> {
}
