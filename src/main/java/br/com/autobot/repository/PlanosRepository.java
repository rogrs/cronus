package br.com.autobot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobot.model.Planos;

public interface PlanosRepository extends JpaRepository<Planos, Long> {

}
