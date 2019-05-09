package org.toggle.toggleio.application.model;


import org.json.JSONException;
import org.json.JSONObject;
import net.jstick.api.Tellstick;

/**
 * Class that performs operations on the tellstick using the operating systems terminal and calling
 * the program tdtool
 */
public class TellstickCore {

    private final static String OUTLET_ON = "ON";
    private final static String OUTLET_OFF = "OFF";

    public static JSONObject getStatus(int id) throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject();
            if (System.getProperty("os.name").equals("Linux")) {
                Tellstick tellstick = new Tellstick();

                jsonObject.put("status_power", tellstick.getLastCmd(id));
                tellstick.close();
                return jsonObject;
            }
            jsonObject.put("status_power", "UNKNOWN");
            return jsonObject;
        } catch (JSONException jsonE) {
            throw new JSONException("Something went wrong with tellstick.getLastCmd");
        }
    }

    /**
     * Turns device with id 1 on tellduscenter ON using connected tellstick and gives feedback on the success
     * of writing the command to a terminal, it does NOT return the the success of the command itself.
     *
     * @return returns true if command was successfully executed in operating system terminal
     */
    public static boolean on(int id) {
        return sendCommand(id, OUTLET_ON);
    }

    /**
     * Turns device with id 1 on tellduscenter OFF using connected tellstick and gives feedback on the success
     * of writing the command to a terminal, it does NOT return the the success of the command itself.
     *
     * @return returns true if command was successfully executed in operating system terminal
     */
    public static boolean off(int id) {
        return sendCommand(id, OUTLET_OFF);
    }

    private static boolean sendCommand(int id, String action) {
        if (System.getProperty("os.name").equals("Linux")) {
            Tellstick tellstick = new Tellstick();
            tellstick.sendCmd(id, action);
            return true;
        }
        boolean success = ScriptRunner.runScript(TelldusScripts.on());
        try {
        } catch (Exception e) {
            return false;
        }
        return success;
    }

}
