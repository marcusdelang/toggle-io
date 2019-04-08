import java.lang.*;
public class httpParser {

    /**
     * @param HTTPMsgToParse Entire HTTP header and body
     * @return JSON contained in HTTP body
     */
    public static String[] getJSON(String HTTPMsgToParse){

        return JSON;
    }

    /**
     * @param JSONToParse HTTP body containing JSON data
     * @return JSON object
     */
    public static String[] parseJSON(String JSONToParse){

        return JSON;
    }
    /**
     * @param HTTPMsgToParse Entire HTTP message with body
     * @return Body of HTTP message
     */
    public static String[] getBody(String HTTPMsgToParse){

        return body;
    }

    /**
     * @param HTTPMsgToParse Entire HTTP message
     * @return date of message
     */
    public static String getDate(String HTTPMsgToParse){

        return date;
    }

    /**
     * @param HTTPMsgToParse Entire HTTP message
     * @return content length of messsage
     */
    public static String getContentLength(String HTTPMsgToParse){

        return contentLength;
    }

    /**
     * @param HTTPMsgToParse Entire HTTP message
     * @return content type
     */
    public static String getContentType(String HTTPMsgToParse){

        return contentType;
    }
}
