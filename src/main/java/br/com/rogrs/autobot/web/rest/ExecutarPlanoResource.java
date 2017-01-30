package br.com.rogrs.autobot.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.rogrs.autobot.domain.ExecutarPlano;

import br.com.rogrs.autobot.repository.ExecutarPlanoRepository;
import br.com.rogrs.autobot.repository.search.ExecutarPlanoSearchRepository;
import br.com.rogrs.autobot.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ExecutarPlano.
 */
@RestController
@RequestMapping("/api")
public class ExecutarPlanoResource {

    private final Logger log = LoggerFactory.getLogger(ExecutarPlanoResource.class);
        
    @Inject
    private ExecutarPlanoRepository executarPlanoRepository;

    @Inject
    private ExecutarPlanoSearchRepository executarPlanoSearchRepository;

    /**
     * POST  /executar-planos : Create a new executarPlano.
     *
     * @param executarPlano the executarPlano to create
     * @return the ResponseEntity with status 201 (Created) and with body the new executarPlano, or with status 400 (Bad Request) if the executarPlano has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/executar-planos")
    @Timed
    public ResponseEntity<ExecutarPlano> createExecutarPlano(@Valid @RequestBody ExecutarPlano executarPlano) throws URISyntaxException {
        log.debug("REST request to save ExecutarPlano : {}", executarPlano);
        if (executarPlano.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("executarPlano", "idexists", "A new executarPlano cannot already have an ID")).body(null);
        }
        ExecutarPlano result = executarPlanoRepository.save(executarPlano);
        executarPlanoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/executar-planos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("executarPlano", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /executar-planos : Updates an existing executarPlano.
     *
     * @param executarPlano the executarPlano to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated executarPlano,
     * or with status 400 (Bad Request) if the executarPlano is not valid,
     * or with status 500 (Internal Server Error) if the executarPlano couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/executar-planos")
    @Timed
    public ResponseEntity<ExecutarPlano> updateExecutarPlano(@Valid @RequestBody ExecutarPlano executarPlano) throws URISyntaxException {
        log.debug("REST request to update ExecutarPlano : {}", executarPlano);
        if (executarPlano.getId() == null) {
            return createExecutarPlano(executarPlano);
        }
        ExecutarPlano result = executarPlanoRepository.save(executarPlano);
        executarPlanoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("executarPlano", executarPlano.getId().toString()))
            .body(result);
    }

    /**
     * GET  /executar-planos : get all the executarPlanos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of executarPlanos in body
     */
    @GetMapping("/executar-planos")
    @Timed
    public List<ExecutarPlano> getAllExecutarPlanos() {
        log.debug("REST request to get all ExecutarPlanos");
        List<ExecutarPlano> executarPlanos = executarPlanoRepository.findAll();
        return executarPlanos;
    }

    /**
     * GET  /executar-planos/:id : get the "id" executarPlano.
     *
     * @param id the id of the executarPlano to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the executarPlano, or with status 404 (Not Found)
     */
    @GetMapping("/executar-planos/{id}")
    @Timed
    public ResponseEntity<ExecutarPlano> getExecutarPlano(@PathVariable Long id) {
        log.debug("REST request to get ExecutarPlano : {}", id);
        ExecutarPlano executarPlano = executarPlanoRepository.findOne(id);
        return Optional.ofNullable(executarPlano)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /executar-planos/:id : delete the "id" executarPlano.
     *
     * @param id the id of the executarPlano to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/executar-planos/{id}")
    @Timed
    public ResponseEntity<Void> deleteExecutarPlano(@PathVariable Long id) {
        log.debug("REST request to delete ExecutarPlano : {}", id);
        executarPlanoRepository.delete(id);
        executarPlanoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("executarPlano", id.toString())).build();
    }

    /**
     * SEARCH  /_search/executar-planos?query=:query : search for the executarPlano corresponding
     * to the query.
     *
     * @param query the query of the executarPlano search 
     * @return the result of the search
     */
    @GetMapping("/_search/executar-planos")
    @Timed
    public List<ExecutarPlano> searchExecutarPlanos(@RequestParam String query) {
        log.debug("REST request to search ExecutarPlanos for query {}", query);
        return StreamSupport
            .stream(executarPlanoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
