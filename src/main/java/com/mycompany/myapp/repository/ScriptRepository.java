package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Script;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Script entity.
 */
public interface ScriptRepository extends JpaRepository<Script,Long> {

}
