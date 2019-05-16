package org.toggle.toggleio.core;

import net.jstick.api.Device;
import net.jstick.api.DeviceConfig;
import net.jstick.api.Tellstick;
import org.json.JSONException;
import org.toggle.toggleio.application.controller.Controller;
import org.toggle.toggleio.application.model.TelldusScripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ToggleIoDevice {

    private Controller controller;
    private ToggleApi toggleApi;

    ToggleIoDevice(Controller controller, ToggleApi toggleApi) {
        this.toggleApi = toggleApi;
        this.controller = controller;
    }

    public void addDevice() {
        String input;
        Tellstick tellstick = new Tellstick(true);
        tellstick.init();
        DeviceConfig deviceConfig = new DeviceConfig();
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Adding new device, enter -q to go back:");


        System.out.println("Please enter a name: ");
        String nameInput = myObj.nextLine();
        if (isQuit(nameInput)) return;
        else deviceConfig.setName(nameInput);


        System.out.flush();
        System.out.println("What model is the device? ");
        System.out.println("1. selflearning-switch");
        System.out.println("2. selflearning-dimmer ");
        System.out.println("3. codeswitch");
        String model = "0";
        String unit = "0";
        String house = "0";
        input = myObj.nextLine();
        boolean validDevice = false;
        while (!validDevice) {
            switch (input) {
                case "1":
                    model = "selflearning-switch";
                    house = Integer.toString(randomHouse());
                    unit = "10";
                    validDevice = true;
                    break;
                case "2":
                    model = "selflearning-dimmer";
                    house = Integer.toString(randomHouse());
                    unit = "9";
                    validDevice = true;
                    break;
                case "3":
                    while (true) {
                        System.out.println("Please enter the house A-P: ");
                        input = myObj.nextLine();
                        if (isQuit(input)) return;
                        else if (input.matches(".*[A-P]+.*")) {
                            house = input;
                            break;
                        }
                    }
                    while (true) {
                        System.out.println("Please enter the unit 1-16: ");
                        input = myObj.nextLine();
                        if (isQuit(input)) return;
                        else if (Integer.parseInt(input) > 0 && Integer.parseInt(input) < 17) {
                            unit = input;
                            break;
                        }
                    }
                    model = "codeswitch";
                    validDevice = true;
                    break;
                case "-q":
                    return;
                default:
                    clearTerminal();
                    System.out.println("Please enter a valid device!");
                    input = myObj.nextLine();
                    break;
            }
        }
        deviceConfig.setParam("unit", unit);
        deviceConfig.setParam("house", house);
        deviceConfig.setModel(model);
        System.out.println("What protocol does it use? ");
        System.out.println("1. arctech ");
        input = myObj.nextLine();
        String protocol = "0";
        validDevice = false;
        while (!validDevice) {
            switch (input) {
                case "1":
                    protocol = "arctech";
                    validDevice = true;
                    break;
                case "-q":
                    return;
                default:
                    clearTerminal();
                    System.out.println("Please enter a valid protocol!");
                    input = myObj.nextLine();
                    break;
            }
        }

        deviceConfig.setProtocol(protocol);


        System.out.println("Now adding device...");
        tellstick.addDevice(deviceConfig);
        controller.addDevice(tellstick.getDeviceIdByName(nameInput), "0");

        tellstick.close();
    }

    private void clearTerminal() {
        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
    }

    private int randomHouse() {
        Random rand = new Random();
        Tellstick tellstick = new Tellstick();
        int n;
        ArrayList<Device> deviceList = tellstick.getDevices();
        boolean houseExists = false;

        do {
            n = rand.nextInt(67108863) + 1;
            for (int i = 0; i < deviceList.size(); i++) {
                Device device = deviceList.get(i);
                String house = tellstick.getDeviceParameter(device.getId(), "house", "0");

                try {
                    if (Integer.parseInt(house) == n) {
                        houseExists = true;
                        break;
                    }
                } catch (NumberFormatException e) {

                }
            }
        } while (houseExists);
        return n;
    }

    public void removeDevice() {
        Tellstick tellstick = new Tellstick(true);
        tellstick.init();
        Scanner myObj = new Scanner(System.in);

        while (true) {
            System.out.println("Removing device, enter -q to go back");
            System.out.println("Enter ID of device you want to remove: ");

            String input = myObj.nextLine();
            if (isQuit(input)) return;
            try {
                System.out.println("Removing device " + input);

                /*NOT IMPLEMENTED YET ON API*/
                if (!controller.getToken(Integer.parseInt(input)).equals("0")) {
                    try {
                        toggleApi.removeDevice(controller.getToken(Integer.parseInt(input)));
                    } catch (IOException | JSONException ie) {
                    }
                }
                tellstick.removeDevice((Integer.parseInt(input)));
                controller.removeDevice(Integer.parseInt(input));
                System.out.println("Device removed!");
                return;
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a valid number");
            } catch (NullPointerException npe) {
                System.out.println("Device removed!");
                return;
            }

        }

    }

    public void learnDevice() {
        Tellstick tellstick = new Tellstick();
        tellstick.init();
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

        while (true) {
            System.out.println("Learning device, enter -q to go back");
            System.out.println("Enter ID of device you want to learn: ");


            String input = myObj.nextLine();
            if (isQuit(input)) return;
            try {
                System.out.println("Learning device " + input);
                tellstick.learn((Integer.parseInt(input)));
                return;
            } catch (NumberFormatException | NullPointerException nfe) {
                System.out.println("Please enter a valid number");
            }

        }

    }

    private boolean isQuit(String input) {
        return input.equals("-q");
    }

}
