package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Plano;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Plano entity.
 */
public interface PlanoRepository extends JpaRepository<Plano,Long> {

}
