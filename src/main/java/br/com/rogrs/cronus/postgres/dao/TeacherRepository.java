package br.com.rogrs.cronus.postgres.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.rogrs.cronus.domain.Teacher;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "teacher", path = "teacher")
public interface TeacherRepository extends CrudRepository<Teacher, UUID> {}