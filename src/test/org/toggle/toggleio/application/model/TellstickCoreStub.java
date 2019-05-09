package org.toggle.toggleio.application.model;

import net.jstick.api.Tellstick;
import org.json.JSONException;
import org.json.JSONObject;

public class TellstickCoreStub extends TellstickCore {
    private JSONObject statuss;

   public TellstickCoreStub() throws Exception {
       super();
       statuss = new JSONObject();
       statuss.put("status_power", "off");
    }

    @Override
    public boolean on() {
       statuss = new JSONObject();
        try {
            statuss.put("status_power", "on");
        } catch (JSONException jsonE) {
            return false;
        }
        return true;
    }

    @Override
    public boolean off() {
        statuss = new JSONObject();
        try {
            statuss.put("status_power", "off");
        } catch (JSONException jsonE) {
            return false;
        }
        return true;
    }
}
