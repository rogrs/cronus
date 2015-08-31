package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.LogExecute;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LogExecute entity.
 */
public interface LogExecuteRepository extends JpaRepository<LogExecute,Long> {

}
