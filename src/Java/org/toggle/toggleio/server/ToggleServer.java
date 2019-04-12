package org.toggle.toggleio.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.PortUnreachableException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import javax.imageio.IIOException;

public class ToggleServer {

  public static void start() throws PortUnreachableException {
    int port = 80;
    try {
      runtime(port);
    } catch (PortUnreachableException pue) {
      throw new PortUnreachableException("Could not open Socket on port " + port);
    }

  }

  public static void start(int port) throws PortUnreachableException {
    try {
      runtime(port);
    } catch (PortUnreachableException pue) {
      throw new PortUnreachableException("Could not open Socket on port" + port);
    }
  }

  private static void runtime(int port) throws PortUnreachableException {

    String clientSentence;
    ServerSocket welcomeSocket;
    String clientLines = null;

    try {
      welcomeSocket = new ServerSocket(port);
    } catch (Exception ex) {
      throw new PortUnreachableException("Could not open socket on port " + port);
    }
    System.out.println("Server is running on port " + port);
    try {
      while (true) {
        Socket connectionSocket = welcomeSocket.accept();
        System.out.println("Received request from " + connectionSocket);
        BufferedReader fromClient =
            new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

        StringBuilder sentenceBuilder = new StringBuilder();
        while ((clientLines = fromClient.readLine()) != null) {
          if (clientLines.isEmpty()) {
            break;
          }
          sentenceBuilder.append(clientLines + "\n");
        }
        if (sentenceBuilder.length() > 0) {
          sentenceBuilder.setLength(sentenceBuilder.length() - 1);
        }
        clientSentence = sentenceBuilder.toString();

        RequestHandler.handleRequest(connectionSocket, clientSentence);
        fromClient.close();
        connectionSocket.close();
      }
    } catch (SocketException se) {
      System.out.println("Could not close connection");
    } catch (IOException ioe) {
      System.out.println("Something went wrong");
    }
  }
}
