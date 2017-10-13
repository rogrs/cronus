package br.com.rogrs.autobot.repository.search;

import br.com.rogrs.autobot.domain.ExecutarPlano;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ExecutarPlano entity.
 */
public interface ExecutarPlanoSearchRepository extends ElasticsearchRepository<ExecutarPlano, Long> {
}
