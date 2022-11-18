//package testController;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.platform.runner.JUnitPlatform;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.data.mongodb.core.mapping.Language;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.yuzee.app.YuzeeApplication;
//import com.yuzee.app.dto.CourseMinRequirementRequestWrapper;
//import com.yuzee.app.dto.CourseRequest;
//import com.yuzee.common.lib.dto.ValidList;
//import com.yuzee.common.lib.dto.institute.CourseIntakeDto;
//import com.yuzee.common.lib.dto.institute.CourseMinRequirementDto;
//import com.yuzee.common.lib.dto.institute.CourseMinRequirementSubjectDto;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RunWith(JUnitPlatform.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//@ContextConfiguration(classes = YuzeeApplication.class)
//class TestCourseMinRequirement {
//	private static final String userId = "8d7c017d-37e3-4317-a8b5-9ae6d9cdcb49";
//	private static final String Id = "1e348e15-45b6-477f-a457-883738227e06";
//	private static final String jobsId= "7132d88e-cf2c-4f48-ac6e-82214208f677";
//	private static final String api= "/api/v1/course";
//	private static final String PATH_SEPARATOR = "/";
//	private static final String  courseId="9230cdd1-7d12-41c6-bbd0-38e52b3595a4";
//	@Autowired
//	private TestRestTemplate testRestTemplate;
//	
//	@DisplayName("saveMinRequirement")
//	@Test
//	  void saveMinRequirement() {
//		try {
//	List<CourseMinRequirementDto> coursePreRequisiteDtos = new ArrayList<>();
//	ValidList<CourseMinRequirementSubjectDto> minRequirementSubjects = new ValidList<>();
//	CourseMinRequirementSubjectDto subjectDto = new CourseMinRequirementSubjectDto();
//	subjectDto.setName("myname");
//	subjectDto.setGrade("mygrade");
//	minRequirementSubjects.add(subjectDto);
//	Set<String> language = new HashSet<>();
//	language.add("English");
//	List<String>linkedCourseId = new ArrayList<>();
//	linkedCourseId.add(courseId);
//	CourseMinRequirementDto minRequirementDto = new CourseMinRequirementDto();
//	minRequirementDto.setId("1e348e15-45b6-477f-a457-883738227e01");
//	minRequirementDto .setCountryName("India");
//	minRequirementDto .setStateName("Delhi");
//	minRequirementDto .setEducationSystemId("d640121d-2f57-11ec-ad9d-023e60730569");
//	minRequirementDto.setGradePoint(12.3);
//	minRequirementDto.setMinRequirementSubjects(minRequirementSubjects);
//	minRequirementDto.setStudyLanguages(language);
//	coursePreRequisiteDtos.add(minRequirementDto);
//	CourseMinRequirementRequestWrapper request= new CourseMinRequirementRequestWrapper();
//	request.setCoursePreRequisiteDtos(coursePreRequisiteDtos);
//	request.setLinkedCourseIds(linkedCourseId );
//	HttpHeaders headers = new HttpHeaders();
//	headers.setContentType(MediaType.APPLICATION_JSON);
//	headers.add("userId", userId);
//	HttpEntity<CourseMinRequirementRequestWrapper> entity = new HttpEntity<>(request, headers);
//	ResponseEntity<String> response = testRestTemplate.exchange(
//			api +PATH_SEPARATOR+courseId
//			+PATH_SEPARATOR +"min-requirement",
//			HttpMethod.POST, entity, String.class);
//	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//	
//	}finally {
//		Map<String,String> params= new HashMap<>();
//	    params.put("course_min_requirement_ids", "1e348e15-45b6-477f-a457-883738227e01");
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.add("userId", userId);
//		HttpEntity<String> entity = new HttpEntity<>( headers);
//		ResponseEntity<String> response = testRestTemplate.exchange(
//				api +PATH_SEPARATOR+courseId
//				+PATH_SEPARATOR +"min-requirement?course_min_requirement_ids="+"1e348e15-45b6-477f-a457-883738227e01",
//				HttpMethod.DELETE, entity, String.class,params);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//	}
//	}
//	
//	@DisplayName("updateMinRequirement")
//	@Test
//	  void updateMinRequirement() {
//	List<CourseMinRequirementDto> coursePreRequisiteDtos = new ArrayList<>();
//	ValidList<CourseMinRequirementSubjectDto> minRequirementSubjects = new ValidList<>();
//	CourseMinRequirementSubjectDto subjectDto = new CourseMinRequirementSubjectDto();
////	subjectDto.setName("name");
////	subjectDto.setGrade("grade");
//	
//	Set<String> language = new HashSet<>();
//	language.add("English");
//	List<String>linkedCourseId = new ArrayList<>();
//	linkedCourseId.add(courseId);
//	CourseMinRequirementDto minRequirementDto = new CourseMinRequirementDto();
//	minRequirementDto.setId("1e348e15-45b6-477f-a457-883738227e05");
//	minRequirementDto .setCountryName("India");
//	minRequirementDto .setStateName("MP");
//	minRequirementDto .setEducationSystemId("d640121d-2f57-11ec-ad9d-023e60730568");
//	minRequirementDto.setGradePoint(15.3);
//	minRequirementDto.setMinRequirementSubjects(minRequirementSubjects);
//	minRequirementDto.setStudyLanguages(language);
//	coursePreRequisiteDtos.add(minRequirementDto);
//	CourseMinRequirementRequestWrapper request= new CourseMinRequirementRequestWrapper();
//	request.setCoursePreRequisiteDtos(coursePreRequisiteDtos);
//	request.setLinkedCourseIds(linkedCourseId );
//	HttpHeaders headers = new HttpHeaders();
//	headers.setContentType(MediaType.APPLICATION_JSON);
//	headers.add("userId", userId);
//	HttpEntity<CourseMinRequirementRequestWrapper> entity = new HttpEntity<>(request, headers);
//	ResponseEntity<String> response = testRestTemplate.exchange(
//			api +PATH_SEPARATOR+courseId
//			+PATH_SEPARATOR +"min-requirement",
//			HttpMethod.POST, entity, String.class);
//	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//	try {
//		language.add("Hindi");
//		linkedCourseId.add(courseId);
//		minRequirementDto.setId("1e348e15-45b6-477f-a457-883738227e05");
//		minRequirementDto .setCountryName("India");
//		minRequirementDto .setStateName("Pune");
//		minRequirementDto .setEducationSystemId("d640121d-2f57-11ec-ad9d-023e60730568");
//		minRequirementDto.setGradePoint(16.3);
//		minRequirementDto.setMinRequirementSubjects(minRequirementSubjects);
//		minRequirementDto.setStudyLanguages(language);
//		coursePreRequisiteDtos.add(minRequirementDto);
//		request.setCoursePreRequisiteDtos(coursePreRequisiteDtos);
//		request.setLinkedCourseIds(linkedCourseId );
//		HttpEntity<CourseMinRequirementRequestWrapper> entityy = new HttpEntity<>(request, headers);
//		ResponseEntity<String> responsed = testRestTemplate.exchange(
//				api +PATH_SEPARATOR+courseId
//				+PATH_SEPARATOR +"min-requirement",
//				HttpMethod.POST, entityy, String.class);
//		assertThat(responsed.getStatusCode()).isEqualTo(HttpStatus.OK);
//	}finally{
//		Map<String,String> params= new HashMap<>();
//	    params.put("course_min_requirement_ids", "1e348e15-45b6-477f-a457-883738227e05");
//		HttpEntity<String> entitys = new HttpEntity<>( headers);
//		ResponseEntity<String> responsee = testRestTemplate.exchange(
//				api +PATH_SEPARATOR+courseId
//				+PATH_SEPARATOR +"min-requirement?course_min_requirement_ids=1e348e15-45b6-477f-a457-883738227e05",
//				HttpMethod.DELETE, entitys, String.class,params);
//		assertThat(responsee.getStatusCode()).isEqualTo(HttpStatus.OK);
//	}
//	}
//	
//	@DisplayName("getMinRequirement")
//	@Test
//	 void getCourseMinRequirement() {
//		
//		List<CourseMinRequirementDto> coursePreRequisiteDtos = new ArrayList<>();
//		ValidList<CourseMinRequirementSubjectDto> minRequirementSubjects = new ValidList<>();
//		CourseMinRequirementSubjectDto subjectDto = new CourseMinRequirementSubjectDto();
//		subjectDto.setName("myname");
//		subjectDto.setGrade("mygrade");
//		minRequirementSubjects.add(subjectDto);
//		Set<String> language = new HashSet<>();
//		language.add("English");
//		List<String>linkedCourseId = new ArrayList<>();
//		linkedCourseId.add(courseId);
//		CourseMinRequirementDto minRequirementDto = new CourseMinRequirementDto();
//		minRequirementDto.setId("1e348e15-45b6-477f-a457-883738227e01");
//		minRequirementDto .setCountryName("India");
//		minRequirementDto .setStateName("Delhi");
//		minRequirementDto .setEducationSystemId("d640121d-2f57-11ec-ad9d-023e60730569");
//		minRequirementDto.setGradePoint(12.3);
//		minRequirementDto.setMinRequirementSubjects(minRequirementSubjects);
//		minRequirementDto.setStudyLanguages(language);
//		coursePreRequisiteDtos.add(minRequirementDto);
//		CourseMinRequirementRequestWrapper request= new CourseMinRequirementRequestWrapper();
//		request.setCoursePreRequisiteDtos(coursePreRequisiteDtos);
//		request.setLinkedCourseIds(linkedCourseId );
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.add("userId", userId);
//		HttpEntity<CourseMinRequirementRequestWrapper> entity = new HttpEntity<>(request, headers);
//		ResponseEntity<String> response = testRestTemplate.exchange(
//				api +PATH_SEPARATOR+courseId
//				+PATH_SEPARATOR +"min-requirement",
//				HttpMethod.POST, entity, String.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//	try {
//		
//		HttpEntity<String> entityy = new HttpEntity<>(headers);
//		ResponseEntity<String> responses = testRestTemplate.exchange(
//				api +PATH_SEPARATOR+courseId
//				+PATH_SEPARATOR +"min-requirement",
//				HttpMethod.GET, entityy, String.class);
//		assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
//	}finally{
//		
//		Map<String,String> params= new HashMap<>();
//	    params.put("course_min_requirement_ids", Id);
//		HttpHeaders headerss = new HttpHeaders();
//		headerss.setContentType(MediaType.APPLICATION_JSON);
//		headerss.add("userId", userId);
//		HttpEntity<String> entityt = new HttpEntity<>( headerss);
//		ResponseEntity<String> responses = testRestTemplate.exchange(
//				api +PATH_SEPARATOR+courseId
//				+PATH_SEPARATOR +"min-requirement?course_min_requirement_ids=1e348e15-45b6-477f-a457-883738227e08",
//				HttpMethod.DELETE, entityt, String.class,params);
//		assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
//	}
//	
//	}
//	
//	@DisplayName("deleteMinRequrement")
//	@Test
//    void deleteMinRequrement() {
//		
//		try {
//			List<CourseMinRequirementDto> coursePreRequisiteDtos = new ArrayList<>();
//			ValidList<CourseMinRequirementSubjectDto> minRequirementSubjects = new ValidList<>();
//			CourseMinRequirementSubjectDto subjectDto = new CourseMinRequirementSubjectDto();
//			subjectDto.setName("myname");
//			subjectDto.setGrade("mygrade");
//			minRequirementSubjects.add(subjectDto);
//			Set<String> language = new HashSet<>();
//			language.add("English");
//			List<String>linkedCourseId = new ArrayList<>();
//			linkedCourseId.add(courseId);
//			CourseMinRequirementDto minRequirementDto = new CourseMinRequirementDto();
//			minRequirementDto.setId("1e348e15-45b6-477f-a457-883738227e01");
//			minRequirementDto .setCountryName("India");
//			minRequirementDto .setStateName("Delhi");
//			minRequirementDto .setEducationSystemId("d640121d-2f57-11ec-ad9d-023e60730569");
//			minRequirementDto.setGradePoint(12.3);
//			minRequirementDto.setMinRequirementSubjects(minRequirementSubjects);
//			minRequirementDto.setStudyLanguages(language);
//			coursePreRequisiteDtos.add(minRequirementDto);
//			CourseMinRequirementRequestWrapper request= new CourseMinRequirementRequestWrapper();
//			request.setCoursePreRequisiteDtos(coursePreRequisiteDtos);
//			request.setLinkedCourseIds(linkedCourseId );
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_JSON);
//			headers.add("userId", userId);
//			HttpEntity<CourseMinRequirementRequestWrapper> entity = new HttpEntity<>(request, headers);
//			ResponseEntity<String> response = testRestTemplate.exchange(
//					api +PATH_SEPARATOR+courseId
//					+PATH_SEPARATOR +"min-requirement",
//					HttpMethod.POST, entity, String.class);
//			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//		}finally {
//		
//		Map<String,String> params= new HashMap<>();
//	    params.put("course_min_requirement_ids", Id);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.add("userId", userId);
//		HttpEntity<String> entity = new HttpEntity<>( headers);
//		ResponseEntity<String> response = testRestTemplate.exchange(
//				api +PATH_SEPARATOR+courseId
//				+PATH_SEPARATOR +"min-requirement?course_min_requirement_ids=1e348e15-45b6-477f-a457-883738227e08",
//				HttpMethod.DELETE, entity, String.class,params);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//	}
//	}
//}
