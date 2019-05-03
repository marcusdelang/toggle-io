package org.toggle.toggleio.application.model;



import org.json.JSONObject;

/**
 * Class that performs operations on the tellstick using the operating systems terminal and calling
 * the program tdtool
 */
public class TellstickCore {

  private JSONObject status;

  public TellstickCore(){
    status = new JSONObject();
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
    boolean success = ScriptRunner.runScript(TelldusScripts.on());
    status.put("status_power","on");
    return success;
  }
  /**
   * Turns device with id 1 on tellduscenter OFF using connected tellstick and gives feedback on the success
   * of writing the command to a terminal, it does NOT return the the success of the command itself.
   * @return returns true if command was successfully executed in operating system terminal
   */
  public boolean off(){
    boolean success = ScriptRunner.runScript(TelldusScripts.off());
    status.put("status_power","off");
    return success;
  }

}
