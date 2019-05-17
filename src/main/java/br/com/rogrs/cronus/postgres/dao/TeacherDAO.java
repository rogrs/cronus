package br.com.rogrs.cronus.postgres.dao;

import org.springframework.data.repository.CrudRepository;

import br.com.rogrs.cronus.domain.Teacher;

import java.util.UUID;

public interface TeacherDAO extends CrudRepository<Teacher, UUID> {}