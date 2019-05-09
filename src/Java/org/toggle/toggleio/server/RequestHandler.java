package org.toggle.toggleio.server;

import net.jstick.api.Device;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.toggle.toggleio.application.controller.Controller;
import org.toggle.toggleio.application.integration.JsonFile;

import java.io.IOException;
import java.util.Iterator;


/**
 * This class contains functions for handling a HTTP request that is meant for a telldus service
 */
public class RequestHandler {
    final private static String CONFIG_FILE = "confg.json";
    Controller controller;
    public RequestHandler(Controller controller){
        this.controller = controller;
    }

    /**
     * Parses a request meant for telldus-io and returns a response on the connection from a given socket.
     *
     * @param request A http request
     * @return String HTTP response based on request received
     */
    public String handleRequest(String request) throws IOException, JSONException {
        String response = HttpResponse.httpBadRequest();

        try {
            if(!HttpParse.parseContentType(request).equals("application/json")){
                return response;
            }
        }catch (Exception e){
            return response;
        }
        org.json.simple.JSONObject jsonConfig = controller.readJSON(CONFIG_FILE);
        JSONObject JSONInRequest = HttpParse.parseJSON(request);
        org.json.simple.JSONObject sad = new org.json.simple.JSONObject();

        String token = (String) JSONInRequest.get("token");
        int id=0;
        JSONArray devices = (JSONArray) jsonConfig.get("devices");
        org.json.simple.JSONObject iteratorJSON;
        Iterator<org.json.simple.JSONObject> iterator = devices.iterator();
        while (iterator.hasNext()) {
            iteratorJSON = iterator.next();
            if (iteratorJSON.containsValue(token)){
                id = (int)iteratorJSON.get("id");
                break;
            }
        }

        String endpoint;
        try {
            endpoint = HttpParse.parseUrlEndpoint(request);
        } catch (IllegalArgumentException iae) {
            return response;
        }
        System.out.println("Endpoint: " + endpoint + "\n");

        if (endpoint.equals("/on")) {
            if (controller.on(id)) response = HttpResponse.httpOk();
            else response = HttpResponse.httpInternalServerError();

        } else if (endpoint.equals("/off")) {
            if (controller.off(id)) response = HttpResponse.httpOk();
            else response = HttpResponse.httpInternalServerError();
        } else if (endpoint.equals("/status")) {
            response = HttpResponse.httpOk(controller.status(id));
        } else return response;

        return response;
    }
}
