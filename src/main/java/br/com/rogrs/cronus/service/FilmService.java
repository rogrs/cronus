package br.com.rogrs.cronus.service;

import br.com.rogrs.cronus.clients.SwapiClient;
import br.com.rogrs.cronus.dto.FilmDTO;
import br.com.rogrs.cronus.entity.Film;
import br.com.rogrs.cronus.exceptions.EntityNotFoundException;
import br.com.rogrs.cronus.repository.FilmRepository;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FilmService {

    private final SwapiClient swapiClient;

    private final FilmRepository filmRepository;

    @Autowired
    public FilmService(SwapiClient swapiClient,FilmRepository filmRepository){
     this.filmRepository=filmRepository;
     this.swapiClient=swapiClient;
    }

    public Film getFilms(Long id) {
        return getFilm(id);
    }

    private Film getFilm(Long id) {
        FilmDTO dto = null;

        try {
        dto = swapiClient.getFilms(id);
         } catch (FeignException.FeignClientException e ) {
            throw new EntityNotFoundException(e.getMessage());
        }


            Optional<Film> optionalFilm = filmRepository.findByTitle(dto.getTitle());
            Film film = null;
            if (optionalFilm.isEmpty()) {
                ModelMapper modelMapper = new ModelMapper();
                film = modelMapper.map(dto, Film.class);
                return filmRepository.saveAndFlush(film);
            } else {
                return optionalFilm.get();
            }


    }
}
