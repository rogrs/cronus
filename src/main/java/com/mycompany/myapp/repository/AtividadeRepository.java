package com.mycompany.myapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.myapp.domain.Atividade;

/**
 * Spring Data JPA repository for the Atividade entity.
 */
public interface AtividadeRepository extends JpaRepository<Atividade,Long> {

    List<Atividade> findAtividadesByPlano(Long  idPlano);

}
