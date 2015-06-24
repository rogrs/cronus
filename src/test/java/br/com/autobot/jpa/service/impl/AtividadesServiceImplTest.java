package br.com.autobot.jpa.service.impl;

import org.junit.Test;

import br.com.autobot.vo.Atividades;

public class AtividadesServiceImplTest {
    
    private  AtividadesServiceImpl service  = null;
    
    
    
    @Test
    public void criarAtividadeTest(){
        
        
        Atividades entity = new  Atividades();
       
        
        service = new AtividadesServiceImpl();
        service.persist(entity);
        
    }

}
