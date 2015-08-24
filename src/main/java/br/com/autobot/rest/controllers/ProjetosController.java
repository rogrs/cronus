package br.com.autobot.rest.controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.autobot.model.Projetos;
import br.com.autobot.repository.ProjetosRepository;

@RestController
@RequestMapping("/projetos")
public class ProjetosController {

	private final ProjetosRepository repository;

	@Autowired
	ProjetosController(ProjetosRepository repository) {
		this.repository = repository;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	Collection<Projetos> readProjetos() {	
		return this.repository.findAll();
	}
		
	@RequestMapping(value = "/{projetoId}", method = RequestMethod.GET)
	Projetos readProjeto(@PathVariable Long projetoId) {
		return this.repository.findOne(projetoId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?>  create(@RequestBody @Valid Projetos create) {
		 Projetos result = repository.save(create);
		 
		 HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setLocation(ServletUriComponentsBuilder
					.fromCurrentRequest().path("/{id}")
					.buildAndExpand(result.getId()).toUri());
		 
		 return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
	}
	

	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public void delete(@PathVariable Long id) {
		repository.delete(id);
	}

}
