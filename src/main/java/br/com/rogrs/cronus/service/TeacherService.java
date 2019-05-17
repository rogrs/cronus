package br.com.rogrs.cronus.service;

import javax.validation.constraints.NotNull;

import br.com.rogrs.cronus.domain.Review;

public interface TeacherService {
    /**
     *
     * @param teacherID
     * @param review
     * @throws javax.persistence.EntityNotFoundException
     */
    void addReview(@NotNull String teacherID, @NotNull Review review);
}