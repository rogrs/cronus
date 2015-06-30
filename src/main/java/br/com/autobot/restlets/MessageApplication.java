package br.com.autobot.restlets;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import br.com.autobot.restlets.impl.AtividadesRestlet;
import br.com.autobot.restlets.impl.AuthenticationRestlet;
import br.com.autobot.restlets.impl.PlanosRestlet;
import br.com.autobot.restlets.impl.PluginsRestlet;
import br.com.autobot.restlets.impl.ProjetosRestlet;
import br.com.autobot.restlets.impl.PropriedadesRestlet;
import br.com.autobot.restlets.impl.ScriptsRestlet;
import br.com.autobot.restlets.impl.UsuariosRestlet;




@ApplicationPath("/rs")
public class MessageApplication extends Application {

    
    private Set<Object> singletons = new HashSet<Object>();

    public MessageApplication() {
        singletons.add(new AuthenticationRestlet());
        singletons.add(new PropriedadesRestlet());
        singletons.add(new UsuariosRestlet());  
        singletons.add(new PlanosRestlet());  
        singletons.add(new ProjetosRestlet()); 
        singletons.add(new PluginsRestlet()); 
        singletons.add(new ScriptsRestlet()); 
        singletons.add(new AtividadesRestlet()); 
    }
    

    @Override
    public Set<Object> getSingletons() {
            return singletons;
    }
    
 }