package br.com.autobot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.autobot.model.Planos;

@RepositoryRestResource(collectionResourceRel = "planosRel", path = "planos")
public interface PlanosRepository extends JpaRepository<Planos, Long> {

}
