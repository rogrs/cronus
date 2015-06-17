package br.com.autobot.restlets;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.Form;

import br.com.api.restlets.forms.UserForm;



//http://howtodoinjava.com/2013/06/26/jax-rs-resteasy-basic-authentication-and-authorization-tutorial/
@Path(value = "/v1/users/")
public interface UsuarioRest {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    Response listAll();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getByID(@PathParam("id") Long id);

    @POST
    @Path("/")
    @Consumes("application/x-www-form-urlencoded")
    Response create(@Form UserForm form);

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response remove(@PathParam("id") Long id);

}