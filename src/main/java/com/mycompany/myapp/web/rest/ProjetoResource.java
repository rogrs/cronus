package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Projeto;
import com.mycompany.myapp.repository.ProjetoRepository;
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
 * REST controller for managing Projeto.
 */
@RestController
@RequestMapping("/api")
public class ProjetoResource {

    private final Logger log = LoggerFactory.getLogger(ProjetoResource.class);

    @Inject
    private ProjetoRepository projetoRepository;

    /**
     * POST  /projetos -> Create a new projeto.
     */
    @RequestMapping(value = "/projetos",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Projeto> create(@RequestBody Projeto projeto) throws URISyntaxException {
        log.debug("REST request to save Projeto : {}", projeto);
        if (projeto.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new projeto cannot already have an ID").body(null);
        }
        Projeto result = projetoRepository.save(projeto);
        return ResponseEntity.created(new URI("/api/projetos/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("projeto", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /projetos -> Updates an existing projeto.
     */
    @RequestMapping(value = "/projetos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Projeto> update(@RequestBody Projeto projeto) throws URISyntaxException {
        log.debug("REST request to update Projeto : {}", projeto);
        if (projeto.getId() == null) {
            return create(projeto);
        }
        Projeto result = projetoRepository.save(projeto);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("projeto", projeto.getId().toString()))
                .body(result);
    }

    /**
     * GET  /projetos -> get all the projetos.
     */
    @RequestMapping(value = "/projetos",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Projeto>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Projeto> page = projetoRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/projetos", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /projetos/:id -> get the "id" projeto.
     */
    @RequestMapping(value = "/projetos/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Projeto> get(@PathVariable Long id) {
        log.debug("REST request to get Projeto : {}", id);
        return Optional.ofNullable(projetoRepository.findOne(id))
            .map(projeto -> new ResponseEntity<>(
                projeto,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /projetos/:id -> delete the "id" projeto.
     */
    @RequestMapping(value = "/projetos/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Projeto : {}", id);
        projetoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("projeto", id.toString())).build();
    }
}
