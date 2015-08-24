package br.com.autobot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobot.model.Projetos;

public interface ProjetosRepository extends JpaRepository<Projetos, Long> {

}
