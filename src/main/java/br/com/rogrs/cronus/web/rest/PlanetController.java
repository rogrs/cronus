package br.com.rogrs.cronus.web.rest;


import br.com.rogrs.cronus.entity.Planet;
import br.com.rogrs.cronus.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlanetController {
    @Autowired
    private PlanetService planetService;

    @GetMapping("/api/planets/{id}")
    public Planet getPlanetByID(@PathVariable("id") Long id) {
        return planetService.getPlanet(id);
    }


}
