package br.com.autobot.restlets;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(value = "/v1/propriedades/")
public interface PropriedadeRest {

    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    Response listar();
    
    @POST
    @Path("create")
    @Consumes("application/x-www-form-urlencoded")
    Response create(@FormParam("chave") String chave, @FormParam("valor") String valor);
    
    
}
