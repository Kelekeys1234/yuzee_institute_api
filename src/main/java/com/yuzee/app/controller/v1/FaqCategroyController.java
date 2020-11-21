package com.yuzee.app.controller.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.FaqCategoryDto;
import com.yuzee.app.endpoint.FaqCategoryEndpoint;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.FaqCategoryProcessor;

import lombok.extern.slf4j.Slf4j;

@RestController("faqCategroyControllerV1")
@Slf4j
public class FaqCategroyController implements FaqCategoryEndpoint {

	@Autowired
	private FaqCategoryProcessor faqCategoryProcessor;

	@Override
	public ResponseEntity<?> addFaqCategory(String userId, FaqCategoryDto faqCategory) throws ValidationException {
		log.info("inside FaqCategroyController.addFaqCategory");
		faqCategoryProcessor.addFaqCategory(userId, faqCategory);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Created faq category successfully").create();
	}

	@Override
	public ResponseEntity<?> updateFaqCategory(String userId, String faqCategoryId, @Valid FaqCategoryDto faqCategory)
			throws ValidationException {
		log.info("inside FaqCategroyController.updateFaqCategory");
		faqCategoryProcessor.updateFaqCategory(userId, faqCategoryId, faqCategory);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Updated faq category successfully").create();
	}

	@Override
	public ResponseEntity<?> deleteFaqCategory(String faqCategoryId) throws ValidationException {
		log.info("inside FaqCategroyController.deleteFaqCategory");
		faqCategoryProcessor.deleteFaqCategory(faqCategoryId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Deleted faq category successfully").create();
	}

	@Override
	public ResponseEntity<?> getFaqCategoryList(Integer pageNumber, Integer pageSize) throws ValidationException {
		log.info("inside FaqCategroyController.getFaqCategoryList");
		return new GenericResponseHandlers.Builder()
				.setData(faqCategoryProcessor.getFaqCategories(pageNumber, pageSize)).setStatus(HttpStatus.OK)
				.setMessage("faq categories fetched successfully").create();
	}

	@Override
	public ResponseEntity<?> getFaqCategoryDetail(String faqCategoryId) throws ValidationException {
		log.info("inside FaqCategroyController.getFaqCategoryDetail");
		return new GenericResponseHandlers.Builder().setData(faqCategoryProcessor.getFaqCategory(faqCategoryId))
				.setStatus(HttpStatus.OK).setMessage("faq category fetched successfully").create();
	}
}
