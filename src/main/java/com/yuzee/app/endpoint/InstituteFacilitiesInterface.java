package com.yuzee.app.endpoint;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuzee.app.dto.InstituteFacilityDto;

@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public interface InstituteFacilitiesInterface {
	
	@PostMapping("/institute/facilities/{instituteId}")
	public ResponseEntity<?> addInstituteFacilities(@RequestHeader("userId") final String userId,@PathVariable final String instituteId, @RequestBody @Valid InstituteFacilityDto instituteFacilityDto) throws Exception;
	
	@DeleteMapping("/institute/facilities/{instituteId}")
	public ResponseEntity<?> deleteInstituteFacilitiesById(@RequestHeader("userId") final String userId,@PathVariable final String instituteId,@RequestParam(value="institute_facility_id", required=false) List<String> institutefacilitiesId) throws Exception;
	
	@GetMapping("/institute/facilities/{instituteId}")
	public ResponseEntity<?> getInstituteFacilities (@RequestHeader("userId") final String userId,@PathVariable final String instituteId) throws Exception;
	
	@GetMapping("/institute/public/facilities/{instituteId}")
	public ResponseEntity<?> getInstitutePublicFacilities (@PathVariable final String instituteId ) throws Exception;

}
