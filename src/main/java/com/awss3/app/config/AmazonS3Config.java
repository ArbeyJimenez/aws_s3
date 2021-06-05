package com.awss3.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class AmazonS3Config {
	
	private Region region = Region.US_EAST_1;

	@Bean
	public S3Client amazonS3() {
		return S3Client.builder().region(region).build();
	}

	@Bean
	public S3Presigner presignerS3() {
		return S3Presigner.builder()
				.region(region)
				.build();	
	}


}
