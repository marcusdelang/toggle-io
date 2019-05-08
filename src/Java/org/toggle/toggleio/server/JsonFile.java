package org.toggle.toggleio.server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.*;

/**
 * This class handles writing and reading a file of type .json
 */
public class JsonFile {
    /**
     * Returns a json of type JSONObject if a file exist and contains correctly formated json
     * @param filename of file that should be read from
     * @return JSONObject
     * @throws ParseException throws ParseException if file is not of json type or incorrectly formated
     */
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

    /**
     * Writes the json object to a existing json file or creates a new file if none exist
     * @param jsonObject to write in file
     * @param filename of file to write to
     * @throws IOException if
     */
    public static void write(JSONObject jsonObject,String filename) throws IOException{
        try (FileWriter file = new FileWriter(filename)) {

            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            throw e;
        }
    }
}
