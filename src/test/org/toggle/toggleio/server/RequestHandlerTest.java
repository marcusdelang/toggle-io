package org.toggle.toggleio.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.toggle.toggleio.application.controller.OutletController;
import org.toggle.toggleio.application.model.TellstickCore;
import org.toggle.toggleio.application.view.Outlet;

class RequestHandlerTest {
  RequestHandler reqHandler;
  OutletStub outlet;

  @BeforeEach
  void setUp() {
    reqHandler = new RequestHandler(new OutletStub(new OutletController(new TellstickCore())));
  }


  @Test
  void handleRequestCorrectON() {
    final String input =
        "POST /on HTTP/1.1\n" + "\r\n";
    final String expected = "HTTP/1.1 200 OK" + "\n\r\n"+ "{\"status_power\":\"on\"}";
    final String actual = reqHandler.handleRequest(input);

    assertEquals(expected,actual);
  }
  @Test
  void handleRequestCorrectOFF() {
    final String input =
        "POST /off HTTP/1.1\n" + "\r\n";
    final String expected = "HTTP/1.1 200 OK" + "\n\r\n"+ "{\"status_power\":\"off\"}";
    final String actual = reqHandler.handleRequest(input);

    assertEquals(expected,actual);
  }
}