package com.yuzee.app.controller.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.bean.FaqCategory;
import com.yuzee.app.dto.PaginationUtilDto;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.service.IFaqCategoryService;
import com.yuzee.app.util.PaginationUtil;

@RestController("faqCategroyControllerV1")
@RequestMapping("/api/v1/faq/category")
public class FaqCategroyController {

	@Autowired
	private IFaqCategoryService iFaqCategoryService;

	@PostMapping
	public ResponseEntity<?> addFaqCategory(@RequestBody @Valid final FaqCategory faqCategory, final BindingResult bindingResult) throws ValidationException {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			throw new ValidationException(fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(",")));
		}
		iFaqCategoryService.addFaqCategory(faqCategory);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Created faq category successfully").create();
	}

	@PutMapping("/{faqCategoryId}")
	public ResponseEntity<?> updateFaqCategory(@PathVariable final String faqCategoryId, @RequestBody @Valid final FaqCategory faqCategory,
			final BindingResult bindingResult) throws ValidationException {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			throw new ValidationException(fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(",")));
		}
		iFaqCategoryService.updateFaqCategory(faqCategory, faqCategoryId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Updated faq category successfully").create();
	}

	@DeleteMapping("/{faqCategoryId}")
	public ResponseEntity<?> deleteFaqCategory(@PathVariable final String faqCategoryId) throws ValidationException {
		iFaqCategoryService.deleteFaqCategory(faqCategoryId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Deleted faq category successfully").create();
	}

	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getFaqCategoryList(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize) throws ValidationException {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<FaqCategory> faqCategories = iFaqCategoryService.getFaqCategoryList(startIndex, pageSize);
		int totalCount = iFaqCategoryService.getFaqCategoryCount();
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get faq category list successfully");
		responseMap.put("data", faqCategories);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@GetMapping("/{faqCategoryId}")
	public ResponseEntity<?> getFaqCategoryDetail(@PathVariable final String faqCategoryId) throws ValidationException {
		FaqCategory faqCategory = iFaqCategoryService.getFaqCategoryDetail(faqCategoryId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(faqCategory).setMessage("Get faq category successfully").create();
	}

}
