package com.seeka.app.endpoint;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seeka.app.dto.InstituteAdditionalInfoDto;

@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public interface InstituteAdditionalInfoInterface {

	@PostMapping("/additional/info/{instituteId}")
	public ResponseEntity<?> addInstituteAdditionalInfo(@RequestHeader("userId") final String userId,@PathVariable final String instituteId ,@RequestBody @Valid InstituteAdditionalInfoDto instituteAdditionalInfoDto) throws Exception;
		
	@GetMapping("/additional/info/{instituteId}")
	public ResponseEntity<?> getInstituteAdditionalInfo(@RequestHeader("userId") final String userId,@PathVariable final String instituteId) throws Exception;
		
	@GetMapping("/public/additional/info/{instituteId}")
	public ResponseEntity<?> getInstitutePublicAdditionalInfo (@PathVariable final String instituteId) throws Exception;
}
