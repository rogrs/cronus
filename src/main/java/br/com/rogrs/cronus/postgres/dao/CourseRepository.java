package br.com.rogrs.cronus.postgres.dao;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.rogrs.cronus.domain.Course;

@RepositoryRestResource(collectionResourceRel = "course", path = "course")
public interface CourseRepository extends  PagingAndSortingRepository<Course, UUID> {}