package org.toggle.toggleio.server;

import org.json.HTTP;
import org.json.JSONObject;

public class HttpParse {

  public static String parseUrlEndpoint(String request) {
    if (!validHTTP(request)) {
      throw new IllegalArgumentException("Not a valid HTTP request");
    }

    String[] httpParts = request.split("\n\n");
    httpParts = httpParts[0].split("\n");
    httpParts = httpParts[0].split(" ");
    String endpoint = httpParts[1];

    return endpoint;
  }

  public static String parseContentType(String request) {
    if (!validHTTP(request)) {
      throw new IllegalArgumentException("Not a valid HTTP request");
    }
    String[] httpParts = request.split("\n\n");
    JSONObject JSONrequest = HTTP.toJSONObject(httpParts[0]);

    String contentType = JSONrequest.getString("Content-Type");
    return contentType;
  }

  private static boolean validHTTP(String httpString) {
    if (httpString.length() < 1) {
      return false;
    }
    String[] httpParts = httpString.split("\n\n");
    if (httpParts.length > 2 || httpParts.length < 1) {
      return false;
    }
    httpParts = httpParts[0].split("\n");
    httpParts = httpParts[0].split(" ");
    if (!(httpParts.length == 3)) {
      return false;
    }

    return true;
  }

}
