package org.toggle.toggleio.core;

import org.toggle.toggleio.server.ToggleServer;

public class Start {

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                ToggleServer.start(Integer.parseInt(args[0]));
            } catch (NumberFormatException e) {
                System.out.println("Usage: Start 'port'");
                return;
            }

        }
        else ToggleServer.start();
    }
}
