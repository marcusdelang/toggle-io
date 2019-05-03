package org.toggle.toggleio.server;

import org.json.JSONObject;
import org.toggle.toggleio.application.controller.OutletController;
import org.toggle.toggleio.application.model.TellstickCore;
import org.toggle.toggleio.application.view.Outlet;

public class OutletStub extends Outlet {
  JSONObject status;

  public OutletStub(OutletController controller) {
      super(new OutletController(new TellstickCore()));
    this.status = new JSONObject();
    status.put("status_power", "off");
  }

  @Override
  public JSONObject getStatus() {
    return status;
  }

  @Override
  public boolean on() {
    status.put("status_power", "on");
    return true;
  }

  @Override
  public boolean off() {
    status.put("status_power", "off");
    return true;
  }
}
