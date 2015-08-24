package br.com.autobot.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
@Ignore
public class ExecuteShellComandTest {

    private String command;
    private String expected;


    public ExecuteShellComandTest(String expectedResult, String command) {
        this.command = command;
        this.expected = expectedResult;
    }

    // Declares parameters here
    @Parameters(name = "{index}: isValid({0}={1})")
    public static List<String[]> data() {

        return Arrays.asList(new String[][] { 
                { "Linux", "uname",  },  
                { "3.19.0-21-generic", "uname -r" }
        });

    }

    @Test
    public void run() {
        System.out.println(expected+" "+ExecuteShellComand.executeCommand(command));
        assertEquals(expected,ExecuteShellComand.executeCommand(command));
    }
    


}
