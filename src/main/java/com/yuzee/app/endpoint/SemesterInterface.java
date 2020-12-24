package com.yuzee.app.endpoint;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuzee.app.dto.SemesterDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.exception.CommonInvokeException;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.ValidationException;

@RequestMapping("/api/v1/semester")
public interface SemesterInterface {

	@PostMapping
	public ResponseEntity<?> saveAll(@RequestHeader("userId") final String userId,
			@RequestBody @Valid final ValidList<SemesterDto> semesters) throws ValidationException;

	@PutMapping("/{semesterId}")
	public ResponseEntity<?> update(@RequestHeader("userId") final String userId, @PathVariable final String semesterId,
			@Valid @RequestBody final SemesterDto semester)
			throws ValidationException, CommonInvokeException, ForbiddenException;

	@DeleteMapping("/{semesterId}")
	public ResponseEntity<?> delete(@RequestHeader("userId") final String userId,
			@PathVariable final String offCampusCourseId) throws ValidationException, ForbiddenException;

	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getAll(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize)
			throws ValidationException;
}
