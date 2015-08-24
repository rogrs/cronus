package br.com.autobot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.autobot.model.Scripts;

@RepositoryRestResource(collectionResourceRel = "scriptsRel", path = "scripts")
public interface ScriptsRepository extends JpaRepository<Scripts, Long> {
}
