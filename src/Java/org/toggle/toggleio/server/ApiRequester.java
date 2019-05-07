package org.toggle.toggleio.server;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.simple.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ApiRequester {

  final private static int DEFAULT_TOKEN_LENGTH = 256;
  final private static String CONFIG_FILE = "confg.json";
/*  For Testing Purposes
  final private static String API_REGISTER_URL = "http://130.229.129.38/api/device/register";
  final private static String API_UPDATE_URL = "http://130.229.129.38/api/device/update";
*/
  final private static String API_REGISTER_URL = "https://toggle-api.eu-gb.mybluemix.net/api/device/register";
  final private static String API_UPDATE_URL = "https://toggle-api.eu-gb.mybluemix.net/api/device/update";

  public static void requestSlot() throws IOException {
    JSONObject jsonObject = JsonContact.getJSONFromFile(CONFIG_FILE);
    if (jsonObject == null) {
      JsonContact.writeJSONToFile(sendRequest(), CONFIG_FILE);
    }
    else sendRequest(jsonObject);
  }
  private static JSONObject sendRequest() throws IOException {
    URL url = null;
    try {
      url = new URL(API_REGISTER_URL);
      HttpURLConnection con = (HttpURLConnection)url.openConnection();
      con.setRequestMethod("POST");
      con.setDoOutput(true);
      try(OutputStream os = con.getOutputStream()) {
      }
      try(BufferedReader br = new BufferedReader(
              new InputStreamReader(con.getInputStream(), "utf-8"))) {
        StringBuilder response = new StringBuilder();
        String responseLine = null;
        while ((responseLine = br.readLine()) != null) {
          response.append(responseLine.trim());
        }
        System.out.println(response.toString());
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = mapper.readValue(response.toString(), Map.class);
        return new JSONObject(map);
      }
    }catch(Exception e){
      System.out.println(e);
      throw new ConnectException("Could not connect to API NOT JSON" + e);
    }


  }
  private static void sendRequest(JSONObject jsonObject) throws IOException,ConnectException {
   URL url = null;
    try {
      url = new URL(API_UPDATE_URL);
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
      throw new ConnectException("Could not connect to API JSON");
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
