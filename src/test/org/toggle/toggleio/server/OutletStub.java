package org.toggle.toggleio.server;

import org.json.JSONException;
import org.json.JSONObject;
import org.toggle.toggleio.application.controller.OutletController;
import org.toggle.toggleio.application.model.TellstickCore;
import org.toggle.toggleio.application.view.Outlet;

public class OutletStub extends Outlet {
  JSONObject status;

  public OutletStub(OutletController controller) throws JSONException {
      super(new OutletController(new TellstickCore()));
    this.status = new JSONObject();
    try {
      status.put("status_power", "off");
    }catch (Exception e){
      throw e;
    }

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
