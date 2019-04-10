package org.toggle.toggleio.application.model;

import java.io.IOException;

/**
 * The class is used to perform scripts through bash for respective operatingsystem.
 *
 * @author Michel Ouadria
 * @version 1.0
 */
public class ScriptRunner {
/*
    public static void main(String args[]) {
        String script = "tdtool --on 2";
        runScript(script);
    }
*/
    public static boolean runScript(String script) {
        String isWindows = "win";
        String isMac = "mac";
        String isUnix = "nix";

        boolean success = true;
        try {
            String OS = System.getProperty("os.name").toLowerCase();

            if (OS.equals(isWindows)) {
                Runtime.getRuntime().exec("powershell.exe " + script);
            } else if (OS.equals(isMac)) {
                Runtime.getRuntime().exec("/bin/bash " + script);
            } else if (OS.equals(isUnix)) {
                Runtime.getRuntime().exec("sh " + script);
            }
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
            return success;
        }

        return success;
    }
}
