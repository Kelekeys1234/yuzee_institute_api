package com.seeka.app.controller.v1;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseEnglishEligibility;
import com.seeka.app.bean.CourseKeywords;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.AdvanceSearchDto;
import com.seeka.app.dto.CourseFilterDto;
import com.seeka.app.dto.CourseMinRequirementDto;
import com.seeka.app.dto.CourseMobileDto;
import com.seeka.app.dto.CourseRequest;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.NearestCoursesDto;
import com.seeka.app.dto.PaginationDto;
import com.seeka.app.dto.PaginationResponseDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.dto.UserCompareCourseResponse;
import com.seeka.app.dto.UserCourse;
import com.seeka.app.dto.UserDto;
import com.seeka.app.endpoint.CourseInterface;
import com.seeka.app.enumeration.EnglishType;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.exception.CommonInvokeException;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.message.MessageByLocaleService;
import com.seeka.app.processor.CourseEnglishEligibilityProcessor;
import com.seeka.app.processor.CourseKeywordProcessor;
import com.seeka.app.processor.CourseProcessor;
import com.seeka.app.processor.InstituteProcessor;
import com.seeka.app.processor.StorageProcessor;
import com.seeka.app.service.IUsersService;
import com.seeka.app.service.UserRecommendationService;
import com.seeka.app.util.PaginationUtil;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RestController("courseControllerV1")
public class CourseController implements CourseInterface {

	@Autowired
	private InstituteProcessor instituteProcessor;

	@Autowired
	private CourseProcessor courseProcessor;

	@Autowired
	private CourseKeywordProcessor courseKeywordProcessor;

	@Autowired
	private CourseEnglishEligibilityProcessor courseEnglishEligibilityProcessor;

	@Autowired
	private UserRecommendationService userRecommendationService;

	@Autowired
	private StorageProcessor storageProcessor;

	@Autowired
	private MessageByLocaleService messageByLocalService;

	@Autowired
	private IUsersService iUsersService;

	public ResponseEntity<?> save(final CourseRequest course) throws ValidationException, CommonInvokeException {
		log.info("Start process to save new course in DB");
		String courseId = courseProcessor.saveCourse(course);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(courseId)
				.setMessage("Course Created successfully").create();
	}

	public ResponseEntity<?> update(final CourseRequest course, final String id) throws ValidationException, CommonInvokeException {
		log.info("Start process to update existing course in DB");
		String courseId = courseProcessor.updateCourse(course, id);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(courseId)
				.setMessage("Course Updated successfully").create();
	}

	public ResponseEntity<?> getAllCourse(final Integer pageNumber, final Integer pageSize) throws Exception {
		log.info("Start process get all courses from DB based on pagination");
		PaginationResponseDto paginationResponseDto = courseProcessor.getAllCourse(pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(paginationResponseDto)
				.setMessage("Courses Displayed successfully").create();
	}

	public ResponseEntity<?> autoSearch(final String searchKey, final Integer pageNumber, final Integer pageSize) throws Exception {
		log.info("Start process to search course based on pagination and searchkeyword");
		PaginationResponseDto paginationResponseDto = courseProcessor.autoSearch(pageNumber, pageSize, searchKey);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(paginationResponseDto)
				.setMessage("Courses Displayed successfully").create();
	}

	public ResponseEntity<?> delete(final String id) throws Exception {
		log.info("Start process to delete existing course from DB");
		courseProcessor.deleteCourse(id);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Courses Deleted successfully").create();
	}

	public ResponseEntity<?> searchCourse(final Integer pageNumber, final Integer pageSize, final List<String> countryIds, final String instituteId, 
			final List<String> facultyIds, final List<String> cityIds, final List<String> levelIds, final List<String> serviceIds, final Double minCost, 
			final Double maxCost, final Integer minDuration, final Integer maxDuration, final String courseName, final String currencyCode, 
			final String searchKeyword, final String sortBy, final boolean sortAsscending, final String userId, final String date) 
			throws ValidationException {
		log.info("Start process to search course based on different passed filters");
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

	public ResponseEntity<?> searchCourse(final String userId, final CourseSearchDto courseSearchDto) throws Exception {
		log.info("Start process to search course based on userID and passed filters");
		courseSearchDto.setUserId(userId);
		return courseSearch(courseSearchDto, null);
	}

	private ResponseEntity<?> courseSearch(final CourseSearchDto courseSearchDto, final String searchKeyword)
			throws ValidationException {
		int startIndex = PaginationUtil.getStartIndex(courseSearchDto.getPageNumber(), courseSearchDto.getMaxSizePerPage());
		
		List<CourseResponseDto> courseList = courseProcessor.getAllCoursesByFilter(courseSearchDto, startIndex, courseSearchDto.getMaxSizePerPage(), 
					searchKeyword);
		int totalCount = courseProcessor.getCountforNormalCourse(courseSearchDto, searchKeyword);
		
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

	public ResponseEntity<?> advanceSearch(final String userId, final String language, final AdvanceSearchDto courseSearchDto) throws Exception {
		log.info("Start process to advance search courses from DB");
		int startIndex = PaginationUtil.getStartIndex(courseSearchDto.getPageNumber(),courseSearchDto.getMaxSizePerPage());
		courseSearchDto.setUserId(userId);
		
		List<CourseResponseDto> courseList = courseProcessor.advanceSearch(courseSearchDto);
		int totalCount = courseProcessor.getCountOfAdvanceSearch(courseSearchDto);
		
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

	public ResponseEntity<Object> get(final String userId, final String id) throws Exception {
		log.info("Start process to get course from DB based on courseId");
		Map<String, Object> response = courseProcessor.getCourseById(userId, id);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Courses Displayed successfully").setData(response).create();
	}

	public ResponseEntity<?> getAllCourseByInstituteID(final String instituteId, final CourseSearchDto request) throws Exception {
		Map<String, Object> response = new HashMap<>();

		InstituteResponseDto instituteResponseDto = instituteProcessor.getInstituteByID(instituteId);
		if (null == instituteResponseDto) {
			throw new NotFoundException("No Institute found in DB for instituteId = "+instituteId);
		}
		List<StorageDto> storageDTOList = storageProcessor.getStorageInformation(instituteResponseDto.getId(), ImageCategory.INSTITUTE.toString(), null, "en");
		instituteResponseDto.setStorageList(storageDTOList);

		List<CourseResponseDto> courseList = courseProcessor.getAllCoursesByInstitute(instituteId, request);

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
		response.put("paginationObj", new PaginationDto(totalCount, showMore,instituteResponseDto));
		response.put("instituteObj", instituteResponseDto);
		response.put("courseList", courseList);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Courses Displayed successfully").setData(response).create();
	}

	public ResponseEntity<?> searchCourseKeyword(final String keyword) throws Exception {
		log.info("Start process to search course keyword based on searcKeyword passed in request");
		List<CourseKeywords> searchkeywordList = courseKeywordProcessor.searchCourseKeyword(keyword);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Courses Keyword Displayed successfully").setData(searchkeywordList).create();
	}

	public ResponseEntity<?> getCoursesByFacultyId(final String facultyId) throws Exception {
		log.info("Start process to get courses based on facultyID");
		List<CourseResponseDto> courseResponseDtos = courseProcessor.getCouresesByFacultyId(facultyId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Courses Displayed successfully").setData(courseResponseDtos).create();
	}

	public ResponseEntity<?> getCouresesByListOfFacultyId(final String facultyId)
			throws Exception {
		List<CourseResponseDto> courseResponseDto = courseProcessor.getCouresesByListOfFacultyId(facultyId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Courses Displayed successfully").setData(courseResponseDto).create();
	}

	public ResponseEntity<?> userCourses(final UserCourse userCourse) throws Exception {
		log.info("Start process to save user courses in DB");
		courseProcessor.addUserCourses(userCourse);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("User Course added successfully").create();
	}

	// Get My course List
	public ResponseEntity<?> getUserCourses(final String userId, final Integer pageNumber, final Integer pageSize, final String currencyCode,
			final String sortBy, final boolean sortAsscending) throws ValidationException, CommonInvokeException {
		log.info("Start process to get user course from DB based on pagination and userID");
		PaginationResponseDto paginationResponseDto = courseProcessor.getUserCourse(userId, pageNumber, pageSize, currencyCode, sortBy, sortAsscending);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(paginationResponseDto)
				.setMessage("User Courses displayed successfully").create();
	}

	public ResponseEntity<?> addUserCompareCourse(final UserCourse userCourse) throws Exception {
		log.info("Start process to save user compare couses in DB");
		courseProcessor.addUserCompareCourse(userCourse);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("User Compare Courses add successfully").create();
	}

	public ResponseEntity<?> getUserCompareCourse(final String userId) throws Exception {
		log.info("Start process to get user compare course from DB based on userID");
		List<UserCompareCourseResponse> compareCourseResponses = courseProcessor.getUserCompareCourse(userId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(compareCourseResponses)
				.setMessage("User Courses displayed successfully").create();
	}

	public ResponseEntity<?> courseFilter(final String userId, String language, final CourseFilterDto courseFilter) throws Exception {
		//Get userCountry Based on userId
		UserDto userDto = iUsersService.getUserById(userId);
		if (userDto == null) {
			throw new NotFoundException(
					messageByLocalService.getMessage("user.not.found", new Object[] { userId }, language));
		} else if (userDto.getCitizenship() == null || userDto.getCitizenship().isEmpty()) {
			throw new ValidationException(messageByLocalService.getMessage("user.citizenship.not.present",
					new Object[] { userId }, language));
		}

		courseFilter.setUserCountryId(userDto.getCitizenship());
		PaginationResponseDto paginationResponseDto = courseProcessor.courseFilter(courseFilter);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(paginationResponseDto)
				.setMessage("Course Displayed Successfully").create();
	}

	public ResponseEntity<?> saveCourseMinRequirement(final List<CourseMinRequirementDto> courseMinRequirementDtoList) throws Exception {
		for (CourseMinRequirementDto courseMinRequirementDto2 : courseMinRequirementDtoList) {
			courseProcessor.saveCourseMinrequirement(courseMinRequirementDto2);
		}
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Created Course Minimum Requirement").create();
	}

	public ResponseEntity<?> getCourseMinRequirement(final String courseId) throws Exception {
		List<CourseMinRequirementDto> courseMinRequirementDto = courseProcessor.getCourseMinRequirement(courseId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Get Course Minimum Requirement").setData(courseMinRequirementDto).create();
	}

	public ResponseEntity<?> autoSearchByCharacter(final String searchKey) throws Exception {
		List<CourseRequest> courses = courseProcessor.autoSearchByCharacter(searchKey);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Courses fetched successfully").setData(courses).create();
	}

	// This API is used when in normal or global search if data is not available based on filter.
	public ResponseEntity<Object> getCourseNoResultRecommendation(final Integer pageNumber, final Integer pageSize, final String facultyId,
			final String countryId, final String userCountry) throws ValidationException {
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<CourseResponseDto> courseResponseDtos = courseProcessor.getCourseNoResultRecommendation(userCountry,
				facultyId, countryId, startIndex, pageSize);
		return new GenericResponseHandlers.Builder().setData(courseResponseDtos)
				.setMessage("Get course list displayed successfully").setStatus(HttpStatus.OK).create();
	}

	// This API is used for course Info page for related course keyword recommendation.
	public ResponseEntity<Object> getCourseKeywordRecommendation(final Integer pageNumber, final Integer pageSize, final String facultyId,
			final String countryId, final String levelId) throws ValidationException {
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<String> courseResponseDtos = courseProcessor.getCourseKeywordRecommendation(facultyId, countryId, levelId,
				startIndex, pageSize);
		return new GenericResponseHandlers.Builder().setData(courseResponseDtos)
				.setMessage("Get course keyword recommendation list displayed successfully").setStatus(HttpStatus.OK)
				.create();
	}

	// This API is used for course Info page for related course keyword recommendation.
	public ResponseEntity<Object> getCheapestCourse(final Integer pageNumber, final Integer pageSize,final String facultyId,
			final String countryId, final String levelId, final String cityId) throws ValidationException {
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<CourseResponseDto> courseResponseDtos = userRecommendationService.getCheapestCourse(facultyId, countryId,
				levelId, cityId, startIndex, pageSize);
		return new GenericResponseHandlers.Builder().setData(courseResponseDtos)
				.setMessage("Get cheapest course recommendation list displayed successfully").setStatus(HttpStatus.OK)
				.create();
	}

	public ResponseEntity<Object> getCourseCountByLevel() {
		Map<String, Integer> courseCount = courseProcessor.getCourseCountByLevel();
		return new GenericResponseHandlers.Builder().setData(courseCount)
				.setMessage("Course count displayed successfully").setStatus(HttpStatus.OK).create();
	}
	
	public ResponseEntity<?> getDistinctCourses(final Integer pageNumber, final Integer pageSize, final String name) {
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		int totalCount = courseProcessor.getDistinctCourseCount(name);
		List<CourseResponseDto> courseList = courseProcessor.getDistinctCourseList(startIndex, pageSize,name);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto();
		paginationResponseDto.setResponse(courseList);
		paginationResponseDto.setTotalCount(totalCount);
		paginationResponseDto.setPageNumber(paginationUtilDto.getPageNumber());
		paginationResponseDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
		paginationResponseDto.setTotalPages(paginationUtilDto.getTotalPages());
		paginationResponseDto.setHasNextPage(paginationUtilDto.isHasNextPage());
		return new GenericResponseHandlers.Builder().setData(paginationResponseDto)
				.setMessage("Course displayed successfully").setStatus(HttpStatus.OK).create();
		
	}
	
	public ResponseEntity<?> addCourseViaMobile(final String userId, final String instituteId, CourseMobileDto courseMobileDto) throws Exception {
		courseProcessor.addMobileCourse(userId, instituteId, courseMobileDto);
		return new GenericResponseHandlers.Builder()
				.setMessage("Mobile course added successfully").setStatus(HttpStatus.OK)
				.create();
	}
	
	public ResponseEntity<?> updateCourseViaMobile(final String userId, final String courseId, CourseMobileDto courseMobileDto) throws Exception {
		courseProcessor.updateMobileCourse(userId, courseId, courseMobileDto);
		return new GenericResponseHandlers.Builder()
				.setMessage("Mobile course updated successfully").setStatus(HttpStatus.OK)
				.create();
	}
	
	public ResponseEntity<?> getCourseViaMobile(final String userId, final String instituteId, final String facultyId, final boolean status) throws Exception {
		List<CourseMobileDto> listOfMobileCourseResponseDto = courseProcessor.getAllMobileCourseByInstituteIdAndFacultyIdAndStatus(userId, instituteId, facultyId, status);
		return new GenericResponseHandlers.Builder().setData(listOfMobileCourseResponseDto)
				.setMessage("Mobile course fetched successfully").setStatus(HttpStatus.OK)
				.create();
	}

	public ResponseEntity<?> getCourseViaMobile(final String instituteId, final String facultyId) throws Exception {
		List<CourseMobileDto> listOfMobileCourseResponseDto = courseProcessor.getPublicMobileCourseByInstituteIdAndFacultyId(instituteId, facultyId);
		return new GenericResponseHandlers.Builder().setData(listOfMobileCourseResponseDto)
				.setMessage("Mobile course fetched successfully").setStatus(HttpStatus.OK)
				.create();
	}
	
	public ResponseEntity<?> changeStatus(final String userId, final String courseId, final boolean  status) throws Exception {
		courseProcessor.changeCourseStatus(userId, courseId, status);
		return new GenericResponseHandlers.Builder()
				.setMessage("course status changed successfully").setStatus(HttpStatus.OK)
				.create();
	}
	
	public ResponseEntity<?> getCourseByInstituteId(Integer pageNumber, Integer pageSize, final String instituteId) throws NotFoundException {
		log.info("Start process to get courses from DB based on instituteId");
		NearestCoursesDto nearestCourseList = courseProcessor.getCourseByInstituteId(pageNumber, pageSize, instituteId);
		return new GenericResponseHandlers.Builder().setData(nearestCourseList)
				.setMessage("Courses displayed successfully").setStatus(HttpStatus.OK)
				.create();
	}
	
	public ResponseEntity<?> getNearestCourseList(final AdvanceSearchDto courseSearchDto) throws Exception {
		log.info("Start process to get nearest course from DB");
		NearestCoursesDto courseResponseDtoList = courseProcessor.getNearestCourses(courseSearchDto);
		return new GenericResponseHandlers.Builder().setData(courseResponseDtoList).setMessage("Courses Displayed Successfully.")
				.setStatus(HttpStatus.OK).create();
	}
	
	public ResponseEntity<?> getCourseByCountryName(Integer pageNumber, Integer pageSize,
			String countryName) throws NotFoundException {
		NearestCoursesDto courseResponse = courseProcessor.getCourseByCountryName(countryName, pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setData(courseResponse)
				.setMessage("Courses displayed successfully").setStatus(HttpStatus.OK).create();
	}
	
	@Deprecated
	public ResponseEntity<?> getAllCourse() throws Exception {
		return ResponseEntity.accepted().body(courseProcessor.getAllCourse());
	}
	
	@Deprecated
	public ResponseEntity<?> getUserListForUserWatchCourseFilter(final String language, final String courseId, final String facultyId,
			final String instituteId, final String countryName, final String cityName) throws ValidationException {
		if (courseId == null && facultyId == null && instituteId == null && countryName == null && cityName == null) {
			throw new ValidationException(
					messageByLocalService.getMessage("specify.filter.parameters", new Object[] {}));
		}
		List<Long> userList = courseProcessor.getUserListForUserWatchCourseFilter(courseId, instituteId, facultyId,
				countryName, cityName);
		return new GenericResponseHandlers.Builder().setData(userList).setMessage("User List Displayed Successfully")
				.setStatus(HttpStatus.OK).create();
	}
	
	@Deprecated
	public ResponseEntity<?> updateGradeAndEnglishEligibility() throws Exception {
		Map<String, Object> response = new HashMap<>();
		List<Course> courseList = courseProcessor.getAll();
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
}