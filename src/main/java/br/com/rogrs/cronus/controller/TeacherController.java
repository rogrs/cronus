package br.com.rogrs.cronus.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.rogrs.cronus.domain.Review;
import br.com.rogrs.cronus.domain.Teacher;
import br.com.rogrs.cronus.postgres.dao.TeacherRepository;

@RestController
public class TeacherController {

    @Autowired
    private TeacherRepository teacherDAO;

   
    @PostMapping("/teachers/{id}/review")
    public ResponseEntity addReview(@RequestBody Review review, @PathVariable("id") String teacherID){

        try {
            //teacherService.addReview(teacherID, review);
            return ResponseEntity.ok().build();
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }


    }
    
    
    @GetMapping("/teachers")
    public ResponseEntity<Iterable<Teacher>> getAll(Pageable pageable) {
        
    	Iterable<Teacher> page = teacherDAO.findAll();
       
        return ResponseEntity.ok().body(page);
    }

}
