package org.toggle.toggleio.server;

import net.jstick.api.Device;
import net.jstick.api.Tellstick;
import org.json.JSONException;
import org.json.JSONObject;
import org.toggle.toggleio.application.controller.Controller;

import java.util.ArrayList;



/**
 * This class contains functions for handling a HTTP request that is meant for a telldus service
 */
public class RequestHandler {

    Controller controller;

    public RequestHandler(Controller controller) {
        this.controller = controller;
    }

    /**
     * Parses a request meant for telldus-io and returns a response on the connection from a given socket.
     *
     * @param request A http request
     * @return String HTTP response based on request received
     */
    public String handleRequest(String request) throws JSONException {
        String response = HttpResponse.httpBadRequest();
        String token;
        try {
            if (!HttpParse.parseContentType(request).equals("application/json")) {
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return response;
        }

        JSONObject JSONInRequest = HttpParse.parseJSON(request);
        if(JSONInRequest==null)return response;
        try {
            token = (String)JSONInRequest.get("token");
        }catch (JSONException s){
            return response;
        }


        int id = 0;
        boolean deviceExist = false;
        Tellstick tellstick = new Tellstick();

        ArrayList<Device> devices = tellstick.getDevices();
        for (int i = 0; i < devices.size(); i++) {
            Device device = devices.get(i);
            if(controller.getToken(device.getId()).equals(token)){
                id = device.getId();
                deviceExist = true;
            }
        }
        if(!deviceExist)return HttpResponse.httpBadRequest();


        String endpoint;
        try {
            endpoint = HttpParse.parseUrlEndpoint(request);
        } catch (IllegalArgumentException iae) {
            return response;
        }

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
