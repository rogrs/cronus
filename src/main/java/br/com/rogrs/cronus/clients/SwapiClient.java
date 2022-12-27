package br.com.rogrs.cronus.clients;

import br.com.rogrs.cronus.dto.Film;
import br.com.rogrs.cronus.dto.Planet;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "swapi", url = "https://swapi.dev/api")
public interface SwapiClient {


    @RequestMapping(method = RequestMethod.GET, value ="/planets/{id}")
    Planet getPlanet(@PathVariable("id") long id);

    @RequestMapping(method = RequestMethod.GET, value ="/films/{id}")
    Film getFilms(@PathVariable("id") long id);


}
