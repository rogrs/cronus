package br.com.autobot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobot.model.Scripts;

public interface ScriptsRepository extends JpaRepository<Scripts, Long> {
}
