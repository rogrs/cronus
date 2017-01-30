package br.com.rogrs.autobot.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.rogrs.autobot.domain.Plugin;

import br.com.rogrs.autobot.repository.PluginRepository;
import br.com.rogrs.autobot.repository.search.PluginSearchRepository;
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
 * REST controller for managing Plugin.
 */
@RestController
@RequestMapping("/api")
public class PluginResource {

    private final Logger log = LoggerFactory.getLogger(PluginResource.class);
        
    @Inject
    private PluginRepository pluginRepository;

    @Inject
    private PluginSearchRepository pluginSearchRepository;

    /**
     * POST  /plugins : Create a new plugin.
     *
     * @param plugin the plugin to create
     * @return the ResponseEntity with status 201 (Created) and with body the new plugin, or with status 400 (Bad Request) if the plugin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/plugins")
    @Timed
    public ResponseEntity<Plugin> createPlugin(@Valid @RequestBody Plugin plugin) throws URISyntaxException {
        log.debug("REST request to save Plugin : {}", plugin);
        if (plugin.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("plugin", "idexists", "A new plugin cannot already have an ID")).body(null);
        }
        Plugin result = pluginRepository.save(plugin);
        pluginSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/plugins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("plugin", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /plugins : Updates an existing plugin.
     *
     * @param plugin the plugin to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated plugin,
     * or with status 400 (Bad Request) if the plugin is not valid,
     * or with status 500 (Internal Server Error) if the plugin couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/plugins")
    @Timed
    public ResponseEntity<Plugin> updatePlugin(@Valid @RequestBody Plugin plugin) throws URISyntaxException {
        log.debug("REST request to update Plugin : {}", plugin);
        if (plugin.getId() == null) {
            return createPlugin(plugin);
        }
        Plugin result = pluginRepository.save(plugin);
        pluginSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("plugin", plugin.getId().toString()))
            .body(result);
    }

    /**
     * GET  /plugins : get all the plugins.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of plugins in body
     */
    @GetMapping("/plugins")
    @Timed
    public List<Plugin> getAllPlugins() {
        log.debug("REST request to get all Plugins");
        List<Plugin> plugins = pluginRepository.findAll();
        return plugins;
    }

    /**
     * GET  /plugins/:id : get the "id" plugin.
     *
     * @param id the id of the plugin to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the plugin, or with status 404 (Not Found)
     */
    @GetMapping("/plugins/{id}")
    @Timed
    public ResponseEntity<Plugin> getPlugin(@PathVariable Long id) {
        log.debug("REST request to get Plugin : {}", id);
        Plugin plugin = pluginRepository.findOne(id);
        return Optional.ofNullable(plugin)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /plugins/:id : delete the "id" plugin.
     *
     * @param id the id of the plugin to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/plugins/{id}")
    @Timed
    public ResponseEntity<Void> deletePlugin(@PathVariable Long id) {
        log.debug("REST request to delete Plugin : {}", id);
        pluginRepository.delete(id);
        pluginSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("plugin", id.toString())).build();
    }

    /**
     * SEARCH  /_search/plugins?query=:query : search for the plugin corresponding
     * to the query.
     *
     * @param query the query of the plugin search 
     * @return the result of the search
     */
    @GetMapping("/_search/plugins")
    @Timed
    public List<Plugin> searchPlugins(@RequestParam String query) {
        log.debug("REST request to search Plugins for query {}", query);
        return StreamSupport
            .stream(pluginSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
