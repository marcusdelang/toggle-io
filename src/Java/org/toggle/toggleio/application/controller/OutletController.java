package org.toggle.toggleio.application.controller;

import org.toggle.toggleio.application.model.ScriptRunner;
import org.toggle.toggleio.application.model.TelldusScripts;
import org.toggle.toggleio.application.model.TellstickCore;

public class OutletController {
    public static boolean isAlive(){
        boolean alive = true;
        return alive;
    }
    public static boolean on(){

        return TellstickCore.on();
    }
    public static boolean off(){

        return TellstickCore.off();
    }
}
