package com.yuzee.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.AccrediatedDetailDto;
import com.yuzee.app.endpoint.AccrediatedDetailInterface;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.AccrediatedDetailProcessor;

import lombok.extern.apachecommons.CommonsLog;

@RestController("accrediationDetailV1")
@CommonsLog
public class AccrediationDetailController implements AccrediatedDetailInterface {

	@Autowired
	private AccrediatedDetailProcessor accrediatedDetailProcessor;
	
	@Override
	public ResponseEntity<?> addAccrediationDetail(AccrediatedDetailDto accrediatedDetailDto) {
		log.info("start adding accrediation for entityId "+accrediatedDetailDto.getEntityId());
		AccrediatedDetailDto detailDto = accrediatedDetailProcessor.addAccrediatedDetail(accrediatedDetailDto);
		return new GenericResponseHandlers.Builder().setData(detailDto).setStatus(HttpStatus.OK).setMessage("Accrediation added successfully").create();
	}

	@Override
	public ResponseEntity<?> getAccrediationDetailByEntityId(String entityId) throws NotFoundException {
		log.info("start getting accrediation for entityId "+entityId);
		List<AccrediatedDetailDto> accrediatedDetailDtos = accrediatedDetailProcessor.getAccrediationDetailByEntityId(entityId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(accrediatedDetailDtos)
				.setMessage("Accrediation detail fetched successfully").create();
	}

	@Override
	public ResponseEntity<?> deleteAccrediationDetailByEntityId(String entityId) throws NotFoundException, InvokeException {
		log.info("start deleting accrediation for entityId "+entityId);
		accrediatedDetailProcessor.deleteAccrediationDetailByEntityId(entityId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Accrediation deleted successfully").create();
	}

	@Override
	public ResponseEntity<?> updateAccrediationDetail(String id, AccrediatedDetailDto accrediatedDetailDto) throws NotFoundException {
		log.info("start updating accrediation for entityId "+id);
		AccrediatedDetailDto detailDto = accrediatedDetailProcessor.updateAccrediatedDetails(id, accrediatedDetailDto);
		return new GenericResponseHandlers.Builder().setData(detailDto).setStatus(HttpStatus.OK).setMessage("Accrediation updated successfully").create();
	}

	@Override
	public ResponseEntity<?> getAccrediationDetailById(String id) throws NotFoundException, InvokeException {
		log.info("start getting accrediation details for id "+id);
		AccrediatedDetailDto detailDto = accrediatedDetailProcessor.getAccrediatedById(id);
		return new GenericResponseHandlers.Builder().setData(detailDto).setStatus(HttpStatus.OK).setMessage("Accrediation Details fetched successfully").create();
	}

	@Override
	public ResponseEntity<?> deleteAccrediationDetailById(String id) throws NotFoundException, InvokeException {
		log.info("start deleting accrediation details for id "+id);
		accrediatedDetailProcessor.deleteAccrediationDetailById(id);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Accrediation deleted successfully").create();
	}

	@Override
	public ResponseEntity<?> getAllAccrediationDetails() {
		log.info("start getting all accrediation details from DB");
		List<AccrediatedDetailDto> accrediatedDetailDtos = accrediatedDetailProcessor.getAllAccrediationDetails();
		return new GenericResponseHandlers.Builder().setData(accrediatedDetailDtos).setStatus(HttpStatus.OK).setMessage("Accrediation fetched successfully").create();
	}
}
