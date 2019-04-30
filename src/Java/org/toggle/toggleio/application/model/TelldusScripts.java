package org.toggle.toggleio.application.model;

/**
 * Complete scripts using the program tdtool and its functions
 */
public class TelldusScripts {

    /**
     * Returns a complete script for turning a device ON using tdtool
     * @return Script for turning device ON
     */
    public static String on(){
        String scriptOn = "tdtool --on 2";
        return scriptOn;
    }

    /**
     * Returns a complete script for turning a device OFF using tdtool
     * @return Script for turning device OFF
     */
    public static String off(){
        String scriptOff = "tdtool --off 2";
        return scriptOff;
    }
}
