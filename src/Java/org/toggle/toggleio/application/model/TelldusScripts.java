package org.toggle.toggleio.application.model;

public class TelldusScripts {
    public static String on(){
        String scriptOn = "tdtool --on 2";
        return scriptOn;
    }
    public static String off(){
        String scriptOff = "tdtool --off 2";
        return scriptOff;
    }
}
