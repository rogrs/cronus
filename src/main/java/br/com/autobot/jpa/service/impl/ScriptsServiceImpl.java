package br.com.autobot.jpa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.api.persistence.generic.GenericDAOJPA;
import br.com.api.persistence.service.GenericJPAService;
import br.com.autobot.vo.Scripts;



public class ScriptsServiceImpl extends GenericDAOJPA<Scripts, Long> implements GenericJPAService<Scripts> {

    public List<Scripts> findByName(String name) {

        String sql = "SELECT s FROM Scripts s WHERE s.script = :script";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("script", name);
        return findQuery(sql, params);

    }

}
