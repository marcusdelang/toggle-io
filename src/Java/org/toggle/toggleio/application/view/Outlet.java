package org.toggle.toggleio.application.view;

import org.toggle.toggleio.application.controller.OutletController;

public class Outlet {
    public static boolean isAlive(){
        return OutletController.isAlive();
    }
    public static boolean on(){
        return OutletController.turnOn();
    }
    public static boolean off(){
        return OutletController.turnOff();
    }
}
