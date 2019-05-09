package org.toggle.toggleio.application.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
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

    public void writeJSON(String filename, org.json.simple.JSONObject jsonObject)throws IOException {
        try {
            JsonFile.write(jsonObject, filename);
        }catch (IOException e){
            throw new IOException("Could not write to file");
        }

    }

    public org.json.simple.JSONObject readJSON(String filename) throws IOException{
        try {
            return JsonFile.read(filename);
        } catch (ParseException | IOException e) {
          throw new IOException("Could not read the file");
        }
    }
}
