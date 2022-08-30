package com.yuzee.app.endpoint;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;


@RequestMapping(path = "/api/v1")
@Consumes({ "application/json", "application/xml" })
@Produces({ "application/json", "application/xml" })
public interface InstituteCampusInterface {

	@PostMapping("campus/instituteId/{instituteId}")
	public ResponseEntity<?> addCampus(@RequestHeader("userId") final String userId,
			@PathVariable final String instituteId, @RequestBody final @NotEmpty List<String> instituteIds)
			throws NotFoundException, ValidationException;

	@GetMapping("/campus/instituteId/{instituteId}")
	public ResponseEntity<?> getInstituteCampuses(@RequestHeader("userId") final String userId,
			@PathVariable final String instituteId) throws NotFoundException;

	@DeleteMapping("/campus/instituteId/{instituteId}")
	public ResponseEntity<?> removeCampus(@RequestHeader(value = "userId", required = true) final String userId,
			@PathVariable final String instituteId, @RequestBody final @NotEmpty List<String> instituteIds)
			throws NotFoundException;
}
