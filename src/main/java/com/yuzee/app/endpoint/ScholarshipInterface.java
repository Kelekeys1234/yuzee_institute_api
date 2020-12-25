package com.yuzee.app.endpoint;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuzee.app.dto.ScholarshipRequestDto;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;

@RequestMapping("/api/v1/scholarship")
public interface ScholarshipInterface {

	@PostMapping()
	public ResponseEntity<?> saveScholarship(@RequestHeader("userId") final String userId,
			@RequestBody @Valid final ScholarshipRequestDto scholarshipDto) throws Exception;

	@PutMapping("/{id}")
	public ResponseEntity<?> updateScholarship(@RequestHeader("userId") final String userId,
			@RequestBody @Valid final ScholarshipRequestDto scholarshipDto, @PathVariable final String id)
			throws Exception;

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable final String id)
			throws ValidationException, NotFoundException, InvokeException;

	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getAllScholarship(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(value = "country_name", required = false) final String countryName,
			@RequestParam(value = "institute_id", required = false) final String instituteId,
			@RequestParam(value = "search_keyword",required = false) final String searchKeyword) throws Exception;

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteScholarship(@RequestHeader("userId") final String userId,
			@PathVariable final String id) throws Exception;

	@GetMapping("/getScholarshipCountByLevel")
	public ResponseEntity<?> getScholarshipCountByLevel() throws Exception;

	@GetMapping("/multiple/id")
	public ResponseEntity<?> getMultipleScholarshipByIds(
			@RequestParam(name = "scholarship_ids", required = true) List<String> scholarshipIds);

}
