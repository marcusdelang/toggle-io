package org.toggle.toggleio.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.stream.Stream;
import javax.imageio.IIOException;
import org.json.HTTP;
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
   * @param connectionSocket A socket that should receive the response
   * @param request A http request
   * @throws IOException Throws exception if unable to close or open socket
   */
  void handleRequest(Socket connectionSocket, String request) throws IOException {
    JSONObject JSONResponse = null;
    String response = HttpResponse.httpBadRequest();
    System.out.println("Endpoint: "+HttpParse.parseUrlEndpoint(request)+"\n");
    try {
      JSONResponse = decideAction(HttpParse.parseUrlEndpoint(request));
    } catch (IllegalArgumentException iae) {
      System.out.println("Invalid Request received");
    }
    if (JSONResponse != null) {
      response = HttpResponse.httpOk() + JSONResponse.toString();
    }
    try {
      DataOutputStream outToClient =
          new DataOutputStream(connectionSocket.getOutputStream());
      System.out.println("Responding with:\n" + response + "\n\n");
      outToClient.writeBytes(response);
      try {
        outToClient.close();
        connectionSocket.close();
      } catch (IOException ioe) {
        throw new SocketException("Could not close socket");
      }

    } catch (IOException ioe) {
      throw new IOException("Could not open output stream");
    }
  }

  private JSONObject decideAction(String endpoint) {
    if (endpoint.equals("/on")) {
      if (outlet.on()) return outlet.getStatus();
      else return null;
    }
    else if (endpoint.equals("/off")) {
      if(outlet.off()) return outlet.getStatus();
      else return null;
    }
    else if (endpoint.equals("/status")) return outlet.getStatus();
    else return null;

  }

}
