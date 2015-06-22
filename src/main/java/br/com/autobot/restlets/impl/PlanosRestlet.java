package br.com.autobot.restlets.impl;

import java.util.Date;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import br.com.autobot.jpa.service.impl.PlanosServiceImpl;
import br.com.autobot.restlets.PlanosRest;
import br.com.autobot.vo.Planos;

public class PlanosRestlet implements PlanosRest {

    private static final Logger logger = Logger.getLogger(PlanosRestlet.class);

    private PlanosServiceImpl service;

    public PlanosRestlet() {
        service = new PlanosServiceImpl();
    }

    public Response listar() {

        List<Planos> entity = null;

        try {

            entity = service.findAll();

        } catch (Exception e) {
            logger.error("erro", e);

        } finally {

        }

        return Response.ok(entity, MediaType.APPLICATION_JSON).build();

    }
    
    public Response create(@FormParam("plano") String plano) {
        return create(plano, "1");

    }

    public Response create(@FormParam("plano") String plano, @FormParam("idprojeto") String idprojeto) {

        String result = null;

        Planos entity = null;
        Long projeto = null;
  
        try {
            projeto = Long.parseLong(idprojeto);
        } catch (NumberFormatException e) {
            result = "Erro ao informar número do projeto";
        }

        try {

            entity = new Planos();
            entity.setPlano(plano);
            entity.setIdprojeto(projeto);
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
