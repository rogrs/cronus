package br.com.autobot.restlets.impl;

import java.util.Date;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import br.com.autobot.jpa.service.impl.PluginsServiceImpl;
import br.com.autobot.restlets.PluginsRest;
import br.com.autobot.vo.Plugins;


public class PluginsRestlet implements PluginsRest {

    private static final Logger logger = Logger.getLogger(PluginsRestlet.class);

    private  PluginsServiceImpl service = null;

    public PluginsRestlet() {

        service = new PluginsServiceImpl();
    }

    public Response listar() {

        List<Plugins> entity = null;

        try {

            entity = service.findAll();
        } catch (Exception e) {
            logger.error("erro", e);
        } finally {

        }

        return Response.ok(entity, MediaType.APPLICATION_JSON).build();

    }

    public Response create(@FormParam("plugin") String plugin, @FormParam("command") String command) {

        String result = null;
        Plugins entity = null;

        try {

            entity = new Plugins();
            entity.setPlugin(plugin);
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

}
