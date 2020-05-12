package com.seeka.app.controller.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.constant.FaqEntityType;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.FaqRequestDto;
import com.seeka.app.dto.FaqResponseDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IFaqService;
import com.seeka.app.util.PaginationUtil;

import lombok.extern.apachecommons.CommonsLog;

@RestController("faqControllerV1")
@RequestMapping("/api/v1/faq")
@CommonsLog
public class FaqController {

	@Autowired
	private IFaqService iFaqService; 
	
	@PostMapping
	public ResponseEntity<?> addFaq(@RequestHeader("userId") final String userId,@RequestBody @Valid final FaqRequestDto faqRequestDto, final BindingResult bindingResult) throws ValidationException {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			throw new ValidationException(fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(",")));
		}
		boolean isEntityTypeValid = EnumUtils.isValidEnum(FaqEntityType.class, faqRequestDto.getEntityType());
		if (!isEntityTypeValid) {
			log.error("entity type value is invalid in request passed " + faqRequestDto.getEntityType()
					+ " expected INSTITUTE,COURSE,PLATFORM");
			throw new ValidationException("entity type value is invalid in request passed " + faqRequestDto.getEntityType()
					+ " expected INSTITUTE,COURSE,PLATFORM");
		}
		iFaqService.addFaq(userId,faqRequestDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Created faq successfully").create();
	}

	@PutMapping("/{faqId}")
	public ResponseEntity<?> updateFaq(@RequestHeader("userId") final String userId,@PathVariable final String faqId, @RequestBody @Valid final FaqRequestDto faqRequestDto,
			final BindingResult bindingResult) throws ValidationException {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			throw new ValidationException(fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(",")));
		}
		boolean isEntityTypeValid = EnumUtils.isValidEnum(FaqEntityType.class, faqRequestDto.getEntityType());
		if (!isEntityTypeValid) {
			log.error("entity type value is invalid in request passed " + faqRequestDto.getEntityType()
					+ " expected INSTITUTE,COURSE");
			throw new ValidationException("entity type value is invalid in request passed " + faqRequestDto.getEntityType()
					+ " expected INSTITUTE,COURSE");
		} 
		iFaqService.updateFaq(userId,faqRequestDto, faqId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Updated faq successfully").create();
	}

	@DeleteMapping("/{faqId}")
	public ResponseEntity<?> deleteFaq(@RequestHeader("userId") final String userId,@PathVariable final String faqId) throws ValidationException {
		iFaqService.deleteFaq(userId,faqId);
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
	public ResponseEntity<?> getFaqDetail(@RequestHeader("userId") final String userId,@PathVariable final String faqId) throws ValidationException {
		FaqResponseDto faqResponseDto = iFaqService.getFaqDetail(userId,faqId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(faqResponseDto).setMessage("Get faq successfully").create();
	}
	
	@GetMapping("/entity/{entityId}")
	public ResponseEntity<?> getFaqListBasedOnEntityId(@RequestHeader("userId") final String userId,@PathVariable final String entityId) throws ValidationException {
		List<FaqResponseDto> listFaqResponseDto = iFaqService.getFaqListBasedOnEntityId(userId, entityId,"PRIVATE");
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(listFaqResponseDto).setMessage("Get faq by entity successfully").create();
	}

	@GetMapping("/public/entity/{entityId}")
	public ResponseEntity<?> getPublicFaqListBasedOnEntityId(@PathVariable final String entityId) throws ValidationException {
		// passing null as user Id as dont want to repeat code, caller is added to identify in service method if its call from public or private
		List<FaqResponseDto> listFaqResponseDto = iFaqService.getFaqListBasedOnEntityId(null, entityId,"PUBLIC");
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(listFaqResponseDto).setMessage("Get faq by entity successfully").create();
	}

}
