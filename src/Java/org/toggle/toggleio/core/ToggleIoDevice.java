package org.toggle.toggleio.core;

import net.jstick.api.DeviceConfig;
import net.jstick.api.Tellstick;
import org.toggle.toggleio.application.controller.Controller;

import java.util.Scanner;

public class ToggleIoDevice {
    Controller controller;

    ToggleIoDevice(Controller controller) {
        this.controller = controller;
    }

    public void addDevice() {
        System.out.println("Add device:");
       // Scanner myObj = new Scanner(System.in);  // Create a Scanner object
       // String input = myObj.nextLine();
        Tellstick tellstick = new Tellstick(true);
        DeviceConfig deviceConfig = new DeviceConfig();
        deviceConfig.setId(10);
        deviceConfig.setName("fishybowl");
        deviceConfig.setProtocol("arctech");
        deviceConfig.setModel("selflearning-switch");
        System.out.println(tellstick.addDevice(deviceConfig));
        tellstick.close();

    }

    public void removeDevice() {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String input = myObj.nextLine();

    }
}
