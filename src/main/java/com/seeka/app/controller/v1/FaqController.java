package com.seeka.app.controller.v1;

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

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.FaqRequestDto;
import com.seeka.app.dto.FaqResponseDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IFaqService;
import com.seeka.app.util.PaginationUtil;

@RestController("faqControllerV1")
@RequestMapping("/v1/faq")
public class FaqController {

	@Autowired
	private IFaqService iFaqService;

	@PostMapping
	public ResponseEntity<?> addFaq(@RequestBody @Valid final FaqRequestDto faqRequestDto, final BindingResult bindingResult) throws ValidationException {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			throw new ValidationException(fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(",")));
		}
		iFaqService.addFaq(faqRequestDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Created faq successfully").create();
	}

	@PutMapping("/{faqId}")
	public ResponseEntity<?> updateFaq(@PathVariable final String faqId, @RequestBody @Valid final FaqRequestDto faqRequestDto,
			final BindingResult bindingResult) throws ValidationException {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			throw new ValidationException(fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(",")));
		}
		iFaqService.updateFaq(faqRequestDto, faqId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Updated faq successfully").create();
	}

	@DeleteMapping("/{faqId}")
	public ResponseEntity<?> deleteFaq(@PathVariable final String faqId) throws ValidationException {
		iFaqService.deleteFaq(faqId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Deleted faq successfully").create();
	}

	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getFaqList(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,
			@RequestParam(required = false) final String faqCategoryId, @RequestParam(required = false) final String faqSubCategoryId,
			@RequestParam(required = false) final String sortByField, @RequestParam(required = false) final String sortByType,
			@RequestParam(required = false) final String searchKeyword) throws ValidationException {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<FaqResponseDto> faqRequestDtos = iFaqService.getFaqList(startIndex, pageSize, faqCategoryId, faqSubCategoryId, sortByField, sortByType,
				searchKeyword);
		int totalCount = iFaqService.getFaqCount(faqCategoryId, faqSubCategoryId, searchKeyword);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get faq list successfully");
		responseMap.put("data", faqRequestDtos);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@GetMapping("/{faqId}")
	public ResponseEntity<?> getFaqDetail(@PathVariable final String faqId) throws ValidationException {
		FaqResponseDto faqResponseDto = iFaqService.getFaqDetail(faqId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(faqResponseDto).setMessage("Get faq successfully").create();
	}

}
