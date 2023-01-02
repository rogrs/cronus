package br.com.rogrs.cronus.web.rest;

import br.com.rogrs.cronus.dto.FilmDTO;
import br.com.rogrs.cronus.entity.Film;
import br.com.rogrs.cronus.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilmsController {
    @Autowired
    private FilmService filmService;

    @GetMapping("/api/films/{id}")
    public Film getFilmsByID(@PathVariable("id") long id) {
        return filmService.getFilms(id);
    }


}
