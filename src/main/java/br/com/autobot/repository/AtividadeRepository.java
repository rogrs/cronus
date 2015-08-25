package br.com.autobot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.autobot.model.Atividades;


@RepositoryRestResource(collectionResourceRel = "atividadesRel", path = "atividades")
public interface AtividadeRepository extends JpaRepository<Atividades, Long>{

}
