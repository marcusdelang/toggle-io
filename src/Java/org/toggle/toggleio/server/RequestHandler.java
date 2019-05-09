package org.toggle.toggleio.server;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.toggle.toggleio.application.controller.OutletController;
import org.toggle.toggleio.core.JsonFile;

import java.io.IOException;


/**
 * This class contains functions for handling a HTTP request that is meant for a telldus service
 */
public class RequestHandler {
    final private static String CONFIG_FILE = "confg.json";

    /**
     * Parses a request meant for telldus-io and returns a response on the connection from a given socket.
     *
     * @param request A http request
     * @return String HTTP response based on request received
     */
    public static String handleRequest(String request) throws IOException,ParseException, JSONException {
        org.json.simple.JSONObject jsonConfig = JsonFile.read(CONFIG_FILE);

        JSONObject JSONRequest = HttpParse.parseJSON(request);
        String token = (String) JSONRequest.get("token");
        int id = (int) jsonConfig.get(token);
        String endpoint;
        String response = HttpResponse.httpBadRequest();
        try {
            endpoint = HttpParse.parseUrlEndpoint(request);
        } catch (IllegalArgumentException iae) {
            return response;
        }
        System.out.println("Endpoint: " + endpoint + "\n");

        if (endpoint.equals("/on")) {
            if (OutletController.on(id)) response = HttpResponse.httpOk();
            else response = HttpResponse.httpInternalServerError();

        } else if (endpoint.equals("/off")) {
            if (OutletController.off(id)) response = HttpResponse.httpOk();
            else response = HttpResponse.httpInternalServerError();
        } else if (endpoint.equals("/status")) {
            response = HttpResponse.httpOk(OutletController.status(id));
        } else return response;

        return response;
    }
}
