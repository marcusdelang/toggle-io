package org.toggle.toggleio.server;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;


public class ApiRequester {

  final private static int DEFAULT_TOKEN_LENGTH = 256;
  final private static String CONFIG_FILE = "confg.json";
  final private static String API_REGISTER_URL = "https://toggle-api.eu-gb.mybluemix.net/api/register";


  public static void requestSlot() throws IOException {
    JSONObject jsonObject = JsonContact.getJSONFromFile(CONFIG_FILE);
    if (jsonObject == null) {
      jsonObject = new JSONObject();
      jsonObject.put("token", generateString(DEFAULT_TOKEN_LENGTH));
      JsonContact.writeJSONToFile(jsonObject, CONFIG_FILE);
    }
    sendRequest(jsonObject);
  }
  private static void sendRequest() throws IOException {
    URL url = null;
    try {
      url = new URL(API_REGISTER_URL);
      HttpURLConnection con = (HttpURLConnection)url.openConnection();
      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/json");
      con.setDoOutput(true);
      try(OutputStream os = con.getOutputStream()) {
        os.write(0);
      }
      try(BufferedReader br = new BufferedReader(
              new InputStreamReader(con.getInputStream(), "utf-8"))) {
        StringBuilder response = new StringBuilder();
        String responseLine = null;
        while ((responseLine = br.readLine()) != null) {
          response.append(responseLine.trim());
        }
        System.out.println(response.toString());
      }
    }catch(Exception e){
      System.out.println(e);
      throw new ConnectException("Could not connect to API");
    }


  }
  private static void sendRequest(JSONObject jsonObject) throws IOException,ConnectException {
   URL url = null;
    try {
      url = new URL(API_REGISTER_URL);
      HttpURLConnection con = (HttpURLConnection)url.openConnection();
      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/json");
      con.setDoOutput(true);
      try(OutputStream os = con.getOutputStream()) {
        byte[] input = jsonObject.toString().getBytes("utf-8");
        os.write(input, 0, input.length);
      }
      try(BufferedReader br = new BufferedReader(
          new InputStreamReader(con.getInputStream(), "utf-8"))) {
        StringBuilder response = new StringBuilder();
        String responseLine = null;
        while ((responseLine = br.readLine()) != null) {
          response.append(responseLine.trim());
        }
        System.out.println(response.toString());
      }
    }catch(Exception e){
      System.out.println(e);
      throw new ConnectException("Could not connect to API");
    }

  }

  private static String generateString(int length) {
    final String TOKEN_CHAR_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    StringBuilder builder = new StringBuilder();
    while (length-- != 0) {
      int character = (int) (Math.random() * TOKEN_CHAR_STRING.length());
      builder.append(TOKEN_CHAR_STRING.charAt(character));
    }

    return builder.toString();
  }
}
