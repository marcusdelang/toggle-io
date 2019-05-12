package org.toggle.toggleio.application.model;

import org.junit.jupiter.api.Test;
import org.toggle.toggleio.server.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class MTelldusScriptsTest {

    @Test
    void on() {
        final String expected = "tdtool --on 1";
        final String actual = TelldusScripts.on();
        assertEquals(actual,expected);
    }

    @Test
    void off() {
        final String expected = "tdtool --off 1";
        final String actual = TelldusScripts.off();
        assertEquals(actual,expected);
    }
}