package testController;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.yuzee.app.YuzeeApplication;
import com.yuzee.app.dto.AdvanceSearchDto;
import com.yuzee.app.dto.CourseIntakeDto;
import com.yuzee.app.dto.CourseMobileDto;
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.dto.CourseSearchDto;
import com.yuzee.app.dto.CourseSearchFilterDto;
import com.yuzee.app.dto.DayTimingDto;
import com.yuzee.app.dto.TimingRequestDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.common.lib.dto.institute.CourseContactPersonDto;
import com.yuzee.common.lib.dto.institute.CourseMinRequirementDto;
import com.yuzee.common.lib.dto.institute.CourseMinRequirementSubjectDto;
import com.yuzee.common.lib.dto.institute.CourseSemesterDto;
import com.yuzee.common.lib.dto.institute.EducationSystemDto;
import com.yuzee.common.lib.dto.institute.FacultyDto;
import com.yuzee.common.lib.dto.institute.GradeDto;
import com.yuzee.common.lib.dto.institute.LevelDto;
import com.yuzee.common.lib.dto.institute.ProviderCodeDto;
import com.yuzee.common.lib.dto.institute.SemesterSubjectDto;
import com.yuzee.common.lib.dto.institute.SubjectDto;
import com.yuzee.common.lib.dto.user.UserInitialInfoDto;
import com.yuzee.common.lib.enumeration.EntitySubTypeEnum;
import com.yuzee.common.lib.enumeration.EntityTypeEnum;
import com.yuzee.common.lib.enumeration.GradeType;
import com.yuzee.common.lib.enumeration.StudentTypeEnum;
import com.yuzee.common.lib.enumeration.TransactionTypeEnum;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;
import com.yuzee.common.lib.handler.StorageHandler;
import com.yuzee.common.lib.handler.UserHandler;
import com.yuzee.common.lib.handler.ViewTransactionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)
public class CourseController {
	private static final String entityId = UUID.randomUUID().toString();
	private static final String instituteId = "1e348e15-45b6-477f-a457-883738227e05";
	private static final String INSTITUTE_ID = "instituteId";
	private static final String userId = "8d7c017d-37e3-4317-a8b5-9ae6d9cdcb49";
	private static final String USER_ID = "userId";
	private static final String COURSE = "/api/v1";
	private static final String COURSE_PATH = COURSE + "/institute";
	private static final String PATH_SEPARATOR = "/";
	private static final String PAGE_NUMBER_PATH = "/pageNumber";
	private static final String PAGE_SIZE_PATH = "/pageSize";
	private static final String COURSE_IDS = "7132d88e-cf2c-4f48-ac6e-82214208f677";

	@Autowired
	private TestRestTemplate testRestTemplate;
	@MockBean
	private PublishSystemEventHandler publishSystemEventHandler;
	@MockBean
	ViewTransactionHandler viewTransactionHandler;
	@MockBean
	StorageHandler storageHandler;
	@MockBean
	UserHandler userHandler;

	@Test
	@DisplayName("save course")
	public void AddCourses() {

		ValidList<CourseMinRequirementDto> courseMinRequirementDtos = new ValidList<>();
		ValidList<CourseContactPersonDto> courseContactPersons = new ValidList<>();
		ValidList<CourseMinRequirementSubjectDto> minRequirementSubjects = new ValidList<>();
		com.yuzee.common.lib.dto.ValidList<SemesterSubjectDto> semesterSubjectDtos = null;
		ValidList<TimingRequestDto> courseTimings = new ValidList<>();
		ValidList<CourseSemesterDto> courseSemesters = new ValidList<>();

		List<SubjectDto> subject = new ArrayList<>();
		List<GradeDto> grades = new ArrayList<>();
		List<Date> date = new ArrayList<>();
		List<String> ids = new ArrayList<>();
		List<DayTimingDto> timings = new ArrayList<>();
		DayTimingDto dayTimingDto = new DayTimingDto("from", "to", "day");
		timings.add(dayTimingDto);
		ids.add(INSTITUTE_ID);
		date.add(new Date());
		Set<String> studyLanguages = new HashSet<>();
		studyLanguages.add("English");

		TimingRequestDto timing = new TimingRequestDto(entityId, timings, "LECTURES", "COURSE", instituteId);
		courseTimings.add(timing);
		SemesterSubjectDto semesterSubjectDto = new SemesterSubjectDto( "name", "description");

		CourseSemesterDto courseSemesterDto = new CourseSemesterDto(entityId, "type", "name", "description",
				semesterSubjectDtos);
		courseSemesters.add(courseSemesterDto);
		CourseIntakeDto courseIntakeDto = new CourseIntakeDto(entityId, new Date());

		UserInitialInfoDto userInitialInfoDto = new UserInitialInfoDto();

		

		LevelDto levelDto = new LevelDto(instituteId, "levelName", "levelCode", "levelCategories", "levelDescriptions",
				12345);

		CourseMinRequirementSubjectDto courseMinRequirementSubjectDto = new CourseMinRequirementSubjectDto(entityId,
				"name", "Grade");
		minRequirementSubjects.add(courseMinRequirementSubjectDto);

		EducationSystemDto educationSystem = new EducationSystemDto().builder().id(INSTITUTE_ID).countryName("INDIA")
				.name("NAME").code("1234").description("Discription").stateName("INDIA").subjects(subject)
				.gradeDtos(grades).levelId(INSTITUTE_ID).levelCode("level Code").gradeTypeCode("gradeType Code")
				.gradeType(GradeType.SG).level(levelDto).build();

		SubjectDto subjectDto = new SubjectDto(instituteId, entityId, "sunjectName", "secondYear", "firstClass");
		subject.add(subjectDto);

		GradeDto gradeDto = new GradeDto(entityId, "INDIA", "systemName", Arrays.asList("first", "second"),
				"secondClass", "2.22");

		CourseMinRequirementDto courseMinRequirement = new CourseMinRequirementDto();
		courseMinRequirement.setCourseMinRequirementsId(UUID.randomUUID().toString());
		courseMinRequirement.setCountryName("INDIA");
		courseMinRequirement.setStateName("INDIA");
		courseMinRequirement.setEducationSystemId(UUID.randomUUID().toString());
		courseMinRequirement.setEducationSystem(educationSystem);
		courseMinRequirement.setGradePoint(22.00);
		courseMinRequirement.setStudyLanguages(studyLanguages);
		courseMinRequirementDtos.add(courseMinRequirement);

		ValidList<ProviderCodeDto> listOfInstituteProviderCode = new ValidList<>();
		ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
		instituteProviderCode.setId(INSTITUTE_ID);
		instituteProviderCode.setName("TestProviderName");
		instituteProviderCode.setValue(("TestProviderValue"));
		listOfInstituteProviderCode.add(instituteProviderCode);

		FacultyDto facultyDto = new FacultyDto(entityId, "Facultyname", "facultyDescription", "facultyIcon");

		// intializing courseRequestDto.......
		CourseRequest couseRequest = new CourseRequest();
		couseRequest.setId(UUID.randomUUID());
		couseRequest.setInstituteId("1e348e15-45b6-477f-a457-883738227e05");
		couseRequest.setFacultyId("73babc67-04a1-4329-9733-d9175a544fc8");
		couseRequest.setFaculty(facultyDto);
		couseRequest.setName("courseName Test");
		couseRequest.setDescription("course Description");
		couseRequest.setLanguage(Arrays.asList("INDIA"));
		couseRequest.setGrades("first Class");
		couseRequest.setDocumentUrl("document Url");
		couseRequest.setWebsite("website Url");
		couseRequest.setLastUpdated("Apis");
		couseRequest.setInstituteName("Institute Name");
		// couseRequest.setLocations("INDIA");
		couseRequest.setWorldRanking("12345");
		couseRequest.setStars(22.0);
		couseRequest.setCost("cost ...");
		couseRequest.setTotalCount("23456");
		couseRequest.setCurrency("dollar");
		couseRequest.setCurrencyTime("ISt");
		couseRequest.setLevelIds("7401b9e0-9541-4336-98bb-934d455afae6");
		couseRequest.setLevel(levelDto);
		couseRequest.setAvailability("always Available");
		couseRequest.setRecognition("recognize");
		couseRequest.setRecognitionType("recognize Type");
		couseRequest.setAbbreviation("abbreviation");
		couseRequest.setRemarks("course remark");
		couseRequest.setApplied(true);
		couseRequest.setViewCourse(true);
		couseRequest.setLatitude(33.00);
		couseRequest.setLongitude(400.00);
		couseRequest.setCountryName("INDIA");
		couseRequest.setCityName("INDIA CITY");
		couseRequest.setCourseDeliveryModes(null);
		couseRequest.setExaminationBoard("examinationBoard");
		couseRequest.setDomesticApplicationFee(23.00);
		couseRequest.setInternationalApplicationFee(233.0);
		couseRequest.setDomesticEnrollmentFee(345.00);
		couseRequest.setInternationalEnrollmentFee(3455.00);
		couseRequest.setDomesticEnrollmentFee(235.00);
		couseRequest.setDomesticBoardingFee(3478.00);
		couseRequest.setInternationalBoardingFee(123456.0);
		couseRequest.setEntranceExam("INDIA EXTRANCE");
		couseRequest.setPhoneNumber("09078902655");
		couseRequest.setGlobalGpa(2344.00);
		couseRequest.setContent("content Area");
		// email
		couseRequest.setEmail("fkelenna@gmail.com");
		couseRequest.setRecDate(new Date());
		couseRequest.setHasEditAccess(true);
		couseRequest.setFavoriteCourse(true);
		couseRequest.setReviewsCount(12345L);
		couseRequest.setAudience("course Audience");
		couseRequest.setFundingsCount(234);
		couseRequest.setCourseSemesters(courseSemesters);
		couseRequest.setCourseSemesters(courseSemesters);
		couseRequest.setCourseTimings(courseTimings);
		couseRequest.setCourseContactPersons(courseContactPersons);
		couseRequest.setProviderCodes(listOfInstituteProviderCode);
		// couseRequest.setIntake(courseIntake);
		couseRequest.setInternationalStudentProcedureId(entityId);
		couseRequest.setDomesticStudentProcedureId(INSTITUTE_ID);
		couseRequest.setCourseMinRequirementDtos(courseMinRequirementDtos);


		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseRequest> entity = new HttpEntity<>(couseRequest, headers);
		ResponseEntity<CourseRequest> response = testRestTemplate.exchange(
				COURSE_PATH + PATH_SEPARATOR + "ed767c27-0124-4c1f-968a-9b09244b5cb6" + PATH_SEPARATOR + "course",
				HttpMethod.POST, entity, CourseRequest.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("update institutes")
	@Test
	public void UpdateCourses() {

		ValidList<CourseMinRequirementDto> courseMinRequirementDtos = new ValidList<>();
		ValidList<CourseContactPersonDto> courseContactPersons = new ValidList<>();
		ValidList<CourseMinRequirementSubjectDto> minRequirementSubjects = new ValidList<>();
		com.yuzee.common.lib.dto.ValidList<SemesterSubjectDto> semesterSubjectDtos = null;
		ValidList<TimingRequestDto> courseTimings = new ValidList<>();
		ValidList<CourseSemesterDto> courseSemesters = new ValidList<>();

		List<SubjectDto> subject = new ArrayList<>();
		List<GradeDto> grades = new ArrayList<>();
		List<Date> date = new ArrayList<>();
		List<String> ids = new ArrayList<>();
		List<DayTimingDto> timings = new ArrayList<>();
		DayTimingDto dayTimingDto = new DayTimingDto("from", "to", "day");
		timings.add(dayTimingDto);
		ids.add(INSTITUTE_ID);
		date.add(new Date());
		Set<String> studyLanguages = new HashSet<>();
		studyLanguages.add("English");

		TimingRequestDto timing = new TimingRequestDto(entityId, timings, "LECTURES", "COURSE", instituteId);
		courseTimings.add(timing);
		SemesterSubjectDto semesterSubjectDto = new SemesterSubjectDto( "name", "description");

		CourseSemesterDto courseSemesterDto = new CourseSemesterDto(entityId, "type", "name", "description",
				semesterSubjectDtos);
		courseSemesters.add(courseSemesterDto);
		CourseIntakeDto courseIntakeDto = new CourseIntakeDto(entityId, new Date());

		UserInitialInfoDto userInitialInfoDto = new UserInitialInfoDto();

		LevelDto levelDto = new LevelDto(instituteId, "levelName", "levelCode", "levelCategories", "levelDescriptions",
				12345);

		CourseMinRequirementSubjectDto courseMinRequirementSubjectDto = new CourseMinRequirementSubjectDto(entityId,
				"name", "Grade");
		minRequirementSubjects.add(courseMinRequirementSubjectDto);

		EducationSystemDto educationSystem = new EducationSystemDto().builder().id(INSTITUTE_ID).countryName("INDIA")
				.name("NAME").code("1234").description("Discription").stateName("INDIA").subjects(subject)
				.gradeDtos(grades).levelId(INSTITUTE_ID).levelCode("level Code").gradeTypeCode("gradeType Code")
				.gradeType(GradeType.SG).level(levelDto).build();

		SubjectDto subjectDto = new SubjectDto(instituteId, entityId, "sunjectName", "secondYear", "firstClass");
		subject.add(subjectDto);

		GradeDto gradeDto = new GradeDto(entityId, "INDIA", "systemName", Arrays.asList("first", "second"),
				"secondClass", "2.22");

		CourseMinRequirementDto courseMinRequirement = new CourseMinRequirementDto();
		courseMinRequirement.setCourseMinRequirementsId(UUID.randomUUID().toString());
		courseMinRequirement.setCountryName("INDIA");
		courseMinRequirement.setStateName("INDIA");
		courseMinRequirement.setEducationSystemId(UUID.randomUUID().toString());
		courseMinRequirement.setEducationSystem(educationSystem);
		courseMinRequirement.setGradePoint(22.00);
		courseMinRequirement.setStudyLanguages(studyLanguages);
		courseMinRequirementDtos.add(courseMinRequirement);

		ValidList<ProviderCodeDto> listOfInstituteProviderCode = new ValidList<>();
		ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
		instituteProviderCode.setId(INSTITUTE_ID);
		instituteProviderCode.setName("TestProviderName");
		instituteProviderCode.setValue(("TestProviderValue"));
		listOfInstituteProviderCode.add(instituteProviderCode);

		FacultyDto facultyDto = new FacultyDto(entityId, "Facultyname", "facultyDescription", "facultyIcon");

		// intializing courseRequestDto.......
		CourseRequest couseRequest = new CourseRequest();
		couseRequest.setId(UUID.randomUUID());
		couseRequest.setInstituteId("1e348e15-45b6-477f-a457-883738227e05");
		couseRequest.setFacultyId("73babc67-04a1-4329-9733-d9175a544fc8");
		couseRequest.setFaculty(facultyDto);
		couseRequest.setName("UpdateName");
		couseRequest.setDescription("course Description");
		couseRequest.setLanguage(Arrays.asList(entityId));
		couseRequest.setGrades("first Class");
		couseRequest.setDocumentUrl("document Url");
		couseRequest.setWebsite("website Url");
		couseRequest.setLastUpdated("Apis");
		couseRequest.setInstituteName("Institute Name");
		// couseRequest.setLocations("INDIA");
		couseRequest.setWorldRanking("12345");
		couseRequest.setStars(22.0);
		couseRequest.setCost("cost ...");
		couseRequest.setTotalCount("23456");
		couseRequest.setCurrency("dollar");
		couseRequest.setCurrencyTime("ISt");
		couseRequest.setLevelIds("73babc67-04a1-4329-9733-d9175a544fc8");
		couseRequest.setLevel(levelDto);
		couseRequest.setAvailability("always Available");
		couseRequest.setRecognition("recognize");
		couseRequest.setRecognitionType("recognize Type");
		couseRequest.setAbbreviation("abbreviation");
		couseRequest.setRemarks("course remark");
		couseRequest.setApplied(true);
		couseRequest.setViewCourse(true);
		couseRequest.setLatitude(33.00);
		couseRequest.setLongitude(400.00);
		couseRequest.setCountryName("INDIA");
		couseRequest.setCityName("INDIA CITY");
		couseRequest.setCourseDeliveryModes(null);
		couseRequest.setExaminationBoard("examinationBoard");
		couseRequest.setDomesticApplicationFee(23.00);
		couseRequest.setInternationalApplicationFee(233.0);
		couseRequest.setDomesticEnrollmentFee(345.00);
		couseRequest.setInternationalEnrollmentFee(3455.00);
		couseRequest.setDomesticEnrollmentFee(235.00);
		couseRequest.setDomesticBoardingFee(3478.00);
		couseRequest.setInternationalBoardingFee(123456.0);
		couseRequest.setEntranceExam("INDIA EXTRANCE");
		couseRequest.setPhoneNumber("09078902655");
		couseRequest.setGlobalGpa(2344.00);
		couseRequest.setContent("content Area");
		// email
		couseRequest.setEmail("fkelenna@gmail.com");
		couseRequest.setRecDate(new Date());
		couseRequest.setHasEditAccess(true);
		couseRequest.setFavoriteCourse(true);
		couseRequest.setReviewsCount(12345L);
		couseRequest.setAudience("course Audience");
		couseRequest.setFundingsCount(234);
		couseRequest.setCourseSemesters(courseSemesters);
		couseRequest.setCourseSemesters(courseSemesters);
		couseRequest.setCourseTimings(courseTimings);
		couseRequest.setCourseContactPersons(courseContactPersons);
		couseRequest.setProviderCodes(listOfInstituteProviderCode);
		// couseRequest.setIntake(courseIntake);
		couseRequest.setInternationalStudentProcedureId(entityId);
		couseRequest.setDomesticStudentProcedureId(INSTITUTE_ID);
		couseRequest.setCourseMinRequirementDtos(courseMinRequirementDtos);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseRequest> entity = new HttpEntity<>(couseRequest, headers);
		ResponseEntity<CourseRequest> response = testRestTemplate.exchange(
				COURSE_PATH + PATH_SEPARATOR + "1e348e15-45b6-477f-a457-883738227e05" + PATH_SEPARATOR + "course"
						+ PATH_SEPARATOR + "502f2d4e-9bde-4e99-b443-d922ce540be9",
				HttpMethod.PUT, entity, CourseRequest.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		// assertThat(response.getStatusCode()).isNotEqualTo(HttpStatus.OK);
	}

	@DisplayName("getAllCourses")
	@Test
	public void getAllCourses() {
		Integer pageNumber = 1;
		Integer pageSize = 1;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + PAGE_NUMBER_PATH + PATH_SEPARATOR + pageNumber
						+ PATH_SEPARATOR + PAGE_SIZE_PATH + PATH_SEPARATOR + pageSize,
				HttpMethod.GET, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("getAllCourses")
	@Test
	public void getAutoSearchCourses() {
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

	@DisplayName("DELETE Courses")
	@Test
	public void DeleteCourse() {

		Map<String, List<String>> param = new HashMap<>();
		List<String> ss = new ArrayList<>();
		ss.add("75218134-062d-41bd-b84d-eeb203cddbae");
		ss.add(instituteId);
		param.put("linked_course_ids", ss);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entityer = new HttpEntity<>(null, headers);
		ResponseEntity<String> responseds = testRestTemplate.exchange(COURSE + PATH_SEPARATOR + "course"
				+ PATH_SEPARATOR + "07372dea-92b7-4179-9082-e6cbbe02649d" + PATH_SEPARATOR + ss, HttpMethod.DELETE,
				entityer, String.class);
		assertThat(responseds.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("course search")
	@Test
	public void courseSearch() {
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
	public void advanceSearch() {
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

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		headers.add("language", "ENGLISH");

		HttpEntity<AdvanceSearchDto> entity = new HttpEntity<>(advanceSearch, headers);
		ResponseEntity<AdvanceSearchDto> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "advanceSearch", HttpMethod.POST, entity,
				AdvanceSearchDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName(value = "get")
	@Test
	public void get() {
		Map<String, String> param = new HashMap<>();
		param.put("is_readable_id", "coursename-Ow9NR9y");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<Object> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "7132d88e-cf2c-4f48-ac6e-82214208f677",
				HttpMethod.GET, entity, Object.class, param);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName("getAllInstituteById")
	@Test
	public void getAllCourseByInstituteID() {
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
		ResponseEntity<CourseSearchDto> response = testRestTemplate
				.exchange(
						COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "institute" + PATH_SEPARATOR
								+ "1e348e15-45b6-477f-a457-883738227e05",
						HttpMethod.PUT, entity, CourseSearchDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName(value = "searchKeyword")
	@Test
	public void searchCourseKeyword() {
		Map<String, String> params = new HashMap<>();
		params.put("keyword", "coursename");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "keyword" + PATH_SEPARATOR + "coursename",
				HttpMethod.GET, entity, String.class, params);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	//// faculty is yet to be created
	public void getCourseByFacultyId() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "keyword" + PATH_SEPARATOR + "coursename",
				HttpMethod.GET, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName(value = "getUserCourse")
	@Test
	public void getUserCourse() {
		List<String> courseIds = new ArrayList<>();
		courseIds.add("7132d88e-cf2c-4f48-ac6e-82214208f677");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "user" + PATH_SEPARATOR + courseIds
						+ PATH_SEPARATOR + "coursename" + PATH_SEPARATOR + "true",
				HttpMethod.GET, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "courseFilter")
	@Test
	public void courseFilter() {
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
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName(value = "searchByCharacter")
	@Test
	public void autoSearchByCharacter() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "autoSearch" + PATH_SEPARATOR + "coursename",
				HttpMethod.GET, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName(value = "getCourseNoResultRecommendation")
	@Test
	public void getCourseNoResultRecommendation() {
		Integer pageNumber = 1;
		Integer pageSize = 1;

		Map<String, String> params = new HashMap<>();
		params.put("facultyId", entityId);
		params.put("countryId", instituteId);
		params.put("userCountry", "INDIA");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "noResult" + PATH_SEPARATOR + "pageNumber"
						+ PATH_SEPARATOR + pageNumber + PATH_SEPARATOR + "pageSize" + PATH_SEPARATOR + pageSize,
				HttpMethod.GET, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName(value = "getCourseNoResultRecommendation")
	@Test
	public void getCourseKeywordRecommendation() {
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

	@DisplayName(value = "getCheapestCourse")
	@Test
	public void getCheapestCourse() {
		Integer pageNumber = 1;
		Integer pageSize = 1;

		Map<String, String> params = new HashMap<>();
		params.put("facultyId", entityId);
		params.put("countryId", instituteId);
		params.put("userCountry", "INDIA");
		params.put("levelId", instituteId);
		params.put("cityId", entityId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "cheapest" + PATH_SEPARATOR + "pageNumber"
						+ PATH_SEPARATOR + pageNumber + PATH_SEPARATOR + "pageSize" + PATH_SEPARATOR + pageSize,
				HttpMethod.GET, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "getCourseCountByLevel")
	@Test
	public void getCourseCountByLevel() {
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
	public void SearchCourses() {
		Map<String, String> params = new HashMap<>();
		params.put("countryIds", COURSE_IDS);

	}

	@DisplayName(value = "getCourseCountByLevel")
	@Test
	public void getDistinctCourses() {
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

/////RUN AFTER FACULTY DB IS CREATED
	@DisplayName(value = "addCourseViaMobile")
	@Test
	public void addCourseViaMobile() {
		CourseMobileDto courseMobileDto = new CourseMobileDto("7132d88e-cf2c-4f48-ac6e-82214208f677", "coursename",
				"course Description", entityId, "facultyName", 3.0, 33.00, 1.22, 22.5, "durationUnit");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		;
		HttpEntity<CourseMobileDto> entity = new HttpEntity<>(courseMobileDto, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR
				+ "mobile" + PATH_SEPARATOR + "1e348e15-45b6-477f-a457-883738227e05", HttpMethod.POST, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

/////RUN AFTER FACULTY DB IS CREATED
	@DisplayName(value = "addCourseViaMobile")
	@Test
	public void udateCourseViaMobile() {
		CourseMobileDto courseMobileDto = new CourseMobileDto("7132d88e-cf2c-4f48-ac6e-82214208f677", "coursename",
				"course Description", entityId, "facultyName", 3.0, 33.00, 1.22, 22.5, "durationUnit");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		;
		HttpEntity<CourseMobileDto> entity = new HttpEntity<>(courseMobileDto, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR
				+ "mobile" + PATH_SEPARATOR + "7132d88e-cf2c-4f48-ac6e-82214208f677", HttpMethod.PUT, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "addCourseViaMobile")
	@Test
	public void getCourseViaMobile() {
		CourseMobileDto courseMobileDto = new CourseMobileDto("7132d88e-cf2c-4f48-ac6e-82214208f677", "coursename",
				"course Description", entityId, "facultyName", 3.0, 33.00, 1.22, 22.5, "durationUnit");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		;
		HttpEntity<CourseMobileDto> entity = new HttpEntity<>(courseMobileDto, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR
				+ "mobile" + PATH_SEPARATOR + "1e348e15-45b6-477f-a457-883738227e05", HttpMethod.GET, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName(value = "changeStatus")
	@Test
	public void changeStatus() {
		Boolean status = true;
		Map<String, Boolean> params = new HashMap<>();
		params.put("Status", status);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR
				+ "status" + PATH_SEPARATOR + "7132d88e-cf2c-4f48-ac6e-82214208f677", HttpMethod.PUT, entity,
				String.class, params);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "getCourseByInstituteId")
	@Test
	public void getCourseByInstituteId() {
		Integer pageNumber = 1;
		Integer pageSize = 1;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "institute" + PATH_SEPARATOR + "pageNumber"
						+ PATH_SEPARATOR + pageNumber + PATH_SEPARATOR + "pageSize" + PATH_SEPARATOR + pageSize
						+ PATH_SEPARATOR + "1e348e15-45b6-477f-a457-883738227e05",
				HttpMethod.GET, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "getNearestCourseList")
	@Test
	public void getNearestCourseList() {
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
		advanceSearch.setLatitude(15.00);
		advanceSearch.setInitialRadius(12);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AdvanceSearchDto> entity = new HttpEntity<>(advanceSearch, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "nearest", HttpMethod.POST, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "getNearestCourseList")
	@Test
	public void getCourseByCountryName() {
		Integer pageNumber = 1;
		Integer pageSize = 1;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "pageNumber" + PATH_SEPARATOR + pageNumber
						+ PATH_SEPARATOR + "pageSize" + PATH_SEPARATOR + pageSize + PATH_SEPARATOR + "INDIA",
				HttpMethod.POST, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "getCourseByIds")
	@Test
	public void getCourseByIds() {
		List<String> course = new ArrayList<>();
		course.add("7132d88e-cf2c-4f48-ac6e-82214208f677");
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
	public void getRecommendateCourses() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR
				+ "recommendation" + PATH_SEPARATOR + "7132d88e-cf2c-4f48-ac6e-82214208f677", HttpMethod.GET, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "getRecommendateCourses")
	@Test
	public void getRelatedCourses() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR
				+ "related" + PATH_SEPARATOR + "7132d88e-cf2c-4f48-ac6e-82214208f677", HttpMethod.GET, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "getRecommendateCourses")
	@Test
	public void getCourseCountByInstituteId() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR
				+ "count" + PATH_SEPARATOR + "1e348e15-45b6-477f-a457-883738227e05", HttpMethod.GET, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "updateBasiCourse")
	@Test
	public void updateBasiCourse() {
		ValidList<CourseMinRequirementDto> courseMinRequirementDtos = new ValidList<>();
		ValidList<CourseContactPersonDto> courseContactPersons = new ValidList<>();
		ValidList<CourseMinRequirementSubjectDto> minRequirementSubjects = new ValidList<>();
		com.yuzee.common.lib.dto.ValidList<SemesterSubjectDto> semesterSubjectDtos = null;
		ValidList<TimingRequestDto> courseTimings = new ValidList<>();
		ValidList<CourseSemesterDto> courseSemesters = new ValidList<>();

		List<SubjectDto> subject = new ArrayList<>();
		List<GradeDto> grades = new ArrayList<>();
		List<Date> date = new ArrayList<>();
		List<String> ids = new ArrayList<>();
		List<DayTimingDto> timings = new ArrayList<>();
		DayTimingDto dayTimingDto = new DayTimingDto("from", "to", "day");
		timings.add(dayTimingDto);
		ids.add(INSTITUTE_ID);
		date.add(new Date());
		Set<String> studyLanguages = new HashSet<>();
		studyLanguages.add("English");

		TimingRequestDto timing = new TimingRequestDto(entityId, timings, "LECTURES", "COURSE", instituteId);
		courseTimings.add(timing);
		SemesterSubjectDto semesterSubjectDto = new SemesterSubjectDto( "name", "description");

		CourseSemesterDto courseSemesterDto = new CourseSemesterDto(entityId, "type", "name", "description",
				semesterSubjectDtos);
		courseSemesters.add(courseSemesterDto);
		CourseIntakeDto courseIntakeDto = new CourseIntakeDto(entityId, new Date());

		UserInitialInfoDto userInitialInfoDto = new UserInitialInfoDto();



		LevelDto levelDto = new LevelDto(instituteId, "levelName", "levelCode", "levelCategories", "levelDescriptions",
				12345);

		CourseMinRequirementSubjectDto courseMinRequirementSubjectDto = new CourseMinRequirementSubjectDto(entityId,
				"name", "Grade");
		minRequirementSubjects.add(courseMinRequirementSubjectDto);

		EducationSystemDto educationSystem = new EducationSystemDto().builder().id(INSTITUTE_ID).countryName("INDIA")
				.name("NAME").code("1234").description("Discription").stateName("INDIA").subjects(subject)
				.gradeDtos(grades).levelId(INSTITUTE_ID).levelCode("level Code").gradeTypeCode("gradeType Code")
				.gradeType(GradeType.SG).level(levelDto).build();

		SubjectDto subjectDto = new SubjectDto(instituteId, entityId, "sunjectName", "secondYear", "firstClass");
		subject.add(subjectDto);

		GradeDto gradeDto = new GradeDto(entityId, "INDIA", "systemName", Arrays.asList("first", "second"),
				"secondClass", "2.22");

		CourseMinRequirementDto courseMinRequirement = new CourseMinRequirementDto();
		courseMinRequirement.setCourseMinRequirementsId(UUID.randomUUID().toString());
		courseMinRequirement.setCountryName("INDIA");
		courseMinRequirement.setStateName("INDIA");
		courseMinRequirement.setEducationSystemId(UUID.randomUUID().toString());
		courseMinRequirement.setEducationSystem(educationSystem);
		courseMinRequirement.setGradePoint(22.00);
		courseMinRequirement.setStudyLanguages(studyLanguages);
		courseMinRequirementDtos.add(courseMinRequirement);

		ValidList<ProviderCodeDto> listOfInstituteProviderCode = new ValidList<>();
		ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
		instituteProviderCode.setId(INSTITUTE_ID);
		instituteProviderCode.setName("TestProviderName");
		instituteProviderCode.setValue(("TestProviderValue"));
		listOfInstituteProviderCode.add(instituteProviderCode);

		FacultyDto facultyDto = new FacultyDto(entityId, "Facultyname", "facultyDescription", "facultyIcon");

		// intializing courseRequestDto.......
		CourseRequest couseRequest = new CourseRequest();
		couseRequest.setId(UUID.randomUUID());
		couseRequest.setInstituteId("1e348e15-45b6-477f-a457-883738227e05");
		couseRequest.setFacultyId(entityId);
		couseRequest.setFaculty(facultyDto);
		couseRequest.setName("UpdateName");
		couseRequest.setDescription("course Description");
		couseRequest.setLanguage(Arrays.asList(entityId));
		couseRequest.setGrades("first Class");
		couseRequest.setDocumentUrl("document Url");
		couseRequest.setWebsite("website Url");
		couseRequest.setLastUpdated("Apis");
		couseRequest.setInstituteName("Institute Name");
		// couseRequest.setLocations("INDIA");
		couseRequest.setWorldRanking("12345");
		couseRequest.setStars(22.0);
		couseRequest.setCost("cost ...");
		couseRequest.setTotalCount("23456");
		couseRequest.setCurrency("dollar");
		couseRequest.setCurrencyTime("ISt");
		couseRequest.setLevelIds(INSTITUTE_ID);
		couseRequest.setLevel(levelDto);
		couseRequest.setAvailability("always Available");
		couseRequest.setRecognition("recognize");
		couseRequest.setRecognitionType("recognize Type");
		couseRequest.setAbbreviation("abbreviation");
		couseRequest.setRemarks("course remark");
		couseRequest.setApplied(true);
		couseRequest.setViewCourse(true);
		couseRequest.setLatitude(33.00);
		couseRequest.setLongitude(400.00);
		couseRequest.setCountryName("INDIA");
		couseRequest.setCityName("INDIA CITY");
		couseRequest.setCourseDeliveryModes(null);
		couseRequest.setExaminationBoard("examinationBoard");
		couseRequest.setDomesticApplicationFee(23.00);
		couseRequest.setInternationalApplicationFee(233.0);
		couseRequest.setDomesticEnrollmentFee(345.00);
		couseRequest.setInternationalEnrollmentFee(3455.00);
		couseRequest.setDomesticEnrollmentFee(235.00);
		couseRequest.setDomesticBoardingFee(3478.00);
		couseRequest.setInternationalBoardingFee(123456.0);
		couseRequest.setEntranceExam("INDIA EXTRANCE");
		couseRequest.setPhoneNumber("09078902655");
		couseRequest.setGlobalGpa(2344.00);
		couseRequest.setContent("content Area");
		// email
		couseRequest.setEmail("fkelenna@gmail.com");
		couseRequest.setRecDate(new Date());
		couseRequest.setHasEditAccess(true);
		couseRequest.setFavoriteCourse(true);
		couseRequest.setReviewsCount(12345L);
		couseRequest.setAudience("course Audience");
		couseRequest.setFundingsCount(234);
		couseRequest.setCourseSemesters(courseSemesters);
		couseRequest.setCourseSemesters(courseSemesters);
		couseRequest.setCourseTimings(courseTimings);
		couseRequest.setCourseContactPersons(courseContactPersons);
		couseRequest.setProviderCodes(listOfInstituteProviderCode);
		// couseRequest.setIntake(courseIntake);
		couseRequest.setInternationalStudentProcedureId(entityId);
		couseRequest.setDomesticStudentProcedureId(INSTITUTE_ID);
		couseRequest.setCourseMinRequirementDtos(courseMinRequirementDtos);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseRequest> entity = new HttpEntity<>(couseRequest, headers);
		ResponseEntity<CourseRequest> response = testRestTemplate.exchange(
				COURSE_PATH + PATH_SEPARATOR + "1e348e15-45b6-477f-a457-883738227e05" + PATH_SEPARATOR + "course"
						+ PATH_SEPARATOR + "basic" + PATH_SEPARATOR + "info",
				HttpMethod.POST, entity, CourseRequest.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName(value = "saveBasiCourse")
	@Test
	public void saveBasiCourse() {
		ValidList<CourseMinRequirementDto> courseMinRequirementDtos = new ValidList<>();
		ValidList<CourseContactPersonDto> courseContactPersons = new ValidList<>();
		ValidList<CourseMinRequirementSubjectDto> minRequirementSubjects = new ValidList<>();
		com.yuzee.common.lib.dto.ValidList<SemesterSubjectDto> semesterSubjectDtos = null;
		ValidList<TimingRequestDto> courseTimings = new ValidList<>();
		ValidList<CourseSemesterDto> courseSemesters = new ValidList<>();

		List<SubjectDto> subject = new ArrayList<>();
		List<GradeDto> grades = new ArrayList<>();
		List<Date> date = new ArrayList<>();
		List<String> ids = new ArrayList<>();
		List<DayTimingDto> timings = new ArrayList<>();
		DayTimingDto dayTimingDto = new DayTimingDto("from", "to", "day");
		timings.add(dayTimingDto);
		ids.add(INSTITUTE_ID);
		date.add(new Date());
		Set<String> studyLanguages = new HashSet<>();
		studyLanguages.add("English");

		TimingRequestDto timing = new TimingRequestDto(entityId, timings, "LECTURES", "COURSE", instituteId);
		courseTimings.add(timing);
		SemesterSubjectDto semesterSubjectDto = new SemesterSubjectDto( "name", "description");

		CourseSemesterDto courseSemesterDto = new CourseSemesterDto(entityId, "type", "name", "description",
				semesterSubjectDtos);
		courseSemesters.add(courseSemesterDto);
		CourseIntakeDto courseIntakeDto = new CourseIntakeDto(entityId, new Date());

		UserInitialInfoDto userInitialInfoDto = new UserInitialInfoDto();


		LevelDto levelDto = new LevelDto(instituteId, "levelName", "levelCode", "levelCategories", "levelDescriptions",
				12345);

		CourseMinRequirementSubjectDto courseMinRequirementSubjectDto = new CourseMinRequirementSubjectDto(entityId,
				"name", "Grade");
		minRequirementSubjects.add(courseMinRequirementSubjectDto);

		EducationSystemDto educationSystem = new EducationSystemDto().builder().id(INSTITUTE_ID).countryName("INDIA")
				.name("NAME").code("1234").description("Discription").stateName("INDIA").subjects(subject)
				.gradeDtos(grades).levelId(INSTITUTE_ID).levelCode("level Code").gradeTypeCode("gradeType Code")
				.gradeType(GradeType.SG).level(levelDto).build();

		SubjectDto subjectDto = new SubjectDto(instituteId, entityId, "sunjectName", "secondYear", "firstClass");
		subject.add(subjectDto);

		GradeDto gradeDto = new GradeDto(entityId, "INDIA", "systemName", Arrays.asList("first", "second"),
				"secondClass", "2.22");

		CourseMinRequirementDto courseMinRequirement = new CourseMinRequirementDto();
		courseMinRequirement.setCourseMinRequirementsId(UUID.randomUUID().toString());
		courseMinRequirement.setCountryName("INDIA");
		courseMinRequirement.setStateName("INDIA");
		courseMinRequirement.setEducationSystemId(UUID.randomUUID().toString());
		courseMinRequirement.setEducationSystem(educationSystem);
		courseMinRequirement.setGradePoint(22.00);
		courseMinRequirement.setStudyLanguages(studyLanguages);
		courseMinRequirementDtos.add(courseMinRequirement);

		ValidList<ProviderCodeDto> listOfInstituteProviderCode = new ValidList<>();
		ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
		instituteProviderCode.setId(INSTITUTE_ID);
		instituteProviderCode.setName("TestProviderName");
		instituteProviderCode.setValue(("TestProviderValue"));
		listOfInstituteProviderCode.add(instituteProviderCode);

		FacultyDto facultyDto = new FacultyDto(entityId, "Facultyname", "facultyDescription", "facultyIcon");

		// intializing courseRequestDto.......
		CourseRequest couseRequest = new CourseRequest();
		couseRequest.setId(UUID.randomUUID());
		couseRequest.setInstituteId("1e348e15-45b6-477f-a457-883738227e05");
		couseRequest.setFacultyId(entityId);
		couseRequest.setFaculty(facultyDto);
		couseRequest.setName("UpdateName");
		couseRequest.setDescription("course Description");
		couseRequest.setLanguage(Arrays.asList(entityId));
		couseRequest.setGrades("first Class");
		couseRequest.setDocumentUrl("document Url");
		couseRequest.setWebsite("website Url");
		couseRequest.setLastUpdated("Apis");
		couseRequest.setInstituteName("Institute Name");
		// couseRequest.setLocations("INDIA");
		couseRequest.setWorldRanking("12345");
		couseRequest.setStars(22.0);
		couseRequest.setCost("cost ...");
		couseRequest.setTotalCount("23456");
		couseRequest.setCurrency("dollar");
		couseRequest.setCurrencyTime("ISt");
		couseRequest.setLevelIds(INSTITUTE_ID);
		couseRequest.setLevel(levelDto);
		couseRequest.setAvailability("always Available");
		couseRequest.setRecognition("recognize");
		couseRequest.setRecognitionType("recognize Type");
		couseRequest.setAbbreviation("abbreviation");
		couseRequest.setRemarks("course remark");
		couseRequest.setApplied(true);
		couseRequest.setViewCourse(true);
		couseRequest.setLatitude(33.00);
		couseRequest.setLongitude(400.00);
		couseRequest.setCountryName("INDIA");
		couseRequest.setCityName("INDIA CITY");
		couseRequest.setCourseDeliveryModes(null);
		couseRequest.setExaminationBoard("examinationBoard");
		couseRequest.setDomesticApplicationFee(23.00);
		couseRequest.setInternationalApplicationFee(233.0);
		couseRequest.setDomesticEnrollmentFee(345.00);
		couseRequest.setInternationalEnrollmentFee(3455.00);
		couseRequest.setDomesticEnrollmentFee(235.00);
		couseRequest.setDomesticBoardingFee(3478.00);
		couseRequest.setInternationalBoardingFee(123456.0);
		couseRequest.setEntranceExam("INDIA EXTRANCE");
		couseRequest.setPhoneNumber("09078902655");
		couseRequest.setGlobalGpa(2344.00);
		couseRequest.setContent("content Area");
		// email
		couseRequest.setEmail("fkelenna@gmail.com");
		couseRequest.setRecDate(new Date());
		couseRequest.setHasEditAccess(true);
		couseRequest.setFavoriteCourse(true);
		couseRequest.setReviewsCount(12345L);
		couseRequest.setAudience("course Audience");
		couseRequest.setFundingsCount(234);
		couseRequest.setCourseSemesters(courseSemesters);
		couseRequest.setCourseSemesters(courseSemesters);
		couseRequest.setCourseTimings(courseTimings);
		couseRequest.setCourseContactPersons(courseContactPersons);
		couseRequest.setProviderCodes(listOfInstituteProviderCode);
		// couseRequest.setIntake(courseIntake);
		couseRequest.setInternationalStudentProcedureId(entityId);
		couseRequest.setDomesticStudentProcedureId(INSTITUTE_ID);
		couseRequest.setCourseMinRequirementDtos(courseMinRequirementDtos);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseRequest> entity = new HttpEntity<>(couseRequest, headers);
		ResponseEntity<CourseRequest> response = testRestTemplate.exchange(
				COURSE_PATH + PATH_SEPARATOR + "1e348e15-45b6-477f-a457-883738227e05" + PATH_SEPARATOR + "course"
						+ PATH_SEPARATOR + "basic" + PATH_SEPARATOR + "info",
				HttpMethod.POST, entity, CourseRequest.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName(value = "updateProcedureIdInCourse")
	@Test
	public void updateProcedureIdInCourse() {
		Map<String, String> params = new HashMap<>();
		params.put("courseIds", COURSE_IDS);
		params.put("studentType", StudentTypeEnum.INTERNATIONAL.toString());
		params.put("procedureId", entityId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "procedure_id", HttpMethod.PUT, entity,
				String.class, params);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "updateProcedureIdInInstituteId")
	@Test
	public void updateProcedureIdInIstituteId() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "procedure_id" + PATH_SEPARATOR + "institute_id"
						+ PATH_SEPARATOR + "1e348e15-45b6-477f-a457-883738227e05" + PATH_SEPARATOR
						+ StudentTypeEnum.INTERNATIONAL.toString() + PATH_SEPARATOR + entityId,
				HttpMethod.PUT, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "publishDraftCourse")
	@Test
	public void publishDraftCourse() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR
				+ "draft" + PATH_SEPARATOR + "publish" + PATH_SEPARATOR + COURSE_IDS, HttpMethod.POST, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "getDreaftcourse")
	@Test
	public void getDreaftcourse() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR
				+ "draft" + PATH_SEPARATOR + "pageNumber" + PATH_SEPARATOR + 1 + PATH_SEPARATOR + "pageSize"
				+ PATH_SEPARATOR + 1 + PATH_SEPARATOR + "IIM" + PATH_SEPARATOR + instituteId, HttpMethod.GET, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName(value = "disCardDreaftcourse")
	@Test
	public void disCardDreaftcourse() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "draft" + PATH_SEPARATOR + COURSE_IDS,
				HttpMethod.DELETE, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
