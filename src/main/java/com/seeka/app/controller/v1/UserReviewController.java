package com.seeka.app.controller.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.UserReview;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.UserReviewDto;
import com.seeka.app.dto.UserReviewResultDto;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IUserReviewService;
import com.seeka.app.util.PaginationUtil;

/**
 *
 * @author SeekADegree
 *
 */
@RestController("userReviewControllerV1")
@RequestMapping("/v1/review")
public class UserReviewController {

	@Autowired
	private IUserReviewService iUserReview;

	@PostMapping("/user")
	public ResponseEntity<?> addUserReview(@Valid @RequestBody final UserReviewDto userReviewDto) throws ValidationException {
		UserReview userReview = iUserReview.addUserReview(userReviewDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(userReview).setMessage("Created user review successfully").create();
	}

	@GetMapping("/{userId}/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getUserReview(@PathVariable final String userId, @PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize) throws ValidationException {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<UserReviewResultDto> userReviewList = iUserReview.getUserReviewList(userId, startIndex, pageSize);
		int totalCount = iUserReview.getUserReviewCount(userId, null, null, null);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get User Review List successfully");
		responseMap.put("data", userReviewList);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getUserReviewBasedOnData(@RequestParam(name = "entityId", required = false) final String entityId,
			@RequestParam(name = "entityType", required = false) final String entityType, @PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(required = false) final String sortByType,
			@RequestParam(required = false) final String searchKeyword) throws ValidationException {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<UserReviewResultDto> userReviewList = iUserReview.getUserReviewBasedOnData(entityId, entityType, startIndex, pageSize, sortByType, searchKeyword);
		int totalCount = iUserReview.getUserReviewCount(null, entityId, entityType, searchKeyword);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get User Review List successfully");
		responseMap.put("data", userReviewList);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<?> getUserReview() throws ValidationException {
		List<UserReviewResultDto> userReviewList = iUserReview.getUserReviewList();
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(userReviewList).setMessage("Get user review successfully").create();
	}

	@GetMapping("/average")
	public ResponseEntity<?> getUserAverageReviewBasedOnData(@RequestParam(name = "entityId") final String entityId,
			@RequestParam(name = "entityType") final String entityType) throws ValidationException {
		UserReviewResultDto userReviewResultDto = iUserReview.getUserAverageReviewBasedOnData(entityId, entityType);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(userReviewResultDto).setMessage("Get user average review successfully")
				.create();
	}

	@DeleteMapping("/{userReviewId}")
	public ResponseEntity<?> deleteUserReview(@PathVariable final String userReviewId) throws ValidationException {
		iUserReview.deleteUserReview(userReviewId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Deleted user review successfully").create();
	}

	@GetMapping("/user/{userReviewId}")
	public ResponseEntity<?> getUserReviewDetails(@PathVariable final String userReviewId) throws ValidationException {
		UserReviewResultDto userReview = iUserReview.getUserReviewDetails(userReviewId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(userReview).setMessage("Get user review details successfully").create();
	}
}
