package br.com.autobot.restlets.impl;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.Form;

import br.com.autobot.jpa.service.impl.AtividadesServiceImpl;
import br.com.autobot.restlets.AtividadesRest;
import br.com.autobot.restlets.forms.AtividadesForm;
import br.com.autobot.vo.Atividades;


public class AtividadesRestlet implements AtividadesRest {

    private static final Logger logger = Logger.getLogger(AtividadesRestlet.class);

    private  AtividadesServiceImpl service = null;

    public AtividadesRestlet() {

        service = new AtividadesServiceImpl();
    }

    public Response listar() {

        List<Atividades> entity = null;

        try {

            entity = service.findAll();
        } catch (Exception e) {
            logger.error("erro", e);
        } finally {

        }

        return Response.ok(entity, MediaType.APPLICATION_JSON).build();

    }

    public Response create(@Form AtividadesForm form) {

        String result = null;
        Atividades entity = null;

        try {

            entity = new Atividades();
            entity.setIdplano(form.getIdplano());
            entity.setIdscript(form.getIdscript());
            entity.setIdusuario(form.getIdusuario());
            entity.setAtividade(form.getAtividade());
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
