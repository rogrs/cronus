package br.com.autobot.restlets;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.Form;

import br.com.autobot.restlets.forms.AtividadesForm;

@Path(value = "/v1/atividades/")
public interface AtividadesRest {

    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    Response listar();
 
    @POST
    @Path("/")
    @Consumes("application/x-www-form-urlencoded")
     Response create(@Form AtividadesForm form);

}
