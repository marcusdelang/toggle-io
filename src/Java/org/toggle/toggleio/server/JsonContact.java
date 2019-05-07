package org.toggle.toggleio.server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.*;

public class JsonContact {

    public static JSONObject getJSONFromFile(String filename) throws IOException {
        String line = null;
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(filename)){
            Object obj = jsonParser.parse(reader);
            return (JSONObject) obj;
        }
        catch (FileNotFoundException exception) {
            return null;
        } catch (Exception exception) {
            throw new IOException(exception);
        }
    }
    public static void writeJSONToFile(JSONObject jsonObject,String filename) throws IOException{
        try (FileWriter file = new FileWriter(filename)) {

            file.write(jsonObject.toJSONString());
            file.flush();

        } catch (IOException e) {
            throw e;
        }
    }
}
