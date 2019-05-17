package br.com.rogrs.cronus.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.rogrs.cronus.domain.Review;
import br.com.rogrs.cronus.service.TeacherService;

@RestController
public class HomeController {

    @Autowired
    private final TeacherService teacherService;

    @Autowired
    public HomeController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/{id}/review")
    public ResponseEntity addReview(@RequestBody Review review, @PathVariable("id") String teacherID){

        try {
            teacherService.addReview(teacherID, review);
            return ResponseEntity.ok().build();
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }


    }

}
