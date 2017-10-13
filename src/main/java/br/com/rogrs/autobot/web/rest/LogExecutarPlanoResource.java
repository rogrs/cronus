package br.com.rogrs.autobot.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.rogrs.autobot.domain.LogExecutarPlano;

import br.com.rogrs.autobot.repository.LogExecutarPlanoRepository;
import br.com.rogrs.autobot.repository.search.LogExecutarPlanoSearchRepository;
import br.com.rogrs.autobot.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing LogExecutarPlano.
 */
@RestController
@RequestMapping("/api")
public class LogExecutarPlanoResource {

    private final Logger log = LoggerFactory.getLogger(LogExecutarPlanoResource.class);

    private static final String ENTITY_NAME = "logExecutarPlano";

    private final LogExecutarPlanoRepository logExecutarPlanoRepository;

    private final LogExecutarPlanoSearchRepository logExecutarPlanoSearchRepository;

    public LogExecutarPlanoResource(LogExecutarPlanoRepository logExecutarPlanoRepository, LogExecutarPlanoSearchRepository logExecutarPlanoSearchRepository) {
        this.logExecutarPlanoRepository = logExecutarPlanoRepository;
        this.logExecutarPlanoSearchRepository = logExecutarPlanoSearchRepository;
    }

    /**
     * POST  /log-executar-planos : Create a new logExecutarPlano.
     *
     * @param logExecutarPlano the logExecutarPlano to create
     * @return the ResponseEntity with status 201 (Created) and with body the new logExecutarPlano, or with status 400 (Bad Request) if the logExecutarPlano has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/log-executar-planos")
    @Timed
    public ResponseEntity<LogExecutarPlano> createLogExecutarPlano(@Valid @RequestBody LogExecutarPlano logExecutarPlano) throws URISyntaxException {
        log.debug("REST request to save LogExecutarPlano : {}", logExecutarPlano);
        if (logExecutarPlano.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new logExecutarPlano cannot already have an ID")).body(null);
        }
        LogExecutarPlano result = logExecutarPlanoRepository.save(logExecutarPlano);
        logExecutarPlanoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/log-executar-planos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /log-executar-planos : Updates an existing logExecutarPlano.
     *
     * @param logExecutarPlano the logExecutarPlano to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated logExecutarPlano,
     * or with status 400 (Bad Request) if the logExecutarPlano is not valid,
     * or with status 500 (Internal Server Error) if the logExecutarPlano couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/log-executar-planos")
    @Timed
    public ResponseEntity<LogExecutarPlano> updateLogExecutarPlano(@Valid @RequestBody LogExecutarPlano logExecutarPlano) throws URISyntaxException {
        log.debug("REST request to update LogExecutarPlano : {}", logExecutarPlano);
        if (logExecutarPlano.getId() == null) {
            return createLogExecutarPlano(logExecutarPlano);
        }
        LogExecutarPlano result = logExecutarPlanoRepository.save(logExecutarPlano);
        logExecutarPlanoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, logExecutarPlano.getId().toString()))
            .body(result);
    }

    /**
     * GET  /log-executar-planos : get all the logExecutarPlanos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of logExecutarPlanos in body
     */
    @GetMapping("/log-executar-planos")
    @Timed
    public List<LogExecutarPlano> getAllLogExecutarPlanos() {
        log.debug("REST request to get all LogExecutarPlanos");
        return logExecutarPlanoRepository.findAll();
        }

    /**
     * GET  /log-executar-planos/:id : get the "id" logExecutarPlano.
     *
     * @param id the id of the logExecutarPlano to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the logExecutarPlano, or with status 404 (Not Found)
     */
    @GetMapping("/log-executar-planos/{id}")
    @Timed
    public ResponseEntity<LogExecutarPlano> getLogExecutarPlano(@PathVariable Long id) {
        log.debug("REST request to get LogExecutarPlano : {}", id);
        LogExecutarPlano logExecutarPlano = logExecutarPlanoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(logExecutarPlano));
    }

    /**
     * DELETE  /log-executar-planos/:id : delete the "id" logExecutarPlano.
     *
     * @param id the id of the logExecutarPlano to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/log-executar-planos/{id}")
    @Timed
    public ResponseEntity<Void> deleteLogExecutarPlano(@PathVariable Long id) {
        log.debug("REST request to delete LogExecutarPlano : {}", id);
        logExecutarPlanoRepository.delete(id);
        logExecutarPlanoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/log-executar-planos?query=:query : search for the logExecutarPlano corresponding
     * to the query.
     *
     * @param query the query of the logExecutarPlano search
     * @return the result of the search
     */
    @GetMapping("/_search/log-executar-planos")
    @Timed
    public List<LogExecutarPlano> searchLogExecutarPlanos(@RequestParam String query) {
        log.debug("REST request to search LogExecutarPlanos for query {}", query);
        return StreamSupport
            .stream(logExecutarPlanoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
