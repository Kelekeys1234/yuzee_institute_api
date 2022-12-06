
package com.yuzee.app.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import com.yuzee.app.dto.AdvanceSearchDto;
import com.yuzee.app.dto.CourseMobileDto;
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.dto.CourseSearchDto;
import com.yuzee.app.dto.CourseSearchFilterDto;
import com.yuzee.common.lib.dto.transaction.UserViewCourseDto;
import com.yuzee.common.lib.dto.user.UserInitialInfoDto;
import com.yuzee.common.lib.enumeration.EntitySubTypeEnum;
import com.yuzee.common.lib.enumeration.EntityTypeEnum;
import com.yuzee.common.lib.enumeration.StudentTypeEnum;
import com.yuzee.common.lib.enumeration.TransactionTypeEnum;
import com.yuzee.common.lib.handler.ApplicationHandler;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;
import com.yuzee.common.lib.handler.ReviewHandler;
import com.yuzee.common.lib.handler.StorageHandler;
import com.yuzee.common.lib.handler.UserHandler;
import com.yuzee.common.lib.handler.ViewTransactionHandler;

@TestInstance(Lifecycle.PER_CLASS)
public class CourseControllerTest extends CreateCourseAndInstitute {

	@Autowired
	private TestRestTemplate testRestTemplate;
	@MockBean
	protected PublishSystemEventHandler publishSystemEventHandler;
	@MockBean
	protected ViewTransactionHandler viewTransactionHandler;
	@MockBean
	protected StorageHandler storageHandler;

	@MockBean
	ApplicationHandler applicationHandler;
	@MockBean
	UserHandler userHandler;
	@MockBean
	private ReviewHandler reviewHandler;

	private String instituteId;
	private CourseRequest courseId;

	@BeforeAll
	public void createAllIntituteAndCourse() throws IOException {
		instituteId = testCreateInstitute();
		courseId = createCourses(instituteId);
	}
	// create institute

	@DisplayName("Update Courses")
	@Test
	void updateCourses() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseRequest> entity = new HttpEntity<>(courseId, headers);
		ResponseEntity<CourseRequest> response = testRestTemplate.exchange(COURSE_PATH + PATH_SEPARATOR + instituteId
				+ PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId.getId().toString(), HttpMethod.PUT, entity,
				CourseRequest.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName("DELETE Courses")
	@Test
	void DeleteCourse() throws IOException {
		/// delete Api
		Map<String, List<String>> param = new HashMap<>();
		List<String> ss = new ArrayList<>();
		ss.add("75218134-062d-41bd-b84d-eeb203cddbae");
		ss.add(instituteId);
		param.put("linked_course_ids", ss);
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("userId", userId);
		HttpEntity<String> entityer = new HttpEntity<>(header);
		ResponseEntity<String> responseds = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + courseId,
				HttpMethod.DELETE, entityer, String.class);
		assertThat(responseds.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName("getAllCourses")
	@Test
	void getAllCourses() {
		Integer pageNumber = 1;
		Integer pageSize = 1;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Mockito.when(storageHandler.getStorages(instituteId, EntityTypeEnum.INSTITUTE, EntitySubTypeEnum.IMAGES))
				.thenReturn(new ArrayList());
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + PAGE_NUMBER_PATH + PATH_SEPARATOR + pageNumber
						+ PATH_SEPARATOR + PAGE_SIZE_PATH + PATH_SEPARATOR + pageSize,
				HttpMethod.GET, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("getAutoSearchCourses")
	@Test
	void getAutoSearchCourses() {
		Mockito.when(storageHandler.getStorages(instituteId, EntityTypeEnum.INSTITUTE, EntitySubTypeEnum.IMAGES))
				.thenReturn(new ArrayList());
		Integer pageNumber = 1;
		Integer pageSize = 1;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate
				.exchange(
						COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "autoSearch" + PATH_SEPARATOR
								+ "coursename" + PATH_SEPARATOR + PAGE_NUMBER_PATH + PATH_SEPARATOR + pageNumber
								+ PATH_SEPARATOR + PAGE_SIZE_PATH + PATH_SEPARATOR + pageSize,
						HttpMethod.GET, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("WrongId deleteCourse")
	@Test
	void DeleteWrongIdCourse() {

		Map<String, List<String>> param = new HashMap<>();
		List<String> ss = new ArrayList<>();
		ss.add("75218134-062d-41bd-b84d-eeb203cddbae");
		ss.add(instituteId);
		param.put("linked_course_ids", ss);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entityer = new HttpEntity<>(headers);
		ResponseEntity<String> responseds = testRestTemplate
				.exchange(
						COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "45ab7d2a-2eb0-469a-b138-37d7f13"
								+ PATH_SEPARATOR + "45ab7d2a-2eb0-469a-b138-37d7f13",
						HttpMethod.DELETE, entityer, String.class);
		assertThat(responseds.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@DisplayName("course search")
	@Test
	void courseSearch() {
		CourseSearchFilterDto courseSearchFilterDto = new CourseSearchFilterDto("200", "10mins", "India", "recognition",
				"latesrCourse", "yuzee", "computer science ", "ranking");
		CourseSearchDto courseSearchDto = new CourseSearchDto();
		courseSearchDto.setSortingObj(courseSearchFilterDto);
		courseSearchDto.setSearchKey("coursename");
		courseSearchDto.setCourseName("courseName");
		courseSearchDto.setIsProfileSearch(true);
		courseSearchDto.setCurrencyId(instituteId);
		courseSearchDto.setUserId(userId);
		courseSearchDto.setCourseKeys(Arrays.asList("coursename"));
		courseSearchDto.setLevelIds(Arrays.asList(instituteId));
		courseSearchDto.setFacultyIds(Arrays.asList(entityId));
		courseSearchDto.setCountryNames(Arrays.asList("INDIA"));
		courseSearchDto.setServiceIds(Arrays.asList(entityId));
		courseSearchDto.setCityNames(Arrays.asList("INDIA"));
		courseSearchDto.setMaxCost(12.00);
		courseSearchDto.setMinCost(10.00);
		courseSearchDto.setMaxDuration(13);
		Mockito.when(viewTransactionHandler.getUserMyCourseByEntityTypeAndTransactionType(courseSearchDto.getUserId(),
				EntityTypeEnum.COURSE.name(), TransactionTypeEnum.FAVORITE.name())).thenReturn(new ArrayList());
		courseSearchDto.setMinDuration(17);
		courseSearchDto.setMaxSizePerPage(2);
		courseSearchDto.setPageNumber(5);
		courseSearchDto.setInstituteId(instituteId);
		courseSearchDto.setSortAscending(true);
		courseSearchDto.setSortBy("APIS");
		courseSearchDto.setCurrencyCode("DOLLARS");
		courseSearchDto.setDate("2022-07-25T11:23:11.311+00:00");
		courseSearchDto.setLatitude(15.00);
		courseSearchDto.setLongitude(23.00);
		courseSearchDto.setUserCountryName("NIGERIA");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseSearchDto> entity = new HttpEntity<>(courseSearchDto, headers);
		ResponseEntity<CourseSearchDto> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "search", HttpMethod.POST, entity,
				CourseSearchDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName(value = "advanceSearch")
	@Test
	void advanceSearch() {
		List<String> courseId = new ArrayList();
		courseId.add(COURSE_IDS);
		Mockito.when(storageHandler.getStorages(instituteId, EntityTypeEnum.INSTITUTE, EntitySubTypeEnum.IMAGES))
				.thenReturn(new ArrayList());
		AdvanceSearchDto advanceSearch = new AdvanceSearchDto();
		advanceSearch.setFaculties(Arrays.asList(instituteId));

		advanceSearch.setLevelIds(Arrays.asList(entityId));
		advanceSearch.setServiceIds(Arrays.asList(entityId));
		advanceSearch.setCountryNames(Arrays.asList("INDIA"));
		advanceSearch.setCourseKeys(Arrays.asList("coursename"));
		advanceSearch.setCityNames(Arrays.asList("INDIA"));
		advanceSearch.setMinCost(14.00);
		advanceSearch.setMaxCost(18.00);
		advanceSearch.setMinDuration(14);
		advanceSearch.setMaxDuration(23);
		advanceSearch.setSortAsscending(true);
		advanceSearch.setSortBy("coursename");
		advanceSearch.setMaxSizePerPage(2);
		advanceSearch.setPageNumber(1);
		advanceSearch.setCurrencyCode("ISO");
		advanceSearch.setUserId(userId);
		advanceSearch.setUserCountryName("NIGERIA");
		advanceSearch.setNames(Arrays.asList("name"));
		advanceSearch.setSearchKeyword("coursename");
		advanceSearch.setStudyModes(Arrays.asList("Online"));
		advanceSearch.setDeliveryMethods(Arrays.asList("deliveringMode"));
		advanceSearch.setInstituteId(instituteId);
		advanceSearch.setLongitude(12.00);
		advanceSearch.setInitialRadius(12);

		Mockito.when(viewTransactionHandler.getUserMyCourseByEntityTypeAndTransactionType(advanceSearch.getUserId(),
				EntityTypeEnum.COURSE.name(), TransactionTypeEnum.VIEW.name())).thenReturn(new ArrayList());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		headers.add("language", "ENGLISH");

		Mockito.when(reviewHandler.getAverageReview("COURSE", courseId)).thenReturn(new HashMap<>());

		HttpEntity<AdvanceSearchDto> entity = new HttpEntity<>(advanceSearch, headers);
		ResponseEntity<AdvanceSearchDto> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "advanceSearch", HttpMethod.POST, entity,
				AdvanceSearchDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName(value = "get")
	@Test
	void get() throws IOException {
		Mockito.when(reviewHandler.getAverageReview(EntityTypeEnum.COURSE.name(), Arrays.asList(courseId.getId())))
				.thenReturn(new HashMap<>());
		Mockito.when(viewTransactionHandler.getUserViewedCourseByEntityIdAndTransactionType(userId,
				EntityTypeEnum.COURSE.name(), courseId.getId(), TransactionTypeEnum.FAVORITE.name()))
				.thenReturn(new UserViewCourseDto());
		Mockito.when(storageHandler.getStorages(Arrays.asList(courseId.getId()), EntityTypeEnum.COURSE,
				Arrays.asList(EntitySubTypeEnum.LOGO, EntitySubTypeEnum.COVER_PHOTO, EntitySubTypeEnum.MEDIA)))
				.thenReturn(new ArrayList<>());
		// Get Api)
		Map<String, String> param = new HashMap<>();
		param.put("is_readable_id", "coursename-Ow9NR9y");
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("userId", userId);
		HttpEntity<String> entitys = new HttpEntity<>(null, header);
		ResponseEntity<Object> responses = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId.getId(), HttpMethod.GET, entitys,
				Object.class, param);
		assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName("getAllInstituteById")
	@Test
	void getAllCourseByInstituteID() throws IOException {
		Mockito.when(storageHandler.getStorages(instituteId, EntityTypeEnum.INSTITUTE, EntitySubTypeEnum.IMAGES))
				.thenReturn(new ArrayList());
		CourseSearchFilterDto courseSearchFilterDto = new CourseSearchFilterDto("200", "10mins", "India", "recognition",
				"latesrCourse", "yuzee", "computer science ", "ranking");
		CourseSearchDto courseSearchDto = new CourseSearchDto();
		courseSearchDto.setSortingObj(courseSearchFilterDto);
		courseSearchDto.setSearchKey("coursename");
		courseSearchDto.setCourseName("courseName");
		courseSearchDto.setIsProfileSearch(true);
		courseSearchDto.setCurrencyId(instituteId);
		courseSearchDto.setUserId(userId);
		courseSearchDto.setCourseKeys(Arrays.asList("coursename"));
		courseSearchDto.setLevelIds(Arrays.asList(instituteId));
		courseSearchDto.setFacultyIds(Arrays.asList(entityId));
		courseSearchDto.setCountryNames(Arrays.asList("INDIA"));
		courseSearchDto.setServiceIds(Arrays.asList(entityId));
		courseSearchDto.setCityNames(Arrays.asList("INDIA"));
		courseSearchDto.setMaxCost(12.00);
		courseSearchDto.setMinCost(10.00);
		courseSearchDto.setMaxDuration(13);
		Mockito.when(viewTransactionHandler.getUserMyCourseByEntityTypeAndTransactionType(courseSearchDto.getUserId(),
				EntityTypeEnum.COURSE.name(), TransactionTypeEnum.FAVORITE.name())).thenReturn(new ArrayList());
		courseSearchDto.setMinDuration(17);
		courseSearchDto.setMaxSizePerPage(2);
		courseSearchDto.setPageNumber(5);
		courseSearchDto.setInstituteId(instituteId);
		courseSearchDto.setSortAscending(true);
		courseSearchDto.setSortBy("APIS");
		courseSearchDto.setCurrencyCode("DOLLARS");
		courseSearchDto.setDate("2022-07-25T11:23:11.311+00:00");
		courseSearchDto.setLatitude(15.00);
		courseSearchDto.setLongitude(23.00);
		courseSearchDto.setUserCountryName("NIGERIA");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseSearchDto> entity = new HttpEntity<>(courseSearchDto, headers);
		ResponseEntity<CourseSearchDto> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "institute" + PATH_SEPARATOR + instituteId,
				HttpMethod.PUT, entity, CourseSearchDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName(value = "searchKeyword")
	@Test
	void searchCourseKeyword() throws IOException {
		CourseControllerTest course = new CourseControllerTest();
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entitys = new HttpEntity<>(header);
		ResponseEntity<String> responses = testRestTemplate.exchange(COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR
				+ "keyword" + PATH_SEPARATOR + "coursename?keyword=" + courseId.getId(), HttpMethod.GET, entitys,
				String.class);
		assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

//	//// faculty is yet to be created
	@DisplayName(value = "GetCourseByFacultyId")
	@Test
	void getCourseByFacultyId() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR
				+ "faculty" + PATH_SEPARATOR + courseId.getFacultyId(), HttpMethod.GET, entity, String.class);
		// assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName(value = "GetCourseByFacultyId")
	@Test
	void wrongIdCourseByFacultyId() throws RestClientException, IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = testRestTemplate.exchange(COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR
				+ "faculty" + PATH_SEPARATOR + "fdfbkjb66n6774m767u90g8g", HttpMethod.GET, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}

	@DisplayName(value = "getUserCourse")
	@Test
	void getUserCourse() throws IOException {
		// api begin
		List<String> courseIds = new ArrayList<>();
		courseIds.add(courseId.getId());
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entitys = new HttpEntity<>(header);
		ResponseEntity<String> responses = testRestTemplate
				.exchange(
						COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "user" + PATH_SEPARATOR + courseId.getId()
								+ PATH_SEPARATOR + "CITY" + PATH_SEPARATOR + "true",
						HttpMethod.GET, entitys, String.class);
		assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName(value = "courseFilter")
	@Test
	void courseFilter() {
		CourseSearchFilterDto courseSearchFilterDto = new CourseSearchFilterDto("200", "10mins", "India", "recognition",
				"latesrCourse", "yuzee", "computer science ", "ranking");
		Mockito.when(userHandler.getUserById(userId)).thenReturn(new UserInitialInfoDto());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		headers.add("language", "in");
		HttpEntity<CourseSearchFilterDto> entity = new HttpEntity<>(courseSearchFilterDto, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "filter", HttpMethod.POST, entity, String.class);
		// assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName(value = "searchByCharacter")
	@Test
	void testAutoSearchByCharacter() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = testRestTemplate.exchange(COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR
				+ "autoSearch" + PATH_SEPARATOR + "mycourseTestnnhi", HttpMethod.GET, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName(value = "getCourseNoResultRecommendation")
	@Test
	void getWrongCourseNoResultRecommendation() {
		Integer pageNumber = 1;
		Integer pageSize = 1;

		Map<String, String> params = new HashMap<>();
		params.put("facultyId", entityId);
		params.put("countryId", instituteId);
		params.put("userCountry", "INDIA");

	}

	@DisplayName(value = "getCourseNoResultRecommendation")
	@Test
	void getCourseKeywordRecommendation() {
		Integer pageNumber = 1;
		Integer pageSize = 1;

		Map<String, String> params = new HashMap<>();
		params.put("facultyId", entityId);
		params.put("countryId", instituteId);
		params.put("userCountry", "INDIA");
		params.put("levelId", instituteId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate
				.exchange(
						COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "keyword" + PATH_SEPARATOR
								+ "recommendatation" + PATH_SEPARATOR + "pageNumber" + PATH_SEPARATOR + pageNumber
								+ PATH_SEPARATOR + "pageSize" + PATH_SEPARATOR + pageSize,
						HttpMethod.GET, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	void getCourseNoResultRecommendation() {
		Integer pageNumber = 1;
		Integer pageSize = 1;

		Map<String, String> params = new HashMap<>();
		params.put("facultyId", entityId);
		params.put("countryId", instituteId);
		params.put("userCountry", "INDIA");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "noResult" + PATH_SEPARATOR + "pageNumber"
						+ PATH_SEPARATOR + 1 + PATH_SEPARATOR + "pageSize" + PATH_SEPARATOR + 1,
				HttpMethod.GET, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName(value = "getCheapestCourse")
	@Test
	void getCheapestCourse() {
		Integer pageNumber = 1;
		Integer pageSize = 1;

//		Map<String, String> params = new HashMap<>();
//		params.put("facultyId", entityId);
//		params.put("countryId", instituteId);
//		params.put("userCountry", "INDIA");
//		params.put("levelId", instituteId);
//		params.put("cityId", entityId);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		HttpEntity<String> entity = new HttpEntity<>(null, headers);
//		ResponseEntity<String> response = testRestTemplate.exchange(
//				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "cheapest" + PATH_SEPARATOR + "pageNumber"
//						+ PATH_SEPARATOR + pageNumber + PATH_SEPARATOR + "pageSize" + PATH_SEPARATOR + pageSize,
//				HttpMethod.GET, entity, String.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "getCourseCountByLevel")
	@Test
	void getCourseCountByLevel() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "getCourseCountByLevel", HttpMethod.GET, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "getCourseCountByLevel")
	@Test
	void SearchCourses() {
		Map<String, String> params = new HashMap<>();
		params.put("countryIds", COURSE_IDS);

	}

	@DisplayName(value = "getCourseCountByLevel")
	@Test
	void getDistinctCourses() {
		Integer pageNumber = 1;
		Integer pageSize = 1;

		Map<String, String> params = new HashMap<>();

		params.put("name", "name");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR
				+ "names" + PATH_SEPARATOR + "distinct" + PATH_SEPARATOR + "pageNumber" + PATH_SEPARATOR + pageNumber
				+ PATH_SEPARATOR + "pageSize" + PATH_SEPARATOR + pageSize, HttpMethod.GET, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "WrongFacultyIdMobileWrongId")
	@Test
	void wrongIdFacultyIdViaMobile() throws RestClientException, IOException {
		CourseMobileDto courseMobileDto = new CourseMobileDto("829af0d4-8b28-4f8b-82b1-7b32f1308967", "coursename",
				"course Description", "5af8b74f-9f04-45dc-81c5-360191", "facultyName", 3.5, 2.5, 3.5, 6.5,
				"durationUnit");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);

		HttpEntity<CourseMobileDto> entity = new HttpEntity<>(courseMobileDto, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "mobile" + PATH_SEPARATOR + courseId.getId(),
				HttpMethod.POST, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}

	@DisplayName(value = "addCourseMobileWrongId")
	@Test
	void wrongIdCourseViaMobile() throws IOException {
		//// api begins
		CourseMobileDto courseMobileDto = new CourseMobileDto("45ab7d2a-2eb0-469a-b138-37d7f133872b",
				"coursename-test-W5F0Bv9", "course Description", "5af8b74f-9f04-45dc-81c5-36019590105c", "facultyName",
				3.5, 2.5, 3.5, 6.5, "durationUnit");
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("userId", userId);

		HttpEntity<CourseMobileDto> entitys = new HttpEntity<>(courseMobileDto, header);
		ResponseEntity<String> responses = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "mobile" + PATH_SEPARATOR + courseId.getId(),
				HttpMethod.POST, entitys, String.class);
		assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}

///////RUN AFTER FACULTY DB IS CREATED
	@DisplayName(value = "addCourseViaMobile")
	@Test
	void addCourseViaMobile() throws IOException {
		CourseMobileDto courseMobileDto = new CourseMobileDto(courseId.getId(), "mycourseTestnnhi",
				"course Description", courseId.getFacultyId(), "facultyName", 9.5, 3.5, 3.5, 8.5, "durationUnit");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);

		HttpEntity<CourseMobileDto> entity = new HttpEntity<>(courseMobileDto, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "mobile" + PATH_SEPARATOR + instituteId,
				HttpMethod.POST, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "WrongIdupdateCourseMobile")
	@Test
	void wrongIdupdateCourseMobile() {
		CourseMobileDto courseMobileDto = new CourseMobileDto("829af0d4-8b28-4f8b-82b1-7b32f1308967", "coursename",
				"course Description", "5af8b74f-9f04-45dc-81c5-36019590105c", "facultyName", 9.5, 3.5, 3.5, 7.5,
				"durationUnit");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);

		HttpEntity<CourseMobileDto> entity = new HttpEntity<>(courseMobileDto, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR
				+ "mobile" + PATH_SEPARATOR + "39d3f31e-64fb-46eb-babc-4ef6", HttpMethod.PUT, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@DisplayName(value = "getCourseViaMobile")
	@Test
	void getWrongCourseViaMobile() {

		CourseMobileDto courseMobileDto = new CourseMobileDto("7132d88e-cf2c-4f48-ac6e-82214208f677", "coursename",
				"course Description", entityId, "facultyName", 3.0, 33.00, 1.22, 22.5, "durationUnit");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);

		HttpEntity<CourseMobileDto> entity = new HttpEntity<>(courseMobileDto, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR
				+ "mobile" + PATH_SEPARATOR
				+ "39d3f31e-64fb-46eb-babc-c54efa69e091?faculty_id=5af8b74f-9f04-45dc-81c5-36019590105c&status=true",
				HttpMethod.GET, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}

	@DisplayName(value = "changeStatus")
	@Test
	void changeStatus() throws IOException {
		Boolean status = true;
		Map<String, Boolean> params = new HashMap<>();
		params.put("Status", status);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "status" + PATH_SEPARATOR + courseId.getId(),
				HttpMethod.PUT, entity, String.class, params);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "getCourseByInstituteId")
	@Test
	void getCourseByInstituteId() throws IOException {
		Integer pageNumber = 1;
		Integer pageSize = 1;

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entitys = new HttpEntity<>(null, header);
		ResponseEntity<String> responses = testRestTemplate
				.exchange(
						COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "institute" + PATH_SEPARATOR
								+ "pageNumber" + PATH_SEPARATOR + pageNumber + PATH_SEPARATOR + "pageSize"
								+ PATH_SEPARATOR + pageSize + PATH_SEPARATOR + courseId.getInstituteId(),
						HttpMethod.GET, entitys, String.class);
		assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName(value = "getCourseByInstituteIdWrongId")
	@Test
	void wrongIdgetCourseByInstituteId() {
		Integer pageNumber = 1;
		Integer pageSize = 1;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate
				.exchange(
						COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "institute" + PATH_SEPARATOR
								+ "pageNumber" + PATH_SEPARATOR + pageNumber + PATH_SEPARATOR + "pageSize"
								+ PATH_SEPARATOR + pageSize + PATH_SEPARATOR + "39d3f31e-64fb-46eb-babc-c54efa61",
						HttpMethod.GET, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@DisplayName(value = "getNearestCourseList")
	@Test
	void getCourseByCountryName() throws IOException {
		Integer pageNumber = 1;
		Integer pageSize = 1;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		Mockito.when(storageHandler.getStorages(courseId.getId(), EntityTypeEnum.COURSE, EntitySubTypeEnum.LOGO))
				.thenReturn(new ArrayList());
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "pageNumber" + PATH_SEPARATOR + pageNumber
						+ PATH_SEPARATOR + "pageSize" + PATH_SEPARATOR + pageSize + PATH_SEPARATOR + "INDIA",
				HttpMethod.POST, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

//	@DisplayName(value = "getNearestCourseList")
//	@Test
//	 void getNearestCourseList() {
//				
//		AdvanceSearchDto advanceSearch = new AdvanceSearchDto();
//		advanceSearch.setFaculties(Arrays.asList(instituteId));
//
//		advanceSearch.setLevelIds(Arrays.asList(entityId));
//		advanceSearch.setServiceIds(Arrays.asList(entityId));
//		advanceSearch.setCountryNames(Arrays.asList("INDIA"));
//		advanceSearch.setCourseKeys(Arrays.asList("coursename"));
//		advanceSearch.setCityNames(Arrays.asList("INDIA"));
//		advanceSearch.setMinCost(14.00);
//		advanceSearch.setMaxCost(18.00);
//		advanceSearch.setMinDuration(14);
//		advanceSearch.setMaxDuration(23);
//		advanceSearch.setSortAsscending(true);
//		advanceSearch.setSortBy("coursename");
//		advanceSearch.setMaxSizePerPage(2);
//		advanceSearch.setPageNumber(1);
//		advanceSearch.setCurrencyCode("ISO");
//		advanceSearch.setUserId(userId);
//		advanceSearch.setUserCountryName("NIGERIA");
//		advanceSearch.setNames(Arrays.asList("name"));
//		advanceSearch.setSearchKeyword("coursename");
//		advanceSearch.setStudyModes(Arrays.asList("Online"));
//		advanceSearch.setDeliveryMethods(Arrays.asList("deliveringMode"));
//		advanceSearch.setInstituteId(instituteId);
//		advanceSearch.setLongitude(12.00);
//		advanceSearch.setLatitude(15.00);
//		advanceSearch.setInitialRadius(12);
//		
//		Mockito.when(storageHandler.getStorages(instituteId, EntityTypeEnum.COURSE, EntitySubTypeEnum.LOGO))
//		.thenReturn(new ArrayList());
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		HttpEntity<AdvanceSearchDto> entity = new HttpEntity<>(advanceSearch, headers);
//		ResponseEntity<String> response = testRestTemplate.exchange(
//				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "nearest", HttpMethod.POST, entity, String.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//	}

	@DisplayName(value = "getCourseByIds")
	@Test
	void getCourseByIds() throws RestClientException, IOException {
		List<String> course = Arrays.asList(courseId.getId());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<List<String>> entity = new HttpEntity<>(course, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "courseIds", HttpMethod.POST, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "getRecommendateCourses")
	@Test
	void getRecommendateCourses() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR
				+ "recommendation" + PATH_SEPARATOR + courseId.getId(), HttpMethod.GET, entity, String.class);
	}

	@DisplayName(value = "getRecommendateCourses")
	@Test
	void getRelatedCourses() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "related" + PATH_SEPARATOR + courseId.getId(),
				HttpMethod.GET, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "getRecommendateCourses")
	@Test
	void getCourseCountByInstituteId() throws RestClientException, IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "count" + PATH_SEPARATOR + courseId.getId(),
				HttpMethod.GET, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "saveBasiCourse")
	@Test
	void saveBasiCourse() throws RestClientException, IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseRequest> entity = new HttpEntity<>(courseId, headers);
		ResponseEntity<CourseRequest> response = testRestTemplate.exchange(COURSE_PATH + PATH_SEPARATOR + instituteId
				+ PATH_SEPARATOR + "course" + PATH_SEPARATOR + "basic" + PATH_SEPARATOR + "info", HttpMethod.POST,
				entity, CourseRequest.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName(value = "updateBasiCourse")
	@Test
	void updateBasiCourse() throws RestClientException, IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseRequest> entity = new HttpEntity<>(courseId, headers);
		ResponseEntity<CourseRequest> response = testRestTemplate.exchange(
				COURSE_PATH + PATH_SEPARATOR + instituteId + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "basic"
						+ PATH_SEPARATOR + "info" + PATH_SEPARATOR + courseId.getId(),
				HttpMethod.PUT, entity, CourseRequest.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName(value = "updateProcedureIdInCourseINTERNATIONAL")
	@Test
	void updateProcedureIdInCourseInternational() {
		Map<String, String> params = new HashMap<>();
		params.put("courseIds", COURSE_IDS);
		params.put("studentType", StudentTypeEnum.INTERNATIONAL.toString());
		params.put("procedureId", entityId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "procedure_id?student_type=INTERNATIONAL",
				HttpMethod.PUT, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "updateProcedureIdInCourse")
	@Test
	void updateProcedureIdInCourse() {
		Map<String, String> params = new HashMap<>();
		params.put("courseIds", COURSE_IDS);
		params.put("studentType", StudentTypeEnum.INTERNATIONAL.toString());
		params.put("procedureId", entityId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "procedure_id?student_type=DOMESTIC",
				HttpMethod.PUT, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "updateProcedureIdInInstituteId")
	@Test
	void updateProcedureIdInIstituteId() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "procedure_id" + PATH_SEPARATOR + "institute_id"
						+ PATH_SEPARATOR + "39d3f31e-64fb-46eb-babc-c54efa69e091" + PATH_SEPARATOR + "INTERNATIONAL"
						+ PATH_SEPARATOR + "8d7c017d-37e3-4317-a8b5-9ae6d9cdcb55",
				HttpMethod.PUT, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "updateProcedureIdInInstituteIdDomestic")
	@Test
	void updateProcedureIdInIstituteIddomestic() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "procedure_id" + PATH_SEPARATOR + "institute_id"
						+ PATH_SEPARATOR + "39d3f31e-64fb-46eb-babc-c54efa69e091" + PATH_SEPARATOR + "DOMESTIC"
						+ PATH_SEPARATOR + "71226418-b086-4514-91aa-54b86096c468",
				HttpMethod.PUT, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName(value = "publishDraftCourse")
	@Test
	void publishDraftCourse() throws RestClientException, IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = testRestTemplate.exchange(COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR

				+ "draft" + PATH_SEPARATOR + "publish" + PATH_SEPARATOR + courseId.getId(), HttpMethod.POST, entity,

				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "getDreaftcourse")
	@Test
	void getDreaftcourse() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = testRestTemplate.exchange(COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR
				+ "draft" + PATH_SEPARATOR + "pageNumber" + PATH_SEPARATOR + 1 + PATH_SEPARATOR + "pageSize"
				+ PATH_SEPARATOR + 1 + PATH_SEPARATOR + "IIM" + PATH_SEPARATOR + instituteId, HttpMethod.GET, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "disCardDreaftcourseWrongid")
	@Test
	void WrongIddisCardDreaftcourse() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = testRestTemplate.exchange(COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR
				+ "draft" + PATH_SEPARATOR + "829af0d4-8b28-4f8b-82b1-7b32f130897", HttpMethod.DELETE, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

}
