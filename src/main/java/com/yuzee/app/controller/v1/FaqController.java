package com.yuzee.app.controller.v1;

import java.util.Arrays;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.constant.FaqEntityType;
import com.yuzee.app.dto.FaqRequestDto;
import com.yuzee.app.dto.FaqResponseDto;
import com.yuzee.app.dto.PaginationResponseDto;
import com.yuzee.app.endpoint.FaqEndpoint;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.FaqProcessor;
import com.yuzee.app.util.Util;

import lombok.extern.slf4j.Slf4j;

@RestController("faqControllerV1")
@Slf4j
public class FaqController implements FaqEndpoint {

	@Autowired
	private FaqProcessor faqProcessor;

	@Override
	public ResponseEntity<Object> addFaq(String userId, @Valid FaqRequestDto faqRequestDto) throws ValidationException {
		log.debug("inside FaqController.addFaq");
		validateFaqEntityType(faqRequestDto.getEntityType());
		faqProcessor.addFaq(userId, faqRequestDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Created faq successfully")
				.create();
	}

	@Override
	public ResponseEntity<Object> updateFaq(String userId, String faqId, @Valid FaqRequestDto faqRequestDto)
			throws ValidationException {
		log.debug("inside FaqController.updateFaq");
		validateFaqEntityType(faqRequestDto.getEntityType());
		faqProcessor.updateFaq(userId, faqId, faqRequestDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("updated faq successfully")
				.create();
	}

	@DeleteMapping("/{faqId}")
	public ResponseEntity<Object> deleteFaq(@PathVariable String faqId) throws ValidationException {
		log.debug("inside FaqController.deleteFaq");
		faqProcessor.deleteFaq(faqId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Deleted faq successfully")
				.create();
	}

	@Override
	public ResponseEntity<Object> getFaqList(Integer pageNumber, Integer pageSize, String entityId,
			String faqCategoryId, String faqSubCategoryId, String searchKeyword) throws ValidationException {

		log.debug("inside FaqController.getFaqList");
		if (pageNumber < 1) {
			log.error("Page number can not be less than 1");
			throw new ValidationException("Page number can not be less than 1");
		}

		if (pageSize < 1) {
			log.error("Page size can not be less than 1");
			throw new ValidationException("Page size can not be less than 1");
		}
		PaginationResponseDto faqPaginationResponseDto = faqProcessor.getFaqList(entityId, pageNumber, pageSize,
				faqCategoryId, faqSubCategoryId, searchKeyword);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(faqPaginationResponseDto)
				.setMessage("Fetched faq successfully").create();
	}

	@GetMapping("/{faqId}")
	public ResponseEntity<Object> getFaqDetail(@PathVariable String faqId) throws ValidationException {
		log.debug("inside FaqController.getFaqDetail");
		FaqResponseDto faqResponseDto = faqProcessor.getFaqDetail(faqId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(faqResponseDto)
				.setMessage("Fetched faq successfully").create();
	}

	private void validateFaqEntityType(String entityType) throws ValidationException {
		if (!EnumUtils.isValidEnum(FaqEntityType.class, entityType)) {
			log.error("entityType must be in one of the following {}",
					Arrays.toString(Util.getEnumNames(FaqEntityType.class)));
			throw new ValidationException("entityType must be in one of the following: "
					+ Arrays.toString(Util.getEnumNames(FaqEntityType.class)));
		}
	}
}
