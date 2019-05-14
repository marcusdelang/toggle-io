package org.toggle.toggleio.application.model;

/**
 * Complete scripts using the program tdtool and its functions
 */
public class TelldusScripts {

    public static String dim(int id, int amount){
        String scriptOff = "tdtool -v "+ amount + " -d "+ id;
        return scriptOff;
    }
}
