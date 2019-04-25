package org.toggle.toggleio.application.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.toggle.toggleio.application.model.ScriptRunner;
import org.toggle.toggleio.application.model.TelldusScripts;

class OutletControllerTest {
  @Test
  void isAlive() {
    assertTrue(OutletController.isAlive());
  }
}