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
import org.springframework.web.bind.annotation.RequestParam;

import com.yuzee.common.lib.dto.institute.InstituteBasicInfoDto;

@RequestMapping(path = "/api/v1")
//@Consumes({ "application/json", "application/xml" })
//@Produces({ "application/json", "application/xml" })
public interface InstituteBasicInfoInterface {

	@PostMapping("/basic/info/{instituteId}")
	public ResponseEntity<?> addUpdateInstituteBasicInfo (@RequestHeader("userId" ) final String userId,@PathVariable final String instituteId, @RequestBody InstituteBasicInfoDto instituteBasicInfoDto) throws Exception;

	@GetMapping("/basic/info/{instituteId}")
	public ResponseEntity<?> getInstituteBasicInfo (@RequestHeader("userId") final String userId,@PathVariable final String instituteId) throws Exception;
	
	@GetMapping("/public/basic/info/{instituteId}")
	public ResponseEntity<?> getInstitutePublicBasicInfo (@PathVariable final String instituteId, @RequestParam(name = "includeInstituteLogo", defaultValue = "true") boolean includeInstituteLogo,
			@RequestParam(name = "includeDetail", required = false) boolean includeDetail) throws Exception;

}
