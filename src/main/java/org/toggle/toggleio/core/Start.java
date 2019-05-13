package org.toggle.toggleio.core;


import org.toggle.toggleio.application.controller.Controller;
import org.toggle.toggleio.core.MenuView;

/**
 * This class handles the startup of the toggle-io server
 */
public class Start {

    public static void main(String[] args) {
        Controller controller = new Controller();
        MenuView menuView = new MenuView(controller);

        menuView.runtime(args);

    }

}
