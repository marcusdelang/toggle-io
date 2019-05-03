package org.toggle.toggleio.server;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import org.json.HTTP;
import org.json.JSONObject;

public class IdHandler {
  final private static int TOKEN_LENGTH = 256;
  final private static String CONFIG_FILE = "confg.txt";
  //final private static String API_URL = "https://webhook.site/29b3e16c-aa95-4687-a751-484fee61c24d";
  //final private static String API_URL = "toggle-api.eu-gb.mybluemix.net";
  final private static String API_URL = "http://130.229.167.163/api/register";
  final private static int PORT = 80;

  public static void requestSlot() throws IOException{
    JSONObject jsonObject = getJSONFromFile();
    if (jsonObject == null){
      jsonObject = new JSONObject();
      jsonObject.put("id",generateString(TOKEN_LENGTH) );
      createJSONFile(jsonObject);
    }
    sendRequest(getHTTPRequestIDSlot(jsonObject), jsonObject);
  }
  private static void sendRequest(String httpRequest, JSONObject jsonObject)throws IOException{
    //Socket clientSocket;
   // PrintWriter out;
    //BufferedReader in;
    URL url = null;
    try {
      url = new URL(API_URL);
      HttpURLConnection con = (HttpURLConnection)url.openConnection();
      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/json; utf-8");
      con.setRequestProperty("Accept", "application/json");
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

    }
   // clientSocket = new Socket(API_URL, PORT);
   // out = new PrintWriter(clientSocket.getOutputStream(),true);
    //in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

  //  out.println(httpRequest);
   // String resp = in.readLine();

    System.out.println(httpRequest+"\n\n");
  //  System.out.println(resp);
   // in.close();
    //out.close();
    //clientSocket.close();
  }
  private static String getHTTPRequestIDSlot(JSONObject jsonObject){
    StringBuilder builder = new StringBuilder();
    builder.append( "POST HTTP/1.1\r\n" +
                    "Content-Type: application/json\r\n\r\n" +
                    jsonObject.toString());
    System.out.println(builder.toString());
    return builder.toString();

  }
  private static void createJSONFile(JSONObject jsonObject){
    String line = null;

    try {
      FileWriter fileWriter = new FileWriter(CONFIG_FILE);

      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

      bufferedWriter.write(jsonObject.toString());
      bufferedWriter.close();
    }catch (IOException ex){
      System.out.println("Error writing");
    }
  }
  private static JSONObject getJSONFromFile() throws IOException{
    String line = null;


    JSONObject jsonObject = null;

    try {
      FileReader fileReader = new FileReader(CONFIG_FILE);

      BufferedReader bufferedReader = new BufferedReader(fileReader);
      StringBuilder builder = new StringBuilder();
      while ((line = bufferedReader.readLine())!= null){
        builder.append(line);
      }
      bufferedReader.close();
      String jsonString = builder.toString();
      if(jsonString.startsWith("{")&&jsonString.endsWith("}"))
        return new JSONObject(jsonString);
      else return null;
    }catch (FileNotFoundException exception){
        return null;
    }catch (IOException exception){
      throw exception;
    }
  }
  public static String generateString(int length){
    final String TOKEN_CHAR_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    StringBuilder builder = new StringBuilder();
    while (length-- != 0) {
      int character = (int)(Math.random()*TOKEN_CHAR_STRING.length());
      builder.append(TOKEN_CHAR_STRING.charAt(character));
    }

    return builder.toString();
  }
}
