package com.seeka.app.controller.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.Help;
import com.seeka.app.dto.HelpAnswerDto;
import com.seeka.app.dto.HelpCategoryDto;
import com.seeka.app.dto.HelpDto;
import com.seeka.app.dto.HelpSubCategoryDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.handler.GenericResponseHandlers;
import com.seeka.app.service.IHelpService;
import com.seeka.app.util.PaginationUtil;

@RestController("helpControllerV1")
@RequestMapping("/api/v1/help")
public class HelpController {

	@Autowired
	private IHelpService helpService;

	@RequestMapping(value = "/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAll(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize) throws Exception {
		return ResponseEntity.accepted().body(helpService.getAll(pageNumber, pageSize));
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> save(@Valid @RequestBody final HelpDto helpDto, @RequestHeader final String userId) throws Exception {
		return ResponseEntity.accepted().body(helpService.save(helpDto, userId));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> get(@PathVariable final String id) throws Exception {
		return ResponseEntity.accepted().body(helpService.get(id));
	}

	@RequestMapping(value = "/by/category/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getHelpByCategoryId(@PathVariable final String id) throws Exception {
		return ResponseEntity.accepted().body(helpService.getHelpByCategory(id));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable final String id, @RequestBody final HelpDto helpDto, @RequestHeader final String userId) {
		return ResponseEntity.accepted().body(helpService.update(helpDto, id, userId));
	}

	@RequestMapping(value = "/category", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> saveCategory(@Valid @RequestBody final HelpCategoryDto categoryDto) throws Exception {
		return ResponseEntity.accepted().body(helpService.save(categoryDto));
	}

	@RequestMapping(value = "/category/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getCatgeory(@PathVariable final String id) throws Exception {
		return ResponseEntity.accepted().body(helpService.getCategory(id));
	}

	@RequestMapping(value = "/subCategory", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> saveSubCategory(@Valid @RequestBody final HelpSubCategoryDto subCategoryDto) throws Exception {
		return ResponseEntity.accepted().body(helpService.save(subCategoryDto));
	}

	@RequestMapping(value = "/subCategory/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getSubCatgeory(@PathVariable final String id) throws Exception {
		return ResponseEntity.accepted().body(helpService.getSubCategory(id));
	}

	@RequestMapping(value = "/category/{categoryId}/subCategory/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getSubCatgeoryByCategory(@PathVariable final String categoryId, @PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize) throws Exception {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<HelpSubCategoryDto> subCategoryDtos = helpService.getSubCategoryByCategory(categoryId, startIndex, pageSize);
		int totalCount = helpService.getSubCategoryCount(categoryId);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get help sub category list successfully");
		responseMap.put("data", subCategoryDtos);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/subCategory/count", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getSubCategoryCount() throws Exception {
		return ResponseEntity.accepted().body(helpService.getSubCategoryCount());
	}

	@RequestMapping(value = "/answer", method = RequestMethod.POST)
	public ResponseEntity<?> saveAnswer(@RequestParam(name = "file", required = false) final MultipartFile file,
			@ModelAttribute final HelpAnswerDto helpAnswerDto) throws Exception {
		return ResponseEntity.accepted().body(helpService.saveAnswer(helpAnswerDto, file));
	}

	@RequestMapping(value = "/answer/{helpId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAnswerByHelpId(@PathVariable final String helpId) throws Exception {
		return ResponseEntity.accepted().body(helpService.getAnswerByHelpId(helpId));
	}

	@RequestMapping(value = "/category/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getCategory(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize) throws Exception {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<HelpCategoryDto> helpCategoryDtos = helpService.getCategory(startIndex, pageSize);
		int totalCount = helpService.getCategoryCount();
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get Help Category list successfully");
		responseMap.put("data", helpCategoryDtos);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> delete(@Valid @PathVariable final String id) throws Exception {
		return ResponseEntity.accepted().body(helpService.delete(id));
	}

	@RequestMapping(value = "/status/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> updateStatus(@PathVariable final String id, @RequestHeader(required = false) final String userId,
			@RequestParam final String status, @RequestParam(required = false) final String assignedUserId) throws Exception {
		return ResponseEntity.accepted().body(helpService.updateStatus(id, assignedUserId, status));
	}

	@RequestMapping(value = "/filter", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> filter(@RequestParam(required = false) final String status, @RequestParam(required = false) final String mostRecent,
			@RequestParam final String categoryId) throws Exception {
		return ResponseEntity.accepted().body(helpService.filter(status, mostRecent, categoryId));
	}

	@RequestMapping(value = "/user/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getUserHelpList(@RequestHeader final String userId, @PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(name = "isArchive", required = false) final boolean isArchive) throws Exception {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<Help> helps = helpService.getUserHelpList(userId, startIndex, pageSize, isArchive);
		int totalCount = helpService.getUserHelpCount(userId, isArchive);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get Help List successfully");
		responseMap.put("data", helps);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@PutMapping(value = "/{id}/isFavourite/{isFavourite}")
	public ResponseEntity<?> setIsFavourite(@RequestHeader(value = "userId") final String userId, @PathVariable(value = "id") final String id,
			@PathVariable(value = "isFavourite") final boolean isFavourite) throws NotFoundException {
		helpService.setIsFavouriteFlag(id, isFavourite);
		return new GenericResponseHandlers.Builder().setMessage("Updated Successfuly").setStatus(HttpStatus.OK).create();
	}

	@PostMapping(value = "/relatedQuestions")
	public ResponseEntity<?> getOptionOnUserSearch(@RequestHeader(value = "userId") final String userId,
			@RequestBody(required = true) final String searchString) throws ValidationException {
		List<String> questionList = helpService.getRelatedSearchQuestions(searchString);
		return new GenericResponseHandlers.Builder().setMessage("Related Questions Displayed Successfully").setStatus(HttpStatus.OK).setData(questionList)
				.create();
	}
}