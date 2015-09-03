package com.mycompany.myapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
import com.mycompany.myapp.domain.Atividade;
import com.mycompany.myapp.domain.Plugin;
import com.mycompany.myapp.domain.Script;
import com.mycompany.myapp.repository.AtividadeRepository;
import com.mycompany.myapp.repository.ScriptRepository;
import com.mycompany.myapp.util.ExecuteShellCommand;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Atividade.
 */
@RestController
@RequestMapping("/api")
public class AtividadeResource {

    private final Logger log = LoggerFactory.getLogger(AtividadeResource.class);

    @Inject
    private AtividadeRepository atividadeRepository;

    @Inject
    private ScriptRepository scriptRepository;


    /**
     * POST /atividades -> Create a new atividade.
     */
    @RequestMapping(value = "/atividades", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Atividade> create(@RequestBody Atividade atividade) throws URISyntaxException {
        log.debug("REST request to save Atividade : {}", atividade);
        if (atividade.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new atividade cannot already have an ID").body(null);
        }
        Atividade result = atividadeRepository.save(atividade);
        return ResponseEntity.created(new URI("/api/atividades/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("atividade", result.getId().toString())).body(result);
    }

    /**
     * PUT /atividades -> Updates an existing atividade.
     */
    @RequestMapping(value = "/atividades", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Atividade> update(@RequestBody Atividade atividade) throws URISyntaxException {
        log.debug("REST request to update Atividade : {}", atividade);
        if (atividade.getId() == null) {
            return create(atividade);
        }
        Atividade result = atividadeRepository.save(atividade);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("atividade", atividade.getId().toString())).body(result);
    }

    /**
     * GET /atividades -> get all the atividades.
     */
    @RequestMapping(value = "/atividades", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Atividade>> getAll(@RequestParam(value = "page", required = false) Integer offset,
            @RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {
        Page<Atividade> page = atividadeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/atividades", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /atividades/:id -> get the "id" atividade.
     */
    @RequestMapping(value = "/atividades/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Atividade> get(@PathVariable Long id) {
        log.debug("REST request to get Atividade : {}", id);
        return Optional.ofNullable(atividadeRepository.findOne(id)).map(atividade -> new ResponseEntity<>(atividade, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE /atividades/:id -> delete the "id" atividade.
     */
    @RequestMapping(value = "/atividades/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Atividade : {}", id);
        atividadeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("atividade", id.toString())).build();
    }

    @RequestMapping(value = "/atividades/plano/{idplano}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> executarPlano(@PathVariable("idplano") final Long idplano) {

        try {
            ExecuteShellCommand esc = new ExecuteShellCommand();
            log.info("Iniciando a execução de scripts do plano " + idplano);
            List<Atividade> atividades = new ArrayList<Atividade>();
            atividades = atividadeRepository.findAtividadesByPlano(idplano);
            log.info("Total de atividade para execução " + atividades.size());
            for (Atividade atividade : atividades) {
                log.info("Iniciando o atividade " + atividade.getDescricao());
                Script script = atividade.getScript();
                log.info("Executando o script " + script.getDescricao());
                Plugin plugin = script.getPlugin();

                String command = plugin.getComando() + " " + script.getDescricao();
                log.info(esc.executeCommand(command));
            }

        } catch (Exception e) {
            log.error("Erro " + e.getMessage(), e);
        } finally {
            log.info("Finalizado a execução de atividades do plano " + idplano);
        }

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("Plano", idplano.toString())).build();
    }

    @RequestMapping(value = "/atividades/script/{idscript}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> executarScript(@PathVariable("idscript") final Long idscript) {

        ExecuteShellCommand esc = null;
        Script script = null;
        try {
            esc = new ExecuteShellCommand();
            script = scriptRepository.findOne(idscript);
            log.info("Iniciando a execução do script " + script);
            Plugin plugin = script.getPlugin();
            String command = plugin.getComando() + " " + script.getDescricao();
            log.info(esc.executeCommand(command));
        } catch (Exception ex) {
            log.info("Erro na execução do script " + idscript, ex);
        } finally {
            log.info("Finalizado a execução script " + idscript);
        }

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("Script", idscript.toString())).build();
    }

}
