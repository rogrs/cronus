package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.ExecutarPlano;
import com.mycompany.myapp.repository.ExecutarPlanoRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ExecutarPlano.
 */
@RestController
@RequestMapping("/api")
public class ExecutarPlanoResource {

    private final Logger log = LoggerFactory.getLogger(ExecutarPlanoResource.class);

    @Inject
    private ExecutarPlanoRepository executarPlanoRepository;

    /**
     * POST  /executarPlanos -> Create a new executarPlano.
     */
    @RequestMapping(value = "/executarPlanos",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExecutarPlano> create(@Valid @RequestBody ExecutarPlano executarPlano) throws URISyntaxException {
        log.debug("REST request to save ExecutarPlano : {}", executarPlano);
        if (executarPlano.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new executarPlano cannot already have an ID").body(null);
        }
        ExecutarPlano result = executarPlanoRepository.save(executarPlano);
        return ResponseEntity.created(new URI("/api/executarPlanos/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("executarPlano", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /executarPlanos -> Updates an existing executarPlano.
     */
    @RequestMapping(value = "/executarPlanos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExecutarPlano> update(@Valid @RequestBody ExecutarPlano executarPlano) throws URISyntaxException {
        log.debug("REST request to update ExecutarPlano : {}", executarPlano);
        if (executarPlano.getId() == null) {
            return create(executarPlano);
        }
        ExecutarPlano result = executarPlanoRepository.save(executarPlano);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("executarPlano", executarPlano.getId().toString()))
                .body(result);
    }

    /**
     * GET  /executarPlanos -> get all the executarPlanos.
     */
    @RequestMapping(value = "/executarPlanos",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ExecutarPlano>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<ExecutarPlano> page = executarPlanoRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/executarPlanos", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /executarPlanos/:id -> get the "id" executarPlano.
     */
    @RequestMapping(value = "/executarPlanos/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExecutarPlano> get(@PathVariable Long id) {
        log.debug("REST request to get ExecutarPlano : {}", id);
        return Optional.ofNullable(executarPlanoRepository.findOne(id))
            .map(executarPlano -> new ResponseEntity<>(
                executarPlano,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /executarPlanos/:id -> delete the "id" executarPlano.
     */
    @RequestMapping(value = "/executarPlanos/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete ExecutarPlano : {}", id);
        executarPlanoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("executarPlano", id.toString())).build();
    }
}
