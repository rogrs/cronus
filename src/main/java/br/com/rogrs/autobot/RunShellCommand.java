package br.com.rogrs.autobot;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RunShellCommand {

	private final Logger log = LoggerFactory.getLogger(RunShellCommand.class);

	public String execute(String command) {

		log.info("Executando o comando " + command);
		StringBuffer output = new StringBuffer();

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
			log.error("Erro na execução do comando  " + command, e);
		}

		return output.toString();

	}
}
