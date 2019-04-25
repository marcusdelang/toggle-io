package org.toggle.toggleio.server;

/**
 * This class contains functions that when called returns a valid HTTP response of
 * a specified type.
 */
public class HttpResponse {

  /**
   * Returns a 1.1 HTTP valid 200 OK response
   * @return Response
   */
  public static String httpOk() {
    String okResponse = "HTTP/1.1 200 OK" + "\n\r\n";
    return okResponse;
  }
  /**
   * Returns a 1.1 HTTP valid 400 Bad Request response
   * @return Response
   */
  public static String httpBadRequest() {
    String badRequestResponse = "HTTP/1.1 400 Bad Request" + "\n\r\n";
    return badRequestResponse;
  }
}
