package com.yuzee.app.endpoint;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuzee.app.dto.InstituteAdditionalInfoDto;

@RequestMapping(path = "/api/v1")
@Consumes({ "application/json", "application/xml" })
@Produces({ "application/json", "application/xml" })
public interface InstituteAdditionalInfoInterface {

	@PostMapping("/additional/info/{instituteId}")
	public ResponseEntity<?> addInstituteAdditionalInfo(@RequestHeader("userId") final String userId,
			@PathVariable final String instituteId,
			@Valid @RequestBody final InstituteAdditionalInfoDto instituteAdditionalInfoDto) throws Exception;

	@GetMapping("/additional/info/{instituteId}")
	public ResponseEntity<?> getInstituteAdditionalInfo(@PathVariable final String instituteId) throws Exception;
}
