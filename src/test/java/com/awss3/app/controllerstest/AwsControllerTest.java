package com.awss3.app.controllerstest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.awss3.app.controllers.AwsController;

public class AwsControllerTest {
	
	@Autowired
	private AwsController awsCtrl;
	
	
	@Test
    void shouldGetDefaultWelcomeMessage() {
        assertEquals("Welcome Stranger!", awsCtrl.getSaludo());
    }


}
