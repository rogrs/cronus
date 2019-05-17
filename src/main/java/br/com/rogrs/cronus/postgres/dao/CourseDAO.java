package br.com.rogrs.cronus.postgres.dao;


import org.springframework.data.repository.CrudRepository;

import br.com.rogrs.cronus.domain.Course;

import java.util.UUID;

public interface CourseDAO extends CrudRepository<Course, UUID> {}