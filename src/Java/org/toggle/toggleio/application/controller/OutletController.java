package org.toggle.toggleio.application.controller;

import org.toggle.toggleio.application.model.ScriptRunner;
import org.toggle.toggleio.application.model.TelldusScripts;

public class OutletController {
    public static boolean isAlive(){
        boolean alive = true;
        return alive;
    }
    public static boolean turnOn(){
        return ScriptRunner.runScript(TelldusScripts.on());
    }
    public static boolean turnOff(){
        return ScriptRunner.runScript(TelldusScripts.off());
    }
}
