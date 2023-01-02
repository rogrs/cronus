package br.com.rogrs.cronus.repository;

import br.com.rogrs.cronus.entity.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends JpaRepository<Planet,Long> {
}
