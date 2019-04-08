import java.lang.*;
import org.json.*;

public class tellstickRequestHandler {
    /**
     *
     * @param clientSentence String HTTP POST request for tellstick service.
     * @return response String HTTP response from tellstick service.
     */
    public static String parseRequest(String clientSentence){
        String[] httpParts = clientSentence.split("\n\n");
        System.out.println("This is header:\n"+httpParts[0]+"\n"); // Development

        JSONObject JSONrequest = HTTP.toJSONObject(httpParts[0]);
        String response = "no"; // no = Placeholder

        JSONObject JSONPost = new JSONObject(httpParts[1]);
        System.out.println("This is body:\n"+httpParts[1]);  // Development
        if(!JSONrequest.getString("Content-Type").equals("application/json"))
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
