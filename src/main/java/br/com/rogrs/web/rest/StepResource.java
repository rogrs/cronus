package br.com.rogrs.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import br.com.rogrs.domain.Step;
import br.com.rogrs.repository.StepRepository;
import br.com.rogrs.repository.search.StepSearchRepository;
import br.com.rogrs.web.rest.errors.BadRequestAlertException;
import br.com.rogrs.web.rest.util.HeaderUtil;
import br.com.rogrs.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Step.
 */
@RestController
@RequestMapping("/api")
public class StepResource {

    private final Logger log = LoggerFactory.getLogger(StepResource.class);

    private static final String ENTITY_NAME = "step";

    private final StepRepository stepRepository;

    private final StepSearchRepository stepSearchRepository;

    public StepResource(StepRepository stepRepository, StepSearchRepository stepSearchRepository) {
        this.stepRepository = stepRepository;
        this.stepSearchRepository = stepSearchRepository;
    }

    /**
     * POST  /steps : Create a new step.
     *
     * @param step the step to create
     * @return the ResponseEntity with status 201 (Created) and with body the new step, or with status 400 (Bad Request) if the step has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/steps")
    @Timed
    public ResponseEntity<Step> createStep(@Valid @RequestBody Step step) throws URISyntaxException {
        log.debug("REST request to save Step : {}", step);
        if (step.getId() != null) {
            throw new BadRequestAlertException("A new step cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Step result = stepRepository.save(step);
        stepSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/steps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /steps : Updates an existing step.
     *
     * @param step the step to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated step,
     * or with status 400 (Bad Request) if the step is not valid,
     * or with status 500 (Internal Server Error) if the step couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/steps")
    @Timed
    public ResponseEntity<Step> updateStep(@Valid @RequestBody Step step) throws URISyntaxException {
        log.debug("REST request to update Step : {}", step);
        if (step.getId() == null) {
            return createStep(step);
        }
        Step result = stepRepository.save(step);
        stepSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, step.getId().toString()))
            .body(result);
    }

    /**
     * GET  /steps : get all the steps.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of steps in body
     */
    @GetMapping("/steps")
    @Timed
    public ResponseEntity<List<Step>> getAllSteps(Pageable pageable) {
        log.debug("REST request to get a page of Steps");
        Page<Step> page = stepRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/steps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /steps/:id : get the "id" step.
     *
     * @param id the id of the step to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the step, or with status 404 (Not Found)
     */
    @GetMapping("/steps/{id}")
    @Timed
    public ResponseEntity<Step> getStep(@PathVariable Long id) {
        log.debug("REST request to get Step : {}", id);
        Step step = stepRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(step));
    }

    /**
     * DELETE  /steps/:id : delete the "id" step.
     *
     * @param id the id of the step to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/steps/{id}")
    @Timed
    public ResponseEntity<Void> deleteStep(@PathVariable Long id) {
        log.debug("REST request to delete Step : {}", id);
        stepRepository.delete(id);
        stepSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/steps?query=:query : search for the step corresponding
     * to the query.
     *
     * @param query the query of the step search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/steps")
    @Timed
    public ResponseEntity<List<Step>> searchSteps(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Steps for query {}", query);
        Page<Step> page = stepSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/steps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    

}
