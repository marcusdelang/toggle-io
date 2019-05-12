package org.toggle.toggleio.core;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import net.jstick.api.Device;

import net.jstick.api.Tellstick;


import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;


/**
 * This class is responsible for register the ip on the API or update the IP on the API with help of the token received.
 * The class requires that the toggle-api is up and running or else you will get a ConnectionException
 */
public class ToggleApi {

    final private static int DEFAULT_TOKEN_LENGTH = 256;
    final private static String CONFIG_FILE = "confg.json";

    /**
     * This function will request a new slot on the API or
     * update the IP on the API to the current one if the TOKEN is NOT expired
     *
     * @param registerUrl
     * @param updateUrl
     * @throws IOException
     * @throws ParseException
     */
    public static void requestSlot(String registerUrl, String updateUrl) throws IOException {
        Tellstick tellstick = new Tellstick();
        int numberOfDevices = tellstick.getNumberOfDevices();
        if (numberOfDevices == 0) return;
        System.out.flush();
        System.out.println("Registering devices...");
        register(registerUrl, updateUrl, tellstick);
    }

    private static void register(String registerUrl, String updateUrl, Tellstick tellstick) {
        String token;
        ArrayList<Device> deviceList = tellstick.getDevices();
        for (int i = 0; i < deviceList.size(); i++) {
            Device device = deviceList.get(i);
            token = tellstick.getDeviceParameter(device.getId(),"code", "0");
            System.out.print("Name: " + device.getName() + ", ");
            try {
                if (token.length() == 0) throw new IOException();
                if (token.equals("0")) token = sendRequestRegister(registerUrl);
                else token = sendRequestUpdate(updateUrl,registerUrl, device);
                tellstick.setDeviceParameter(device.getId(), "code", token);
                System.out.print("Token: " + token+"\n\n");
            } catch (IOException|JSONException io) {
                System.out.print("COULD NOT REGISTER DEVICE\n");
            }
        }
    }

    private static String sendRequestRegister(String registerUrl) throws IOException, JSONException {
        URL url = null;

        try {
            url = new URL(registerUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
            }
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
                JSONObject jsonObject = new JSONObject(response.toString());

                return (String) jsonObject.get("token");

            }
        } catch (Exception e) {
            throw new ConnectException("Could not connect to API");
        }


    }

    private static String sendRequestUpdate(String updateUrl, String registerUrl,Device device) throws IOException, JSONException {
        URL url = null;
        JSONObject jsonObject = new JSONObject();
        Tellstick tellstick = new Tellstick();
        jsonObject.put("token", tellstick.getDeviceParameter(device.getId(), "code", "0"));
        try {
            url = new URL(updateUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonObject.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            if (con.getResponseCode() == 418) return sendRequestRegister(registerUrl);
            else if (con.getResponseCode() == 200) return tellstick.getDeviceParameter(device.getId(), "code", "0");
            else throw new ConnectException();
        } catch (Exception e) {
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
