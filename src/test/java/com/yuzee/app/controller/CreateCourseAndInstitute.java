package com.yuzee.app.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yuzee.app.YuzeeApplication;
import com.yuzee.app.bean.Careers;
import com.yuzee.app.bean.InstituteDomesticRankingHistory;
import com.yuzee.app.bean.InstituteWorldRankingHistory;
import com.yuzee.app.bean.Location;
import com.yuzee.app.bean.Service;
import com.yuzee.app.dto.AccrediatedDetailDto;
import com.yuzee.app.dto.CourseCareerOutcomeRequestWrapper;
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.dto.CourseSemesterRequestWrapper;
import com.yuzee.app.dto.DayTimingDto;
import com.yuzee.app.dto.InstituteEnglishRequirementsDto;
import com.yuzee.app.dto.InstituteFundingDto;
import com.yuzee.app.dto.InstituteRequestDto;
import com.yuzee.app.dto.TimingRequestDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.repository.CareerRepository;
import com.yuzee.app.repository.CourseRepository;
import com.yuzee.app.repository.ServiceRepository;
import com.yuzee.common.lib.dto.GenericWrapperDto;
import com.yuzee.common.lib.dto.institute.CareerDto;
import com.yuzee.common.lib.dto.institute.CourseCareerOutcomeDto;
import com.yuzee.common.lib.dto.institute.CourseContactPersonDto;
import com.yuzee.common.lib.dto.institute.CourseEnglishEligibilityDto;
import com.yuzee.common.lib.dto.institute.CourseIntakeDto;
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
import com.yuzee.common.lib.enumeration.GradeType;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;
import com.yuzee.common.lib.handler.StorageHandler;
import com.yuzee.common.lib.handler.UserHandler;
import com.yuzee.common.lib.handler.ViewTransactionHandler;
import com.yuzee.common.lib.util.ObjectMapperHelper;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)
public class CreateCourseAndInstitute {

	protected static final String entityId = UUID.randomUUID().toString();
	protected static final String instituteId = "39d3f31e-64fb-46eb-babc-c54efa69e091";
	protected static final String INSTITUTE_ID = "instituteId";
	protected static final String userId = "8d7c017d-37e3-4317-a8b5-9ae6d9cdcb49";
	protected static final String USER_ID = "userId";
	protected static final String COURSE = "/api/v1";
	protected static final String COURSE_PATH = COURSE + "/institute";
	protected static final String PATH_SEPARATOR = "/";
	protected static final String PAGE_NUMBER_PATH = "/pageNumber";
	protected static final String PAGE_SIZE_PATH = "/pageSize";
	protected static final String COURSE_IDS = "829af0d4-8b28-4f8b-82b1-7b32f1308967";
	protected static final String INSTITUTE_PRE_PATH = "/api/v1";
	protected static final String api = "/api/v1/course";
	protected static final String INSTITUTE_PATH = "/api/v1/institute";

	@Autowired
	protected TestRestTemplate testRestTemplate;
	@Autowired
	protected CourseRepository courseRepository;
	@Autowired
	private CareerRepository careerRepository;
	@Autowired
	private ServiceRepository serviceRepository;


	protected String testCreateInstitute() throws IOException {

		String instituteId = null;

		ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
		ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
		instituteFundingDto.add(0, new InstituteFundingDto(UUID.randomUUID().toString()));

		List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
		ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
		instituteProviderCode.setName("TestProviderName");
		instituteProviderCode.setValue(("TestProviderValue"));
		listOfInstituteProviderCode.add(instituteProviderCode);

		InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
		instituteRequestDto.setInstituteId(UUID.randomUUID().toString());
		instituteRequestDto.setName("IIM");
		instituteRequestDto.setCityName("AHMEDABAD");

		instituteRequestDto.setCountryName("INDIA");
		instituteRequestDto.setEditAccess(true);
		instituteRequestDto.setAboutInfo(
				"Domestic Language School, Cambridge, is accredited by the French Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
		instituteRequestDto.setDescription("Test update method Description");
		instituteRequestDto.setInstituteFundings(instituteFundingDto);
		instituteRequestDto.setEnrolmentLink("https://www.centrallanguageschool.com/enrol");
		instituteRequestDto.setWhatsNo("https://api.whatsapp.com/send?phone=60173010314");
		instituteRequestDto.setCourseStart("March, April, May");
		instituteRequestDto.setWebsite("https://www.centrallanguageschool.com/");
		instituteRequestDto.setAddress("41 St Andrew's St, Cambridge CB2 3AR, UK");
		Location location = new Location(UUID.randomUUID().toString(), new GeoJsonPoint(25.32, 12.56));
		instituteRequestDto.setLatitude(91.202743);
		instituteRequestDto.setLongitude(56.1240);
		instituteRequestDto.setEmail("info@testEmail.comm");
		instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
		instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
		instituteRequestDto.setPostalCode(1234);
		instituteRequestDto.setReadableId(UUID.randomUUID().toString());
		instituteRequestDto.setTagLine("Inspirings");
		instituteRequestDto.setShowSuggestion(true);
		List<String> intakes = new ArrayList<>();
		intakes.add("myintake");
		instituteRequestDto.setIntakes(intakes);
		List<String> accreditation = new ArrayList<>();
		accreditation.add("myaccreditation");
		instituteRequestDto.setAccreditation(accreditation);
		List<AccrediatedDetailDto> accreditationDetails = new ArrayList<>();
		AccrediatedDetailDto accrediatedDto = new AccrediatedDetailDto();
		accrediatedDto.setAccrediatedName("myaccreditation");
		accrediatedDto.setAccrediatedWebsite("https://www.edfntrallanguagihcuhcv.com");
		accrediatedDto.setEntityId("sbjbudgfdyudnthrusb5355n6gfogkjgng");
		accrediatedDto.setEntityType("myentitytype");
		accreditationDetails.add(accrediatedDto);
		instituteRequestDto.setAccreditationDetails(accreditationDetails);

		List<InstituteDomesticRankingHistory> domesticList = new ArrayList<>();
		InstituteDomesticRankingHistory idr = new InstituteDomesticRankingHistory();
		idr.setDomesticRanking(21);
		domesticList.add(idr);
		instituteRequestDto.setInstituteDomesticRankingHistories(domesticList);
		List<InstituteWorldRankingHistory> worldranking = new ArrayList<>();
		InstituteWorldRankingHistory iwr = new InstituteWorldRankingHistory();
		iwr.setWorldRanking(22);
		worldranking.add(iwr);
		instituteRequestDto.setInstituteWorldRankingHistories(worldranking);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		listOfInstituteRequestDto.add(instituteRequestDto);
		instituteRequestDto.setProviderCodes(listOfInstituteProviderCode);
		HttpEntity<ValidList<InstituteRequestDto>> entity = new HttpEntity<>(listOfInstituteRequestDto, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PRE_PATH, HttpMethod.POST, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		GenericWrapperDto<ValidList<InstituteRequestDto>> genericResponse = ObjectMapperHelper.readValueFromJSON(
				response.getBody(), new TypeReference<GenericWrapperDto<ValidList<InstituteRequestDto>>>() {
				});
		ValidList<InstituteRequestDto> r = genericResponse.getData();
		for (InstituteRequestDto data : r) {
			instituteId = data.getInstituteId();
		}
		return instituteId;
	}

	String createLevel() {
		LevelDto level = new LevelDto();
		level.setId(UUID.randomUUID().toString());
		level.setCode("code");
		level.setDescription("Description");
		level.setName("name");
		level.setSequenceNo(223456);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LevelDto> entity = new HttpEntity<>(level, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PRE_PATH + PATH_SEPARATOR + "level",
				HttpMethod.POST, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		return level.getId();
	}

	// create faculty

	String createFaculty() {
		FacultyDto faculty = new FacultyDto();
		faculty.setId(UUID.randomUUID().toString());
		faculty.setDescription("Description");
		faculty.setName("falculty name");
		faculty.setIcon("icons");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<FacultyDto> entity = new HttpEntity<>(faculty, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PRE_PATH + PATH_SEPARATOR + "faculty",
				HttpMethod.POST, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		return faculty.getId();
	}
	// create Course

	protected CourseRequest createCourses(String instituteId) throws IOException {
		///// create institute api
		String facultyId = createFaculty();
		String levelId = createLevel();
		CourseRequest couseRequests = new CourseRequest();
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
		ids.add("39d3f31e-64fb-46eb-babc-c54efa69e091");
		date.add(new Date());
		Set<String> studyLanguages = new HashSet<>();
		studyLanguages.add("English");

		TimingRequestDto timing = new TimingRequestDto(entityId, timings, "LECTURES", "COURSE", instituteId);
		courseTimings.add(timing);
		SemesterSubjectDto semesterSubjectDto = new SemesterSubjectDto();
		semesterSubjectDto.setName("name");
		semesterSubjectDto.setDescription("description");

		CourseSemesterDto courseSemesterDto = new CourseSemesterDto(entityId, "type", "name", "description",
				semesterSubjectDtos);
		courseSemesters.add(courseSemesterDto);
		List<String> linkedCourseId = new ArrayList<>();
		CourseIntakeDto courseIntake = new CourseIntakeDto();
		courseIntake.setType("SPECIFIC");
		courseIntake.setDates(date);
		UserInitialInfoDto userInitialInfoDto = new UserInitialInfoDto();

		LevelDto levelDto = new LevelDto("39d3f31e-64fb-46eb-babc-c54efa69e091", "levelName", "levelCode",
				"levelCategories", "levelDescriptions", 12345);

		CourseMinRequirementSubjectDto courseMinRequirementSubjectDto = new CourseMinRequirementSubjectDto();
		courseMinRequirementSubjectDto.setName("name");
		courseMinRequirementSubjectDto.setGrade("Grade");
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
		courseMinRequirement.setId(UUID.randomUUID().toString());
		courseMinRequirement.setCountryName("INDIA");
		courseMinRequirement.setStateName("MP");
		courseMinRequirement.setEducationSystemId(UUID.randomUUID().toString());
		courseMinRequirement.setEducationSystem(educationSystem);
		courseMinRequirement.setGradePoint(22.00);
		courseMinRequirement.setStudyLanguages(studyLanguages);
		courseMinRequirementDtos.add(courseMinRequirement);

		ValidList<ProviderCodeDto> listOfInstituteProviderCode = new ValidList<>();
		ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
		instituteProviderCode.setName("TestProviderName");
		instituteProviderCode.setValue(("TestProviderValue"));
		listOfInstituteProviderCode.add(instituteProviderCode);
		CourseEnglishEligibilityDto courseEnglishEligibilityDtoo = new CourseEnglishEligibilityDto();
		ValidList<CourseEnglishEligibilityDto> courseEnglishEligibilityDtoList = new ValidList<>();
		courseEnglishEligibilityDtoo.setEnglishType("demo");
		courseEnglishEligibilityDtoo.setReading(8.5);
		courseEnglishEligibilityDtoo.setWriting(9.5);
		courseEnglishEligibilityDtoo.setSpeaking(7.5);
		courseEnglishEligibilityDtoo.setListening(4.5);
		courseEnglishEligibilityDtoo.setOverall(8.5);
		courseEnglishEligibilityDtoList.add(courseEnglishEligibilityDtoo);

		List<String> linked_course_ids = new ArrayList<>();
		linked_course_ids.add("75a34cb9-1034-404c-9d7c-db704cf5b659");
		FacultyDto facultyDto = new FacultyDto("5af8b74f-9f04-45dc-81c5-36019590105c", "Facultyname",
				"facultyDescription", "facultyIcon");

		// intializing courseRequestDto.......
		CourseRequest couseRequest = new CourseRequest();
		couseRequest.setId(UUID.randomUUID().toString());
		couseRequest.setInstituteId(instituteId);
		couseRequest.setFacultyId(facultyId);
		couseRequest.setFaculty(facultyDto);
		couseRequest.setName("mycourseTestnnhi");
		couseRequest.setDescription("course Description");
		couseRequest.setLanguage(Arrays.asList("Hindi"));
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
		couseRequest.setLevelIds(levelId);
		couseRequest.setLevel(levelDto);
		couseRequest.setAvailability("always Available");
		couseRequest.setRecognition("recognize");
		couseRequest.setRecognitionType("recognize Type");
		couseRequest.setAbbreviation("abbreviation");
		couseRequest.setApplied(true);
		couseRequest.setViewCourse(true);
		couseRequest.setLatitude(33.00);
		couseRequest.setLongitude(400.00);
		couseRequest.setCountryName("INDIA");
		couseRequest.setCityName("CITY");
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
		couseRequest.setEnglishEligibility(courseEnglishEligibilityDtoList);
		couseRequest.setCourseContactPersons(courseContactPersons);
		couseRequest.setProviderCodes(listOfInstituteProviderCode);
		couseRequest.setIntake(courseIntake);
		couseRequest.setInternationalStudentProcedureId(null);
		couseRequest.setDomesticStudentProcedureId(null);
		couseRequest.setCourseMinRequirementDtos(courseMinRequirementDtos);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseRequest> entity = new HttpEntity<>(couseRequest, headers);
		ResponseEntity<CourseRequest> response = testRestTemplate.exchange(
				COURSE_PATH + PATH_SEPARATOR + instituteId + PATH_SEPARATOR + "course", HttpMethod.POST, entity,
				CourseRequest.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		return couseRequest;
	}

	InstituteEnglishRequirementsDto addInstituteEnglishRequirements() throws IOException {
		String instituteId = testCreateInstitute();
		InstituteEnglishRequirementsDto instituteEnglishRequirementsDto = new InstituteEnglishRequirementsDto();
		String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR + instituteId;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);
		instituteEnglishRequirementsDto.setInstituteId(UUID.randomUUID().toString());
		instituteEnglishRequirementsDto.setExamName("testExamName");
		instituteEnglishRequirementsDto.setListeningMarks(54.34);
		instituteEnglishRequirementsDto.setOralMarks(89.334);
		instituteEnglishRequirementsDto.setReadingMarks(67.321);
		instituteEnglishRequirementsDto.setWritingMarks(88.90);
		instituteEnglishRequirementsDto.getInstituteId();
		HttpEntity<InstituteEnglishRequirementsDto> entity = new HttpEntity<>(instituteEnglishRequirementsDto, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.POST, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		return instituteEnglishRequirementsDto;
	}

	String saveCareer() {
		Careers career = new Careers();
		career.setId(UUID.randomUUID().toString());
		career.setCareer("career");
		careerRepository.save(career);
		return career.getId();
	}

	String service() {

		Service services = new Service();
		services.setId(UUID.randomUUID().toString());
		services.setDescription("description");
		services.setName("name");
		services.setCreatedBy(userId);
		services.setCreatedOn(new Date());
		serviceRepository.save(services);
		return services.getId();

	}

	String saveCourseSemester() throws IOException {
		String instituteId = testCreateInstitute();
		CourseRequest courseId = createCourses(instituteId);
		com.yuzee.app.dto.ValidList<CourseSemesterDto> courseSemesterDtos = new com.yuzee.app.dto.ValidList<CourseSemesterDto>();
		com.yuzee.common.lib.dto.ValidList<SemesterSubjectDto> subjects = new com.yuzee.common.lib.dto.ValidList<SemesterSubjectDto>();
		SemesterSubjectDto semesterSubjectDto = new SemesterSubjectDto();
		semesterSubjectDto.setName(UUID.randomUUID().toString());
		semesterSubjectDto.setDescription("Description");
		subjects.add(semesterSubjectDto);
		CourseSemesterDto courseSemesterDto = new CourseSemesterDto();
		courseSemesterDto.setId(UUID.randomUUID().toString());
		courseSemesterDto.setType("Type");
		courseSemesterDto.setDescription("description");
		courseSemesterDto.setName(UUID.randomUUID().toString());
		courseSemesterDto.setSubjects(subjects);
		courseSemesterDtos.add(courseSemesterDto);

		List<String> linkedCourseId = new ArrayList<>();
		linkedCourseId.add(courseId.getId());

		CourseSemesterRequestWrapper request = new CourseSemesterRequestWrapper();
		request.setCourseSemesterDtos(courseSemesterDtos);
		request.setLinkedCourseIds(linkedCourseId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseSemesterRequestWrapper> entity = new HttpEntity<>(request, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + "semester", HttpMethod.POST, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		return courseSemesterDto.getId();
	}
}
