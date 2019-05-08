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
  private JSONObject status;
  private Tellstick ts;

  public TellstickCore(){
    status = new JSONObject();
    ts = new Tellstick(false);
    this.off();
  }
  public JSONObject getStatus() {
    return status;
  }

  /**
   * Turns device with id 1 on tellduscenter ON using connected tellstick and gives feedback on the success
   * of writing the command to a terminal, it does NOT return the the success of the command itself.
   * @return returns true if command was successfully executed in operating system terminal
   */
  public boolean on(){
    if  (System.getProperty("os.name").equals("Linux")){
    int stat = ts.sendCmd(1, OUTLET_ON);
    if(stat == 0) {
      try {
        status.put("status_power","on");
      }catch (JSONException e){
        return false;
      }

      return true;
    }
    else return false;
    }
    boolean success = ScriptRunner.runScript(TelldusScripts.on());
    try {
      status.put("status_power","on");
    }catch (Exception e){
      return false;
    }
    return success;
  }
  /**
   * Turns device with id 1 on tellduscenter OFF using connected tellstick and gives feedback on the success
   * of writing the command to a terminal, it does NOT return the the success of the command itself.
   * @return returns true if command was successfully executed in operating system terminal
   */
  public boolean off(){
    if  (System.getProperty("os.name").equals("Linux")){
      int stat = ts.sendCmd(1, OUTLET_OFF);
      if(stat == 0) {
        try {
          status.put("status_power", "off");
        }catch (JSONException e){
          return false;
        }

        return true;
      }
      else return false;
    }
    boolean success = ScriptRunner.runScript(TelldusScripts.off());
    try {
      status.put("status_power","off");
    }catch (Exception e){
      return false;
    }
    return success;
  }

}
