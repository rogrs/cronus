package com.mycompany.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.myapp.domain.Plugin;

/**
 * Spring Data JPA repository for the Plugin entity.
 */
public interface PluginRepository extends JpaRepository<Plugin,Long> {

}
