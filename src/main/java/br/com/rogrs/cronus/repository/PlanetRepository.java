package br.com.rogrs.cronus.repository;

import br.com.rogrs.cronus.entity.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanetRepository extends JpaRepository<Planet,Long> {

    Optional<Planet> findByName(String name);
}
