package br.com.rogrs.cronus.web.rest;

import br.com.rogrs.cronus.clients.SwapiClient;
import br.com.rogrs.cronus.dto.PeopleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PeoplesController {
    @Autowired
    private SwapiClient swapiClient;

    @GetMapping("/api/people/{id}")
    public PeopleDTO getPeopleByID(@PathVariable("id") long id) {
        return swapiClient.getPeople(id);
    }


}
