package com.yuzee.app.controller.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.FaqSubCategoryRequestDto;
import com.yuzee.app.endpoint.FaqSubCategoryEndpoint;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.app.processor.FaqSubCategoryProcessor;
import com.yuzee.common.lib.handler.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController("faqSubCategroyControllerV1")
@Slf4j
public class FaqSubCategoryController implements FaqSubCategoryEndpoint {

	@Autowired
	private FaqSubCategoryProcessor faqSubCategoryProcessor;

	@Override
	public ResponseEntity<?> addFaqSubCategory(String userId, FaqSubCategoryRequestDto faqSubCategoryRequestDto)
			throws ValidationException {
		log.info("inside FaqSubCategoryController.addFaqSubCategory");
		faqSubCategoryProcessor.addFaqSubCategory(userId, faqSubCategoryRequestDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Created faq sub category successfully").create();
	}

	@Override
	public ResponseEntity<?> updateFaqSubCategory(String userId, String faqSubCategoryId,
			@Valid FaqSubCategoryRequestDto faqSubCategoryRequestDto) throws ValidationException {
		log.info("inside FaqSubCategoryController.updateFaqSubCategory");
		faqSubCategoryProcessor.updateFaqSubCategory(userId, faqSubCategoryId, faqSubCategoryRequestDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Updated faq sub category successfully").create();
	}

	@Override
	public ResponseEntity<?> deleteFaqSubCategory(String faqSubCategoryId) throws ValidationException {
		log.info("inside FaqSubCategoryController.deleteFaqSubCategory");
		faqSubCategoryProcessor.deleteFaqSubCategory(faqSubCategoryId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Deleted faq sub category successfully").create();
	}

	@Override
	public ResponseEntity<?> getFaqSubCategoryList(Integer pageNumber, Integer pageSize, String faqCategoryId)
			throws ValidationException {
		log.info("inside FaqSubCategoryController.getFaqSubCategoryList");
		if (pageNumber < 1) {
			log.error("Page number can not be less than 1");
			throw new ValidationException("Page number can not be less than 1");
		}

		if (pageSize < 1) {
			log.error("Page size can not be less than 1");
			throw new ValidationException("Page size can not be less than 1");
		}
		return new GenericResponseHandlers.Builder()
				.setData(faqSubCategoryProcessor.getFaqSubCategories(faqCategoryId, pageNumber, pageSize))
				.setStatus(HttpStatus.OK).setMessage("faq sub categories fetched successfully").create();
	}

	@Override
	public ResponseEntity<?> getFaqSubCategoryDetail(String faqSubCategoryId) throws ValidationException {
		log.info("inside FaqSubCategoryController.getFaqSubCategoryDetail");
		return new GenericResponseHandlers.Builder()
				.setData(faqSubCategoryProcessor.getFaqSubCategory(faqSubCategoryId)).setStatus(HttpStatus.OK)
				.setMessage("faq sub category fetched successfully").create();
	}
}
