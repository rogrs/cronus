package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.LogExecute;
import com.mycompany.myapp.repository.LogExecuteRepository;
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
 * REST controller for managing LogExecute.
 */
@RestController
@RequestMapping("/api")
public class LogExecuteResource {

    private final Logger log = LoggerFactory.getLogger(LogExecuteResource.class);

    @Inject
    private LogExecuteRepository logExecuteRepository;

    /**
     * POST  /logExecutes -> Create a new logExecute.
     */
    @RequestMapping(value = "/logExecutes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LogExecute> create(@RequestBody LogExecute logExecute) throws URISyntaxException {
        log.debug("REST request to save LogExecute : {}", logExecute);
        if (logExecute.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new logExecute cannot already have an ID").body(null);
        }
        LogExecute result = logExecuteRepository.save(logExecute);
        return ResponseEntity.created(new URI("/api/logExecutes/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("logExecute", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /logExecutes -> Updates an existing logExecute.
     */
    @RequestMapping(value = "/logExecutes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LogExecute> update(@RequestBody LogExecute logExecute) throws URISyntaxException {
        log.debug("REST request to update LogExecute : {}", logExecute);
        if (logExecute.getId() == null) {
            return create(logExecute);
        }
        LogExecute result = logExecuteRepository.save(logExecute);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("logExecute", logExecute.getId().toString()))
                .body(result);
    }

    /**
     * GET  /logExecutes -> get all the logExecutes.
     */
    @RequestMapping(value = "/logExecutes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<LogExecute>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<LogExecute> page = logExecuteRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/logExecutes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /logExecutes/:id -> get the "id" logExecute.
     */
    @RequestMapping(value = "/logExecutes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LogExecute> get(@PathVariable Long id) {
        log.debug("REST request to get LogExecute : {}", id);
        return Optional.ofNullable(logExecuteRepository.findOne(id))
            .map(logExecute -> new ResponseEntity<>(
                logExecute,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /logExecutes/:id -> delete the "id" logExecute.
     */
    @RequestMapping(value = "/logExecutes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete LogExecute : {}", id);
        logExecuteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("logExecute", id.toString())).build();
    }
}
