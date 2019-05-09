package org.toggle.toggleio.application.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import java.io.IOException;

public class ControllerStub extends Controller {

    public ControllerStub(){
        super();
    }
    @Override
    public JSONObject status(int d) {
        if(d ==2) return null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status_power", "ON");
        } catch (JSONException jsone) {
            return null;
        }
        return jsonObject;
    }

    @Override
    public boolean on(int d) {
        if(d !=1) return false;
        return true;
    }

    @Override
    public boolean off(int d) {
        if(d !=1) return false;
        return true;
    }

    @Override
    public void writeJSON(String filename, org.json.simple.JSONObject jsonObject){

    }

    @Override
    public org.json.simple.JSONObject readJSON(String filename) {
        org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();
        JSONArray list = new JSONArray();
        org.json.simple.JSONObject device = new org.json.simple.JSONObject();
        org.json.simple.JSONObject device2 = new org.json.simple.JSONObject();
        device.put("token", "abcde12345");
        device.put("id",1);
        list.add(device);
        device2.put("token", "12345abcde");
        device2.put("id",2);
        list.add(device2);
        jsonObject.put("devices", list);
        return jsonObject;
    }
}

