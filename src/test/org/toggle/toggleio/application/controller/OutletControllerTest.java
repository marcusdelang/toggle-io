package org.toggle.toggleio.application.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;

class OutletControllerTest {

	private OutletController outletController;
	private String script;
	
    @BeforeEach
    void setUp() {
    	//ScriptRunner scriptRunner = new ScriptRunner();        
    	//TelldusScript telldusScript = new TelldusScript();
    	outletController=new OutletController();
        script=TelldusScript.on();
    }

    @Test
    void isAlive() {
    	assertTrue(outletController.isAlive());
    }

    @Test
    void turnOn() {
        assertThat(script, containsString("tdtool --on 2"));        
        assertThat(scriptRunner.runScript(script),is(true));
    }

    @Test
    void turnOff() {
        assertThat(script, containsString("tdtool --off 2"));        
        assertThat(scriptRunner.runScript(script,is(true));
    }
    
}