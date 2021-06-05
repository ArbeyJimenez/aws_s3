package com.awss3.app.controllers;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.awss3.app.models.AdjuntoPoder;
import com.awss3.app.repositories.AdjuntoPoderRepository;
import com.awss3.app.services.DocumentManagementServiceImpl;

import software.amazon.awssdk.services.s3.model.S3Object;

@RestController
@RequestMapping(value = "/aws")
public class AwsController 
{
	@Autowired
	private DocumentManagementServiceImpl s3;

	@Autowired
	private AdjuntoPoderRepository adjuntoPoder;
	
	
	@GetMapping(value = "/getsaludo")
	public String getSaludo() 
	{
		return "Welcome Stranger!";
	}
	
	
	@GetMapping(value = "/getbuckets")
	public ResponseEntity<?> getBuckets() 
	{
		List<S3Object> buckes = s3.listObjectsBucket();
		return ResponseEntity.ok(buckes);
	}
	
	@GetMapping(value = "/getobjectssigned")
	public ResponseEntity<?> getObjetcsSigned()
	{
		HashMap<String, String> urlSigned = s3.getGeneratePresignedURL();
		return ResponseEntity.ok(urlSigned);
	}
	
	
	@GetMapping(value = "/getitems")
	public ResponseEntity<?> getItems()
	{
		List<AdjuntoPoder> documentos = adjuntoPoder.findByCodAsamblea(1);
		
		if (!documentos.isEmpty()) {
			HashMap<String, String> urlSigned = new HashMap<String, String>();
			for (AdjuntoPoder adjuntoPoder : documentos) {
				String doc = s3.getGeneratePresignedURLByItem(adjuntoPoder.getRutaBucket());
				urlSigned.put(adjuntoPoder.getRutaBucket(), doc);
			}
			return ResponseEntity.ok(urlSigned);
		} else {
			ResponseEntity.notFound();
		}
		return null;
	}
}
