package org.toggle.toggleio.application.model;

import java.io.IOException;

/**
 * The class is used to perform scripts through bash for respective operatingsystem.
 *
 * @author Michel Ouadria
 * @version 1.0
 */
public class ScriptRunner {

    public static boolean runScript(String script) {

        boolean success = true;
        try {
                Runtime.getRuntime().exec(script);
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
            return success;
        }
        return success;
    }
}