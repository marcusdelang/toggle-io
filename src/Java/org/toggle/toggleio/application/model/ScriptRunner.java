package org.toggle.toggleio.application.model;

import java.io.IOException;

public class ScriptRunner {
    public static boolean runScript(String script){
        boolean success = true;
        try {
            Process process = Runtime.getRuntime().exec("powershell.exe " + script);

        }
        catch (IOException e) {
            e.printStackTrace();
            success = false;
            return success;
        }

        return success;
    }
}
