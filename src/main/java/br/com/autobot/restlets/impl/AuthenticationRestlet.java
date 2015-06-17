package br.com.autobot.restlets.impl;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import br.com.api.persistence.generic.impl.UsuariosServiceImpl;
import br.com.api.persistence.generic.vo.Usuarios;
import br.com.api.restlets.forms.AuthenticationForm;
import br.com.autobot.restlets.AuthenticationRest;



public class AuthenticationRestlet implements AuthenticationRest {

    private static final Logger logger = Logger.getLogger(AuthenticationRestlet.class);

    private  UsuariosServiceImpl service;

    public AuthenticationRestlet() {

        service = new UsuariosServiceImpl();
          
    } 

    public Response session(AuthenticationForm form) {

        Usuarios usuario = null;
        try {

            usuario = service.authenticate(form.getUsername(), form.getActualPassword());

            
            if (usuario == null){
                usuario = new Usuarios("Não Válido");
            }

            
           
        } catch (Exception e) {
            logger.error("Erro ao executar AuthenticationRestlet.session()" , e);
        }
      

       return Response.ok(usuario).build();
      

    }

    @Override
    public Response logout(String userName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response credentials(String userName) {
        // TODO Auto-generated method stub
        return null;
    }

}
