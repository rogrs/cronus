package br.com.autobot.jpa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.api.persistence.generic.GenericDAOJPA;
import br.com.api.persistence.service.GenericJPAService;
import br.com.autobot.vo.Planos;


public class PlanosServiceImpl extends GenericDAOJPA<Planos, Long> implements GenericJPAService<Planos> {

    public List<Planos> findByName(String name) {

        String sql = "SELECT p FROM Planos p WHERE p.plano = :plano";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("plano", name);
        return findQuery(sql, params);

    }

}
