package com.seeka.app.endpoint;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seeka.app.dto.AccrediatedDetailDto;
import com.seeka.app.exception.NotFoundException;

@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public interface AccrediatedDetailInterface {

	@PostMapping("/accrediation")
	public ResponseEntity<?> addAccrediationDetail(@RequestBody AccrediatedDetailDto accrediatedDetailDto) ;
	
	@GetMapping("/accrediation/{entityId}")
	public ResponseEntity<?> getAccrediationDetail(@PathVariable("entityId") String entityId) throws NotFoundException ;
	
	@DeleteMapping("/accrediation/{entityId}")
	public ResponseEntity<?> deleteAccrediationDetail(@PathVariable("entityId") String entityId) ;
	
	@PutMapping("/accrediation/{id}")
	public ResponseEntity<?> updateAccrediationDetail(@PathVariable("id") String id, @RequestBody AccrediatedDetailDto accrediatedDetailDto) throws NotFoundException;
}
