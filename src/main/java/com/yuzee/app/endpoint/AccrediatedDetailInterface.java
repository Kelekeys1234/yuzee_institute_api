package com.yuzee.app.endpoint;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuzee.app.dto.AccrediatedDetailDto;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;

@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public interface AccrediatedDetailInterface {

	@PostMapping("/accrediation")
	public ResponseEntity<?> addAccrediationDetail(@RequestBody AccrediatedDetailDto accrediatedDetailDto);
	
	@GetMapping("/accrediation/entity/{id}")
	public ResponseEntity<?> getAccrediationDetailByEntityId(@PathVariable("id") String id) throws NotFoundException;
	
	@DeleteMapping("/accrediation/entity/{id}")
	public ResponseEntity<?> deleteAccrediationDetailByEntityId (@PathVariable("id") String id) throws NotFoundException, InvokeException;
	
	@PutMapping("/accrediation/{id}")
	public ResponseEntity<?> updateAccrediationDetail(@PathVariable("id") String id, @RequestBody AccrediatedDetailDto accrediatedDetailDto) throws NotFoundException;
	
	@GetMapping("/accrediation/{id}")
	public ResponseEntity<?> getAccrediationDetailById(@PathVariable("id") String id) throws NotFoundException, InvokeException;
	
	@DeleteMapping("accrediation/{id}")
	public ResponseEntity<?> deleteAccrediationDetailById(@PathVariable("id") String id) throws NotFoundException, InvokeException;
	
	@GetMapping("/accrediation")
	public ResponseEntity<?> getAllAccrediationDetails();
}
