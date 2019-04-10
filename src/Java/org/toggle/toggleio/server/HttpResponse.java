package org.toggle.toggleio.server;

public class HttpResponse {

  public static String httpOk() {
    String okResponse = "HTTP/1.1 200 OK" + "\n\r\n";
    return okResponse;
  }

  public static String httpBadRequest() {
    String badRequestResponse = "HTTP/1.1 400 Bad Request" + "\n\r\n";
    return badRequestResponse;
  }
}
