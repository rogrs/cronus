package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Plugin;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Plugin entity.
 */
public interface PluginRepository extends JpaRepository<Plugin,Long> {

}
