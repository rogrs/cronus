package br.com.autobot.jpa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.api.persistence.generic.GenericDAOJPA;
import br.com.api.persistence.service.GenericJPAService;
import br.com.autobot.vo.Atividades;


public class AtividadesServiceImpl extends GenericDAOJPA<Atividades, Long> implements GenericJPAService<Atividades> {

    public List<Atividades> findByName(String name) {
        
        String sql = "SELECT a FROM Atividades a WHERE a.atividade = :atividade";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("atividade", name);
        return findQuery(sql, params);
       
    }
    

}
