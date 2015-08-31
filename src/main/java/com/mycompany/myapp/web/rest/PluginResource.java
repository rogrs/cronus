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
import com.mycompany.myapp.domain.Plugin;
import com.mycompany.myapp.repository.PluginRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Plugin.
 */
@RestController
@RequestMapping("/api")
public class PluginResource {

    private final Logger log = LoggerFactory.getLogger(PluginResource.class);

    @Inject
    private PluginRepository pluginRepository;

    /**
     * POST  /plugins -> Create a new plugin.
     */
    @RequestMapping(value = "/plugins",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Plugin> create(@RequestBody Plugin plugin) throws URISyntaxException {
        log.debug("REST request to save Plugin : {}", plugin);
        if (plugin.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new plugin cannot already have an ID").body(null);
        }
        Plugin result = pluginRepository.save(plugin);
        return ResponseEntity.created(new URI("/api/plugins/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("plugin", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /plugins -> Updates an existing plugin.
     */
    @RequestMapping(value = "/plugins",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Plugin> update(@RequestBody Plugin plugin) throws URISyntaxException {
        log.debug("REST request to update Plugin : {}", plugin);
        if (plugin.getId() == null) {
            return create(plugin);
        }
        Plugin result = pluginRepository.save(plugin);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("plugin", plugin.getId().toString()))
                .body(result);
    }

    /**
     * GET  /plugins -> get all the plugins.
     */
    @RequestMapping(value = "/plugins",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Plugin>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Plugin> page = pluginRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/plugins", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /plugins/:id -> get the "id" plugin.
     */
    @RequestMapping(value = "/plugins/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Plugin> get(@PathVariable Long id) {
        log.debug("REST request to get Plugin : {}", id);
        return Optional.ofNullable(pluginRepository.findOne(id))
            .map(plugin -> new ResponseEntity<>(
                plugin,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /plugins/:id -> delete the "id" plugin.
     */
    @RequestMapping(value = "/plugins/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Plugin : {}", id);
        pluginRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("plugin", id.toString())).build();
    }
}
