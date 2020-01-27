package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Level;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.ScholarshipDto;
import com.seeka.app.dto.ScholarshipResponseDTO;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.ILevelService;
import com.seeka.app.service.IScholarshipService;
import com.seeka.app.util.PaginationUtil;

@RestController
@RequestMapping("/scholarship")
public class ScholarshipController {

	@Autowired
	private IScholarshipService iScholarshipService;
	
	@Autowired
    private ILevelService levelService;

	@PostMapping()
	public ResponseEntity<?> saveScholarship(@RequestBody final ScholarshipDto scholarshipDto, final BindingResult bindingResult) throws Exception {
		final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			throw new ValidationException(fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(",")));
		}
		iScholarshipService.saveScholarship(scholarshipDto);
		return new GenericResponseHandlers.Builder().setMessage("Create Scholarship Successfully.").setStatus(HttpStatus.OK).create();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateScholarship(@RequestBody final ScholarshipDto scholarshipDto, final BindingResult bindingResult,
			@PathVariable final BigInteger id) throws Exception {
		final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			throw new ValidationException(fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(",")));
		}
		iScholarshipService.updateScholarship(scholarshipDto, id);
		return new GenericResponseHandlers.Builder().setMessage("Update Scholarship Successfully.").setStatus(HttpStatus.OK).create();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable final BigInteger id) throws Exception {
		return new GenericResponseHandlers.Builder().setMessage("Get Scholarship Successfully.").setData(iScholarshipService.getScholarshipById(id))
				.setStatus(HttpStatus.OK).create();
	}

	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getAllScholarship(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,
			@RequestParam(required = false) final String sortByField, @RequestParam(required = false) final String sortByType,
			@RequestParam(required = false) final String searchKeyword, @RequestParam(required = false) final BigInteger countryId,
			@RequestParam(required = false) final BigInteger instituteId, @RequestParam(required = false) final String validity,
			@RequestParam(required = false) final Boolean isActive,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") final Date updatedOn) throws Exception {

		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<ScholarshipResponseDTO> scholarshipResponseDTOs = iScholarshipService.getScholarshipList(startIndex, pageSize, countryId, instituteId, validity,
				isActive, updatedOn, searchKeyword, sortByField, sortByType);
		int totalCount = iScholarshipService.countScholarshipList(countryId, instituteId, validity, isActive, updatedOn, searchKeyword);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get Scholarship List successfully");
		responseMap.put("data", scholarshipResponseDTOs);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteScholarship(@PathVariable final BigInteger id) throws Exception {
		iScholarshipService.deleteScholarship(id);
		return new GenericResponseHandlers.Builder().setMessage("delete Scholarship Successfully.").setStatus(HttpStatus.OK).create();
	}
	
	@GetMapping(value = "/getScholarshipCountByLevel")
	public ResponseEntity<?> getScholarshipCountByLevel() throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		List<Level> levels = levelService.getAll();
		if (!CollectionUtils.isEmpty(levels)) {
			response = iScholarshipService.getScholarshipCountByLevelId(levels);
		}
		return ResponseEntity.accepted().body(response);
	}
}
