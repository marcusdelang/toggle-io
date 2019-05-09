package org.toggle.toggleio.server;

import org.json.JSONObject;
import org.toggle.toggleio.application.view.Outlet;


/**
 * This class contains functions for handling a HTTP request that is meant for a telldus service
 */
public class RequestHandler {
  private Outlet outlet;

  public RequestHandler(Outlet outlet){
    this.outlet = outlet;
  }
  /**
   * Parses a request meant for telldus-io and returns a response on the connection from a given socket.
   * @param request A http request
   * @return String HTTP response based on request received
   */
  public String handleRequest(String request) {
    JSONObject JSONResponse = null;
    String endpoint;
    String response = HttpResponse.httpBadRequest();
    try {
       endpoint = HttpParse.parseUrlEndpoint(request);
    }catch (IllegalArgumentException iae){
      return response;
    }
    System.out.println("Endpoint: "+endpoint+"\n");

    if (endpoint.equals("/on")) {
      if (outlet.on()) response = HttpResponse.httpOk();
      else return response;
    }
    else if (endpoint.equals("/off")) {
      if(outlet.off()) response = HttpResponse.httpOk();
      else return response;
    }
    else if (endpoint.equals("/status")){
      JSONResponse = outlet.getStatus();
      response = HttpResponse.httpOk(JSONResponse);
    }
    else return response;

    return response;
  }
}
