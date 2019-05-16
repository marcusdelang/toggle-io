package org.toggle.toggleio.application.controller;

import org.json.JSONException;
import org.json.JSONObject;

public class ControllerStub extends Controller {

    /**
     * Returns device status depending on type of device
     * @return JSONObject
     */

    @Override
    public JSONObject status(int id) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status_power", "on");
        return jsonObject;
    }

    @Override
    public boolean on(int id) {

        return true;
    }

    @Override
    public boolean off(int id) {
        return true;
    }

    @Override
    public int dim(int id, int amount) {
        return 0;
    }
    @Override
    public synchronized void setToken(int id, String token) {

    }
    @Override
    public synchronized String getToken(int id) {
        return "abcde12345";
    }
}
