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

  final private static String API_REGISTER_URL = "http://130.229.151.183/api/device/register";
  final private static String API_UPDATE_URL = "http://130.229.151.183/api/device/update";

  // final private static String API_REGISTER_URL = "https://toggle-api.eu-gb.mybluemix.net/api/device/register";
  // final private static String API_UPDATE_URL = "https://toggle-api.eu-gb.mybluemix.net/api/device/update";
  public static void main(String[] args) {

    try {
      System.out.println("Requesting slot!\n");
      ToggleApi.requestSlot(API_REGISTER_URL, API_UPDATE_URL);
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
