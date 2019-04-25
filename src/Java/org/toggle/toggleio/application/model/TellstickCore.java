package org.toggle.toggleio.application.model;

public class TellstickCore {
  public static boolean on(){
    return  ScriptRunner.runScript(TelldusScripts.on());
  }
  public static boolean off(){
    return ScriptRunner.runScript(TelldusScripts.off());
  }

}
