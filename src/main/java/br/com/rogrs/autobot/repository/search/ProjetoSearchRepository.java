package br.com.rogrs.autobot.repository.search;

import br.com.rogrs.autobot.domain.Projeto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Projeto entity.
 */
public interface ProjetoSearchRepository extends ElasticsearchRepository<Projeto, Long> {
}
