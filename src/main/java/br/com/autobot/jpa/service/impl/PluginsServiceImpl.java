package br.com.autobot.jpa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.api.persistence.generic.GenericDAOJPA;
import br.com.api.persistence.service.GenericJPAService;
import br.com.autobot.vo.Plugins;


public class PluginsServiceImpl extends GenericDAOJPA<Plugins, Long> implements GenericJPAService<Plugins> {

    public List<Plugins> findByName(String name) {

        String sql = "SELECT p FROM Plugins p WHERE p.plugin = :plugin";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("plugin", name);
        return findQuery(sql, params);

    }

}
