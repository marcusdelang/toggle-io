package org.toggle.toggleio.server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.PortUnreachableException;
import java.net.ServerSocket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ToggleServerTest {

  @Test
  void startOccupied() {
    try {
      ServerSocket occupy = new ServerSocket(80);
      Assertions.assertThrows(PortUnreachableException.class, () -> {
        ToggleServer.start();
      });
    }catch (IOException ioe){
      fail();
    }

  }

  @Test
  void startPortOccupied() {
    final int port = 8888;
    try {
      ServerSocket occupy = new ServerSocket(8888);
      Assertions.assertThrows(PortUnreachableException.class, () -> {
        ToggleServer.start(port);
      });
    }catch (IOException ioe){
      fail();
    }
  }

  @Test
  void runtimePortOccupied() {
    final int port = 8888;
    try {
      ServerSocket occupy = new ServerSocket(8888);
      Assertions.assertThrows(PortUnreachableException.class, () -> {
        ToggleServer.runtime(port);
      });
    }catch (IOException ioe){
      fail();
    }
  }
}