package br.com.rogrs.cronus;

import br.com.rogrs.cronus.web.rest.FilmsController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CronusApplicationTests {

	@Autowired
	private FilmsController filmsController;


	@Test
	void contextLoads() {
		Assertions.assertNotNull(filmsController);
	}

}
