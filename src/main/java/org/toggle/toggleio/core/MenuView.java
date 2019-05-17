package org.toggle.toggleio.core;

import net.jstick.api.Device;
import net.jstick.api.Tellstick;
import org.toggle.toggleio.application.controller.Controller;
import org.toggle.toggleio.server.RequestHandler;
import org.toggle.toggleio.server.ToggleServer;
import java.util.ArrayList;
import java.util.Scanner;


public class MenuView {


   /*final private static String API_REGISTER_URL = "http://130.229.147.100/api/device/register";
   final private static String API_UPDATE_URL = "http://130.229.147.100/api/device/update";
    final private static String API_REMOVE_URL = "http://130.229.147.100/api/device/remove";

    */

    private Controller controller;
    private ToggleServer toggleServer;
    private Tellstick tellstick;
    private ToggleIoDevice toggleIoDevice;
    private ToggleApi toggleApi;

    final private static String API_REGISTER_URL = "https://toggle-api.eu-gb.mybluemix.net/api/device/register";
    final private static String API_UPDATE_URL = "https://toggle-api.eu-gb.mybluemix.net/api/device/update";
    final private static String API_REMOVE_URL = "https://toggle-api.eu-gb.mybluemix.net/api/device/remove";


    public MenuView(Controller controller){
        this.controller = controller;
        this.toggleServer = new ToggleServer(this, new RequestHandler(controller));
        this.tellstick  = new Tellstick();
        this.toggleApi = new ToggleApi(controller,API_REGISTER_URL,API_UPDATE_URL,API_REMOVE_URL);
        this.toggleIoDevice = new ToggleIoDevice(controller, toggleApi);
    }

    public void runtime(String[] args) {
        menuRefresh();
        while (true) {
            Scanner scanner = new Scanner(System.in);

            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    if (tellstick.getNumberOfDevices()==0)break;
                    if (toggleServer.isClosed()) {
                            toggleApi.requestSlot();
                            runServer(args);
                    } else if (!toggleServer.isClosed() && !toggleServer.exiting()) {
                        toggleServer.closeServer();
                    }
                    break;
                case "2":
                    if (toggleServer.isClosed()) toggleIoDevice.addDevice();
                    else {
                        tellstick.close();
                        toggleServer.closeServer();
                        return;
                    }
                    break;
                case "3":
                    if (toggleServer.isClosed()) toggleIoDevice.removeDevice();
                    else printDevices();
                    break;
                case "4":
                    if (toggleServer.isClosed()) toggleIoDevice.learnDevice();
                    break;
                case "5":
                    if (toggleServer.isClosed()) {
                        tellstick.close();
                        final String ANSI_CLS = "\u001b[2J";
                        final String ANSI_HOME = "\u001b[H";
                        System.out.print(ANSI_CLS + ANSI_HOME);
                        return;
                    }
                    break;

            }
            menuRefresh();
        }

    }

    public void menuRefresh() {

        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
        System.out.println("TOGGLE-IO");
        printDevices();
        if (toggleServer.isClosed()&&tellstick.getNumberOfDevices()>0) System.out.println("1. Start Listening");
        else if (tellstick.getNumberOfDevices()==0)System.out.println("Please add devices before starting server");
        else if (toggleServer.exiting()) System.out.println("Closing server please wait");
        else if (!toggleServer.isClosed()) System.out.println("1. Stop Listening");
        if (toggleServer.isClosed()) System.out.println("2. Add device");
        if (toggleServer.isClosed()) System.out.println("3. Remove device");
        if (toggleServer.isClosed()) System.out.println("4. Learn device");
        if (toggleServer.isClosed()) System.out.println("5. Exit program");
        else {
            System.out.println("2. Exit Program");
            System.out.println("3. Show Devices");
        }
        tellstick.close();
    }

    private void printDevices() {

            ArrayList<Device> deviceList = tellstick.getDevices();
            for (int i = 0; i < deviceList.size(); i++) {
                Device device = deviceList.get(i);

                System.out.println("Name: " + device.getName());
                System.out.println("ID: " + device.getId());
                System.out.println("Token: " + controller.getToken(device.getId()));
                System.out.println("Model: " + device.getModel());
                System.out.println("Status: "+ device.getLastCmd());

                System.out.println();
            }
        }



    private void runServer(String[] args) {
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
