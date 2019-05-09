package org.toggle.toggleio.application.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.toggle.toggleio.application.model.TellstickCore;

public class OutletController {
    /**
     * Returns device status depending on type of device
     * @return JSONObject
     */
    public static JSONObject status(int id)throws JSONException {
        return TellstickCore.getStatus(id);
    }
    public static boolean on(int id){
        return TellstickCore.on(id);
    }
    public static boolean off(int id){
        return TellstickCore.off(id);
    }
}
