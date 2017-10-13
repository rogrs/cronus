package br.com.rogrs.autobot.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.rogrs.autobot.domain.Script;

import br.com.rogrs.autobot.repository.ScriptRepository;
import br.com.rogrs.autobot.repository.search.ScriptSearchRepository;
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
 * REST controller for managing Script.
 */
@RestController
@RequestMapping("/api")
public class ScriptResource {

    private final Logger log = LoggerFactory.getLogger(ScriptResource.class);

    private static final String ENTITY_NAME = "script";

    private final ScriptRepository scriptRepository;

    private final ScriptSearchRepository scriptSearchRepository;

    public ScriptResource(ScriptRepository scriptRepository, ScriptSearchRepository scriptSearchRepository) {
        this.scriptRepository = scriptRepository;
        this.scriptSearchRepository = scriptSearchRepository;
    }

    /**
     * POST  /scripts : Create a new script.
     *
     * @param script the script to create
     * @return the ResponseEntity with status 201 (Created) and with body the new script, or with status 400 (Bad Request) if the script has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/scripts")
    @Timed
    public ResponseEntity<Script> createScript(@Valid @RequestBody Script script) throws URISyntaxException {
        log.debug("REST request to save Script : {}", script);
        if (script.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new script cannot already have an ID")).body(null);
        }
        Script result = scriptRepository.save(script);
        scriptSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/scripts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /scripts : Updates an existing script.
     *
     * @param script the script to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated script,
     * or with status 400 (Bad Request) if the script is not valid,
     * or with status 500 (Internal Server Error) if the script couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/scripts")
    @Timed
    public ResponseEntity<Script> updateScript(@Valid @RequestBody Script script) throws URISyntaxException {
        log.debug("REST request to update Script : {}", script);
        if (script.getId() == null) {
            return createScript(script);
        }
        Script result = scriptRepository.save(script);
        scriptSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, script.getId().toString()))
            .body(result);
    }

    /**
     * GET  /scripts : get all the scripts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of scripts in body
     */
    @GetMapping("/scripts")
    @Timed
    public List<Script> getAllScripts() {
        log.debug("REST request to get all Scripts");
        return scriptRepository.findAll();
        }

    /**
     * GET  /scripts/:id : get the "id" script.
     *
     * @param id the id of the script to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the script, or with status 404 (Not Found)
     */
    @GetMapping("/scripts/{id}")
    @Timed
    public ResponseEntity<Script> getScript(@PathVariable Long id) {
        log.debug("REST request to get Script : {}", id);
        Script script = scriptRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(script));
    }

    /**
     * DELETE  /scripts/:id : delete the "id" script.
     *
     * @param id the id of the script to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/scripts/{id}")
    @Timed
    public ResponseEntity<Void> deleteScript(@PathVariable Long id) {
        log.debug("REST request to delete Script : {}", id);
        scriptRepository.delete(id);
        scriptSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/scripts?query=:query : search for the script corresponding
     * to the query.
     *
     * @param query the query of the script search
     * @return the result of the search
     */
    @GetMapping("/_search/scripts")
    @Timed
    public List<Script> searchScripts(@RequestParam String query) {
        log.debug("REST request to search Scripts for query {}", query);
        return StreamSupport
            .stream(scriptSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
