package br.com.autobot.util;

import org.junit.Test;

public class ExecuteShellComandTest {
    
    @Test
    public void runCommandPING(){
        
        ExecuteShellComand obj = new ExecuteShellComand();
        
        String domainName = "google.com";

        //in mac oxs
        String command = "ping -c 3 " + domainName;

        //in windows
        //String command = "ping -n 3 " + domainName;

        String output = obj.executeCommand(command);

        System.out.println(output);
        
    }

}
