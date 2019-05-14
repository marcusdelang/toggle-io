package org.toggle.toggleio.application.model;


import org.json.JSONException;
import org.json.JSONObject;
import net.jstick.api.Tellstick;

/**
 * Class that performs operations on the tellstick using the operating systems terminal and calling
 * the program tdtool
 */
public class TellstickCore {
    final static int ERROR_CMD_NOT_SUPPORTED = 1;
    final static int SUCCESS = 0;
    final static int ERROR = 2;
    private final static String OUTLET_ON = "ON";
    private final static String OUTLET_OFF = "OFF";

    /**
     * Returns last command given to a device
     *
     * @param id
     * @return JSON with either status_power or status_dim depending on last command
     * @throws JSONException
     */
    public static JSONObject getStatus(int id) throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject();
            if (System.getProperty("os.name").equals("Linux")) {
                Tellstick tellstick = new Tellstick();
                String status = tellstick.getLastCmd(id).toLowerCase();

                if (status.equals("on") || status.equals("off"))
                    jsonObject.put("status_power", tellstick.getLastCmd(id).toLowerCase());
                else if (status.startsWith("dim")) {
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
     * Turns device with id on using connected tellstick and gives feedback on the success
     * as either true or false
     *
     * @return returns true if command was successfully executed
     */
    public static boolean on(int id) {
        return sendCommand(id, OUTLET_ON);
    }

    /**
     * Turns device with id off using connected tellstick and gives feedback on the success
     * as either true or false
     *
     * @return returns true if command was successfully executed
     */
    public static boolean off(int id) {
        return sendCommand(id, OUTLET_OFF);
    }

    /**
     * Dim a device and return true or false depending of the success of the command
     *
     * @param id     id of device to dim
     * @param amount amount of dim value between 0 and 255
     * @return returns true if command was successfully executed
     */
    public static int dim(int id, int amount) {
        return sendCommand(id, "DIM", amount);
    }

    private static boolean sendCommand(int id, String action) {
        Tellstick tellstick = new Tellstick();
        if(tellstick.getDeviceType(id).equals("selflearning-dimmer"))tellstick.sendCmd(id, action);
        if (tellstick.sendCmd(id, action) == 0) return true;

        return false;
    }

    private static int sendCommand(int id, String action, int amount) {
        return ScriptRunner.runScript(TelldusScripts.dim(id, amount));
    }
}
