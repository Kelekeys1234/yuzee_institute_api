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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.FaqSubCategoryDto;
import com.yuzee.app.dto.PaginationUtilDto;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.service.IFaqSubCategoryService;
import com.yuzee.app.util.PaginationUtil;

@RestController("faqSubCategroyControllerV1")
@RequestMapping("/api/v1/faq/sub/category")
public class FaqSubCategroyController {

	@Autowired
	private IFaqSubCategoryService iFaqSubCategoryService;

	@PostMapping
	public ResponseEntity<?> addFaqSubCategory(@RequestBody @Valid final FaqSubCategoryDto faqSubCategoryDto, final BindingResult bindingResult)
			throws ValidationException {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			throw new ValidationException(fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(",")));
		}
		iFaqSubCategoryService.addFaqSubCategory(faqSubCategoryDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Created faq sub category successfully").create();
	}

	@PutMapping("/{faqSubCategoryId}")
	public ResponseEntity<?> updateFaqSubCategory(@PathVariable final String faqSubCategoryId,
			@RequestBody @Valid final FaqSubCategoryDto faqSubCategoryDto, final BindingResult bindingResult) throws ValidationException {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			throw new ValidationException(fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(",")));
		}
		iFaqSubCategoryService.updateFaqSubCategory(faqSubCategoryDto, faqSubCategoryId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Updated faq sub category successfully").create();
	}

	@DeleteMapping("/{faqSubCategoryId}")
	public ResponseEntity<?> deleteFaqSubCategory(@PathVariable final String faqSubCategoryId) throws ValidationException {
		iFaqSubCategoryService.deleteFaqSubCategory(faqSubCategoryId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Deleted faq sub category successfully").create();
	}

	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getFaqSubCategoryList(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,
			@RequestParam(required = false) final String faqCategoryId) throws ValidationException {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<FaqSubCategoryDto> faqSubCategoryDtos = iFaqSubCategoryService.getFaqSubCategoryList(startIndex, pageSize, faqCategoryId);
		int totalCount = iFaqSubCategoryService.getFaqSubCategoryCount(faqCategoryId);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get faq sub category list successfully");
		responseMap.put("data", faqSubCategoryDtos);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@GetMapping("/{faqSubCategoryId}")
	public ResponseEntity<?> getFaqSubCategoryDetail(@PathVariable final String faqSubCategoryId) throws ValidationException {
		FaqSubCategoryDto faqSubCategoryDto = iFaqSubCategoryService.getFaqSubCategoryDetail(faqSubCategoryId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(faqSubCategoryDto).setMessage("Get faq sub category successfully")
				.create();
	}

}
