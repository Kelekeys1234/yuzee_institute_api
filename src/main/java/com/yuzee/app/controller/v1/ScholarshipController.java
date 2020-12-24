package com.yuzee.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.PaginationResponseDto;
import com.yuzee.app.dto.ScholarshipIntakeDto;
import com.yuzee.app.dto.ScholarshipRequestDto;
import com.yuzee.app.endpoint.ScholarshipInterface;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.ScholarshipProcessor;
import com.yuzee.app.util.ValidationUtil;

import lombok.extern.slf4j.Slf4j;

@RestController("scholarshipControllerV1")
@Slf4j
public class ScholarshipController implements ScholarshipInterface {

	@Autowired
	private ScholarshipProcessor scholarshipProcessor;

	@Override
	public ResponseEntity<?> saveScholarship(final String userId, final ScholarshipRequestDto scholarshipDto)
			throws Exception {
		if (!CollectionUtils.isEmpty(scholarshipDto.getIntakes())) {
			for (ScholarshipIntakeDto intakeDto : scholarshipDto.getIntakes()) {
				ValidationUtil.validateStudentCategory(intakeDto.getStudentCategory());
			}
		}

		return new GenericResponseHandlers.Builder()
				.setData(scholarshipProcessor.saveScholarship(userId, scholarshipDto))
				.setMessage("Scholarship save successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> updateScholarship(final String userId, final ScholarshipRequestDto scholarshipDto,
			final String id) throws Exception {

		if (!CollectionUtils.isEmpty(scholarshipDto.getIntakes())) {
			scholarshipDto.getIntakes().stream().forEach(e -> {
				try {
					ValidationUtil.validateStudentCategory(e.getStudentCategory());
				} catch (ValidationException ex) {
					throw new RuntimeException(ex);
				}
			});
		}
		scholarshipProcessor.updateScholarship(userId, scholarshipDto, id);
		return new GenericResponseHandlers.Builder().setData(id).setMessage("Update Scholarship Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> get(final String id) throws ValidationException, NotFoundException, InvokeException {
		return new GenericResponseHandlers.Builder().setMessage("Get Scholarship Successfully")
				.setData(scholarshipProcessor.getScholarshipById(id)).setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getAllScholarship(final Integer pageNumber, final Integer pageSize,
			final String countryName, final String instituteId, final String searchKeyword) throws Exception {
		PaginationResponseDto paginationResponseDto = scholarshipProcessor.getScholarshipList(pageNumber, pageSize,
				countryName, instituteId, searchKeyword);
		return new GenericResponseHandlers.Builder().setMessage("Scholarship fetched Successfully")
				.setData(paginationResponseDto).setStatus(HttpStatus.OK).setData(paginationResponseDto).create();
	}

	@Override
	public ResponseEntity<?> deleteScholarship(final String userId, final String id) throws Exception {
		scholarshipProcessor.deleteScholarship(userId, id);
		return new GenericResponseHandlers.Builder().setMessage("delete Scholarship Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getScholarshipCountByLevel() throws Exception {
		return new GenericResponseHandlers.Builder().setMessage("Scholarship count fetched Successfully")
				.setData(scholarshipProcessor.getScholarshipCountByLevel()).setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getMultipleScholarshipByIds(List<String> scholarshipIds) {
		log.info("Inside ScholarshipController invoking getMultipleScholarshipsById() method ");
		return new GenericResponseHandlers.Builder().setMessage("Scholarships fetched Successfully")
				.setData(scholarshipProcessor.getScholarshipByIds(scholarshipIds)).setStatus(HttpStatus.OK).create();
	}
}
