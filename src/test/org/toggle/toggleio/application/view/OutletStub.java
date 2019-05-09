package org.toggle.toggleio.application.view;

import org.json.JSONException;
import org.json.JSONObject;
import org.toggle.toggleio.application.controller.OutletController;
import org.toggle.toggleio.application.controller.OutletControllerStub;
import org.toggle.toggleio.application.model.TellstickCore;
import org.toggle.toggleio.application.model.TellstickCoreStub;
import org.toggle.toggleio.application.view.Outlet;

public class OutletStub extends Outlet {
  private JSONObject status;

  public OutletStub() throws JSONException, Exception{
    super(new OutletControllerStub());

    this.status = new JSONObject();
      status.put("status_power", "off");
  }

  @Override
  public JSONObject getStatus() {
    return status;
  }

  public void putON(){
    try {
      status.put("status_power", "on");
    }catch (Exception e){}

  }
  public void putOFF(){
    try {
      status.put("status_power", "off");
    }catch (Exception e){}

  }
  @Override
  public boolean on() {
    putON();
    return true;
  }

  @Override
  public boolean off() {
    putOFF();
    return true;
  }
}
