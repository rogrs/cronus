package br.com.rogrs.autobot.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.rogrs.autobot.domain.LogExecutarPlano;

import br.com.rogrs.autobot.repository.LogExecutarPlanoRepository;
import br.com.rogrs.autobot.repository.search.LogExecutarPlanoSearchRepository;
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
 * REST controller for managing LogExecutarPlano.
 */
@RestController
@RequestMapping("/api")
public class LogExecutarPlanoResource {

    private final Logger log = LoggerFactory.getLogger(LogExecutarPlanoResource.class);
        
    @Inject
    private LogExecutarPlanoRepository logExecutarPlanoRepository;

    @Inject
    private LogExecutarPlanoSearchRepository logExecutarPlanoSearchRepository;

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
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("logExecutarPlano", "idexists", "A new logExecutarPlano cannot already have an ID")).body(null);
        }
        LogExecutarPlano result = logExecutarPlanoRepository.save(logExecutarPlano);
        logExecutarPlanoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/log-executar-planos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("logExecutarPlano", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /log-executar-planos : Updates an existing logExecutarPlano.
     *
     * @param logExecutarPlano the logExecutarPlano to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated logExecutarPlano,
     * or with status 400 (Bad Request) if the logExecutarPlano is not valid,
     * or with status 500 (Internal Server Error) if the logExecutarPlano couldnt be updated
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
            .headers(HeaderUtil.createEntityUpdateAlert("logExecutarPlano", logExecutarPlano.getId().toString()))
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
        List<LogExecutarPlano> logExecutarPlanos = logExecutarPlanoRepository.findAll();
        return logExecutarPlanos;
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
        return Optional.ofNullable(logExecutarPlano)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("logExecutarPlano", id.toString())).build();
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
