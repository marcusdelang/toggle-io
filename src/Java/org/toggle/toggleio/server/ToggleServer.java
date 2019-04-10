package org.toggle.toggleio.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.IIOException;

public class ToggleServer {
    public static void start(){
        int port = 80;
        runtime(port);
    }
    public static void start(int port){
       runtime(port);
    }
    public static void runtime(int port) {

        String clientSentence;
        ServerSocket welcomeSocket;
        String clientLines = null;

        try {
            welcomeSocket = new ServerSocket(port);
        } catch (Exception ex) {
            System.out.println("Could not open Socket!");
            return;
        }
        try {
            while (true) {

                Socket connectionSocket = welcomeSocket.accept();
                BufferedReader fromClient =
                    new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

                StringBuilder sentenceBuilder = new StringBuilder();
                while ((clientLines = fromClient.readLine()) != null) {
                    if (clientLines.isEmpty())
                        break;
                    sentenceBuilder.append(clientLines + "\n");
                }

                sentenceBuilder.setLength(sentenceBuilder.length() - 1);
                clientSentence = sentenceBuilder.toString();

                RequestHandler.handleRequest(connectionSocket, clientSentence);
            }
        }
        catch (IOException ioe){
            System.out.println("Something went wrong");
            return;
        }


    }

}
