package com.yuzee.app.endpoint;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuzee.app.dto.ScholarshipDto;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;

@RequestMapping("/api/v1/scholarship")
public interface ScholarshipInterface {

	@PostMapping()
	public ResponseEntity<?> saveScholarship(@RequestBody final ScholarshipDto scholarshipDto, final BindingResult bindingResult) throws Exception;
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateScholarship(@RequestBody final ScholarshipDto scholarshipDto, final BindingResult bindingResult,
			@PathVariable final String id) throws Exception;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable final String id) throws ValidationException, NotFoundException, InvokeException;
	
	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getAllScholarship(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,
			@RequestParam(required = false) final String sortByField, @RequestParam(required = false) final String sortByType,
			@RequestParam(required = false) final String searchKeyword, @RequestParam(required = false) final String countryName,
			@RequestParam(required = false) final String instituteId, @RequestParam(required = false) final String validity,
			@RequestParam(required = false) final Boolean isActive,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") final Date updatedOn) throws Exception;
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteScholarship(@PathVariable final String id) throws Exception;
	
	@GetMapping("/getScholarshipCountByLevel")
	public ResponseEntity<?> getScholarshipCountByLevel() throws Exception;
	
	@GetMapping("/multiple/id")
	public ResponseEntity<?> getMultipleScholarshipByIds(@RequestParam(name = "scholarship_ids", required = true) List<String> scholarshipIds);
	
}
