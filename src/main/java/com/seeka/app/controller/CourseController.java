package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseEnglishEligibility;
import com.seeka.app.bean.CourseGradeEligibility;
import com.seeka.app.bean.CourseKeywords;
import com.seeka.app.bean.CoursePricing;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteLevel;
import com.seeka.app.bean.YoutubeVideo;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.AdvanceSearchDto;
import com.seeka.app.dto.CourseFilterDto;
import com.seeka.app.dto.CourseMinRequirementDto;
import com.seeka.app.dto.CourseRequest;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.ErrorDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.PaginationDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.dto.UserCourse;
import com.seeka.app.enumeration.EnglishType;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.message.MessageByLocaleService;
import com.seeka.app.service.ICourseEnglishEligibilityService;
import com.seeka.app.service.ICourseGradeEligibilityService;
import com.seeka.app.service.ICourseKeywordService;
import com.seeka.app.service.ICoursePricingService;
import com.seeka.app.service.ICourseService;
import com.seeka.app.service.IInstituteService;
import com.seeka.app.service.IStorageService;
import com.seeka.app.service.InstituteLevelService;
import com.seeka.app.service.UserRecommendationService;
import com.seeka.app.util.CommonUtil;
import com.seeka.app.util.IConstant;
import com.seeka.app.util.PaginationUtil;

@RestController
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private IInstituteService instituteService;

	@Autowired
	private ICourseService courseService;

	@Autowired
	private ICoursePricingService coursePricingService;

	@Autowired
	private ICourseKeywordService courseKeywordService;

	@Autowired
	private ICourseEnglishEligibilityService courseEnglishService;

	@Autowired
	private UserRecommendationService userRecommendationService;

	@Autowired
	private ICourseGradeEligibilityService courseGradeService;

	@Autowired
	private InstituteLevelService instituteLevelService;

	@Autowired
	private IStorageService iStorageService;

	@Autowired
	private MessageByLocaleService messageByLocalService;

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> save(@Valid @RequestBody final CourseRequest course) throws ValidationException {
		BigInteger courseId = courseService.save(course);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(courseId).setMessage("Course Created successfully").create();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> update(@RequestBody final CourseRequest course, @PathVariable final BigInteger id) throws ValidationException {
		BigInteger courseId = courseService.update(course, id);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(courseId).setMessage("Course Updated successfully").create();
	}

	@RequestMapping(value = "/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllCourse(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize) throws Exception {
		return ResponseEntity.accepted().body(courseService.getAllCourse(pageNumber, pageSize));
	}

	@RequestMapping(value = "/autoSearch/{searchKey}/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> autoSearch(@PathVariable final String searchKey, @PathVariable final Integer pageNumber, @PathVariable final Integer pageSize)
			throws Exception {
		return ResponseEntity.accepted().body(courseService.autoSearch(pageNumber, pageSize, searchKey));
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllCourse() throws Exception {
		return ResponseEntity.accepted().body(courseService.getAllCourse());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> delete(@Valid @PathVariable final BigInteger id) throws Exception {
		return ResponseEntity.accepted().body(courseService.deleteCourse(id));
	}

	@RequestMapping(value = "/search/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> searchCourse(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,
			@RequestParam(required = false) final List<BigInteger> countryIds, @RequestParam(required = false) final BigInteger instituteId,
			@RequestParam(required = false) final List<BigInteger> facultyIds, @RequestParam(required = false) final List<BigInteger> cityIds,
			@RequestParam(required = false) final List<BigInteger> levelIds, @RequestParam(required = false) final List<BigInteger> serviceIds,
			@RequestParam(required = false) final Double minCost, @RequestParam(required = false) final Double maxCost,
			@RequestParam(required = false) final Integer minDuration, @RequestParam(required = false) final Integer maxDuration,
			@RequestParam(required = false) final String courseName, @RequestParam(required = false) final String currencyCode,
			@RequestParam(required = false) final String searchKeyword, @RequestParam(required = false) final String sortBy,
			@RequestParam(required = false) final boolean sortAsscending, @RequestHeader(required = true) final BigInteger userId,
			@RequestParam(required = false) final String date) throws Exception {
		CourseSearchDto courseSearchDto = new CourseSearchDto();
		courseSearchDto.setCountryIds(countryIds);
		courseSearchDto.setInstituteId(instituteId);
		courseSearchDto.setFacultyIds(facultyIds);
		courseSearchDto.setCityIds(cityIds);
		courseSearchDto.setLevelIds(levelIds);
		courseSearchDto.setServiceIds(serviceIds);
		courseSearchDto.setMinCost(minCost);
		courseSearchDto.setMaxCost(maxCost);
		courseSearchDto.setMinDuration(minDuration);
		courseSearchDto.setMaxDuration(maxDuration);
		courseSearchDto.setCourseName(courseName);
		courseSearchDto.setPageNumber(pageNumber);
		courseSearchDto.setMaxSizePerPage(pageSize);
		courseSearchDto.setSortBy(sortBy);
		courseSearchDto.setCurrencyCode(currencyCode);
		courseSearchDto.setSortAsscending(sortAsscending);
		courseSearchDto.setUserId(userId);
		courseSearchDto.setDate(date);
		return courseSearch(courseSearchDto, searchKeyword);
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> searchCourse(@RequestHeader(required = true) final BigInteger userId, @RequestBody final CourseSearchDto courseSearchDto)
			throws Exception {
		courseSearchDto.setUserId(userId);
		return courseSearch(courseSearchDto, null);
	}

	private ResponseEntity<?> courseSearch(final CourseSearchDto courseSearchDto, final String searchKeyword) throws ValidationException {
		int startIndex = PaginationUtil.getStartIndex(courseSearchDto.getPageNumber(), courseSearchDto.getMaxSizePerPage());
		List<CourseResponseDto> courseList = courseService.getAllCoursesByFilter(courseSearchDto, startIndex, courseSearchDto.getMaxSizePerPage(),
				searchKeyword);
		int totalCount = courseService.getCountforNormalCourse(courseSearchDto, searchKeyword);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, courseSearchDto.getMaxSizePerPage(), totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get course List successfully");
		responseMap.put("data", courseList);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/advanceSearch", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> advanceSearch(@RequestHeader(required = true) final BigInteger userId, @RequestBody final AdvanceSearchDto courseSearchDto)
			throws Exception {
		int startIndex = PaginationUtil.getStartIndex(courseSearchDto.getPageNumber(), courseSearchDto.getMaxSizePerPage());
		courseSearchDto.setUserId(userId);
		List<CourseResponseDto> courseList = courseService.advanceSearch(courseSearchDto);
		int totalCount = courseService.getCountOfAdvanceSearch(courseSearchDto);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, courseSearchDto.getMaxSizePerPage(), totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get course List successfully");
		responseMap.put("data", courseList);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> get(@Valid @PathVariable final BigInteger id) throws Exception {
		ErrorDto errorDto = null;
		CourseRequest courseRequest = null;
		Map<String, Object> response = new HashMap<>();
		Course course = courseService.get(id);
		if (course == null) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Invalid course.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		courseRequest = CommonUtil.convertCourseDtoToCourseRequest(course);
		courseRequest.setIntake(courseService.getCourseIntakeBasedOnCourseId(id).stream().map(x -> x.getIntakeDates()).collect(Collectors.toList()));
		courseRequest.setDeliveryMethod(courseService.getCourseDeliveryMethodBasedOnCourseId(id).stream().map(x -> x.getName()).collect(Collectors.toList()));
		courseRequest.setLanguage(courseService.getCourseLanguageBasedOnCourseId(id).stream().map(x -> x.getName()).collect(Collectors.toList()));
		Institute instituteObj = course.getInstitute();
		if (instituteObj != null) {
			List<StorageDto> storageDTOList = iStorageService.getStorageInformation(instituteObj.getId(), ImageCategory.INSTITUTE.toString(), null, "en");
			courseRequest.setStorageList(storageDTOList);
		}
		List<CourseEnglishEligibility> englishCriteriaList = courseEnglishService.getAllEnglishEligibilityByCourse(id);
		if (!englishCriteriaList.isEmpty()) {
			courseRequest.setEnglishEligibility(englishCriteriaList);
		}
		List<YoutubeVideo> youtubeData = courseService.getYoutubeDataforCourse(instituteObj.getId(), course.getName(), 1, 10);
		List<CourseResponseDto> recommendCourse = userRecommendationService.getCourseRecommended(id);
		List<CourseResponseDto> relatedCourse = userRecommendationService.getCourseRelated(id);
		if (course.getInstitute() != null) {
			List<InstituteLevel> instituteLevels = instituteLevelService.getAllLevelByInstituteId(course.getInstitute().getId());
			if (instituteLevels != null && !instituteLevels.isEmpty()) {
				if (instituteLevels.get(0).getLevel() != null) {
					courseRequest.setLevelId(instituteLevels.get(0).getLevel().getId());
					courseRequest.setLevelName(instituteLevels.get(0).getLevel().getName());
				}
			}
		}
		response.put("status", HttpStatus.OK.value());
		response.put("message", "Success.!");
		response.put("courseObj", courseRequest);
		response.put("recommendCourse", recommendCourse);
		response.put("relatedCourse", relatedCourse);
		response.put("instituteObj", instituteObj);
		response.put("youtubeData", youtubeData);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(value = "/institute/{instituteId}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> getAllCourseByInstituteID(@Valid @PathVariable final BigInteger instituteId, @Valid @RequestBody final CourseSearchDto request)
			throws Exception {
		ErrorDto errorDto = null;
		Map<String, Object> response = new HashMap<>();

		InstituteResponseDto instituteResponseDto = instituteService.getInstituteByID(instituteId);
		if (null == instituteResponseDto) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Invalid institute selection.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		List<StorageDto> storageDTOList = iStorageService.getStorageInformation(instituteResponseDto.getId(), ImageCategory.INSTITUTE.toString(), null, "en");
		instituteResponseDto.setStorageList(storageDTOList);

		List<CourseResponseDto> courseList = courseService.getAllCoursesByInstitute(instituteId, request);

		Integer maxCount = 0, totalCount = 0;
		if (null != courseList && !courseList.isEmpty()) {
			totalCount = courseList.get(0).getTotalCount();
			maxCount = courseList.size();
		}
		boolean showMore;
		if (request.getMaxSizePerPage() == maxCount) {
			showMore = true;
		} else {
			showMore = false;
		}
		response.put("status", 1);
		response.put("message", "Success.!");
		response.put("paginationObj", new PaginationDto(totalCount, showMore));
		response.put("instituteObj", instituteResponseDto);
		response.put("courseList", courseList);
		return ResponseEntity.accepted().body(response);
	}

	@RequestMapping(value = "/pricing", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> saveService(@RequestBody final CoursePricing obj) throws Exception {
		Map<String, Object> response = new HashMap<>();
		coursePricingService.save(obj);
		response.put("status", 1);
		response.put("message", "Success");
		return ResponseEntity.accepted().body(response);
	}

	@RequestMapping(value = "/keyword", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> searchCourseKeyword(@RequestParam(value = "keyword") final String keyword) throws Exception {
		Map<String, Object> response = new HashMap<>();
		List<CourseKeywords> searchkeywordList = courseKeywordService.searchCourseKeyword(keyword);
		response.put("status", 1);
		response.put("searchkeywordList", searchkeywordList);
		response.put("message", "Success");
		return ResponseEntity.accepted().body(response);
	}

	@RequestMapping(value = "/faculty/{facultyId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getCouresesByFacultyId(@Valid @PathVariable final BigInteger facultyId) throws Exception {
		Map<String, Object> response = new HashMap<>();
		List<CourseResponseDto> courseDtos = courseService.getCouresesByFacultyId(facultyId);
		if (courseDtos != null && !courseDtos.isEmpty()) {
			response.put("status", IConstant.SUCCESS_CODE);
			response.put("message", IConstant.SUCCESS_MESSAGE);
		} else {
			response.put("status", HttpStatus.NOT_FOUND.value());
			response.put("message", IConstant.COURSES_NOT_FOUND);
		}
		response.put("courses", courseDtos);
		return ResponseEntity.accepted().body(response);
	}

	@RequestMapping(value = "/multiple/faculty/{facultyId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getCouresesByListOfFacultyId(@Valid @PathVariable final String facultyId) throws Exception {
		Map<String, Object> response = new HashMap<>();
		List<CourseResponseDto> courseDtos = courseService.getCouresesByListOfFacultyId(facultyId);
		if (courseDtos != null && !courseDtos.isEmpty()) {
			response.put("status", IConstant.SUCCESS_CODE);
			response.put("message", IConstant.SUCCESS_MESSAGE);
		} else {
			response.put("status", HttpStatus.NOT_FOUND.value());
			response.put("message", IConstant.COURSES_NOT_FOUND);
		}
		response.put("courses", courseDtos);
		return ResponseEntity.accepted().body(response);
	}

	@RequestMapping(value = "/eligibility/update", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> updateGradeAndEnglishEligibility() throws Exception {
		Map<String, Object> response = new HashMap<>();
		List<Course> courseList = courseService.getAll();
		Date now = new Date();

		CourseEnglishEligibility englishEligibility = null;
		CourseGradeEligibility courseGradeEligibility = null;

		int size = courseList.size(), i = 1;

		for (Course course : courseList) {
			System.out.println("Total:  " + size + ",  Completed:  " + i + ",  CourseID:  " + course.getId());
			i++;
			try {
				courseGradeEligibility = new CourseGradeEligibility();
				// courseGradeEligibility.setCourseId(course.getId());
				// courseGradeEligibility.setGlobalALevel1("A");
				// courseGradeEligibility.setGlobalALevel2("A");
				// courseGradeEligibility.setGlobalALevel3("A");
				// courseGradeEligibility.setGlobalALevel4("A");
				courseGradeEligibility.setGlobalGpa(3.5);
				courseGradeEligibility.setIsActive(true);
				courseGradeEligibility.setIsDeleted(false);
				courseGradeEligibility.setCreatedBy("AUTO");
				courseGradeEligibility.setCreatedOn(now);
				courseGradeService.save(courseGradeEligibility);

				englishEligibility = new CourseEnglishEligibility();
				englishEligibility.setCourse(course);
				englishEligibility.setEnglishType(EnglishType.IELTS.toString());
				englishEligibility.setIsActive(true);
				englishEligibility.setListening(4.0);
				englishEligibility.setOverall(4.5);
				englishEligibility.setReading(4.0);
				englishEligibility.setSpeaking(5.0);
				englishEligibility.setWriting(5.0);
				englishEligibility.setIsDeleted(false);
				englishEligibility.setCreatedBy("AUTO");
				englishEligibility.setCreatedOn(now);
				courseEnglishService.save(englishEligibility);
				englishEligibility = new CourseEnglishEligibility();
				englishEligibility.setCourse(course);
				englishEligibility.setEnglishType(EnglishType.TOEFL.toString());
				englishEligibility.setIsActive(true);
				englishEligibility.setListening(4.0);
				englishEligibility.setOverall(4.5);
				englishEligibility.setReading(4.0);
				englishEligibility.setSpeaking(5.0);
				englishEligibility.setWriting(5.0);
				englishEligibility.setIsDeleted(false);
				englishEligibility.setCreatedBy("AUTO");
				englishEligibility.setCreatedOn(now);
				courseEnglishService.save(englishEligibility);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		response.put("status", 1);
		response.put("message", "Success.!");
		response.put("courseList", courseList);
		return ResponseEntity.accepted().body(response);
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> userCourses(@Valid @RequestBody final UserCourse userCourse) throws Exception {
		return ResponseEntity.accepted().body(courseService.addUserCourses(userCourse));
	}

	/**
	 * Get My course List
	 *
	 * @param userId
	 * @param pageNumber
	 * @param pageSize
	 * @param currencyCode
	 * @param sortBy
	 * @param sortAsscending
	 * @return
	 * @throws ValidationException
	 * @throws Exception
	 */
	@RequestMapping(value = "user/{userId}/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getUserCourses(@PathVariable final BigInteger userId, @PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,
			@RequestParam(required = false) final String currencyCode, @RequestParam(required = false) final String sortBy,
			@RequestParam(required = false) final boolean sortAsscending) throws ValidationException {
		return ResponseEntity.accepted().body(courseService.getUserCourse(userId, pageNumber, pageSize, currencyCode, sortBy, sortAsscending));
	}

	@RequestMapping(value = "/compare", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> userCompareCourse(@Valid @RequestBody final UserCourse userCourse) throws Exception {
		return ResponseEntity.accepted().body(courseService.addUserCompareCourse(userCourse));
	}

	@RequestMapping(value = "/compare/user/{userId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getUserCompareCourse(@PathVariable final BigInteger userId) throws Exception {
		return ResponseEntity.accepted().body(courseService.getUserCompareCourse(userId));
	}

	@RequestMapping(value = "/youtube/{courseId}/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getYoutubeDataforCourse(@PathVariable final BigInteger courseId, @PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize) throws Exception {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<YoutubeVideo> youtubeData = courseService.getYoutubeDataforCourse(courseId, startIndex, pageSize);
		int totalCount = courseService.getYoutubeDataforCourse(courseId, null, null).size();
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get Youtube  List successfully");
		responseMap.put("data", youtubeData);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/filter", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> courseFilter(@RequestBody final CourseFilterDto courseFilter) throws Exception {
		return ResponseEntity.ok().body(courseService.courseFilter(courseFilter));
	}

	@RequestMapping(value = "/minimumRequirement", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> saveCourseMinRequirement(@Valid @RequestBody final List<CourseMinRequirementDto> courseMinRequirementDtoList) throws Exception {
		for (CourseMinRequirementDto courseMinRequirementDto2 : courseMinRequirementDtoList) {
			courseService.saveCourseMinrequirement(courseMinRequirementDto2);
		}
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Created Course Minimum Requirement").create();
	}

	@RequestMapping(value = "/minimumRequirement/{courseId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getCourseMinRequirement(@PathVariable final BigInteger courseId) throws Exception {
		List<CourseMinRequirementDto> courseMinRequirementDto = courseService.getCourseMinRequirement(courseId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Get Course Minimum Requirement").setData(courseMinRequirementDto)
				.create();
	}

	@RequestMapping(value = "/autoSearch/{searchKey}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> autoSearchByCharacter(@PathVariable final String searchKey) throws Exception {
		return ResponseEntity.accepted().body(courseService.autoSearchByCharacter(searchKey));
	}

	@GetMapping(value = "/myCourse/filter")
	public ResponseEntity<?> getUserListForMyCourseFilter(@RequestHeader(required = false) final String language,
			@RequestParam(name = "courseId", required = false) final BigInteger courseId,
			@RequestParam(name = "facultyId", required = false) final BigInteger facultyId,
			@RequestParam(name = "instituteId", required = false) final BigInteger instituteId,
			@RequestParam(name = "countryId", required = false) final BigInteger countryId,
			@RequestParam(name = "cityId", required = false) final BigInteger cityId) throws ValidationException {
		if (courseId == null && facultyId == null && instituteId == null && countryId == null && cityId == null) {
			throw new ValidationException(messageByLocalService.getMessage("specify.filter.parameters", new Object[] {}));
		}
		List<Long> userList = courseService.getUserListBasedOnLikedCourseOnParameters(courseId, instituteId, facultyId, countryId, cityId);
		return new GenericResponseHandlers.Builder().setData(userList).setMessage("User List Displayed Successfully").setStatus(HttpStatus.OK).create();
	}

	@GetMapping(value = "/viewCourse/filter")
	public ResponseEntity<?> getUserListForUserWatchCourseFilter(@RequestHeader(required = false) final String language,
			@RequestParam(name = "courseId", required = false) final BigInteger courseId,
			@RequestParam(name = "facultyId", required = false) final BigInteger facultyId,
			@RequestParam(name = "instituteId", required = false) final BigInteger instituteId,
			@RequestParam(name = "countryId", required = false) final BigInteger countryId,
			@RequestParam(name = "cityId", required = false) final BigInteger cityId) throws ValidationException {
		if (courseId == null && facultyId == null && instituteId == null && countryId == null && cityId == null) {
			throw new ValidationException(messageByLocalService.getMessage("specify.filter.parameters", new Object[] {}));
		}
		List<Long> userList = courseService.getUserListForUserWatchCourseFilter(courseId, instituteId, facultyId, countryId, cityId);
		return new GenericResponseHandlers.Builder().setData(userList).setMessage("User List Displayed Successfully").setStatus(HttpStatus.OK).create();
	}
}