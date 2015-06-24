package br.com.autobot.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class ExecuteShellComand {

    private static final Logger LOGGER = Logger.getLogger(ExecuteShellComand.class);

    public static String executeCommand(String command) {

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
            output.append("Erro ao executar o "+command+" - " + e.getMessage());
            LOGGER.error("Erro ao executar o comando executeCommand " + output, e);

        }

        return output.toString();

    }

}
