package br.com.autobot.restlets.impl;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import br.com.api.persistence.generic.impl.PropriedadeServiceImpl;
import br.com.api.persistence.generic.vo.Propriedades;
import br.com.autobot.restlets.PropriedadeRest;


public class PropriedadesRestlet implements PropriedadeRest {

    private static final Logger logger = Logger.getLogger(PropriedadesRestlet.class);

    private PropriedadeServiceImpl service = null;

    public PropriedadesRestlet() {

        service = new PropriedadeServiceImpl();
    }

    public Response listar() {

        List<Propriedades> entity = null;
        try {
            entity = service.findAll();
        } catch (Exception e) {
            logger.error("erro", e);
        } finally {

        }

        return Response.ok(entity, MediaType.APPLICATION_JSON).build();

    }

    public Response create(@FormParam("chave") String chave, @FormParam("valor") String valor) {

        String result = null;
       
        Propriedades entity = null;

        try {
  

            entity = new Propriedades();
            entity.setChave(chave);
            entity.setValor(valor);
            entity.setEnabled(1);
            service.persist(entity);

            result = "sucesso";
        } catch (Exception e) {
            logger.error("erro", e);
            result = "Error " + e.getMessage();
        } finally {

        }
        return Response.ok(result).build();

    }

}
