package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Projeto;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Projeto entity.
 */
public interface ProjetoRepository extends JpaRepository<Projeto,Long> {

}
