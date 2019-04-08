import java.lang.*;

public class tellstickRequestHandler {
    public static String parseRequest(String clientSentence){
        String response = "no"; // no = Placeholder
        if(!httpParser.getContentType(clientSentence).equals("application/json"))
        {
            response = "HTTP/1.1 400 Bad Request"+"\r\n";
            return response;
        }
        return response;
    }
    private static boolean tellstickMsgBuilder(String deviceID, String Operation){
        String commandToSend = "tdtool --on 2"; //Placeholder
        return shellSend(commandToSend);
    }
    private static boolean shellSend(String shellMsg){
        boolean success = false;
        return success;
    }
}
