package com.mycompany.myapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Script;
import com.mycompany.myapp.repository.ScriptRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Script.
 */
@RestController
@RequestMapping("/api")
public class ScriptResource {

    private final Logger log = LoggerFactory.getLogger(ScriptResource.class);

    @Inject
    private ScriptRepository scriptRepository;

    /**
     * POST  /scripts -> Create a new script.
     */
    @RequestMapping(value = "/scripts",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Script> create(@RequestBody Script script) throws URISyntaxException {
        log.debug("REST request to save Script : {}", script);
        if (script.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new script cannot already have an ID").body(null);
        }
        Script result = scriptRepository.save(script);
        return ResponseEntity.created(new URI("/api/scripts/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("script", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /scripts -> Updates an existing script.
     */
    @RequestMapping(value = "/scripts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Script> update(@RequestBody Script script) throws URISyntaxException {
        log.debug("REST request to update Script : {}", script);
        if (script.getId() == null) {
            return create(script);
        }
        Script result = scriptRepository.save(script);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("script", script.getId().toString()))
                .body(result);
    }

    /**
     * GET  /scripts -> get all the scripts.
     */
    @RequestMapping(value = "/scripts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Script>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Script> page = scriptRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/scripts", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /scripts/:id -> get the "id" script.
     */
    @RequestMapping(value = "/scripts/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Script> get(@PathVariable Long id) {
        log.debug("REST request to get Script : {}", id);
        return Optional.ofNullable(scriptRepository.findOne(id))
            .map(script -> new ResponseEntity<>(
                script,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /scripts/:id -> delete the "id" script.
     */
    @RequestMapping(value = "/scripts/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Script : {}", id);
        scriptRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("script", id.toString())).build();
    }
}
