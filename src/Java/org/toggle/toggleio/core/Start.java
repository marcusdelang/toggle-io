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
        Controller controller = new Controller();
        runtime(args, controller);

    }

    private static void runtime(String[] args, Controller controller) {
        Tellstick tellstick = new Tellstick();
        ToggleServer toggleServer = new ToggleServer(new RequestHandler(controller));
        ToggleIoDevice toggleIoDevice = new ToggleIoDevice(controller);

        menuRefresh(tellstick, toggleServer);
        while (true) {

            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            String input = myObj.nextLine();
            switch (input) {
                case "1":
                    if (toggleServer.isClosed() == true) {
                        runNewServer(args, controller, toggleServer);

                    } else if(!toggleServer.isClosed()&&!toggleServer.exiting()){
                        closeServer(toggleServer);
                    }
                    break;
                case "2":
                    toggleIoDevice.addDevice();
                    break;
                case "3":
                    toggleIoDevice.removeDevice();
                    break;
                case "4":
                    return;
            }
            menuRefresh(tellstick, toggleServer);
        }

    }

    private static void menuRefresh(Tellstick tellstick, ToggleServer toggleServer) {
        ArrayList<Device> deviceList = tellstick.getDevices();
        System.out.flush();
        System.out.println("TOGGLE-IO");

        for (int i = 0; i < deviceList.size(); i++) {
            Device device = deviceList.get(i);
            System.out.println("ID: "+ device.getId());
            System.out.println("Name: "+device.getName());
            System.out.println("Proto: "+device.getProto());
            System.out.println("Type: "+device.getType());
            System.out.println("Model: "+device.getModel());
            System.out.println("Cmd: "+device.getLastCmd());
            System.out.println();
        }
        if (toggleServer.isClosed() == true) System.out.println("1. Start Listening");
        else if(toggleServer.exiting())System.out.println("Closing server please wait");
        else if (toggleServer.isClosed() == false) System.out.println("1. Stop Listening");
        System.out.println("2. Add device");
        System.out.println("3. Remove device");
        System.out.println("4, Exit program");
        tellstick.close();
    }

    private static void closeServer(ToggleServer toggleServer) {
        toggleServer.setExit(true);
        toggleServer.closeSocket();
    }

    private static void runNewServer(String[] args, Controller controller, ToggleServer toggleServer) {
        Thread serverThread = new Thread(toggleServer, "T1");
        if (args.length > 0) {
            try {
                toggleServer.setExit(false);
                toggleServer.setPort(Integer.parseInt(args[0]));
                serverThread.start();
                System.out.println("Running thread");
            } catch (NumberFormatException e) {
                System.out.println("Usage: Start 'port'");
            } catch (Exception e) {
                throw e;
            }

        } else {
            serverThread.start();
        }
    }
}
