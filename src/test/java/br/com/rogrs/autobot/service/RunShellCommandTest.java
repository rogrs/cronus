package br.com.rogrs.autobot.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.rogrs.autobot.AutobotApp;
import br.com.rogrs.autobot.RunShellCommand;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutobotApp.class)
@Transactional
public class RunShellCommandTest {

	@Autowired
	private RunShellCommand runShellCommand;
	
	

	@Test
	public void assertCommand() {

		String resultado = runShellCommand.execute("ls -l /");
		
		assertNotNull(resultado);
	}

}
