package br.com.rogrs.autobot.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.rogrs.autobot.domain.Plano;

import br.com.rogrs.autobot.repository.PlanoRepository;
import br.com.rogrs.autobot.repository.search.PlanoSearchRepository;
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
 * REST controller for managing Plano.
 */
@RestController
@RequestMapping("/api")
public class PlanoResource {

    private final Logger log = LoggerFactory.getLogger(PlanoResource.class);

    private static final String ENTITY_NAME = "plano";

    private final PlanoRepository planoRepository;

    private final PlanoSearchRepository planoSearchRepository;

    public PlanoResource(PlanoRepository planoRepository, PlanoSearchRepository planoSearchRepository) {
        this.planoRepository = planoRepository;
        this.planoSearchRepository = planoSearchRepository;
    }

    /**
     * POST  /planos : Create a new plano.
     *
     * @param plano the plano to create
     * @return the ResponseEntity with status 201 (Created) and with body the new plano, or with status 400 (Bad Request) if the plano has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/planos")
    @Timed
    public ResponseEntity<Plano> createPlano(@Valid @RequestBody Plano plano) throws URISyntaxException {
        log.debug("REST request to save Plano : {}", plano);
        if (plano.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new plano cannot already have an ID")).body(null);
        }
        Plano result = planoRepository.save(plano);
        planoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/planos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /planos : Updates an existing plano.
     *
     * @param plano the plano to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated plano,
     * or with status 400 (Bad Request) if the plano is not valid,
     * or with status 500 (Internal Server Error) if the plano couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/planos")
    @Timed
    public ResponseEntity<Plano> updatePlano(@Valid @RequestBody Plano plano) throws URISyntaxException {
        log.debug("REST request to update Plano : {}", plano);
        if (plano.getId() == null) {
            return createPlano(plano);
        }
        Plano result = planoRepository.save(plano);
        planoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, plano.getId().toString()))
            .body(result);
    }

    /**
     * GET  /planos : get all the planos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of planos in body
     */
    @GetMapping("/planos")
    @Timed
    public List<Plano> getAllPlanos() {
        log.debug("REST request to get all Planos");
        return planoRepository.findAll();
        }

    /**
     * GET  /planos/:id : get the "id" plano.
     *
     * @param id the id of the plano to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the plano, or with status 404 (Not Found)
     */
    @GetMapping("/planos/{id}")
    @Timed
    public ResponseEntity<Plano> getPlano(@PathVariable Long id) {
        log.debug("REST request to get Plano : {}", id);
        Plano plano = planoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(plano));
    }

    /**
     * DELETE  /planos/:id : delete the "id" plano.
     *
     * @param id the id of the plano to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/planos/{id}")
    @Timed
    public ResponseEntity<Void> deletePlano(@PathVariable Long id) {
        log.debug("REST request to delete Plano : {}", id);
        planoRepository.delete(id);
        planoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/planos?query=:query : search for the plano corresponding
     * to the query.
     *
     * @param query the query of the plano search
     * @return the result of the search
     */
    @GetMapping("/_search/planos")
    @Timed
    public List<Plano> searchPlanos(@RequestParam String query) {
        log.debug("REST request to search Planos for query {}", query);
        return StreamSupport
            .stream(planoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
