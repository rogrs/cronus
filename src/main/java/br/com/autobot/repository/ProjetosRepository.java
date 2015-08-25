package br.com.autobot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.autobot.model.Projetos;


@RepositoryRestResource(collectionResourceRel = "projetosRel", path = "projetos")
public interface ProjetosRepository extends JpaRepository<Projetos, Long> {

}
