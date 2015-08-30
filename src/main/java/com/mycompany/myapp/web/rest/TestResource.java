package com.mycompany.myapp.web.rest;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Plugin;
import com.mycompany.myapp.domain.Script;
import com.mycompany.myapp.repository.ScriptRepository;
import com.mycompany.myapp.util.ExecuteShellCommand;
import com.mycompany.myapp.web.rest.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class TestResource {

    private final Logger log = LoggerFactory.getLogger(TestResource.class);

    @Autowired
    private ScriptRepository scriptRepository;

    @RequestMapping(value = "/execute/plano/{planoID}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> executarPlano(@PathVariable("planoID") final String planoID) {
        Long idPlano = null;

        try {
            idPlano = Long.parseLong(planoID);
        } catch (NumberFormatException nfe) {
            log.error("NumberFormatException: " + nfe.getMessage(), nfe);
        }

        ExecuteShellCommand esc = null;
        Collection<Script> sripts = null;
        esc = new ExecuteShellCommand();
        log.info("Iniciando a execução de scripts do plano " + idPlano);
        sripts = new ArrayList<Script>();
       // sripts = scriptRepository.findByPlano(idPlano);
        log.info("Total de scripts para execução " + sripts.size());
        for (Script script : sripts) {
            log.info("Executando o script " + script.getDescricao());
            Plugin plugin = script.getPlugin();
            String command = plugin.getComando() + " " + script.getDescricao();
            log.info(esc.executeCommand(command));
        }
        log.info("Finalizado a execução de scripts do plano " + idPlano);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("Plano", planoID.toString())).build();
    }

    @RequestMapping(value = "/execute/script/{scriptID}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> executarScript(@PathVariable("scriptID") final String scriptID) {
        Long idScript = null;

        try {
            idScript = Long.parseLong(scriptID);
        } catch (NumberFormatException nfe) {
            log.error("NumberFormatException: " + nfe.getMessage(), nfe);
        }

        ExecuteShellCommand esc = null;
        Script script = null;
        try {
            esc = new ExecuteShellCommand();
            script = scriptRepository.findOne(idScript);
            log.info("Iniciando a execução do script " + script);
            Plugin plugin = script.getPlugin();
            String command = plugin.getComando() + " " + script.getDescricao();
            log.info(esc.executeCommand(command));
        } catch (Exception ex) {
            log.info("Erro na execução do script " + idScript, ex);
        } finally {
            log.info("Finalizado a execução script " + idScript);
        }

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("Script", scriptID.toString())).build();
    }

}
