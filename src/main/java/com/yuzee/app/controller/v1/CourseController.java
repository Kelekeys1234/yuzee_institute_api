package com.yuzee.app.controller.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.bean.CourseKeywords;
import com.yuzee.app.dto.AdvanceSearchDto;
import com.yuzee.app.dto.CourseCountDto;
import com.yuzee.app.dto.CourseDto;
import com.yuzee.app.dto.CourseFilterDto;
import com.yuzee.app.dto.CourseMobileDto;
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.dto.CourseResponseDto;
import com.yuzee.app.dto.CourseSearchDto;
import com.yuzee.app.dto.InstituteResponseDto;
import com.yuzee.app.dto.NearestCoursesDto;
import com.yuzee.app.dto.PaginationDto;
import com.yuzee.app.dto.PaginationResponseDto;
import com.yuzee.app.dto.PaginationUtilDto;
import com.yuzee.app.dto.StorageDto;
import com.yuzee.app.dto.UserDto;
import com.yuzee.app.dto.UserMyCourseDto;
import com.yuzee.app.endpoint.CourseInterface;
import com.yuzee.app.enumeration.EntitySubTypeEnum;
import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.enumeration.TransactionTypeEnum;
import com.yuzee.app.exception.CommonInvokeException;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.handler.IdentityHandler;
import com.yuzee.app.handler.StorageHandler;
import com.yuzee.app.handler.ViewTransactionHandler;
import com.yuzee.app.message.MessageByLocaleService;
import com.yuzee.app.processor.CourseKeywordProcessor;
import com.yuzee.app.processor.CourseProcessor;
import com.yuzee.app.processor.InstituteProcessor;
import com.yuzee.app.service.UserRecommendationService;
import com.yuzee.app.util.PaginationUtil;
import com.yuzee.app.util.ValidationUtil;

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
	private UserRecommendationService userRecommendationService;

	@Autowired
	private StorageHandler storageHandler;

	@Autowired
	private MessageByLocaleService messageByLocalService;

	@Autowired
	private IdentityHandler identityHandler;
	
	@Autowired
	private ViewTransactionHandler viewTransactionHandler;

	public ResponseEntity<?> save(final String userId, String instituteId, final CourseRequest course)
			throws ValidationException, CommonInvokeException, NotFoundException, ForbiddenException {
		log.info("Start process to save new course in DB");
		ValidationUtil.validateTimingDtoFromCourseRequest(course);
		String courseId = courseProcessor.saveCourse(userId, instituteId, course);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(courseId)
				.setMessage("Course Created successfully").create();
	}

	@Override
	public ResponseEntity<?> saveBasicCourse(final String userId, String instituteId, final CourseRequest course)
			throws ValidationException, CommonInvokeException, NotFoundException, ForbiddenException {
		log.info("Start process to save new course basic details in DB");
		String courseId = courseProcessor.saveOrUpdateBasicCourse(userId, instituteId, course, null);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(courseId)
				.setMessage("Course Created successfully").create();
	}

	public ResponseEntity<?> update(final String userId, String instituteId, final CourseRequest course, final String id)
			throws ValidationException, CommonInvokeException, NotFoundException, ForbiddenException {
		log.info("Start process to update existing course in DB");
		ValidationUtil.validateTimingDtoFromCourseRequest(course);
		String courseId = courseProcessor.updateCourse(userId, instituteId, course, id);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(courseId)
				.setMessage("Course Updated successfully").create();
	}

	@Override
	public ResponseEntity<?> updateBasicCourse(final String userId, String instituteId, final CourseRequest course, final String id)
			throws ValidationException, CommonInvokeException, NotFoundException, ForbiddenException {
		log.info("Start process to update existing basic course in DB");
		String courseId = courseProcessor.saveOrUpdateBasicCourse(userId,instituteId, course, id);
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

	public ResponseEntity<?> delete(final String userId, final String id) throws ForbiddenException, NotFoundException{
		log.info("Start process to delete existing course from DB");
		courseProcessor.deleteCourse(userId, id);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Courses Deleted successfully").create();
	}

	public ResponseEntity<?> searchCourse(final Integer pageNumber, final Integer pageSize, final List<String> countryIds, final String instituteId, 
			final List<String> facultyIds, final List<String> cityIds, final List<String> levelIds, final List<String> serviceIds, final Double minCost, 
			final Double maxCost, final Integer minDuration, final Integer maxDuration, final String courseName, final String currencyCode, 
			final String searchKeyword, final String sortBy, final boolean sortAsscending, final String userId, final String date) 
			throws ValidationException, InvokeException, NotFoundException {
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
			throws ValidationException, InvokeException, NotFoundException {
		int startIndex = PaginationUtil.getStartIndex(courseSearchDto.getPageNumber(), courseSearchDto.getMaxSizePerPage());
		
		log.info("Calling view transaction service to fetch user my course data");
		List<UserMyCourseDto> userMyCourseDtos = viewTransactionHandler.getUserMyCourseByEntityIdAndTransactionType(courseSearchDto.getUserId(), 
				EntityTypeEnum.COURSE.name(), TransactionTypeEnum.FAVORITE.name());
		List<String> entityIds = userMyCourseDtos.stream().map(UserMyCourseDto::getEntityId).collect(Collectors.toList());
		
		List<CourseResponseDto> courseList = courseProcessor.getAllCoursesByFilter(courseSearchDto, startIndex, courseSearchDto.getMaxSizePerPage(), 
					searchKeyword, entityIds);
		int totalCount = courseProcessor.getCountforNormalCourse(courseSearchDto, searchKeyword, entityIds);
		
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
		
		log.info("Calling view transaction service to fetch user my course data");
		List<UserMyCourseDto> userMyCourseDtos = viewTransactionHandler.getUserMyCourseByEntityIdAndTransactionType(courseSearchDto.getUserId(), 
				EntityTypeEnum.COURSE.name(), TransactionTypeEnum.FAVORITE.name());
		List<String> entityIds = userMyCourseDtos.stream().map(UserMyCourseDto::getEntityId).collect(Collectors.toList());
		
		List<CourseResponseDto> courseList = courseProcessor.advanceSearch(courseSearchDto, entityIds);
		int totalCount = courseProcessor.getCountOfAdvanceSearch(courseSearchDto, entityIds);
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
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Courses Displayed successfully")
				.setData(courseProcessor.getCourseById(userId, id)).create();
	}

	public ResponseEntity<?> getAllCourseByInstituteID(final String instituteId, final CourseSearchDto request) throws Exception {
		Map<String, Object> response = new HashMap<>();

		InstituteResponseDto instituteResponseDto = instituteProcessor.getInstituteByID(instituteId);
		if (null == instituteResponseDto) {
			throw new NotFoundException("No Institute found in DB for instituteId = "+instituteId);
		}
		List<StorageDto> storageDTOList = storageHandler.getStorages(instituteResponseDto.getId(), EntityTypeEnum.INSTITUTE,EntitySubTypeEnum.IMAGES);
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

	public ResponseEntity<?> getUserCourses(final List<String> courseIds,final String sortBy, final String sortAsscending) 
			throws ValidationException, CommonInvokeException {
		log.info("Start process to get user course from DB based on pagination and userID");
		List<CourseDto> courses = courseProcessor.getUserCourse(courseIds, sortBy, sortAsscending);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(courses)
				.setMessage("User Courses displayed successfully").create();
	}

	public ResponseEntity<?> courseFilter(final String userId, String language, final CourseFilterDto courseFilter) throws Exception {
		//Get userCountry Based on userId
		UserDto userDto = identityHandler.getUserById(userId);
		if (userDto == null) {
			throw new NotFoundException(
					messageByLocalService.getMessage("user.not.found", new Object[] { userId }, language));
		} else if (userDto.getCitizenship() == null || userDto.getCitizenship().isEmpty()) {
			throw new ValidationException(messageByLocalService.getMessage("user.citizenship.not.present",
					new Object[] { userId }, language));
		}

		courseFilter.setUserCountryName(userDto.getCitizenship());
		PaginationResponseDto paginationResponseDto = courseProcessor.courseFilter(courseFilter);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(paginationResponseDto)
				.setMessage("Course Displayed Successfully").create();
	}

	public ResponseEntity<?> autoSearchByCharacter(final String searchKey) throws Exception {
		List<CourseRequest> courses = courseProcessor.autoSearchByCharacter(searchKey);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Courses fetched successfully").setData(courses).create();
	}

	// This API is used when in normal or global search if data is not available based on filter.
	public ResponseEntity<Object> getCourseNoResultRecommendation(final Integer pageNumber, final Integer pageSize, final String facultyId,
			final String countryId, final String userCountry) throws ValidationException, InvokeException, NotFoundException {
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
	
	@Override
	public ResponseEntity<?> getCourseByIds(List<String> courseIds) {
		List<CourseDto> courseDtos = courseProcessor.getCourseByMultipleId(courseIds);
		return new GenericResponseHandlers.Builder().setData(courseDtos).setMessage("Course List Displayed Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getRecommendateCourses(String courseId) throws ValidationException {
		List<CourseResponseDto> recommendCourse = userRecommendationService.getCourseRecommended(courseId);
		return new GenericResponseHandlers.Builder().setData(recommendCourse).setMessage("Recommendate Courses Displayed Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getRelatedCourses(String courseId) throws ValidationException {
		List<CourseResponseDto> relatedCourse = userRecommendationService.getCourseRelated(courseId);
		return new GenericResponseHandlers.Builder().setData(relatedCourse).setMessage("Related Courses Displayed Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getCourseCountByInstituteId(String instituteId) throws ValidationException {
		CourseCountDto courseCountDto  = courseProcessor.getCourseCountByInstitute(instituteId);
		return new GenericResponseHandlers.Builder().setData(courseCountDto).setMessage("Course count fetched successfully")
				.setStatus(HttpStatus.OK).create();
	}
}