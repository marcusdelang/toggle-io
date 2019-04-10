package org.toggle.toggleio.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.lang.String;

class RequestHandlerTest {

  @Test
  void parseUrlEndpointCorrectInput() {
   final String input =
        "POST /toggle HTTP/1.1\n" +
            "Host: www.example.com\n" +
            "Content-Type: application/json\n" +
            "Content-Length: 49\n" +
            "\n" +
            "{\n" +
            "    \"deviceID\": \"2\",\n" +
            "    \"operation\": \"1\",\n" +
            "}";
   final String expected = "/toggle";
   final String actual = RequestHandler.parseUrlEndpoint(input);
   assertEquals(actual,expected);
  }
  @Test
  void parseUrlEndpointEmptyInputException() {
    final String input = "";
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      RequestHandler.parseUrlEndpoint(input);
    });
  }

  @Test
  void parseContentTypeCorrectInput() {
    final String input =
        "POST /toggle HTTP/1.1\n" +
            "Host: www.example.com\n" +
            "Content-Type: application/json\n" +
            "Content-Length: 49\n" +
            "\n" +
            "{\n" +
            "    \"deviceID\": \"2\",\n" +
            "    \"operation\": \"1\",\n" +
            "}";
    final String expected = "application/json";
    final String actual = RequestHandler.parseContentType(input);
    assertEquals(actual,expected);
  }
  @Test
  void parseContentTypeEmptyInputException() {
    final String input = "";
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      RequestHandler.parseContentType(input);
    });

  }

}