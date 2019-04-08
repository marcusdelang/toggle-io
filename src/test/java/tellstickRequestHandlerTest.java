import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.String;

class tellstickRequestHandlerTest {
    private tellstickRequestHandler tellHandler;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void parseCorrectRequest() {
        final String input =
                "POST /toggle\n" +
                "Host: www.example.com\n" +
                "Content-Type: application/json\n" +
                "Content-Length: 49 \n" +
                "\r\n" +
                "{\n" +
                "    \"deviceID\": \"2\",\n" +
                "    \"operation\": \"1\",\n" +
                "}";
        final String expectedResponse = "HTTP/1.1 200 OK" + "\r\n";
        final String response = tellHandler.parseRequest(input);
        assertEquals(response, expectedResponse);
    }

    @Test
    void parseInvalidJSONRequest() {
        final String input =  "POST /toggle\n" +
                "Host: www.example.com\n" +
                "Content-Type: application/json\n" +
                "Content-Length: 49 \n" +
                "\n" +
                "{\n" +
                "    \"deviceD\": \"2\",\n" + //deviceD instead of deviceID
                "    \"operation\": \"1\",\n" +
                "}";
        final String expectedResponse = "HTTP/1.1 400 Bad Request"+"\r\n";
    }
    @Test
    void parseJibberishRequest() {
        final String input = "ASGjgoak asf sasa as \n asfsa g\n \nasfasfasfasfasfas";
        final String expectedResponse = "HTTP/1.1 400 Bad Request"+"\r\n";

    }
    @Test
    void parseAlmostCorrectRequest() {
        final String input = "ASGjgoak asf sasa as \n asfsa g\n \nasfasfasfasfasfas";
        final String expectedResponse = "HTTP/1.1 400 Bad Request"+"\r\n";
    }

}