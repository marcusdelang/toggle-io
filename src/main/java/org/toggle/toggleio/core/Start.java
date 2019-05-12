package org.toggle.toggleio.core;


import net.jstick.api.Device;
import net.jstick.api.Tellstick;
import org.toggle.toggleio.application.controller.Controller;
import org.toggle.toggleio.server.RequestHandler;
import org.toggle.toggleio.server.ToggleServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class handles the startup of the toggle-io server
 */
public class Start {

   // final private static String API_REGISTER_URL = "http://130.229.151.183/api/device/register";
    //final private static String API_UPDATE_URL = "http://130.229.151.183/api/device/update";


    final private static String API_REGISTER_URL = "https://toggle-api.eu-gb.mybluemix.net/api/device/register";
    final private static String API_UPDATE_URL = "https://toggle-api.eu-gb.mybluemix.net/api/device/update";
    public static void main(String[] args){
        Controller controller = new Controller();
        runtime(args, controller);

    }

    private static void runtime(String[] args, Controller controller) {
        Tellstick tellstick = new Tellstick();
        ToggleServer toggleServer = new ToggleServer(new RequestHandler(controller));
        ToggleIoDevice toggleIoDevice = new ToggleIoDevice(controller);

        menuRefresh(tellstick, toggleServer);
        while (true) {

            Scanner scanner = new Scanner(System.in);  // Create a Scanner object
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    if (toggleServer.isClosed() == true) {
                        try {
                            ToggleApi.requestSlot(API_REGISTER_URL, API_UPDATE_URL);
                            runNewServer(args, controller, toggleServer);
                        } catch (IOException ex) {
                            System.out.println("Could not register your device/s");
                        }

                    } else if (!toggleServer.isClosed() && !toggleServer.exiting()) {
                        closeServer(toggleServer);
                    }
                    break;
                case "2":
                    if (toggleServer.isClosed()) toggleIoDevice.addDevice();
                    else {
                        tellstick.close();
                        if(!toggleServer.isClosed())closeServer(toggleServer);
                        return;
                    }
                    break;
                case "3":
                    if(toggleServer.isClosed())toggleIoDevice.removeDevice();
                    break;
                case "4":
                    tellstick.close();
                    if(!toggleServer.isClosed())closeServer(toggleServer);
                    return;
            }
            menuRefresh(tellstick, toggleServer);
        }

    }

    private static void menuRefresh(Tellstick tellstick, ToggleServer toggleServer) {

        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
        System.out.println("TOGGLE-IO");
        printDevices(tellstick);
        if (toggleServer.isClosed() == true) System.out.println("1. Start Listening");
        else if (toggleServer.exiting()) System.out.println("Closing server please wait");
        else if (toggleServer.isClosed() == false) System.out.println("1. Stop Listening");
        if (toggleServer.isClosed())System.out.println("2. Add device");
        if (toggleServer.isClosed())System.out.println("3. Remove device");
        if (toggleServer.isClosed())System.out.println("4. Exit program");
        else System.out.println("2. Exit Program");
        tellstick.close();
    }
    private static void printDevices(Tellstick tellstick)
    {
        ArrayList<Device> deviceList = tellstick.getDevices();
        for (int i = 0; i < deviceList.size(); i++) {
            Device device = deviceList.get(i);
            System.out.println("Name: " + device.getName());
            System.out.println("ID: " + device.getId());
            System.out.println("Token: " + tellstick.getDeviceParameter(device.getId(),"code", "0"));
            System.out.println();
        }
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
