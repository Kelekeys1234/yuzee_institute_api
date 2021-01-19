package com.yuzee.app.endpoint;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuzee.app.dto.CourseFundingDto;
import com.yuzee.app.exception.ValidationException;

@RequestMapping("/api/v1/course-funding")
public interface CourseFundingInterface {

	@PostMapping("/institute/{instituteId}/add-funding-to-all-courses")
	public ResponseEntity<?> addFundingToAllInstituteCourses(@RequestHeader("userId") final String userId,
			@PathVariable final String instituteId, @Valid @RequestBody final CourseFundingDto courseFundingDto)
			throws ValidationException;
}
