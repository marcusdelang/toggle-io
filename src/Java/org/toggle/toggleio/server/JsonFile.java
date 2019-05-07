package org.toggle.toggleio.server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.*;

public class JsonFile {

    public static JSONObject read(String filename) throws ParseException, IOException {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(filename)){
            Object obj = jsonParser.parse(reader);
            return (JSONObject) obj;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw e;
        } catch (ParseException e) {
            throw e;
        }

    }
    public static void write(JSONObject jsonObject,String filename) throws IOException{
        try (FileWriter file = new FileWriter(filename)) {

            file.write(jsonObject.toJSONString());
            file.flush();

        } catch (IOException e) {
            throw e;
        }
    }
}
