package com.yuzee.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.endpoint.ScholarshipInterface;
import com.yuzee.app.processor.ScholarshipProcessor;
import com.yuzee.common.lib.dto.PaginationResponseDto;
import com.yuzee.common.lib.dto.institute.ScholarshipRequestDto;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.handler.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController("scholarshipControllerV1")
@Slf4j
public class ScholarshipController implements ScholarshipInterface {

	@Autowired
	private ScholarshipProcessor scholarshipProcessor;

	@Override
	public ResponseEntity<?> saveScholarship(final String userId, final ScholarshipRequestDto scholarshipDto)
			throws Exception {
		return new GenericResponseHandlers.Builder()
				.setData(scholarshipProcessor.saveScholarship(userId, scholarshipDto))
				.setMessage("Scholarship save successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> saveBasicScholarship(final String userId, final ScholarshipRequestDto scholarshipDto)
			throws Exception {
		return new GenericResponseHandlers.Builder()
				.setData(scholarshipProcessor.saveOrUpdateBasicScholarship(userId, scholarshipDto, null))
				.setMessage("Scholarship save successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> updateScholarship(final String userId, final ScholarshipRequestDto scholarshipDto,
			final String id) throws Exception {

		scholarshipProcessor.updateScholarship(userId, scholarshipDto, id);
		return new GenericResponseHandlers.Builder().setData(id).setMessage("Update Scholarship Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> updateBasicScholarship(final String userId, final ScholarshipRequestDto scholarshipDto,
			final String id) throws Exception {

		scholarshipProcessor.saveOrUpdateBasicScholarship(userId, scholarshipDto, id);
		return new GenericResponseHandlers.Builder().setData(id).setMessage("Update Scholarship Successful")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> get(final String userId, final String id, final boolean isReadableId)
			throws ValidationException, NotFoundException, InvokeException {
		return new GenericResponseHandlers.Builder().setMessage("Get Scholarship Successfully")
				.setData(scholarshipProcessor.getScholarshipById(userId, id, isReadableId)).setStatus(HttpStatus.OK).create();
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

	@Override
	public ResponseEntity<Object> changeStatus(String userId, String instituteId, boolean status) {
		log.info("Inside ScholarshipController.changeStatus method");
		scholarshipProcessor.changeScholarshipStatus(userId, instituteId, status);
		return new GenericResponseHandlers.Builder().setMessage("Scholarship Status changed successfully")
				.setStatus(HttpStatus.OK).create();
	}
}
