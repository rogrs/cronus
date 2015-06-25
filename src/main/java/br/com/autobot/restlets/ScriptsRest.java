package br.com.autobot.restlets;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(value = "/v1/scripts/")
public interface ScriptsRest {

    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    Response listar();
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getByID(@PathParam("id") Long id);
    
    @POST
    @Path("/")
    @Consumes("application/x-www-form-urlencoded")
    Response create(@FormParam("script") String script, @FormParam("path") String path);
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response remove(@PathParam("id") Long id);
    
}
