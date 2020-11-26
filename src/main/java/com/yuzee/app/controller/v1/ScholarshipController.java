package com.yuzee.app.controller.v1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.bean.Scholarship;
import com.yuzee.app.dto.LevelDto;
import com.yuzee.app.dto.PaginationResponseDto;
import com.yuzee.app.dto.PaginationUtilDto;
import com.yuzee.app.dto.ScholarshipCountDto;
import com.yuzee.app.dto.ScholarshipDto;
import com.yuzee.app.dto.ScholarshipResponseDTO;
import com.yuzee.app.endpoint.ScholarshipInterface;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.LevelProcessor;
import com.yuzee.app.processor.ScholarshipProcessor;
import com.yuzee.app.util.PaginationUtil;

import lombok.extern.slf4j.Slf4j;

@RestController("scholarshipControllerV1")
@Slf4j
public class ScholarshipController implements ScholarshipInterface {

	@Autowired
	private ScholarshipProcessor scholarshipProcessor;
	
	@Autowired
    private LevelProcessor levelProcessor;

	@Override
	public ResponseEntity<?> saveScholarship(final ScholarshipDto scholarshipDto, final BindingResult bindingResult) throws Exception {
		final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			throw new ValidationException(fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(",")));
		}
		Scholarship scholarship = scholarshipProcessor.saveScholarship(scholarshipDto);
		return new GenericResponseHandlers.Builder().setData(scholarship.getId()).setMessage("Scholarship save successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> updateScholarship(final ScholarshipDto scholarshipDto, final BindingResult bindingResult,
			final String id) throws Exception {
		final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			throw new ValidationException(fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(",")));
		}
		scholarshipProcessor.updateScholarship(scholarshipDto, id);
		return new GenericResponseHandlers.Builder().setData(id).setMessage("Update Scholarship Successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> get(final String id) throws ValidationException, NotFoundException, InvokeException {
		return new GenericResponseHandlers.Builder().setMessage("Get Scholarship Successfully").setData(scholarshipProcessor.getScholarshipById(id))
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getAllScholarship(final Integer pageNumber, final Integer pageSize,
			final String sortByField, final String sortByType, final String searchKeyword, final String countryName,
			final String instituteId, final String validity, final Boolean isActive, final Date updatedOn) throws Exception {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<ScholarshipResponseDTO> scholarshipResponseDTOs = scholarshipProcessor.getScholarshipList(startIndex, pageSize, countryName, instituteId, 
				validity, isActive, updatedOn, searchKeyword, sortByField, sortByType);
		int totalCount = scholarshipProcessor.countScholarshipList(countryName, instituteId, validity, isActive, updatedOn, searchKeyword);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto();
		paginationResponseDto.setResponse(scholarshipResponseDTOs);
		paginationResponseDto.setTotalCount(totalCount);
		paginationResponseDto.setPageNumber(paginationUtilDto.getPageNumber());
		paginationResponseDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
		paginationResponseDto.setTotalPages(paginationUtilDto.getTotalPages());
		paginationResponseDto.setHasNextPage(paginationUtilDto.isHasNextPage());
		return new GenericResponseHandlers.Builder().setMessage("Scholarship fetched Successfully")
				.setStatus(HttpStatus.OK).setData(paginationResponseDto).create();
	}

	@Override
	public ResponseEntity<?> deleteScholarship(final String id) throws Exception {
		scholarshipProcessor.deleteScholarship(id);
		return new GenericResponseHandlers.Builder().setMessage("delete Scholarship Successfully").setStatus(HttpStatus.OK).create();
	}
	
	@Override
	public ResponseEntity<?> getScholarshipCountByLevel() throws Exception {
		List<ScholarshipCountDto> scholarshipCountDtos = new ArrayList<>();
		List<LevelDto> levels = levelProcessor.getAllLevels();
		if (!CollectionUtils.isEmpty(levels)) {
			scholarshipCountDtos = scholarshipProcessor.getScholarshipCountByLevelId(levels);
		}
		return new GenericResponseHandlers.Builder().setMessage("Scholarship count fetched Successfully").setData(scholarshipCountDtos)
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getMultipleScholarshipByIds(List<String> scholarshipIds) {
		log.info("Inside ScholarshipController invoking getMultipleScholarshipsById() method ");
		return new GenericResponseHandlers.Builder().setMessage("Scholarships fetched Successfully")
				.setData(scholarshipProcessor.getScholarshipByIds(scholarshipIds))
				.setStatus(HttpStatus.OK).create();
	}
}
