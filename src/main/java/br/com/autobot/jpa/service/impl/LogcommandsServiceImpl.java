package br.com.autobot.jpa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.api.persistence.generic.GenericDAOJPA;
import br.com.api.persistence.service.GenericJPAService;
import br.com.autobot.vo.Logcommands;



public class LogcommandsServiceImpl extends GenericDAOJPA<Logcommands, Long> implements GenericJPAService<Logcommands> {

    public List<Logcommands> findByName(String name) {

        String sql = "SELECT l FROM Logcommands l WHERE l.info = :info";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("info", name);
        return findQuery(sql, params);

    }

}
