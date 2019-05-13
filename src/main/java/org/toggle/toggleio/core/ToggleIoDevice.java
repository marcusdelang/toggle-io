package org.toggle.toggleio.core;

import net.jstick.api.DeviceConfig;
import net.jstick.api.Tellstick;
import org.toggle.toggleio.application.controller.Controller;

import java.util.Scanner;

public class ToggleIoDevice {

    private Controller controller;

    ToggleIoDevice(Controller controller) {
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

        System.out.println("Please enter the type of device: ");
        input = myObj.nextLine();
        if (isQuit(input)) return;
        else deviceConfig.setModel(input);

        System.out.println("Please enter the protocol it shall use: ");
        input = myObj.nextLine();
        if (isQuit(input)) return;
        else deviceConfig.setProtocol(input);

        deviceConfig.setParam("house", "953934");
        deviceConfig.setParam("unit", "10");
        System.out.println("Now adding device...");
        tellstick.addDevice(deviceConfig);
        controller.addDevice(tellstick.getDeviceIdByName(nameInput), "0");

        tellstick.close();
    }

    public void removeDevice() {
        Tellstick tellstick = new Tellstick(true);
        tellstick.init();
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

        while (true) {
            System.out.println("Removing device, enter -q to go back");
            System.out.println("Enter ID of device you want to remove: ");

            String input = myObj.nextLine();
            if (isQuit(input))return;
            try {
                System.out.println("Removing device "+ input);
                tellstick.removeDevice((Integer.parseInt(input)));
                controller.removeDevice(Integer.parseInt(input));
                return;
            }catch (NumberFormatException|NullPointerException nfe){
                System.out.println("Please enter a valid number");
            }

        }

    }
    public void learnDevice(){
        Tellstick tellstick = new Tellstick();
        tellstick.init();
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

        while (true) {
            System.out.println("Learning device, enter -q to go back");
            System.out.println("Enter ID of device you want to learn: ");

            String input = myObj.nextLine();
            if (isQuit(input))return;
            try {
                System.out.println("Learning device "+ input);
                tellstick.learn((Integer.parseInt(input)));
                return;
            }catch (NumberFormatException|NullPointerException nfe){
                System.out.println("Please enter a valid number");
            }

        }

    }

    private boolean isQuit(String input) {
        return input.equals("-q");
    }

}
