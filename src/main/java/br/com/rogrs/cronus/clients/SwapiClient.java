package br.com.rogrs.cronus.clients;

import br.com.rogrs.cronus.dto.FilmDTO;

import br.com.rogrs.cronus.dto.PeopleDTO;
import br.com.rogrs.cronus.dto.PlanetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "swapi", url = "https://swapi.dev/api")
public interface SwapiClient {


    @RequestMapping(method = RequestMethod.GET, value ="/planets/{id}")
    PlanetDTO getPlanet(@PathVariable("id") long id);

    @RequestMapping(method = RequestMethod.GET, value ="/films/{id}")
    FilmDTO getFilms(@PathVariable("id") long id);

    @RequestMapping(method = RequestMethod.GET, value ="/people/{id}")
    PeopleDTO getPeople(@PathVariable("id") long id);


}
