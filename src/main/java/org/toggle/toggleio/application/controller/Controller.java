package org.toggle.toggleio.application.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.toggle.toggleio.application.integration.JsonFile;
import org.toggle.toggleio.application.model.TellstickCore;

import java.io.IOException;


public class Controller {

    /**
     * Returns device status depending on type of device
     *
     * @return JSONObject
     */

    public JSONObject status(int id) throws JSONException {
        return TellstickCore.getStatus(id);
    }

    public boolean on(int id) {
        return TellstickCore.on(id);
    }

    public boolean off(int id) {
        return TellstickCore.off(id);
    }

    public synchronized void addDevice(int id, String token) {
        JsonFile.addDevice(id, token);
    }

    public synchronized void removeDevice(int id) {
        JsonFile.removeDevice(id);
    }

    public synchronized void setToken(int id, String token) {
        JsonFile.setToken(id, token);
    }

    public synchronized String getToken(int id) {
        return JsonFile.getToken(id);
    }


}
