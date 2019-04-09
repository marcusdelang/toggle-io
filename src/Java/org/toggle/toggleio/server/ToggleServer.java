package org.toggle.toggleio.server;

import java.io.DataOutputStream;

public class ToggleServer {
    public static void start(){
        DataOutputStream socket;
        RequestHandler.handleRequest(socket);
    }

}
