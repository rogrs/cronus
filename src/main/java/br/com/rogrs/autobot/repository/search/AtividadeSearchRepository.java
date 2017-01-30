package br.com.rogrs.autobot.repository.search;

import br.com.rogrs.autobot.domain.Atividade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Atividade entity.
 */
public interface AtividadeSearchRepository extends ElasticsearchRepository<Atividade, Long> {
}
