package com.seeka.app.controller.v1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseEnglishEligibility;
import com.seeka.app.bean.CourseIntake;
import com.seeka.app.bean.CourseKeywords;
import com.seeka.app.bean.CourseLanguage;
import com.seeka.app.bean.CoursePricing;
import com.seeka.app.bean.Institute;
import com.seeka.app.controller.handler.CommonHandler;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.AccrediatedDetailDto;
import com.seeka.app.dto.AdvanceSearchDto;
import com.seeka.app.dto.CourseEnglishEligibilityDto;
import com.seeka.app.dto.CourseFilterDto;
import com.seeka.app.dto.CourseMinRequirementDto;
import com.seeka.app.dto.CourseMobileDto;
import com.seeka.app.dto.CourseRequest;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.ErrorDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.NearestCoursesDto;
import com.seeka.app.dto.PaginationDto;
import com.seeka.app.dto.PaginationResponseDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.dto.StudentVisaDto;
import com.seeka.app.dto.UserCompareCourseResponse;
import com.seeka.app.dto.UserCourse;
import com.seeka.app.dto.UserDto;
import com.seeka.app.dto.UserReviewResultDto;
import com.seeka.app.dto.YouTubeVideoDto;
import com.seeka.app.enumeration.EnglishType;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.message.MessageByLocaleService;
import com.seeka.app.processor.AccrediatedDetailProcessor;
import com.seeka.app.processor.CourseEnglishEligibilityProcessor;
import com.seeka.app.processor.InstituteGoogleReviewProcessor;
import com.seeka.app.processor.InstituteProcessor;
import com.seeka.app.processor.InstituteServiceProcessor;
import com.seeka.app.service.ICourseKeywordService;
import com.seeka.app.service.ICoursePricingService;
import com.seeka.app.service.ICourseService;
import com.seeka.app.service.IEnrollmentService;
import com.seeka.app.service.IStorageService;
import com.seeka.app.service.IUserReviewService;
import com.seeka.app.service.IUsersService;
import com.seeka.app.service.IViewService;
import com.seeka.app.service.UserRecommendationService;
import com.seeka.app.util.CommonUtil;
import com.seeka.app.util.IConstant;
import com.seeka.app.util.PaginationUtil;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RestController("courseControllerV1")
@RequestMapping("/api/v1/course")
public class CourseController {

	@Autowired
	private InstituteProcessor instituteProcessor;

	@Autowired
	private ICourseService courseService;

	@Autowired
	private ICoursePricingService coursePricingService;

	@Autowired
	private ICourseKeywordService courseKeywordService;

	@Autowired
	private CourseEnglishEligibilityProcessor courseEnglishEligibilityProcessor;

	@Autowired
	private UserRecommendationService userRecommendationService;

	@Autowired
	private IStorageService iStorageService;

	@Autowired
	private MessageByLocaleService messageByLocalService;

	@Autowired
	private IUsersService iUsersService;

	@Autowired
	private IEnrollmentService iEnrolmentService;

	@Autowired
	private IViewService iViewService;

	@Autowired
	private IUserReviewService iUserReviewService;

	@Autowired
	private InstituteGoogleReviewProcessor instituteGoogleReviewProcessor;
	
	@Autowired
	private InstituteServiceProcessor instituteServiceProcessor;
	
	@Autowired
	private CommonHandler commonHandler;
	
	@Autowired
	private AccrediatedDetailProcessor accrediatedDetailProcessor;

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> save(@Valid @RequestBody final CourseRequest course) throws ValidationException {
		String courseId = courseService.saveCourse(course);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(courseId)
				.setMessage("Course Created successfully").create();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> update(@RequestBody final CourseRequest course, @PathVariable final String id)
			throws ValidationException {
		String courseId = courseService.updateCourse(course, id);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(courseId)
				.setMessage("Course Updated successfully").create();
	}

	@RequestMapping(value = "/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllCourse(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize)
			throws Exception {
		PaginationResponseDto paginationResponseDto = courseService.getAllCourse(pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(paginationResponseDto)
				.setMessage("Courses Displayed successfully").create();
	}

	@RequestMapping(value = "/autoSearch/{searchKey}/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> autoSearch(@PathVariable final String searchKey, @PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize) throws Exception {
		PaginationResponseDto paginationResponseDto = courseService.autoSearch(pageNumber, pageSize, searchKey);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(paginationResponseDto)
				.setMessage("Courses Displayed successfully").create();
	}

	@Deprecated
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllCourse() throws Exception {
		return ResponseEntity.accepted().body(courseService.getAllCourse());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> delete(@Valid @PathVariable final String id) throws Exception {
		courseService.deleteCourse(id);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Courses Deleted successfully").create();
	}

	@RequestMapping(value = "/search/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> searchCourse(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,
			@RequestParam(required = false) final List<String> countryIds,
			@RequestParam(required = false) final String instituteId,
			@RequestParam(required = false) final List<String> facultyIds,
			@RequestParam(required = false) final List<String> cityIds,
			@RequestParam(required = false) final List<String> levelIds,
			@RequestParam(required = false) final List<String> serviceIds,
			@RequestParam(required = false) final Double minCost, @RequestParam(required = false) final Double maxCost,
			@RequestParam(required = false) final Integer minDuration,
			@RequestParam(required = false) final Integer maxDuration,
			@RequestParam(required = false) final String courseName,
			@RequestParam(required = false) final String currencyCode,
			@RequestParam(required = false) final String searchKeyword,
			@RequestParam(required = false) final String sortBy,
			@RequestParam(required = false) final boolean sortAsscending,
			@RequestHeader(required = true) final String userId, @RequestParam(required = false) final String date)
			throws ValidationException {
		CourseSearchDto courseSearchDto = new CourseSearchDto();
		courseSearchDto.setCountryNames(countryIds);
		courseSearchDto.setInstituteId(instituteId);
		courseSearchDto.setFacultyIds(facultyIds);
		courseSearchDto.setCityNames(cityIds);
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
	public ResponseEntity<?> searchCourse(@RequestHeader(required = true) final String userId,
			@RequestBody final CourseSearchDto courseSearchDto) throws Exception {
		log.info("Start process to search courses based on passed filters");
		courseSearchDto.setUserId(userId);
		return courseSearch(courseSearchDto, null);
	}

	private ResponseEntity<?> courseSearch(final CourseSearchDto courseSearchDto, final String searchKeyword)
			throws ValidationException {
		int startIndex = PaginationUtil.getStartIndex(courseSearchDto.getPageNumber(), courseSearchDto.getMaxSizePerPage());
		
		List<CourseResponseDto> courseList = courseService.getAllCoursesByFilter(courseSearchDto, startIndex, courseSearchDto.getMaxSizePerPage(), searchKeyword);
		int totalCount = courseService.getCountforNormalCourse(courseSearchDto, searchKeyword);
		
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, courseSearchDto.getMaxSizePerPage(), totalCount);
		
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto();
		paginationResponseDto.setResponse(courseList);
		paginationResponseDto.setTotalCount(totalCount);
		paginationResponseDto.setPageNumber(paginationUtilDto.getPageNumber());
		paginationResponseDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
		paginationResponseDto.setTotalPages(paginationUtilDto.getTotalPages());
		paginationResponseDto.setHasNextPage(paginationUtilDto.isHasNextPage());
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).
				setMessage("Courses Displayed successfully").setData(paginationResponseDto).create();
	}

	@RequestMapping(value = "/advanceSearch", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> advanceSearch(@RequestHeader(required = true) final String userId,
			@RequestHeader(required = false) final String language, @RequestBody final AdvanceSearchDto courseSearchDto)
			throws Exception {
		int startIndex = PaginationUtil.getStartIndex(courseSearchDto.getPageNumber(),courseSearchDto.getMaxSizePerPage());
		courseSearchDto.setUserId(userId);
		
		List<CourseResponseDto> courseList = courseService.advanceSearch(courseSearchDto);
		int totalCount = courseService.getCountOfAdvanceSearch(courseSearchDto);
		
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex,courseSearchDto.getMaxSizePerPage(), totalCount);
		
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto();
		paginationResponseDto.setResponse(courseList);
		paginationResponseDto.setTotalCount(totalCount);
		paginationResponseDto.setPageNumber(paginationUtilDto.getPageNumber());
		paginationResponseDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
		paginationResponseDto.setTotalPages(paginationUtilDto.getTotalPages());
		paginationResponseDto.setHasNextPage(paginationUtilDto.isHasNextPage());
		
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).
				setMessage("Courses Displayed successfully").setData(paginationResponseDto).create();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> get(@RequestHeader(required = false) final String userId,
			@Valid @PathVariable final String id) throws Exception {
		InstituteResponseDto instituteResponseDto = new InstituteResponseDto();
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
		Map<String, Double> googleReviewMap = instituteGoogleReviewProcessor
				.getInstituteAvgGoogleReviewForList(Arrays.asList(courseRequest.getInstituteId()));
		Map<String, Double> seekaReviewMap = iUserReviewService
				.getUserAverageReviewBasedOnDataList("INSTITUTE", Arrays.asList(courseRequest.getInstituteId()));
		
		if(courseRequest.getStars() != null && courseRequest.getInstituteId() != null) {
			courseRequest.setStars(String.valueOf(courseService.calculateAverageRating(googleReviewMap, seekaReviewMap,
					Double.valueOf(courseRequest.getStars()), courseRequest.getInstituteId())));
		}
		
		courseRequest.setIntake(courseService.getCourseIntakeBasedOnCourseId(id).stream()
				.map(CourseIntake::getIntakeDates).collect(Collectors.toList()));
		
		courseRequest.setLanguage(courseService.getCourseLanguageBasedOnCourseId(id).stream()
				.map(CourseLanguage::getLanguage).collect(Collectors.toList()));
		
		Institute instituteObj = course.getInstitute();
		BeanUtils.copyProperties(instituteObj, instituteResponseDto);
		List<YouTubeVideoDto> youtubeData = new ArrayList<>();
		if (instituteObj != null) {
			List<String> instituteServices = instituteServiceProcessor.getAllServices(instituteObj.getId());
			if(!CollectionUtils.isEmpty(instituteServices)) {
				instituteResponseDto.setInstituteServices(instituteServices);
			}
			List<StorageDto> storageDTOList = iStorageService.getStorageInformation(instituteObj.getId(), ImageCategory.INSTITUTE.toString(), null, "en");
			courseRequest.setWorldRanking(String.valueOf(instituteObj.getWorldRanking()));
			courseRequest.setStorageList(storageDTOList);
			youtubeData = commonHandler.getYoutubeDataBasedOnCriteria(instituteObj.getName(), instituteObj.getCountryName(),
					instituteObj.getCityName(), course.getName(), 1, 10);
		}
		
		List<AccrediatedDetailDto> accrediatedInstituteDetailsFromDB = accrediatedDetailProcessor.getAccrediationDetailByEntityId(instituteObj.getId());
		if(!CollectionUtils.isEmpty(accrediatedInstituteDetailsFromDB)) {
			instituteResponseDto.setAccrediatedDetail(accrediatedInstituteDetailsFromDB);
		}
		
		List<AccrediatedDetailDto> accrediatedCourseDetails = accrediatedDetailProcessor.getAccrediationDetailByEntityId(course.getId());
		if(!CollectionUtils.isEmpty(accrediatedCourseDetails)) {
			courseRequest.setAccrediatedDetail(accrediatedCourseDetails);
		}
		List<CourseEnglishEligibilityDto> englishCriteriaList = courseEnglishEligibilityProcessor.getAllEnglishEligibilityByCourse(id);
		
		if (!englishCriteriaList.isEmpty()) {
			courseRequest.setEnglishEligibility(englishCriteriaList);
		} else {
			courseRequest.setEnglishEligibility(new ArrayList<>());
		}

		List<CourseResponseDto> recommendCourse = userRecommendationService.getCourseRecommended(id);
		List<CourseResponseDto> relatedCourse = userRecommendationService.getCourseRelated(id);
//		if (course.getInstitute() != null) {
//			List<InstituteLevel> instituteLevels = instituteLevelService
//					.getAllLevelByInstituteId(course.getInstitute().getId());
//			if (instituteLevels != null && !instituteLevels.isEmpty()) {
//				if (instituteLevels.get(0).getLevel() != null) {
//					courseRequest.setLevelId(instituteLevels.get(0).getLevel().getId());
//					courseRequest.setLevelName(instituteLevels.get(0).getLevel().getName());
//				}
//			}
//		}

		if (course.getInstitute() != null) {
			courseRequest.setLatitude(course.getInstitute().getLatitude());
			courseRequest.setLongitude(course.getInstitute().getLongitude());
		}

		/**
		 * Get Enrollment details for the course
		 */
		if (userId != null) {
			int count = iEnrolmentService.countOfEnrollment(userId, id, null, null, null, null, null);
			courseRequest.setApplied(count == 0 ? false : true);
		} else {
			courseRequest.setApplied(false);
		}

		/**
		 * Get User View Course Details
		 */
		if (userId != null) {
			int count = iViewService.getUserViewDataCountBasedOnUserId(userId, id, "COURSE");
			courseRequest.setViewCourse(count == 0 ? false : true);
		} else {
			courseRequest.setViewCourse(false);
		}

		/**
		 * Add User Review to the course info response
		 */
		List<UserReviewResultDto> userReviewResultList = iUserReviewService.getUserReviewBasedOnData(id, "COURSE", 1, 5,
				null, null);
		courseRequest.setUserReviewResult(userReviewResultList);

		StudentVisaDto studentVisaDto = commonHandler.getStudentVisaDetailsByCountryName(course.getInstitute().getCountryName());
		if(!ObjectUtils.isEmpty(studentVisaDto.getId())) {
			courseRequest.setStudentVisa(studentVisaDto);
		}
		response.put("status", HttpStatus.OK.value());
		response.put("message", "Success.!");
		response.put("courseObj", courseRequest);
		response.put("recommendCourse", recommendCourse);
		response.put("relatedCourse", relatedCourse);
		response.put("instituteObj", instituteResponseDto);
		response.put("youtubeData", youtubeData);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(value = "/institute/{instituteId}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> getAllCourseByInstituteID(@Valid @PathVariable final String instituteId,
			@Valid @RequestBody final CourseSearchDto request) throws Exception {
		ErrorDto errorDto = null;
		Map<String, Object> response = new HashMap<>();

		InstituteResponseDto instituteResponseDto = instituteProcessor.getInstituteByID(instituteId);
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
		response.put("paginationObj", new PaginationDto(totalCount, showMore,instituteResponseDto));
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
	public ResponseEntity<?> searchCourseKeyword(@RequestParam(value = "keyword") final String keyword)
			throws Exception {
		List<CourseKeywords> searchkeywordList = courseKeywordService.searchCourseKeyword(keyword);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Courses Keyword Displayed successfully").setData(searchkeywordList).create();
	}

	@RequestMapping(value = "/faculty/{facultyId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getCoursesByFacultyId(@Valid @PathVariable final String facultyId) throws Exception {
		List<CourseResponseDto> courseResponseDtos = courseService.getCouresesByFacultyId(facultyId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Courses Displayed successfully").setData(courseResponseDtos).create();
	}

	@RequestMapping(value = "/multiple/faculty/{facultyId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getCouresesByListOfFacultyId(@Valid @PathVariable final String facultyId)
			throws Exception {
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

	@Deprecated
	@RequestMapping(value = "/eligibility/update", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> updateGradeAndEnglishEligibility() throws Exception {
		Map<String, Object> response = new HashMap<>();
		List<Course> courseList = courseService.getAll();
		Date now = new Date();

		CourseEnglishEligibility englishEligibility = null;

		int size = courseList.size(), i = 1;

		for (Course course : courseList) {
			System.out.println("Total:  " + size + ",  Completed:  " + i + ",  CourseID:  " + course.getId());
			i++;
			try {
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
				courseEnglishEligibilityProcessor.save(englishEligibility);
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
				courseEnglishEligibilityProcessor.save(englishEligibility);
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
	public ResponseEntity<?> getUserCourses(@PathVariable final String userId,
			@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,
			@RequestParam(required = false) final String currencyCode,
			@RequestParam(required = false) final String sortBy,
			@RequestParam(required = false) final boolean sortAsscending) throws ValidationException {
		return ResponseEntity.accepted()
				.body(courseService.getUserCourse(userId, pageNumber, pageSize, currencyCode, sortBy, sortAsscending));
	}

	@RequestMapping(value = "/compare", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> addUserCompareCourse(@Valid @RequestBody final UserCourse userCourse) throws Exception {
		courseService.addUserCompareCourse(userCourse);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("User Compare Courses add successfully").create();
	}

	@RequestMapping(value = "/compare/user/{userId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getUserCompareCourse(@PathVariable final String userId) throws Exception {
		List<UserCompareCourseResponse> compareCourseResponses = courseService.getUserCompareCourse(userId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(compareCourseResponses)
				.setMessage("User Courses displayed successfully").create();
	}

	@RequestMapping(value = "/filter", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> courseFilter(@RequestHeader(required = true) final String userId,
			@RequestHeader(required = false) final String language, @RequestBody final CourseFilterDto courseFilter)
			throws Exception {

		/**
		 * Get userCountry Based on userId
		 */
		UserDto userDto = iUsersService.getUserById(userId);
		if (userDto == null) {
			throw new NotFoundException(
					messageByLocalService.getMessage("user.not.found", new Object[] { userId }, language));
		} else if (userDto.getCitizenship() == null || userDto.getCitizenship().isEmpty()) {
			throw new ValidationException(messageByLocalService.getMessage("user.citizenship.not.present",
					new Object[] { userId }, language));
		}

		courseFilter.setUserCountryId(userDto.getCitizenship());
		PaginationResponseDto paginationResponseDto = courseService.courseFilter(courseFilter);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(paginationResponseDto)
				.setMessage("Course Displayed Successfully").create();
	}

	@RequestMapping(value = "/minimumRequirement", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> saveCourseMinRequirement(
			@Valid @RequestBody final List<CourseMinRequirementDto> courseMinRequirementDtoList) throws Exception {
		for (CourseMinRequirementDto courseMinRequirementDto2 : courseMinRequirementDtoList) {
			courseService.saveCourseMinrequirement(courseMinRequirementDto2);
		}
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Created Course Minimum Requirement").create();
	}

	@RequestMapping(value = "/minimumRequirement/{courseId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getCourseMinRequirement(@PathVariable final String courseId) throws Exception {
		List<CourseMinRequirementDto> courseMinRequirementDto = courseService.getCourseMinRequirement(courseId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Get Course Minimum Requirement").setData(courseMinRequirementDto).create();
	}

	@RequestMapping(value = "/autoSearch/{searchKey}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> autoSearchByCharacter(@PathVariable final String searchKey) throws Exception {
		List<CourseRequest> courses = courseService.autoSearchByCharacter(searchKey);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Courses fetched successfully").setData(courses).create();
	}

	@GetMapping(value = "/viewCourse/filter")
	public ResponseEntity<?> getUserListForUserWatchCourseFilter(@RequestHeader(required = false) final String language,
			@RequestParam(name = "courseId", required = false) final String courseId,
			@RequestParam(name = "facultyId", required = false) final String facultyId,
			@RequestParam(name = "instituteId", required = false) final String instituteId,
			@RequestParam(name = "countryId", required = false) final String countryId,
			@RequestParam(name = "cityId", required = false) final String cityId) throws ValidationException {
		if (courseId == null && facultyId == null && instituteId == null && countryId == null && cityId == null) {
			throw new ValidationException(
					messageByLocalService.getMessage("specify.filter.parameters", new Object[] {}));
		}
		List<Long> userList = courseService.getUserListForUserWatchCourseFilter(courseId, instituteId, facultyId,
				countryId, cityId);
		return new GenericResponseHandlers.Builder().setData(userList).setMessage("User List Displayed Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	/**
	 * This API is used when in normal or global search if data is not available
	 * based on filter.
	 *
	 * @param pageNumber
	 * @param pageSize
	 * @param facultyId
	 * @param countryId
	 * @param userCountry
	 * @return
	 * @throws ValidationException
	 */
	@GetMapping(value = "/noResult/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<Object> getCourseNoResultRecommendation(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(required = true) final String facultyId,
			@RequestParam(required = true) final String countryId,
			@RequestParam(required = true) final String userCountry) throws ValidationException {
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<CourseResponseDto> courseResponseDtos = courseService.getCourseNoResultRecommendation(userCountry,
				facultyId, countryId, startIndex, pageSize);
		return new GenericResponseHandlers.Builder().setData(courseResponseDtos)
				.setMessage("Get course list displayed successfully").setStatus(HttpStatus.OK).create();
	}

	/**
	 * This API is used for course Info page for related course keyword
	 * recommendation.
	 *
	 * @param pageNumber
	 * @param pageSize
	 * @param facultyId
	 * @param countryId
	 * @param levelId
	 * @return
	 * @throws ValidationException
	 */
	@GetMapping(value = "/keyword/recommendatation/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<Object> getCourseKeywordRecommendation(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(required = true) final String facultyId,
			@RequestParam(required = true) final String countryId,
			@RequestParam(required = true) final String levelId) throws ValidationException {
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<String> courseResponseDtos = courseService.getCourseKeywordRecommendation(facultyId, countryId, levelId,
				startIndex, pageSize);
		return new GenericResponseHandlers.Builder().setData(courseResponseDtos)
				.setMessage("Get course keyword recommendation list displayed successfully").setStatus(HttpStatus.OK)
				.create();
	}

	/**
	 * This API is used for course Info page for related course keyword
	 * recommendation.
	 *
	 * @param pageNumber
	 * @param pageSize
	 * @param facultyId
	 * @param countryId
	 * @param levelId
	 * @return
	 * @throws ValidationException
	 */
	@GetMapping(value = "/cheapest/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<Object> getCheapestCourse(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(required = true) final String facultyId,
			@RequestParam(required = true) final String countryId,
			@RequestParam(required = true) final String levelId,
			@RequestParam(required = true) final String cityId) throws ValidationException {
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<CourseResponseDto> courseResponseDtos = userRecommendationService.getCheapestCourse(facultyId, countryId,
				levelId, cityId, startIndex, pageSize);
		return new GenericResponseHandlers.Builder().setData(courseResponseDtos)
				.setMessage("Get cheapest course recommendation list displayed successfully").setStatus(HttpStatus.OK)
				.create();
	}

	@GetMapping(value = "/getCourseCountByLevel")
	public ResponseEntity<Object> getCourseCountByLevel() {
		Map<String, Integer> courseCount = courseService.getCourseCountByLevel();
		return new GenericResponseHandlers.Builder().setData(courseCount)
				.setMessage("Course count displayed successfully").setStatus(HttpStatus.OK).create();
	}
	
	@GetMapping(value = "/names/distinct/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getDistinctCourses(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,@RequestParam(required = false) final String name) {
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		int totalCount = courseService.getDistinctCourseCount(name);
		List<CourseResponseDto> couserList = courseService.getDistinctCourseList(startIndex, pageSize,name);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get course List successfully");
		responseMap.put("data", couserList);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
		
	}
	
	@PostMapping(value = "/mobile/{instituteId}")
	public ResponseEntity<?> addCourseViaMobile(@RequestHeader("userId") final String userId,@PathVariable final String instituteId, @Valid @RequestBody CourseMobileDto courseMobileDto) throws Exception {
		courseService.addMobileCourse(userId, instituteId, courseMobileDto);
		return new GenericResponseHandlers.Builder()
				.setMessage("Mobile course added successfully").setStatus(HttpStatus.OK)
				.create();
	}
	
	@PutMapping(value = "/mobile/{courseId}")
	public ResponseEntity<?> updateCourseViaMobile(@RequestHeader("userId") final String userId,@PathVariable final String courseId, @Valid @RequestBody CourseMobileDto courseMobileDto) throws Exception {
		courseService.updateMobileCourse(userId, courseId, courseMobileDto);
		return new GenericResponseHandlers.Builder()
				.setMessage("Mobile course updated successfully").setStatus(HttpStatus.OK)
				.create();
	}
	
	@GetMapping(value = "/mobile/{instituteId}")
	public ResponseEntity<?> getCourseViaMobile(@RequestHeader("userId") final String userId,@PathVariable final String instituteId,@RequestParam(name = "faculty_id" ,required = true) final String facultyId,@RequestParam(name = "status" ,required = true) final boolean status) throws Exception {
		List<CourseMobileDto> listOfMobileCourseResponseDto = courseService.getAllMobileCourseByInstituteIdAndFacultyIdAndStatus(userId, instituteId, facultyId, status);
		return new GenericResponseHandlers.Builder().setData(listOfMobileCourseResponseDto)
				.setMessage("Mobile course fetched successfully").setStatus(HttpStatus.OK)
				.create();
	}

	@GetMapping(value = "/public/mobile/{instituteId}")
	public ResponseEntity<?> getCourseViaMobile(@PathVariable final String instituteId,@RequestParam(name = "faculty_id" ,required = true) final String facultyId) throws Exception {
		List<CourseMobileDto> listOfMobileCourseResponseDto = courseService.getPublicMobileCourseByInstituteIdAndFacultyId(instituteId, facultyId);
		return new GenericResponseHandlers.Builder().setData(listOfMobileCourseResponseDto)
				.setMessage("Mobile course fetched successfully").setStatus(HttpStatus.OK)
				.create();
	}
	
	@PutMapping(value = "/mobile/change/status/{courseId}")
	public ResponseEntity<?> changeStatus(@RequestHeader("userId") final String userId,@PathVariable final String courseId,@RequestParam(name = "status" ,required = true) final boolean  status) throws Exception {
		courseService.changeCourseStatus(userId, courseId, status);
		return new GenericResponseHandlers.Builder()
				.setMessage("course status changed successfully").setStatus(HttpStatus.OK)
				.create();
	}
	
	@GetMapping(value = "/institute/pageNumber/{pageNumber}/pageSize/{pageSize}/{id}")
	public ResponseEntity<?> getCourseByInstituteId(@PathVariable Integer pageNumber, @PathVariable Integer pageSize, 
			@PathVariable final String id) throws NotFoundException {
		NearestCoursesDto nearestCourseList = courseService.getCourseByInstituteId(pageNumber, pageSize, id);
		return new GenericResponseHandlers.Builder().setData(nearestCourseList)
				.setMessage("Courses displayed successfully").setStatus(HttpStatus.OK)
				.create();
	}
	
	@PostMapping(value = "/nearest", produces = "application/json")
	public ResponseEntity<?> getNearestCourseList(@RequestBody final AdvanceSearchDto courseSearchDto) throws Exception {
		NearestCoursesDto courseResponseDtoList = courseService.getNearestCourses(courseSearchDto);
		return new GenericResponseHandlers.Builder().setData(courseResponseDtoList).setMessage("Courses Displayed Successfully.")
				.setStatus(HttpStatus.OK).create();
	}
}