package org.toggle.toggleio.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.stream.Stream;
import javax.imageio.IIOException;
import org.json.HTTP;
import org.json.JSONObject;
import org.toggle.toggleio.application.view.Outlet;

public class RequestHandler {

  public static void handleRequest(Socket connectionSocket, String request) throws IOException {

    String response;
    boolean goodRequest = decideAction(parseUrlEndpoint(request));

    if (goodRequest == true) {
      response = HttpResponse.httpOk();
    } else {
      response = HttpResponse.httpBadRequest();
    }
    try {
      DataOutputStream outToClient =
          new DataOutputStream(connectionSocket.getOutputStream());

      outToClient.writeBytes(response);
      outToClient.close();

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

  public static String parseUrlEndpoint(String request) {
    if (request.length() < 0) {
      throw new IllegalArgumentException("Not a valid HTTP request");
    }
    String[] httpParts = request.split("\n\n");
    if (httpParts.length > 2 || httpParts.length < 1) {
      throw new IllegalArgumentException("Not a valid HTTP request");
    }
    httpParts = httpParts[0].split("\n");
    httpParts = httpParts[0].split(" ");
    if (!(httpParts.length == 3)) {
      throw new IllegalArgumentException("Not a valid HTTP request");
    }

    String endpoint = httpParts[1];

    return endpoint;
  }

  public static String parseContentType(String request) {
    if (request.length() < 0) {
      throw new IllegalArgumentException("Not a valid HTTP request");
    }
    String[] httpParts = request.split("\n\n");
    if (httpParts.length > 2 || httpParts.length < 1) {
      throw new IllegalArgumentException("Not a valid HTTP request");
    }
    JSONObject JSONrequest = HTTP.toJSONObject(httpParts[0]);
    httpParts = httpParts[0].split("\n");
    httpParts = httpParts[0].split(" ");
    if (!(httpParts.length == 3)) {
      throw new IllegalArgumentException("Not a valid HTTP request");
    }

    String contentType = JSONrequest.getString("Content-Type");
    return contentType;
  }
}
