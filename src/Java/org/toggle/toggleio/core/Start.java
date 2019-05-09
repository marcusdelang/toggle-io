package org.toggle.toggleio.core;


import java.net.PortUnreachableException;
import net.jstick.api.Device;
import net.jstick.api.Tellstick;
import org.json.JSONException;
import org.toggle.toggleio.application.controller.Controller;
import org.toggle.toggleio.server.RequestHandler;
import org.toggle.toggleio.server.ToggleServer;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class handles the startup of the toggle-io server
 */
public class Start {

    final private static String API_REGISTER_URL = "http://130.229.151.183/api/device/register";
    final private static String API_UPDATE_URL = "http://130.229.151.183/api/device/update";


    // final private static String API_REGISTER_URL = "https://toggle-api.eu-gb.mybluemix.net/api/device/register";
    // final private static String API_UPDATE_URL = "https://toggle-api.eu-gb.mybluemix.net/api/device/update";
    public static void main(String[] args) throws JSONException, Exception {

        runtime();
    /*try {
      ToggleApi.requestSlot(API_REGISTER_URL, API_UPDATE_URL);
    }catch (IOException ex){
      System.out.println("Start: " + ex);
      return;
    }catch (ParseException pe){
      System.out.println("Something is wrong with JSON file");
      return;
    }*/
        ToggleServer toggleServer = new ToggleServer(new RequestHandler(new Controller()));

        if (args.length > 0) {
            try {
                toggleServer.start(Integer.parseInt(args[0]));
            } catch (NumberFormatException e) {
                System.out.println("Usage: Start 'port'");
                return;
            } catch (PortUnreachableException pue) {
                System.out.println("Port " + args[0] + " unreachable, try again later!");
            } catch (Exception e) {
                throw e;
            }

        } else {
            try {
                toggleServer.start();
            } catch (PortUnreachableException pue) {
                System.out.println("Port 80 unreachable, try again later!");
            }
        }
    }

    private static void runtime() {
        Tellstick tellstick = new Tellstick();
        ArrayList<Device> devicelist = tellstick.getDevices();
        System.out.flush();
        System.out.println("TOGGLE-IO");
        for (int i = 0; i < devicelist.size(); i++) {
            Device device = devicelist.get(i);
            System.out.println(device.getId());


            System.out.println(device.getLastCmd());
            System.out.println();
        }
        tellstick.close();
        System.out.println("1. Start listening");
        System.out.println("2. Add device");
        System.out.println("3. Remove device");
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String input = myObj.nextLine();
        switch (input) {
            case "1":
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                break;
        }

    }
}
