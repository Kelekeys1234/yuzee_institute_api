package com.seeka.app.endpoint;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seeka.app.dto.InstitutePageRequestDto;

@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public interface InstitutePageRequestInterface {

	@PostMapping("/request/page/{instituteId}")
	public ResponseEntity<?> requestInstitutePageAccess(@RequestHeader("userId") final String userId,@PathVariable final String instituteId, @Valid @RequestBody InstitutePageRequestDto institutePageRequestDto) throws Exception;
	
	
	/*
	 * @GetMapping("/institute/facilities/{instituteId}") public ResponseEntity<?>
	 * getRequestInstitutePageAccess(@RequestHeader("userId") final String userId,
	 * 
	 * @PathVariable final String instituteId) throws Exception;
	 * 
	 * @GetMapping("/institute/public/facilities/{instituteId}") public
	 * ResponseEntity<?> getInstitutePublicFacilities(@PathVariable final String
	 * instituteId) throws Exception;
	 */
	 
}
