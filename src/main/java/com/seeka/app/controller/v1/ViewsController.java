package com.seeka.app.controller.v1;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.UserViewData;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.UserCourseView;
import com.seeka.app.dto.UserViewDataRequestDto;
import com.seeka.app.dto.ViewEntityDto;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IViewService;
import com.seeka.app.util.PaginationUtil;

@RestController("viewsControllerV1")
@RequestMapping("/v1/view")
public class ViewsController {

	@Autowired
	private IViewService iViewService;

	@PostMapping
	public ResponseEntity<?> createUserViewData(@RequestHeader final BigInteger userId, @RequestBody final UserViewDataRequestDto userViewDataRequestDto)
			throws ValidationException {
		userViewDataRequestDto.setUserId(userId);
		iViewService.createUserViewData(userViewDataRequestDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Added to user view data.").create();
	}

	@GetMapping
	public ResponseEntity<?> getUserViewData(@RequestHeader(name = "userId") final BigInteger userId, @RequestParam final String entityType,
			@RequestParam(name = "isUnique", required = false) final boolean isUnique) throws ValidationException {
		List<UserViewData> userViewDatas = iViewService.getUserViewData(userId, entityType, isUnique, null, null);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Get user view data.").setData(userViewDatas).create();
	}

	@GetMapping("/user/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getUserViewDataCourse(@RequestParam(name = "userId") final BigInteger userId,
			@RequestParam(name = "isUnique", required = false) final boolean isUnique, @PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize) {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<CourseResponseDto> userViewDatas = iViewService.getUserViewDataCourse(userId, isUnique, startIndex, pageSize);
		int totalCount = iViewService.getUserViewData(userId, "COURSE", isUnique, null, null).size();
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get user view Course List successfully");
		responseMap.put("data", userViewDatas);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@GetMapping(value = "/user")
	public ResponseEntity<?> getUserViewDataCountBasedOnUserId(@RequestHeader final String userId, @RequestParam final String entityId,
			@RequestParam final String entityType) throws ValidationException {
		int count = iViewService.getUserViewDataCountBasedOnUserId(userId, entityId, entityType);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Get user view count.").setData(count).create();
	}

	@GetMapping(value = "/entity")
	public ResponseEntity<?> getUserViewDataCountBasedOnEntityId(@RequestParam final BigInteger entityId, @RequestParam final String entityType)
			throws ValidationException {
		int count = iViewService.getUserViewDataCountBasedOnEntityId(entityId, entityType);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Get user view count based on entity.").setData(count).create();
	}

	@GetMapping(value = "/visit")
	public ResponseEntity<?> userVisited(@RequestHeader final String userId, @RequestParam final String entityId, @RequestParam final String entityType)
			throws ValidationException {
		int count = iViewService.getUserViewDataCountBasedOnUserId(userId, entityId, entityType);
		boolean flag = count > 0;
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage(" visited entity.").setData(flag).create();
	}

	@PostMapping(value = "/visit/entity")
	public ResponseEntity<?> userVisitedBasedonEntityId(@RequestHeader final BigInteger userId, @RequestBody final ViewEntityDto viewEntityDto) {
		List<BigInteger> viewedEntityIds = iViewService.getUserViewDataBasedOnEntityIdList(userId, viewEntityDto.getEntityType(), viewEntityDto.getEntityIds());
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage(" visited entity.").setData(viewedEntityIds).create();
	}

	@PostMapping(value = "/city")
	public ResponseEntity<Object> userVisistedCourseBasedOncity(@RequestParam final String cityName,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") final Date fromDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") final Date toDate)
			throws ValidationException {
		List<UserCourseView> count = iViewService.userVisistedCourseBasedOncity(cityName, fromDate, toDate);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage(" visited entity.").setData(count).create();
	}

	@PostMapping(value = "/country")
	public ResponseEntity<Object> userVisistedCourseBasedOnCountry(@RequestParam final String countryName,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") final Date fromDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") final Date toDate)
			throws ValidationException {
		List<UserCourseView> userCourseViews = iViewService.userVisistedCourseBasedOnCountry(countryName, fromDate, toDate);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage(" visited entity.").setData(userCourseViews).create();
	}

}
