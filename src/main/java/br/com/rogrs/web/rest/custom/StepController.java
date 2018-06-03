package br.com.rogrs.web.rest.custom;

import java.net.URISyntaxException;
import java.util.List;

//https://www.callicoder.com/hibernate-spring-boot-jpa-one-to-many-mapping-example/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rogrs.domain.Project;
import br.com.rogrs.domain.Step;
import br.com.rogrs.repository.ProjectRepository;
import br.com.rogrs.repository.StepRepository;
import br.com.rogrs.web.rest.errors.BadRequestAlertException;

@RestController
@RequestMapping("/v1")
public class StepController {

	private final Logger log = LoggerFactory.getLogger(StepController.class);

	private ExecuteShellComand executeShellComand;

	private final ProjectRepository projectRepository;

	private final StepRepository stepRepository;

	@Autowired
	public StepController(ExecuteShellComand command, ProjectRepository repository, StepRepository step) {
		this.projectRepository = repository;
		this.stepRepository = step;
		this.executeShellComand = command;
	}

	@RequestMapping("/clone")
	public ResponseEntity<String> clone(@RequestParam(value = "branch") String branch) throws URISyntaxException {

		log.info(String.format("Clonando o branch  %s", branch));

		Project prj = projectRepository.findByName(branch);

		if (prj == null) {
			 throw new BadRequestAlertException("Projeto não encontrado!", branch, "branchnotexists");
			
		} else {

			List<Step> steps = stepRepository.findByProject(prj);

			for (Step s : steps) {

				executeShellComand.execute(s.getCommand());
			}

		}

		return new ResponseEntity<String>(String.format("Successful", branch), HttpStatus.OK);
	}

}