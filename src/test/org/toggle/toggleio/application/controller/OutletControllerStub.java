package org.toggle.toggleio.application.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.toggle.toggleio.application.model.TellstickCoreStub;

public class OutletControllerStub extends OutletController {


    private TellstickCoreStub core;

    public OutletControllerStub() throws JSONException, Exception {
        super(new TellstickCoreStub());

        this.core = core;
    }


    public JSONObject status() {
        return core.getStatus();
    }

    public boolean on() {
        return core.on();
    }

    public boolean off() {
        return core.off();
    }
}

