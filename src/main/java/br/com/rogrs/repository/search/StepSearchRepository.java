package br.com.rogrs.repository.search;

import br.com.rogrs.domain.Step;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Step entity.
 */
public interface StepSearchRepository extends ElasticsearchRepository<Step, Long> {
}
