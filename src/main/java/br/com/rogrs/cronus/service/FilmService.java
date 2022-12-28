package br.com.rogrs.cronus.service;

import br.com.rogrs.cronus.clients.SwapiClient;
import br.com.rogrs.cronus.dto.FilmDTO;
import br.com.rogrs.cronus.entity.Film;
import br.com.rogrs.cronus.mapper.FilmMapper;
import br.com.rogrs.cronus.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilmService {

    private final SwapiClient swapiClient;

    private final FilmMapper mapper;

    private final FilmRepository filmRepository;

    @Autowired
    public FilmService(SwapiClient swapiClient,FilmMapper filmMapper,FilmRepository filmRepository){
     this.filmRepository=filmRepository;
     this.mapper=filmMapper;
     this.swapiClient=swapiClient;

    }

    public FilmDTO getFilms(long id) {
        FilmDTO dto = swapiClient.getFilms(id);

        Film film = mapper.dtoToModel(dto);
        filmRepository.saveAndFlush(film);

        return dto;

    }
}
