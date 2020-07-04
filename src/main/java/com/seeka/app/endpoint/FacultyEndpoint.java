package com.seeka.app.endpoint;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seeka.app.dto.FacultyDto;

@RequestMapping("/api/v1/faculty")
public interface FacultyEndpoint {

	@PostMapping
	public ResponseEntity<?> saveFaculty(@RequestBody FacultyDto facultyDto) throws Exception;

	@GetMapping
	public ResponseEntity<?> getAll() throws Exception;

	@GetMapping("/{facultyId}")
	public ResponseEntity<?> getFacultyById(@PathVariable String facultyId) throws Exception;

	@GetMapping("/name/{facultyName}")
	public ResponseEntity<?> getFacultyByFacultyName(@PathVariable("facultyName") String facultyName) throws Exception;
	
	@Deprecated
	@GetMapping("/institute/{instituteId}")
	public ResponseEntity<?> getFacultyByInstituteId(@Valid @PathVariable String instituteId) throws Exception;

	@Deprecated
	@GetMapping("/multiple/institute/{instituteId}")
	public ResponseEntity<?> getFacultyByListOfInstituteId(@Valid @PathVariable String instituteId) throws Exception;

	@Deprecated
	@GetMapping("/course/country/{countryId}/level/{levelId}")
	public ResponseEntity<?> getCourseFaculty(@PathVariable String countryId, @PathVariable String levelId)
			throws Exception;

	@Deprecated
	@GetMapping("/country/{countryId}/level/{levelId}")
	public ResponseEntity<?> getFacultyeByCountryAndLevelId(@PathVariable String countryId,
			@PathVariable String levelId) throws Exception;
	

}
