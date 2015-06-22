package br.com.autobot.restlets.impl;

import java.util.Date;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import br.com.autobot.jpa.service.impl.ProjetosServiceImpl;
import br.com.autobot.restlets.ProjetosRest;
import br.com.autobot.vo.Projetos;


public class ProjetosRestlet implements ProjetosRest {

    private static final Logger logger = Logger.getLogger(ProjetosRestlet.class);

    private  ProjetosServiceImpl service = null;

    public ProjetosRestlet() {

        service = new ProjetosServiceImpl();
    }

    public Response listar() {

        List<Projetos> entity = null;

        try {

            entity = service.findAll();
        } catch (Exception e) {
            logger.error("erro", e);
        } finally {

        }

        return Response.ok(entity, MediaType.APPLICATION_JSON).build();

    }

    public Response create(@FormParam("projeto") String projeto) {

        String result = null;
        Projetos entity = null;

        try {

            entity = new Projetos();
            entity.setProjeto(projeto);
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
