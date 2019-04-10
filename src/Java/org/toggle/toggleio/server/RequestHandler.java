package org.toggle.toggleio.server;

import java.io.DataOutputStream;
import java.net.Socket;
import org.json.HTTP;
import org.json.JSONObject;

public class RequestHandler {
    public static void handleRequest(Socket connectionSocket, String request){


    }
    public static String parseUrlEndpoint(String request){
        if (request.length()<0){
            throw new IllegalArgumentException("Not a valid HTTP request");
        }
        String[] httpParts = request.split("\n\n");
        if (httpParts.length>2 || httpParts.length<1){
            throw new IllegalArgumentException("Not a valid HTTP request");
        }
        httpParts = httpParts[0].split("\n");
        httpParts = httpParts[0].split(" ");
        if (!(httpParts.length==3)){
            throw new IllegalArgumentException("Not a valid HTTP request");
        }

        String endpoint = httpParts[1];

        return endpoint;
    }
    public static String parseContentType(String request){
        if (request.length()<0){
            throw new IllegalArgumentException("Not a valid HTTP request");
        }
        String[] httpParts = request.split("\n\n");
        if (httpParts.length>2 || httpParts.length<1){
            throw new IllegalArgumentException("Not a valid HTTP request");
        }
        JSONObject JSONrequest = HTTP.toJSONObject(httpParts[0]);
        httpParts = httpParts[0].split("\n");
        httpParts = httpParts[0].split(" ");
        if (!(httpParts.length==3)){
            throw new IllegalArgumentException("Not a valid HTTP request");
        }

        String contentType = JSONrequest.getString("Content-Type");
        return contentType;
    }
}
