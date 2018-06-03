package br.com.rogrs.web.rest.custom;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExecuteShellComand {

	private final Logger log = LoggerFactory.getLogger(ExecuteShellComand.class);

	public String execute(String command) {
		
		if (command == null) {
			
			log.info(String.format("Comando nao foi informado %s", command));
		}

		StringBuffer output = new StringBuffer();

		log.info(String.format("Executando o comando %s", command));

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			log.error(String.format("Erro ao executar o comando %s", command), e);
		}

		return output.toString();
	}

}