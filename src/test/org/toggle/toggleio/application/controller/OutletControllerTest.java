package org.toggle.toggleio.application.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.toggle.toggleio.application.model.ScriptRunner;
import org.toggle.toggleio.application.model.TelldusScripts;

class OutletControllerTest {

  private OutletController outletController;
  private String script;
  private ScriptRunner scriptRunner;

  @BeforeEach
  void setUp() {
    //ScriptRunner scriptRunner = new ScriptRunner();
    //TelldusScript telldusScript = new TelldusScript();
    outletController = new OutletController();
    scriptRunner = new ScriptRunner();
    script = TelldusScripts.on();
  }

  @Test
  void isAlive() {
    assertTrue(outletController.isAlive());
  }

  @Test
  void turnOn() {
    assertEquals("tdtool --on 1", script);
    assertTrue(scriptRunner.runScript(script));
  }

  @Test
  void turnOff() {
    script = TelldusScripts.off();
    assertEquals(script, "tdtool --off 1");
    assertEquals(scriptRunner.runScript(script), true);
  }

}