package br.com.autobot.jpa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.api.persistence.generic.GenericDAOJPA;
import br.com.api.persistence.service.GenericJPAService;
import br.com.autobot.vo.Projetos;


public class ProjetosServiceImpl extends GenericDAOJPA<Projetos, Long> implements GenericJPAService<Projetos> {

    public List<Projetos> findByName(String name) {
        String sql = "SELECT p FROM Projeto p WHERE p.projeto = :projeto";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("projeto", name);
        return findQuery(sql, params);

    }

}
