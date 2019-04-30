package org.toggle.toggleio.application.view;

import org.json.JSONObject;
import org.toggle.toggleio.application.controller.OutletController;

/**
 * Class that functions controls a outlet
 */

public class Outlet {
  private OutletController controller;
  public Outlet(OutletController controller){
    this.controller = controller;
  }
  public JSONObject getStatus() {
    return controller.status();
  }

  public boolean on() {
    return controller.on();
  }

  public boolean off() {
    return controller.off();
  }
}
