package br.com.autobot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobot.model.Plugins;

public interface PluginsRepository extends JpaRepository<Plugins, Long> {

}
