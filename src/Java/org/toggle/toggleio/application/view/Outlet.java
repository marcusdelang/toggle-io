package org.toggle.toggleio.application.view;

import org.toggle.toggleio.application.controller.OutletController;

/**
 * Class that functions controls a outlet
 */

public class Outlet {

  public static boolean isAlive() {
    return OutletController.isAlive();
  }

  public static boolean on() {
    return OutletController.on();
  }

  public static boolean off() {
    return OutletController.off();
  }
}
