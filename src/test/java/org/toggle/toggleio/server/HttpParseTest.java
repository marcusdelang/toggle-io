package org.toggle.toggleio.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HttpParseTest {

  @Test
  void parseUrlEndpointCorrectInput() {
    final String input =
        "POST /toggle HTTP/1.1\n" +
            "Host: www.example.com\n" +
            "Content-Type: application/json\n" +
            "Content-Length: 49\n" +
            "\n" +
            "{" +
            "\"deviceID\":\"2\"," +
            "\"operation\":\"1\"" +
            "}";
    final String expected = "/toggle";
    final String actual = HttpParse.parseUrlEndpoint(input);
    assertEquals(actual,expected);
  }
  @Test
  void parseUrlEndpointEmptyInputException() {
    final String input = "";
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      HttpParse.parseUrlEndpoint(input);
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
            "{" +
            "    \"deviceID\": \"2\"," +
            "    \"operation\": \"1\"" +
            "}";
    final String expected = "application/json";
    final String actual = HttpParse.parseContentType(input);
    assertEquals(actual,expected);
  }
  @Test
  void parseContentTypeIncorrectInput() {
    final String input =
            "POT /toggle HTP/1.1\n" +
                    "Host: www.example.com\n" +
                    "Content-Type: application/json\n" +
                    "Content-Length: 49\n" +
                    "\n" +
                    "{\n" +
                    "    \"deviceID\": \"2\"," +
                    "    \"operation\": \"1\"" +
                    "}";
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      HttpParse.parseContentType(input);
    });
  }
  @Test
  void parseContentTypeEmptyInputException() {
    final String input = "";
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      HttpParse.parseContentType(input);
    });

  }
}