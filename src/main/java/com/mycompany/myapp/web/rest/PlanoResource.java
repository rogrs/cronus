package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Plano;
import com.mycompany.myapp.repository.PlanoRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Plano.
 */
@RestController
@RequestMapping("/api")
public class PlanoResource {

    private final Logger log = LoggerFactory.getLogger(PlanoResource.class);

    @Inject
    private PlanoRepository planoRepository;

    /**
     * POST  /planos -> Create a new plano.
     */
    @RequestMapping(value = "/planos",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Plano> create(@RequestBody Plano plano) throws URISyntaxException {
        log.debug("REST request to save Plano : {}", plano);
        if (plano.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new plano cannot already have an ID").body(null);
        }
        Plano result = planoRepository.save(plano);
        return ResponseEntity.created(new URI("/api/planos/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("plano", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /planos -> Updates an existing plano.
     */
    @RequestMapping(value = "/planos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Plano> update(@RequestBody Plano plano) throws URISyntaxException {
        log.debug("REST request to update Plano : {}", plano);
        if (plano.getId() == null) {
            return create(plano);
        }
        Plano result = planoRepository.save(plano);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("plano", plano.getId().toString()))
                .body(result);
    }

    /**
     * GET  /planos -> get all the planos.
     */
    @RequestMapping(value = "/planos",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Plano>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Plano> page = planoRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/planos", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /planos/:id -> get the "id" plano.
     */
    @RequestMapping(value = "/planos/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Plano> get(@PathVariable Long id) {
        log.debug("REST request to get Plano : {}", id);
        return Optional.ofNullable(planoRepository.findOne(id))
            .map(plano -> new ResponseEntity<>(
                plano,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /planos/:id -> delete the "id" plano.
     */
    @RequestMapping(value = "/planos/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Plano : {}", id);
        planoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("plano", id.toString())).build();
    }
}
