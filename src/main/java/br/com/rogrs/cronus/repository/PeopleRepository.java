package br.com.rogrs.cronus.repository;

import br.com.rogrs.cronus.entity.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<People,Long> {
}
