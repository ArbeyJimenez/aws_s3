package com.awss3.app.services;


import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import com.awss3.app.config.AmazonS3Config;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Service
public class DocumentManagementServiceImpl {

	private Logger logger = LoggerFactory.getLogger(DocumentManagementServiceImpl.class);
	
	private static final String BUCKET = "pruebacontentprivate";

	@Autowired
	private AmazonS3Config s3Config;


	/**
	 * Retorna el listado de los objetos que se encuentran en el
	 * bucket
	 * @return
	 */
	public List<S3Object> listObjectsBucket() {

		ListObjectsRequest listObjects = ListObjectsRequest
				.builder()
				.bucket(BUCKET)
				.build();

		S3Client s3client = s3Config.amazonS3();
		ListObjectsResponse res = s3client.listObjects(listObjects);
		List<S3Object> objects = res.contents();
		//s3client.close();
		return objects;
	}

	/**
	 * Genera un listado de url privadas firmadas
	 * @return
	 */
	public HashMap<String, String> getGeneratePresignedURL() {
		try {
			List<S3Object> items = this.listObjectsBucket();
			HashMap<String, String> urls = new HashMap<String, String>();

			for (S3Object item : items) {
				String urlPresigned =  getGeneratePresignedURLByItem(item.key());
				urls.put(item.key(), urlPresigned);
			}

			return urls;

		} catch (AwsServiceException e) {
			logger.error("Error AwsServiceException: " + e.getMessage());
			return null;
		} catch (SdkClientException e) {
			//e.printStackTrace();
			logger.error("Error SdkClientException: " + e.getMessage());
			return null;
		}	
	}


	/**
	 * Retorna la url firmada para acceder a un recurso privado
	 * @param item
	 * @return
	 */
	public String getGeneratePresignedURLByItem(String item) {
		try {
			S3Presigner presigner = s3Config.presignerS3();

			
			GetObjectRequest getObjectRequest = GetObjectRequest.builder()
					.bucket(BUCKET)
					.key(item)
					.build();

			GetObjectPresignRequest getObjectPresignRequest =  GetObjectPresignRequest.builder()
					.signatureDuration(Duration.ofMinutes(10))
					.getObjectRequest(getObjectRequest)
					.build();

			// Generate the presigned request
			PresignedGetObjectRequest presignedGetObjectRequest = presigner.presignGetObject(getObjectPresignRequest);
			
			return presignedGetObjectRequest.url().toString();

		} catch (AwsServiceException e) {
			logger.error("Error AwsServiceException: " + e.getMessage());
			return null;
		} catch (SdkClientException e) {
			logger.error("Error SdkClientException: " + e.getMessage());
			return null;
		}	
	}

}
