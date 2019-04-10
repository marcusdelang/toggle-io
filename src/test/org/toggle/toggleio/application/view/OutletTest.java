package org.toggle.toggleio.application.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OutletTest {

  @BeforeEach
  void setUp() {
  }

  @Test
  void isAliveTest() {
    assertTrue(Outlet.isAlive());
  }

  @Test
  void onTest() {
    assertTrue(Outlet.on());
  }

  @Test
  void offTest() {
    assertTrue(Outlet.off());
  }
}