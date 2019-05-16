package org.toggle.toggleio.application.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The class is used to run scripts through bash in respective operatingsystem.
 *
 * @author Michel Ouadria
 * @version 1.0
 */
public class ScriptRunner {
final static int ERROR_CMD_NOT_SUPPORTED = 1;
final static int SUCCESS = 0;
final static int ERROR = 2;
    /**
     * Runs a given script on the operating systems terminal
     *
     * @param script to run
     * @return true if the command was writen successfully
     */
    public static int runScript(String script) {

        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(script);
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream()));

            String s = null;
            StringBuilder builder = new StringBuilder();
            while ((s = stdInput.readLine()) != null) {
                builder.append(s);
            }
            s = builder.toString();
            String[] output = s.split("-");
            try {
                if (output[1].equals(" The method you tried to use is not supported by the device")) return ERROR_CMD_NOT_SUPPORTED;
            } catch (IndexOutOfBoundsException ioobe) {
                return ERROR;
            }

        } catch (IOException e) {
            e.printStackTrace();

            return ERROR;
        }
        return SUCCESS;
    }
}