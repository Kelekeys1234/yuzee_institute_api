package com.yuzee.app.endpoint;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuzee.app.dto.InstituteEnglishRequirementsDto;

@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public interface InstituteEnglishRequirementsInterface {

	@PostMapping("/englishRequirements/{instituteId}")
	public ResponseEntity<?> addInstituteEnglishRequirements(@RequestHeader("userId") final String userId,@PathVariable final String instituteId,@Valid @RequestBody InstituteEnglishRequirementsDto instituteEnglishRequirementsDto) throws Exception;

	@PutMapping("/englishRequirements/{instituteId}")
	public ResponseEntity<?> updateInstituteEnglishRequirements(@RequestHeader("userId") final String userId,@PathVariable final String instituteId,@Valid @RequestBody InstituteEnglishRequirementsDto instituteEnglishRequirementsDto) throws Exception;
	
	@GetMapping("/englishRequirements/{instituteId}")
	public ResponseEntity<?> getInstituteEnglishRequirementsByInstituteId (@RequestHeader("userId") final String userId,@PathVariable final String instituteId) throws Exception;
	
	@GetMapping("/public/englishRequirements/{instituteId}")
	public ResponseEntity<?> getInstitutePublicEnglishRequirementsByInstituteId (@PathVariable final String instituteId) throws Exception;
	
	@DeleteMapping("/englishRequirements/{instituteId}")
	public ResponseEntity<?> deleteInstituteEnglishRequirementsByRequirementsId (@RequestHeader("userId") final String userId,@PathVariable final String englishRequirementsId);
	
}
