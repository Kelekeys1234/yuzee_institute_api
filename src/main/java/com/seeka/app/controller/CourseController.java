package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseEnglishEligibility;
import com.seeka.app.bean.CourseGradeEligibility;
import com.seeka.app.bean.CourseKeywords;
import com.seeka.app.bean.CoursePricing;
import com.seeka.app.bean.Currency;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteLevel;
import com.seeka.app.bean.UserInfo;
import com.seeka.app.bean.YoutubeVideo;
import com.seeka.app.dto.AdvanceSearchDto;
import com.seeka.app.dto.CourseFilterDto;
import com.seeka.app.dto.CourseMinRequirementDto;
import com.seeka.app.dto.CourseRequest;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.ErrorDto;
import com.seeka.app.dto.ImageResponseDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.PaginationDto;
import com.seeka.app.dto.UserCourse;
import com.seeka.app.enumeration.EnglishType;
import com.seeka.app.jobs.CurrencyUtil;
import com.seeka.app.service.ICourseEnglishEligibilityService;
import com.seeka.app.service.ICourseGradeEligibilityService;
import com.seeka.app.service.ICourseKeywordService;
import com.seeka.app.service.ICoursePricingService;
import com.seeka.app.service.ICourseService;
import com.seeka.app.service.IInstituteImagesService;
import com.seeka.app.service.IInstituteService;
import com.seeka.app.service.IUserService;
import com.seeka.app.service.InstituteLevelService;
import com.seeka.app.service.UserRecommendationService;
import com.seeka.app.util.CDNServerUtil;
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
	private IUserService userService;

	@Autowired
	private ICourseEnglishEligibilityService courseEnglishService;

	@Autowired
	private UserRecommendationService userRecommendationService;

	@Autowired
	private ICourseGradeEligibilityService courseGradeService;

	@Autowired
	private IInstituteImagesService iInstituteImagesService;

	@Autowired
	private InstituteLevelService instituteLevelService;

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> save(@Valid @RequestBody final CourseRequest course) throws Exception {
		return ResponseEntity.accepted().body(courseService.save(course));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> update(@Valid @RequestBody final CourseRequest course, @PathVariable final BigInteger id) throws Exception {
		return ResponseEntity.accepted().body(courseService.update(course, id));
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
			@RequestParam(required = false) final String sortBy, @RequestParam(required = false) final boolean sortAsscending) throws Exception {
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
		return courseSearch(courseSearchDto);
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> searchCourse(@RequestBody final CourseSearchDto courseSearchDto) throws Exception {
		return courseSearch(courseSearchDto);
	}

	private ResponseEntity<?> courseSearch(final CourseSearchDto courseSearchDto) {
		Map<String, Object> response = new HashMap<>();
		ErrorDto errorDto = null;
		if (courseSearchDto.getPageNumber() > PaginationUtil.courseResultPageMaxSize) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Maximum course limit per is " + PaginationUtil.courseResultPageMaxSize);
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		Map<BigInteger, Boolean> favouriteMap = new HashMap<>();
		List<CourseResponseDto> courseList = courseService.getAllCoursesByFilter(courseSearchDto);
		for (CourseResponseDto obj : courseList) {
			try {
				Boolean isFav = favouriteMap.get(obj.getCourseId());
				if (null != isFav) {
					obj.setIsFavourite(isFav);
				}
				List<ImageResponseDto> imageResponseDtos = iInstituteImagesService.getInstituteImageListBasedOnId(obj.getInstituteId());
				obj.setInstituteImages(imageResponseDtos);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Integer maxCount = 0, totalCount = 0;
		if (null != courseList && !courseList.isEmpty()) {
			totalCount = courseList.get(0).getTotalCount();
			maxCount = courseList.size();
		}
		boolean showMore;
		if (courseSearchDto.getMaxSizePerPage() == maxCount) {
			showMore = true;
		} else {
			showMore = false;
		}
		response.put("status", 1);
		response.put("message", "Success.!");
		response.put("paginationObj", new PaginationDto(totalCount, showMore));
		response.put("courseList", courseList);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(value = "/advanceSearch", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> advanceSearch(@RequestBody final AdvanceSearchDto courseSearchDto) throws Exception {
		return ResponseEntity.ok().body(courseService.advanceSearch(courseSearchDto));
	}

	@RequestMapping(value = "/mycourses", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> getAllMyCourses(@RequestBody final CourseSearchDto courseSearchDto) throws Exception {
		Map<String, Object> response = new HashMap<>();
		ErrorDto errorDto = null;
		if (courseSearchDto.getPageNumber() > PaginationUtil.courseResultPageMaxSize) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Maximum course limit per is " + PaginationUtil.courseResultPageMaxSize);
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		UserInfo user = userService.get(courseSearchDto.getUserId());
		if (null == user) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Invalid user.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}

		// Currency userCurrency =
		// CurrencyUtil.getCurrencyObjById(user.getCurrencyId());
		Currency currency = null;
		String message = "";
		if (null != courseSearchDto.getCurrencyId() && !user.getPreferredCurrencyId().equals(courseSearchDto.getCurrencyId())) {
			currency = CurrencyUtil.getCurrencyObjById(courseSearchDto.getCurrencyId());
			response.put("showCurrencyPopup", true);
			message = "Do you want to change " + currency.getName() + " (" + currency.getCode() + ") as your currency.?";
		} else {
			currency = CurrencyUtil.getCurrencyObjById(user.getPreferredCurrencyId());
			response.put("showCurrencyPopup", false);
		}
		response.put("currencyPopupMsg", message);

		List<CourseResponseDto> courseList = courseService.getAllCoursesByFilter(courseSearchDto);
		for (CourseResponseDto obj : courseList) {
			List<ImageResponseDto> imageResponseDtos = iInstituteImagesService.getInstituteImageListBasedOnId(obj.getInstituteId());
			obj.setInstituteImages(imageResponseDtos);
		}
		Integer maxCount = 0, totalCount = 0;
		if (null != courseList && !courseList.isEmpty()) {
			totalCount = courseList.get(0).getTotalCount();
			maxCount = courseList.size();
		}
		boolean showMore;
		if (courseSearchDto.getMaxSizePerPage() == maxCount) {
			showMore = true;
		} else {
			showMore = false;
		}
		/*
		 * CourseFilterCostResponseDto costResponseDto =
		 * courseService.getAllCoursesFilterCostInfo(courseSearchDto, currency,
		 * userCurrency.getCode()); costResponseDto.setCurrencyId(currency.getId());
		 * costResponseDto.setCurrencySymbol(currency.getSymbol());
		 * costResponseDto.setCurrencyCode(currency.getCode());
		 * costResponseDto.setCurrencyName(currency.getName());
		 */

		response.put("status", 1);
		response.put("message", "Success.!");
		response.put("paginationObj", new PaginationDto(totalCount, showMore));
		response.put("courseList", courseList);
		// response.put("costCurrency",costResponseDto);
		return ResponseEntity.accepted().body(response);
	}

	@RequestMapping(value = "/name", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> search(@Valid @RequestParam("searchkey") final String searchkey) throws Exception {
		Map<String, Object> response = new HashMap<>();
		CourseSearchDto courseSearchDto = new CourseSearchDto();
		courseSearchDto.setSearchKey(searchkey);
		List<CourseResponseDto> courseList = courseService.getAllCoursesByFilter(courseSearchDto);
		response.put("status", 1);
		response.put("message", "Success.!");
		response.put("courseList", courseList);
		return ResponseEntity.accepted().body(response);
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
		Institute instituteObj = course.getInstitute();
		if (instituteObj != null && instituteObj.getCountry() != null) {
			courseRequest.setInstituteLogoUrl(CDNServerUtil.getInstituteLogoImage(instituteObj.getCountry().getName(), instituteObj.getName()));
			courseRequest.setInstituteImageUrl(CDNServerUtil.getInstituteMainImage(instituteObj.getCountry().getName(), instituteObj.getName()));
		}
		List<CourseEnglishEligibility> englishCriteriaList = courseEnglishService.getAllEnglishEligibilityByCourse(id);
		if (!englishCriteriaList.isEmpty()) {
			courseRequest.setEnglishEligibility(englishCriteriaList);
		}
		List<YoutubeVideo> youtubeData = courseService.getYoutubeDataforCourse(instituteObj.getId(), course.getName());
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
		response.put("status", 1);
		response.put("message", "Success.!");
		response.put("courseObj", courseRequest);
		response.put("recommendCourse", recommendCourse);
		response.put("relatedCourse", relatedCourse);
		response.put("instituteObj", instituteObj);
		response.put("youtubeData", youtubeData);
		return ResponseEntity.accepted().body(response);
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
		List<ImageResponseDto> imageResponseDtos = iInstituteImagesService.getInstituteImageListBasedOnId(instituteResponseDto.getInstituteId());
		instituteResponseDto.setInstituteImages(imageResponseDtos);

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

	@RequestMapping(value = "user/{userId}/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getUserCourses(@PathVariable final BigInteger userId, @PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,
			@RequestParam(required = false) final String currencyCode, @RequestParam(required = false) final String sortBy,
			@RequestParam(required = false) final boolean sortAsscending) throws Exception {
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

	@RequestMapping(value = "/youtube/{courseId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getYoutubeDataforCourse(@PathVariable final BigInteger courseId) throws Exception {
		return ResponseEntity.accepted().body(courseService.getYoutubeDataforCourse(courseId));
	}

	@RequestMapping(value = "/filter", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> courseFilter(@RequestBody final CourseFilterDto courseFilter) throws Exception {
		return ResponseEntity.ok().body(courseService.courseFilter(courseFilter));
	}

	@RequestMapping(value = "/minimumRequirement", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> saveCourseMinRequirement(@Valid @RequestBody final CourseMinRequirementDto courseMinRequirementDto) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			courseService.saveCourseMinrequirement(courseMinRequirementDto);
			response.put("status", HttpStatus.OK.value());
			response.put("message", "CourseMinRequirement Save Successfully");
		} catch (Exception exception) {
			exception.printStackTrace();
			response.put("status", HttpStatus.NOT_FOUND.value());
			response.put("message", "Error to save CourseMinRequirement");
		}
		return ResponseEntity.accepted().body(response);
	}

    @RequestMapping(value = "/minimumRequirement/{courseId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getCourseMinRequirement(@PathVariable final BigInteger courseId) throws Exception {
        Map<String, Object> response = new HashMap<>();
        try {
            CourseMinRequirementDto courseMinRequirementDto = courseService.getCourseMinRequirement(courseId);
            if (courseMinRequirementDto.getGrade() != null && !courseMinRequirementDto.getGrade().isEmpty()) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Course minimum requirement fetch Successfully");
                response.put("data", courseMinRequirementDto);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "Course minimum requirement Not Found");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", exception);
        }
        return ResponseEntity.accepted().body(response);
    }

	@RequestMapping(value = "/autoSearch/{searchKey}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> autoSearchByCharacter(@PathVariable final String searchKey) throws Exception {
		return ResponseEntity.accepted().body(courseService.autoSearchByCharacter(searchKey));
	}

}