package com.mycompany.myapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mycompany.myapp.domain.Atividade;

/**
 * Spring Data JPA repository for the Atividade entity.
 */
public interface AtividadeRepository extends JpaRepository<Atividade,Long> {
	
	//@Query("select * from Atividade where plano_id = ?0")
	///@Query("select a from Atividade a where a.plano_id = ?")
   //// List<Atividade> findAtividadesByPlano(Long  idPlano);

}
