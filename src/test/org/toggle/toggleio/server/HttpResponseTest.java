package org.toggle.toggleio.server;


import static org.junit.jupiter.api.Assertions.*;
import java.lang.String;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HttpResponseTest {
  @Test
  void httpOk() {
    final String expected =  "HTTP/1.1 200 OK"+"\n"+"\r\n";
    final String actual = HttpResponse.httpOk();
    assertEquals(actual,expected);

  }

  @Test
  void httpBadRequest() {
    final String expected = "HTTP/1.1 400 Bad Request"+"\n"+"\r\n";
    final String actual = HttpResponse.httpBadRequest();
    assertEquals(actual,expected);
  }
}