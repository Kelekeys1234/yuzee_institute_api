package com.seeka.app.controller.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.UserSearchDTO;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.UserSearchService;
import com.seeka.app.util.PaginationUtil;

@RestController("userSearchControllerV1")
@RequestMapping("/api/v1/userSearch")
public class UserSearchController {

	@Autowired
	private UserSearchService iUserSearchService;

	@PostMapping
	public ResponseEntity<?> createUserSearchEntry(@RequestHeader final String userId, @RequestBody UserSearchDTO userSearchDTO)
			throws ValidationException {
		userSearchDTO.setUserId(userId);
		userSearchDTO = iUserSearchService.createUserSearchEntry(userSearchDTO);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Added to user search.").setData(userSearchDTO).create();
	}

	@GetMapping
	public ResponseEntity<?> getUserSearchEntry(@RequestHeader(name = "userId") final String userId, @RequestParam final String entityType,
			@RequestParam(value = "pageNumber") final Integer pageNumber, @RequestParam(value = "pageSize") final Integer pageSize) throws ValidationException {
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		int totalCount = iUserSearchService.getUserSearchEntryCount(userId, entityType);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		List<UserSearchDTO> userSearchList = iUserSearchService.getUserSearchEntry(userId, entityType, startIndex, pageSize);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get user search list.");
		responseMap.put("data", userSearchList);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteUserSearchEntry(@RequestHeader(name = "userId") final String userId, @RequestParam final String entityType)
			throws ValidationException {
		iUserSearchService.deleteUserSearchEntry(userId, entityType);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("User search history deleted successfully.").create();
	}

	@GetMapping(value = "/recommendation/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getUserSearchRecommendation(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,
			@RequestParam(name = "searchKeyword", required = true) final String searchKeyword) {
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("get UserSearch recommendation successfully.")
				.setData(iUserSearchService.getUserSearchKeyword(startIndex, pageSize, searchKeyword)).create();

	}
}
