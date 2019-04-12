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
 * Parses a http request and sends back a response on socket received
 */
public class RequestHandler {

  /**
   *
   * @param connectionSocket A socket that should receive the response
   * @param request A http request
   * @throws IOException Throws exception if unable to close or open socket
   */
  static void handleRequest(Socket connectionSocket, String request) throws IOException {
    boolean goodRequest = false;
    String response = HttpResponse.httpBadRequest();
    try {
      goodRequest = decideAction(HttpParse.parseUrlEndpoint(request));
    } catch (IllegalArgumentException iae) {
      System.out.println("Invalid Request received");
    }
    if (goodRequest) {
      response = HttpResponse.httpOk();
    }
    try {
      DataOutputStream outToClient =
          new DataOutputStream(connectionSocket.getOutputStream());

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

  private static boolean decideAction(String endpoint) {
    if (endpoint.equals("/on")) {
      return Outlet.on();
    } else if (endpoint.equals("/off")) {
      return Outlet.off();
    } else if (endpoint.equals("/isAlive")) {
      return Outlet.isAlive();
    } else {
      return false;
    }
  }

}
