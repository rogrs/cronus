package br.com.autobot.restlets.impl;

import java.util.Date;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import br.com.autobot.jpa.service.impl.ScriptsServiceImpl;
import br.com.autobot.restlets.ScriptsRest;
import br.com.autobot.vo.Scripts;


public class ScriptsRestlet implements ScriptsRest {

    private static final Logger logger = Logger.getLogger(ScriptsRestlet.class);

    private  ScriptsServiceImpl service = null;

    public ScriptsRestlet() {

        service = new ScriptsServiceImpl();
    }

    public Response listar() {

        List<Scripts> entity = null;

        try {

            entity = service.findAll();
        } catch (Exception e) {
            logger.error("erro", e);
        } finally {

        }

        return Response.ok(entity, MediaType.APPLICATION_JSON).build();

    }

    public Response create(@FormParam("script") String script,@FormParam("path") String path) {

        String result = null;
        Scripts entity = null;

        try {

            entity = new Scripts();
            entity.setScript(script);
            entity.setPath(path);
            entity.setEnable(1);
            entity.setDtcreate(new Date());
            service.persist(entity);

            result = "sucesso";
        } catch (Exception e) {
            logger.error("erro", e);
            result = "Error " + e.getMessage();
        } finally {

        }
        return Response.ok(result).build();

    }

    @Override
    public Response getByID(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response remove(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

}
