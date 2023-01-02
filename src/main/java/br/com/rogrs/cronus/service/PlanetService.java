package br.com.rogrs.cronus.service;

import br.com.rogrs.cronus.clients.SwapiClient;
import br.com.rogrs.cronus.dto.PlanetDTO;
import br.com.rogrs.cronus.entity.Planet;
import br.com.rogrs.cronus.exceptions.EntityNotFoundException;
import br.com.rogrs.cronus.repository.PlanetRepository;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlanetService {

    private final SwapiClient swapiClient;

    private final PlanetRepository planetRepository;

    @Autowired
    public PlanetService(SwapiClient swapiClient, PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
        this.swapiClient = swapiClient;
    }

    public Planet getPlanet(Long id) {
        PlanetDTO dto = null;

        try {
            dto = swapiClient.getPlanet(id);
        } catch (FeignException.FeignClientException e) {
            throw new EntityNotFoundException(e.getMessage());
        }

        Optional<Planet> optionalPlanet = planetRepository.findByName(dto.getName());

        if (optionalPlanet.isEmpty()) {
            ModelMapper modelMapper = new ModelMapper();
            Planet planet = modelMapper.map(dto, Planet.class);
            return planetRepository.saveAndFlush(planet);
        } else {
            return optionalPlanet.get();
        }
    }


}
