package br.com.rogrs.cronus.web.rest;

import br.com.rogrs.cronus.clients.SwapiClient;
import br.com.rogrs.cronus.dto.PlanetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlanetController {
    @Autowired
    private SwapiClient swapiClient;

    @GetMapping("/api/planets/{id}")
    public PlanetDTO getPlanetByID(@PathVariable("id") long id) {
        return swapiClient.getPlanet(id);
    }


}
