//package testController;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.platform.runner.JUnitPlatform;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
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
//import com.yuzee.app.dto.CoursePreRequisiteRequestWrapper;
//import com.yuzee.app.dto.CourseSemesterRequestWrapper;
//import com.yuzee.common.lib.dto.ValidList;
//import com.yuzee.common.lib.dto.institute.CourseSemesterDto;
//import com.yuzee.common.lib.dto.institute.SemesterSubjectDto;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RunWith(JUnitPlatform.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//@ContextConfiguration(classes = YuzeeApplication.class)
// class TestCourseSemester {
//	private static final String userId = "8d7c017d-37e3-4317-a8b5-9ae6d9cdcb49";
//	private static final String Id = "1e348e15-45b6-477f-a457-883738227e05";
//	private static final String jobsId= "7132d88e-cf2c-4f48-ac6e-82214208f677";
//	private static final String api= "/api/v1/course";
//	private static final String PATH_SEPARATOR = "/";
//	@Autowired
//	private TestRestTemplate testRestTemplate;
//	
//	@DisplayName("saveCourseSemster")
//	@Test
//	  void saveCourseSemester() {
//		com.yuzee.app.dto.ValidList<CourseSemesterDto> courseSemesterDtos = new com.yuzee.app.dto.ValidList<CourseSemesterDto>();
//		ValidList<SemesterSubjectDto> subjects = new ValidList<SemesterSubjectDto> ();
//		SemesterSubjectDto semesterSubjectDto = new SemesterSubjectDto();
//		semesterSubjectDto.setName("semester name");
//		semesterSubjectDto.setDescription("Description");
//		subjects.add(semesterSubjectDto);
//		CourseSemesterDto courseSemesterDto = new CourseSemesterDto();
//		courseSemesterDto.setId(Id);
//		courseSemesterDto.setType("Type");
//		courseSemesterDto.setDescription("description");
//		courseSemesterDto.setName("semester name");
//		courseSemesterDto.setSubjects(subjects);
//		courseSemesterDtos.add(courseSemesterDto);
//		
//		List<String>linkedCourseId = new ArrayList<>();
//		linkedCourseId.add("c4c5d73b-3eaf-4528-a3bb-2e09a70007f0");
//		
//		CourseSemesterRequestWrapper request = new CourseSemesterRequestWrapper();
//		request.setCourseSemesterDtos(courseSemesterDtos);
//		request.setLinkedCourseIds(linkedCourseId);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.add("userId", userId);
//		HttpEntity<CourseSemesterRequestWrapper > entity = new HttpEntity<>(request, headers);
//		ResponseEntity<String> response = testRestTemplate.exchange(
//				api +PATH_SEPARATOR+"c4c5d73b-3eaf-4528-a3bb-2e09a70007f0"
//				+PATH_SEPARATOR +"semester",
//				HttpMethod.POST, entity, String.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//	}
//	
//	public  void deleteCourseSemester() {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.add("userId", userId);
//		HttpEntity<String> entity = new HttpEntity<>(null, headers);
//		ResponseEntity<String> response = testRestTemplate.exchange(
//				api +PATH_SEPARATOR+"96a2e11b-d64b-4964-9d28-2a4d7a41d944"
//				+PATH_SEPARATOR +"semester",
//				HttpMethod.DELETE, entity, String.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//	}
//}
