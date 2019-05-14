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
                String status = tellstick.getLastCmd(id).toLowerCase();

                if(status.equals("on")||status.equals("off")) jsonObject.put("status_power", tellstick.getLastCmd(id).toLowerCase());
                else if (status.startsWith("dim")){
                    String[] statusArray = status.split(" ");
                    jsonObject.put("status_dim", statusArray[1]);
                }
                tellstick.close();
                return jsonObject;
            }
            jsonObject.put("status_power", "unknown");
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

    public static boolean dim(int id, int amount) {
        return sendCommand(id, "DIM", amount);
    }

    private static boolean sendCommand(int id, String action) {
        if (System.getProperty("os.name").equals("Linux")) {
            Tellstick tellstick = new Tellstick();
            if (tellstick.sendCmd(id, action) == 0) return true;
        }
        return false;
    }

    private static boolean sendCommand(int id, String action, int amount) {
        if (System.getProperty("os.name").equals("Linux")) {
        return ScriptRunner.runScript(TelldusScripts.dim(id, amount));
        }
        return false;
    }
}
