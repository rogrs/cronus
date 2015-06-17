package br.com.autobot.restlets.impl;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.Form;

import br.com.api.persistence.generic.impl.UsuariosServiceImpl;
import br.com.api.persistence.generic.vo.Usuarios;
import br.com.api.restlets.forms.UserForm;
import br.com.api.security.util.Hash;
import br.com.autobot.restlets.UsuarioRest;


public class UsuariosRestlet implements UsuarioRest {

    private static final Logger logger = Logger.getLogger(UsuariosRestlet.class);

    private UsuariosServiceImpl service = null;

    public UsuariosRestlet() {

        service = new UsuariosServiceImpl();
    }
    

    public Response listAll() {

        List<Usuarios> entity = null;

        try {

            entity = service.findAll();
        } catch (Exception e) {
            logger.error("erro", e);

        } finally {

        }

        return Response.ok(entity, MediaType.APPLICATION_JSON).build();

    }

    public Response create(@Form UserForm form) {

        Usuarios entity = null;

        URI location = null;

        try {

            if (form.isValidPassword()) {
                entity = new Usuarios();
                entity.setUsername(form.getUsername());
                entity.setFullname(form.getFullname());
                entity.setRole(form.getRole());
                entity.setEnable(1);
                entity.setEmail(form.getEmail());
                entity.setDtcreate(new Date());
                entity.setSalt(Hash.userNameKey);
                entity.setPwd(Hash.encrypt(entity.getSalt(),form.getActualPassword()));

                service.persist(entity);

                location = new URI("../users-list.html");
            } else {
                location = new URI("../register.html");
            }

        } catch (Exception e) {
            logger.error("Erro ao executar create", e);

        } finally {

        }
        return Response.temporaryRedirect(location).build();
    }

    @Override
    public Response getByID(Long id) {

        URI location = null;
        try {

            service.find(id);
            location = new URI("../Usuarios.html");
        } catch (Exception e) {
            logger.error("Erro ao executar getByID " + id, e);
        }

        return Response.temporaryRedirect(location).build();
    }

    @Override
    public Response remove(Long id) {

        Usuarios entity = null;
        URI location = null;
        try {

            entity = new Usuarios(id);
            service.remove(entity);
            location = new URI("../Usuarios.html");
        } catch (Exception e) {
            logger.error("Erro ao executar remove " + id, e);
        }

        return Response.temporaryRedirect(location).build();

    }

}
