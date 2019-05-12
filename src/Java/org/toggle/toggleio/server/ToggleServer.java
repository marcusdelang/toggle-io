package org.toggle.toggleio.server;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.PortUnreachableException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Class that contains a server that listens on a port using the SOCKET library.
 * It will hand over all HTTP requests to the Telldus RequestHandler
 */
public class ToggleServer implements Runnable{
    private RequestHandler requestHandler;
    private int port;
    private volatile boolean exit;
    private volatile boolean closed;
    ServerSocket welcomeSocket;
    public ToggleServer(RequestHandler requestHandler, int port) {
        this.requestHandler = requestHandler;
        this.exit=false;
        this.port = port;
        this.closed = true;
    }
    public ToggleServer(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
        this.exit=false;
        this.port = 8080;
        this.closed = true;
    }
    @Override
    public void run() {
        int port = 8080;
            runtime(this.port);

    }

    private void runtime(int port) {
        String clientSentence;
        String clientLines = null;
        String response = null;



        try {
            welcomeSocket = new ServerSocket(port);
        } catch (Exception ex) {
            System.out.println("Could not open socket on port " + port);
            return;
        }
        System.out.println("Server is running on port " + port);
        try {
            while (!exit) {
                this.closed = false;
                Socket connectionSocket = welcomeSocket.accept();

                try {
                    connectionSocket.setSoTimeout(5000);
                    BufferedReader fromClient =
                            new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                    DataOutputStream outToClient =
                            new DataOutputStream(connectionSocket.getOutputStream());
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
                    StringBuilder payload = new StringBuilder();
                    while(fromClient.ready()){
                        payload.append((char) fromClient.read());
                    }
                    if(payload.length()>0)sentenceBuilder.append("\n\n");
                    sentenceBuilder.append(payload.toString());
                    clientSentence = sentenceBuilder.toString();

                    try {
                        response = requestHandler.handleRequest(clientSentence);
                    } catch (JSONException pe) {
                        response = HttpResponse.httpInternalServerError();
                        System.out.println("Responding with:\n" + response + "\n\n");
                        outToClient.writeBytes(response);
                        throw pe;
                    }
                    System.out.println("Responding with:\n" + response + "\n\n");
                    outToClient.writeBytes(response);
                    fromClient.close();
                    connectionSocket.close();
                } catch (SocketTimeoutException ste) {
                    System.out.println("Connection timed out!");
                    connectionSocket.close();
                } catch (JSONException pe) {
                    System.out.println("Something is wrong with the JSON file!");
                    connectionSocket.close();
                    return;
                }
            }
        } catch (SocketException se) {
            this.closed=true;
            this.exit = false;
            System.out.println(se);
        } catch (IOException ioe) {
            this.closed=true;
            this.exit=false;
            System.out.println(ioe);
        }
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
    public void setPort(int port){
        this.port = port;
    }
    public boolean exiting(){
        return this.exit;
    }
    public boolean isClosed() {
        return closed;
    }
    public void closeSocket(){

        try {
            welcomeSocket.close();
        }catch (IOException io){
            io.printStackTrace();
        }
    }
}
