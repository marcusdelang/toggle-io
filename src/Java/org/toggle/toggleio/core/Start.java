package org.toggle.toggleio.core;

import java.io.IOException;
import java.net.PortUnreachableException;

import org.json.simple.parser.ParseException;
import org.toggle.toggleio.application.controller.OutletController;
import org.toggle.toggleio.application.model.TellstickCore;
import org.toggle.toggleio.application.view.Outlet;
import org.toggle.toggleio.server.ToggleApi;
import org.toggle.toggleio.server.RequestHandler;
import org.toggle.toggleio.server.ToggleServer;

/**
 * This class handles the startup of the toggle-io server
 */
public class Start {

  public static void main(String[] args) {
    /*boolean debug = false;

    if ((args.length>0&&args[0].equals("-debug")) || (args.length>1&&args[1].equals("-debug"))) debug = true;
    Tellstick ts = new Tellstick(debug);
    int intNumberOfDevices = ts.getNumberOfDevices();
    System.out.println(intNumberOfDevices);
    ts.sendCmd(1,"OFF");*/
    try {
      ToggleApi.requestSlot();
    }catch (IOException ex){
      System.out.println("Start: " + ex);
      return;
    }catch (ParseException pe){
      System.out.println("Something is wrong with JSON file");
      return;
    }

    if (args.length > 0) {
      try {
        ToggleServer toggleServer = new ToggleServer(new RequestHandler(new Outlet(new OutletController(new TellstickCore()))));
        toggleServer.start(Integer.parseInt(args[0]));
      } catch (NumberFormatException e) {
        System.out.println("Usage: Start 'port'");
        return;
      } catch (PortUnreachableException pue) {
        System.out.println("Port " + args[0] + " unreachable, try again later!");
      }

    } else {
      try {

       ToggleServer toggleServer = new ToggleServer(new RequestHandler(new Outlet(new OutletController(new TellstickCore()))));
        toggleServer.start();
      } catch (PortUnreachableException pue) {
        System.out.println("Port 80 unreachable, try again later!");
      }
    }
  }
}
