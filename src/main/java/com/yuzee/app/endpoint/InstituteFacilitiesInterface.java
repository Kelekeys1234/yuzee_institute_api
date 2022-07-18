package com.yuzee.app.endpoint;

import java.util.List;



import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

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
import com.yuzee.common.lib.exception.NotFoundException;

@RequestMapping(path = "/api/v1")
@Consumes({ "application/json", "application/xml" })
@Produces({ "application/json", "application/xml" })
public interface InstituteFacilitiesInterface {

	@PostMapping("/institute/facilities/{instituteId}")
	public ResponseEntity<?> addInstituteFacilities(@PathVariable final String instituteId,
			@RequestBody InstituteFacilityDto instituteFacilityDto) throws NotFoundException;

	@DeleteMapping("/institute/facilities/{instituteId}")
	public ResponseEntity<?> deleteInstituteFacilitiesById(@PathVariable final String instituteId,
			@RequestParam(value = "institute_facility_id", required = false) List<String> institutefacilitiesId);

	@GetMapping("/institute/getFacilities/{instituteId}")
	public ResponseEntity<?> getInstituteFacilities(@PathVariable final String instituteId);

	@GetMapping("/institute/public/facilities/{instituteId}")
	public ResponseEntity<?> getInstitutePublicFacilities(@PathVariable final String instituteId);

}
