package br.com.rogrs.cronus.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import br.com.rogrs.cronus.domain.Review;
import br.com.rogrs.cronus.domain.Teacher;
import br.com.rogrs.cronus.postgres.dao.TeacherDAO;

@Service
public class SimpleTeacherService implements TeacherService {
    private final TeacherDAO teacherDAO;

    public SimpleTeacherService(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }
    
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addReview(String teacherID, Review review) {
        Objects.requireNonNull(teacherID);
        Objects.requireNonNull(review);
        Teacher teacher = teacherDAO
                .findById(UUID.fromString(teacherID))
                .orElseThrow(() -> new EntityNotFoundException(teacherID));
        review.setDate(LocalDate.now());
        if(teacher.getReviews() == null){
            teacher.setReviews(new ArrayList<>());
        }
        teacher.getReviews().add(review);
        teacherDAO.save(teacher);
    }
}