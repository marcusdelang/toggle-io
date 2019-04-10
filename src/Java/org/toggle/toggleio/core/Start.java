package org.toggle.toggleio.core;

import java.net.PortUnreachableException;
import org.toggle.toggleio.server.ToggleServer;

public class Start {

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                ToggleServer.start(Integer.parseInt(args[0]));
            } catch (NumberFormatException e) {
                System.out.println("Usage: Start 'port'");
                return;
            }catch (PortUnreachableException pue){
                System.out.println("Port "+ args[0]+" unreachable, try again later!");
            }

        }
        else {
            try{
            ToggleServer.start();
            }catch (PortUnreachableException pue){
                System.out.println("Port 80 unreachable, try again later!");
            }
        }
    }
}
