package com.awss3.app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.awss3.app.controllers.AwsController;

@SpringBootTest
class Awss3ApplicationTests {

	
	@Autowired
	private AwsController awsCtrl;
	
	
	@Test
	void contextLoads() {
		assertThat(awsCtrl).isNotNull();
	}

}
