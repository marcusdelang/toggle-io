package org.toggle.toggleio.core;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.jstick.api.Tellstick;
import org.json.simple.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.ParseException;
import org.toggle.toggleio.application.integration.JsonFile;

/**
 * This class is responsible for register the ip on the API or update the IP on the API with help of the token received.
 * The class requires that the toggle-api is up and running or else you will get a ConnectionException
 */
public class ToggleApi {

  final private static int DEFAULT_TOKEN_LENGTH = 256;
  final private static String CONFIG_FILE = "confg.json";
  //For Testing Purposes


  /**
   * This function will request a new slot on the API or
   * update the IP on the API to the current one if the TOKEN is NOT expired
   * @param registerUrl
   * @param updateUrl
   * @throws IOException
   * @throws ParseException
   */
  public static void requestSlot(String registerUrl, String updateUrl) throws IOException, ParseException {
    Tellstick tellstick = new Tellstick();
    int numberOfDevices = tellstick.getNumberOfDevices();
    if(numberOfDevices==0)throw new IOException();
    else if (numberOfDevices==1) registerOne(registerUrl, updateUrl, tellstick);
    else registerMultiple(registerUrl, updateUrl, tellstick);
  }
  private static void registerMultiple(String registerUrl, String updateUrl, Tellstick tellstick)throws IOException, ParseException{
    JSONObject jsonObject;
    try {
      jsonObject = JsonFile.read(CONFIG_FILE);
    }catch (ParseException pe){
      throw pe;
    }
    if (jsonObject == null) {
      try {
        jsonObject = sendRequest(registerUrl);

      }catch (Exception e){
        throw e;
      }
      JsonFile.write(jsonObject, CONFIG_FILE);
    }
    else {
      try {
        sendRequest(updateUrl,jsonObject);
      }catch (InvalidObjectException ide){
        jsonObject = sendRequest(registerUrl);
        JsonFile.write(jsonObject, CONFIG_FILE);
      }
      catch (IOException ie){
        throw ie;
      }
    }
  }
  private static void registerOne(String registerUrl, String updateUrl, Tellstick tellstick) throws IOException, ParseException{
    JSONObject jsonObject;
    try {
      jsonObject = JsonFile.read(CONFIG_FILE);
    }catch (ParseException pe){
      throw pe;
    }
    if (jsonObject == null) {
      try {
        jsonObject = sendRequest(registerUrl);

      }catch (Exception e){
        throw e;
      }
      JsonFile.write(jsonObject, CONFIG_FILE);
    }
    else {
      try {
        sendRequest(updateUrl,jsonObject);
      }catch (InvalidObjectException ide){
        jsonObject = sendRequest(registerUrl);
        JsonFile.write(jsonObject, CONFIG_FILE);
      }
      catch (IOException ie){
        throw ie;
      }
    }
  }
  private static JSONObject sendRequest(String registerUrl) throws IOException {
    URL url = null;
    try {
      url = new URL(registerUrl);
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
       Map tokenMap = new HashMap();
       tokenMap.put("token", map.get("token"));
        return new JSONObject(tokenMap);
      }
    }
    catch(Exception e){
      throw new ConnectException("Could not connect to API");
    }


  }
  private static void sendRequest(String updateUrl,JSONObject jsonObject) throws IOException {
   URL url = null;
    try {
      url = new URL(updateUrl);
      HttpURLConnection con = (HttpURLConnection)url.openConnection();
      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/json");
      con.setDoOutput(true);
      try(OutputStream os = con.getOutputStream()) {
        byte[] input = jsonObject.toString().getBytes("utf-8");
        os.write(input, 0, input.length);
      }

      if(con.getResponseCode()==418)throw new InvalidObjectException("no");
      try(BufferedReader br = new BufferedReader(
          new InputStreamReader(con.getInputStream(), "utf-8"))) {
        StringBuilder response = new StringBuilder();
        String responseLine = null;
        while ((responseLine = br.readLine()) != null) {
          response.append(responseLine.trim());
        }

        System.out.println(response.toString());
      }
    }catch (InvalidObjectException ide) {
    throw ide;
    }
    catch (Exception e){
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
