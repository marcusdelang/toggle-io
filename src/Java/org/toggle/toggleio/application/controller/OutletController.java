package org.toggle.toggleio.application.controller;

import org.json.JSONObject;
import org.toggle.toggleio.application.model.ScriptRunner;
import org.toggle.toggleio.application.model.TelldusScripts;
import org.toggle.toggleio.application.model.TellstickCore;

public class OutletController {
    private TellstickCore core;
    public OutletController(TellstickCore core){
        this.core = core;
    }
    /**
     * Returns device status depending on type of device
     * @return JSONObject
     */
    public JSONObject status(){
        return core.getStatus();
    }
    public boolean on(){
        return core.on();
    }
    public boolean off(){
        return core.off();
    }
}
