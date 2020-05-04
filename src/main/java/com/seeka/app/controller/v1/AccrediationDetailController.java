package com.seeka.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.AccrediatedDetailDto;
import com.seeka.app.endpoint.AccrediatedDetailInterface;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.processor.AccrediatedDetailProcessor;

import lombok.extern.apachecommons.CommonsLog;

@RestController("accrediationDetailV1")
@CommonsLog
public class AccrediationDetailController implements AccrediatedDetailInterface {

	@Autowired
	private AccrediatedDetailProcessor accrediatedDetailProcessor;
	
	@Override
	public ResponseEntity<?> addAccrediationDetail(AccrediatedDetailDto accrediatedDetailDto) {
		log.info("Adding accrediation for entityId "+accrediatedDetailDto.getEntityId());
		AccrediatedDetailDto detailDto = accrediatedDetailProcessor.addAccrediatedDetail(accrediatedDetailDto);
		return new GenericResponseHandlers.Builder().setData(detailDto).setStatus(HttpStatus.OK).setMessage("Accrediation added successfully").create();
	}

	@Override
	public ResponseEntity<?> getAccrediationDetail(String entityId) throws NotFoundException {
		log.info("getting accrediation for entityId "+entityId);
		List<AccrediatedDetailDto> accrediatedDetailDtos = accrediatedDetailProcessor.getAccrediationDetailByEntityId(entityId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(accrediatedDetailDtos)
				.setMessage("Accrediation detail fetched successfully").create();
	}

	@Override
	public ResponseEntity<?> deleteAccrediationDetail(String entityId) {
		log.info("deleting accrediation for entityId "+entityId);
		accrediatedDetailProcessor.deleteAccrediationDetailByEntityId(entityId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Accrediation detail deleted successfully").create();
	}

	@Override
	public ResponseEntity<?> updateAccrediationDetail(String id, AccrediatedDetailDto accrediatedDetailDto) throws NotFoundException {
		AccrediatedDetailDto detailDto = accrediatedDetailProcessor.updateAccrediatedDetails(id, accrediatedDetailDto);
		return new GenericResponseHandlers.Builder().setData(detailDto).setStatus(HttpStatus.OK).setMessage("Accrediation updated successfully").create();
	}
}
