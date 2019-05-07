package org.toggle.toggleio.server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.*;

public class JsonContact {
  /*  public static void createJSONFile(JSONObject jsonObject, String filename) {
        String line = null;

        try {
            FileWriter fileWriter = new FileWriter(filename);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(jsonObject.toString());
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("Error writing");
        }
    }*/

    public static JSONObject getJSONFromFile(String filename) throws IOException {
        String line = null;
        try {
            FileReader fileReader = new FileReader(filename);

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder builder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            bufferedReader.close();
            String jsonString = builder.toString();
            if (jsonString.startsWith("{") && jsonString.endsWith("}")) {
                return new JSONObject();
            } else {
                return null;
            }
        } catch (FileNotFoundException exception) {
            return null;
        } catch (IOException exception) {
            throw exception;
        }
    }
    public static void writeJSONToFile(JSONObject jsonObject,String filename) throws IOException{
        try (FileWriter file = new FileWriter(filename)) {

            file.write(jsonObject.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
