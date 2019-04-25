package org.toggle.toggleio.application.model;

/**
 * Class that performs operations on the tellstick using the operating systems terminal and calling
 * the program tdtool
 */
public class TellstickCore {
  /**
   * Turns device with id 1 on tellduscenter ON using connected tellstick and gives feedback on the success
   * of writing the command to a terminal, it does NOT return the the success of the command itself.
   * @return returns true if command was successfully executed in operating system terminal
   */
  public static boolean on(){
    return  ScriptRunner.runScript(TelldusScripts.on());
  }
  /**
   * Turns device with id 1 on tellduscenter OFF using connected tellstick and gives feedback on the success
   * of writing the command to a terminal, it does NOT return the the success of the command itself.
   * @return returns true if command was successfully executed in operating system terminal
   */
  public static boolean off(){
    return ScriptRunner.runScript(TelldusScripts.off());
  }

}
