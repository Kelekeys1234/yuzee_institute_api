package com.yuzee.app.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuzee.app.exception.NotFoundException;

@RequestMapping(path = "/api/v1/off-campus-course")
public interface OffCampusCourseInterface {

	@GetMapping(value = "/institute/{instituteId}/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getOffCampusCoursesByInstituteId(@PathVariable final String instituteId,
			@PathVariable Integer pageNumber, @PathVariable Integer pageSize) throws NotFoundException;
}
