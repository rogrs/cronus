package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Atividade;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Atividade entity.
 */
public interface AtividadeRepository extends JpaRepository<Atividade,Long> {

}
