package org.toggle.toggleio.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.toggle.toggleio.application.controller.OutletController;
import org.toggle.toggleio.application.controller.OutletControllerStub;
import org.toggle.toggleio.application.model.TellstickCore;
import org.toggle.toggleio.application.model.TellstickCoreStub;
import org.toggle.toggleio.application.view.Outlet;
import org.toggle.toggleio.application.view.OutletStub;

class RequestHandlerTest {
  RequestHandler reqHandler;

  @BeforeEach
  void setUp() {
    try {
      reqHandler = new RequestHandler(new OutletStub());
    }catch (Exception e){
      System.out.println(e);
      e.printStackTrace();
      fail();
    }

  }


  @Test
  void handleRequestCorrectON() {
    final String input =
        "POST /on HTTP/1.1\n" + "\r\n";
    final String expected = "HTTP/1.1 200 OK" + "\n\r\n";
    final String actual = reqHandler.handleRequest(input);

    assertEquals(expected,actual);
  }
  @Test
  void handleRequestCorrectOFF() {
    final String input =
        "POST /off HTTP/1.1\n" + "\r\n";
    final String expected = "HTTP/1.1 200 OK" + "\n\r\n";
    final String actual = reqHandler.handleRequest(input);

    assertEquals(expected,actual);
  }
  @Test
  void handleRequestCorrectSTATUS() {
    final String input =
            "POST /status HTTP/1.1\n" + "\r\n";
    final String expected = "HTTP/1.1 200 OK\n" + "content-type: application/json\n" +
            "\r\n" +
            "{\"status_power\":\"off\"}";
    final String actual = reqHandler.handleRequest(input);

    assertEquals(expected,actual);
  }
  @Test
  void handleRequestIncorrectEndpoint() {
    final String input =
            "POST /fish HTTP/1.1\n" + "\r\n";
    final String expected = HttpResponse.httpBadRequest();
    final String actual = reqHandler.handleRequest(input);

    assertEquals(expected,actual);
  }
  @Test
  void handleRequestIncorrectRequest() {
    final String input =
            "PST /status HTT/1.1\n" + "\r\n";
    final String expected = HttpResponse.httpBadRequest();
    final String actual = reqHandler.handleRequest(input);

    assertEquals(expected,actual);
  }

}