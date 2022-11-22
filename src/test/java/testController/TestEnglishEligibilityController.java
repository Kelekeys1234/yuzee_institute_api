package testController;
import static org.assertj.core.api.Assertions.assertThat;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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

import com.yuzee.app.YuzeeApplication;
import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseEnglishEligibility;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dto.CourseEnglishEligibilityRequestWrapper;
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.dto.ValidList;
import com.yuzee.common.lib.dto.institute.CourseEnglishEligibilityDto;
import com.yuzee.common.lib.dto.institute.CourseMinRequirementDto;
import com.yuzee.common.lib.exception.RuntimeNotFoundException;
import com.yuzee.common.lib.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(JUnitPlatform.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)


 class TestEnglishEligibilityController {
	private static final String userId = "8d7c017d-37e3-4317-a8b5-9ae6d9cdcb49";
	private static final String PATH_SEPARATOR = "/";
	private static final String COURSE_PATH = "/api/v1";
    private static final String courseId="9230cdd1-7d12-41c6-bbd0-38e52b3595a4";
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	CourseDao courseDao;

	@DisplayName("Add EnglishEligibility")
	@Test
	  void addEnglishEligibility() {
        try {
		CourseEnglishEligibilityDto courseEnglishEligibilityDto = new CourseEnglishEligibilityDto();
		CourseEnglishEligibilityDto courseEnglishEligibilityDtoo = new CourseEnglishEligibilityDto();
		CourseEnglishEligibilityRequestWrapper requestWrapper = new CourseEnglishEligibilityRequestWrapper();
		ValidList<CourseEnglishEligibilityDto> courseEnglishEligibilityDtoList = new ValidList<>();
		courseEnglishEligibilityDtoo.setEnglishType("demo");
		courseEnglishEligibilityDtoo.setReading(7.5);
		courseEnglishEligibilityDtoo.setWriting(8.5);
		courseEnglishEligibilityDtoo.setSpeaking(7.2);
		courseEnglishEligibilityDtoo.setListening(4.5);
		courseEnglishEligibilityDtoo.setOverall(8.5);

		courseEnglishEligibilityDto.setEnglishType("easy");
		courseEnglishEligibilityDto.setReading(8.5);
		courseEnglishEligibilityDto.setWriting(8.5);
		courseEnglishEligibilityDto.setSpeaking(7.2);
		courseEnglishEligibilityDto.setListening(4.5);
		courseEnglishEligibilityDto.setOverall(8.5);

		courseEnglishEligibilityDtoList.add(courseEnglishEligibilityDto);
		courseEnglishEligibilityDtoList.add(courseEnglishEligibilityDtoo);

		List<String> linked_course_ids = new ArrayList<>();
		linked_course_ids.add(courseId);
		requestWrapper.setCourseEnglishEligibilityDtos(courseEnglishEligibilityDtoList);
		requestWrapper.setLinkedCourseIds(linked_course_ids);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);

		HttpEntity<CourseEnglishEligibilityRequestWrapper> entity = new HttpEntity<>(requestWrapper, headers);
		ResponseEntity<CourseEnglishEligibilityRequestWrapper> response = testRestTemplate
				.exchange(
						COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId
								+ PATH_SEPARATOR + "english-eligibility",
						HttpMethod.POST, entity, CourseEnglishEligibilityRequestWrapper.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
      }finally {
    	  
    	   HttpHeaders headers = new HttpHeaders();
  		   headers.setContentType(MediaType.APPLICATION_JSON);
  		   headers.set("userId", userId);
    		HttpEntity<String> entity = new HttpEntity<>(headers);
    		ResponseEntity<String> response = testRestTemplate
    				.exchange(
    						COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR
    								+ courseId + PATH_SEPARATOR + "english-eligibility",
    						HttpMethod.DELETE, entity, String.class);
    		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
      }
	}

	@DisplayName("Update EnglishEligibility")
	@Test
	 void updateEnglishEligibility() {

		CourseEnglishEligibilityDto courseEnglishEligibilityDto = new CourseEnglishEligibilityDto();
		CourseEnglishEligibilityDto courseEnglishEligibilityDtoo = new CourseEnglishEligibilityDto();
		CourseEnglishEligibilityRequestWrapper requestWrapper = new CourseEnglishEligibilityRequestWrapper();
		ValidList<CourseEnglishEligibilityDto> courseEnglishEligibilityDtoList = new ValidList<>();
		courseEnglishEligibilityDtoo.setEnglishType("demo");
		courseEnglishEligibilityDtoo.setReading(8.5);
		courseEnglishEligibilityDtoo.setWriting(9.5);
		courseEnglishEligibilityDtoo.setSpeaking(7.5);
		courseEnglishEligibilityDtoo.setListening(4.5);
		courseEnglishEligibilityDtoo.setOverall(8.5);

		courseEnglishEligibilityDto.setEnglishType("easy");
		courseEnglishEligibilityDto.setReading(8.5);
		courseEnglishEligibilityDto.setWriting(8.5);
		courseEnglishEligibilityDto.setSpeaking(7.2);
		courseEnglishEligibilityDto.setListening(4.5);
		courseEnglishEligibilityDto.setOverall(8.5);

		courseEnglishEligibilityDtoList.add(courseEnglishEligibilityDto);
		courseEnglishEligibilityDtoList.add(courseEnglishEligibilityDtoo);
 
		List<String> linked_course_ids = new ArrayList<>();
		linked_course_ids.add(courseId);
		requestWrapper.setCourseEnglishEligibilityDtos(courseEnglishEligibilityDtoList);
		requestWrapper.setLinkedCourseIds(linked_course_ids);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);

		HttpEntity<CourseEnglishEligibilityRequestWrapper> entity = new HttpEntity<>(requestWrapper, headers);
		ResponseEntity<CourseEnglishEligibilityRequestWrapper> response = testRestTemplate
				.exchange(
						COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId
								+ PATH_SEPARATOR + "english-eligibility",
						HttpMethod.POST, entity, CourseEnglishEligibilityRequestWrapper.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
       try {
    	   courseEnglishEligibilityDtoo.setEnglishType("demo");
   		   courseEnglishEligibilityDtoo.setReading(9.5);
   		   courseEnglishEligibilityDtoo.setWriting(9.5);
   		   courseEnglishEligibilityDtoo.setSpeaking(8.5);
   	       courseEnglishEligibilityDtoo.setListening(4.5);
   		   courseEnglishEligibilityDtoo.setOverall(7.5);

   		  courseEnglishEligibilityDto.setEnglishType("easy");
   		  courseEnglishEligibilityDto.setReading(9.5);
   		  courseEnglishEligibilityDto.setWriting(8.5);
   		  courseEnglishEligibilityDto.setSpeaking(7.5);
   		  courseEnglishEligibilityDto.setListening(6.5);
   		  courseEnglishEligibilityDto.setOverall(8.5);
   		courseEnglishEligibilityDtoList.add(courseEnglishEligibilityDto);
		courseEnglishEligibilityDtoList.add(courseEnglishEligibilityDtoo);
		requestWrapper.setCourseEnglishEligibilityDtos(courseEnglishEligibilityDtoList);
		requestWrapper.setLinkedCourseIds(linked_course_ids);

		HttpEntity<CourseEnglishEligibilityRequestWrapper> entityy = new HttpEntity<>(requestWrapper, headers);
		ResponseEntity<CourseEnglishEligibilityRequestWrapper> responses = testRestTemplate
				.exchange(
						COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId
								+ PATH_SEPARATOR + "english-eligibility",
						HttpMethod.POST, entityy, CourseEnglishEligibilityRequestWrapper.class);
		assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
       }finally {
    	 

    		HttpEntity<String> entitys = new HttpEntity<>(headers);
    		ResponseEntity<String> responseed = testRestTemplate
    				.exchange(
    						COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR
    								+ courseId + PATH_SEPARATOR + "english-eligibility",
    						HttpMethod.DELETE, entitys, String.class);
    		assertThat(responseed.getStatusCode()).isEqualTo(HttpStatus.OK);
    	   
       }
	}

	@DisplayName("Remove EnglishEligibility")
	@Test
	  void removeEnglishEligibility() {
      try {
		CourseEnglishEligibilityDto courseEnglishEligibilityDtoo = new CourseEnglishEligibilityDto();
		CourseEnglishEligibilityRequestWrapper requestWrapper = new CourseEnglishEligibilityRequestWrapper();
		ValidList<CourseEnglishEligibilityDto> courseEnglishEligibilityDtoList = new ValidList<>();
		courseEnglishEligibilityDtoo.setEnglishType("demo");
		courseEnglishEligibilityDtoo.setReading(8.5);
		courseEnglishEligibilityDtoo.setWriting(9.5);
		courseEnglishEligibilityDtoo.setSpeaking(7.5);
		courseEnglishEligibilityDtoo.setListening(4.5);
		courseEnglishEligibilityDtoo.setOverall(8.5);
		courseEnglishEligibilityDtoList.add(courseEnglishEligibilityDtoo);

		List<String> linked_course_ids = new ArrayList<>();
		linked_course_ids.add(courseId);
		requestWrapper.setCourseEnglishEligibilityDtos(courseEnglishEligibilityDtoList);
		requestWrapper.setLinkedCourseIds(linked_course_ids);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);

		HttpEntity<CourseEnglishEligibilityRequestWrapper> entity = new HttpEntity<>(requestWrapper, headers);
		ResponseEntity<CourseEnglishEligibilityRequestWrapper> response = testRestTemplate
				.exchange(
						COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId
								+ PATH_SEPARATOR + "english-eligibility",
						HttpMethod.POST, entity, CourseEnglishEligibilityRequestWrapper.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
     }
       finally {
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
	headers.add("userId", userId);

	HttpEntity<String> entity = new HttpEntity<>(headers);
	ResponseEntity<String> response = testRestTemplate
			.exchange(
					COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR
							+ courseId + PATH_SEPARATOR + "english-eligibility",
					HttpMethod.DELETE, entity, String.class);
	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
}
	}

	@DisplayName("DELETE All EnglishEligibility")
	@Test
	  void deleteAllEnglishEligibility() {
       try {
    	   CourseEnglishEligibilityDto courseEnglishEligibilityDtoo = new CourseEnglishEligibilityDto();
   		CourseEnglishEligibilityRequestWrapper requestWrapper = new CourseEnglishEligibilityRequestWrapper();
   		ValidList<CourseEnglishEligibilityDto> courseEnglishEligibilityDtoList = new ValidList<>();
   		courseEnglishEligibilityDtoo.setEnglishType("demo");
   		courseEnglishEligibilityDtoo.setReading(8.5);
   		courseEnglishEligibilityDtoo.setWriting(9.5);
   		courseEnglishEligibilityDtoo.setSpeaking(7.5);
   		courseEnglishEligibilityDtoo.setListening(4.5);
   		courseEnglishEligibilityDtoo.setOverall(8.5);
   		courseEnglishEligibilityDtoList.add(courseEnglishEligibilityDtoo);

   		List<String> linked_course_ids = new ArrayList<>();
   		linked_course_ids.add(courseId);
   		requestWrapper.setCourseEnglishEligibilityDtos(courseEnglishEligibilityDtoList);
   		requestWrapper.setLinkedCourseIds(linked_course_ids);

   		HttpHeaders headers = new HttpHeaders();
   		headers.setContentType(MediaType.APPLICATION_JSON);
   		headers.set("userId", userId);

   		HttpEntity<CourseEnglishEligibilityRequestWrapper> entity = new HttpEntity<>(requestWrapper, headers);
   		ResponseEntity<CourseEnglishEligibilityRequestWrapper> response = testRestTemplate
   				.exchange(
   						COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId
   								+ PATH_SEPARATOR + "english-eligibility",
   						HttpMethod.POST, entity, CourseEnglishEligibilityRequestWrapper.class);
   		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
       
       }finally{
    	   
       HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);

		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate
				.exchange(
						COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR
								+ courseId + PATH_SEPARATOR + "english-eligibility",
						HttpMethod.DELETE, entity, String.class);
      
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
       }
	}
	
	
	@DisplayName("Send empty_englishType")
	@Test
	  void sendEmptyEnglishEligibility() {

		CourseEnglishEligibilityDto courseEnglishEligibilityDtoo = new CourseEnglishEligibilityDto();
		CourseEnglishEligibilityRequestWrapper requestWrapper = new CourseEnglishEligibilityRequestWrapper();
		ValidList<CourseEnglishEligibilityDto> courseEnglishEligibilityDtoList = new ValidList<>();
		courseEnglishEligibilityDtoo.setEnglishType("");
		courseEnglishEligibilityDtoo.setReading(8.5);
		courseEnglishEligibilityDtoo.setWriting(9.5);
		courseEnglishEligibilityDtoo.setSpeaking(7.5);
		courseEnglishEligibilityDtoo.setListening(4.5);
		courseEnglishEligibilityDtoo.setOverall(8.5);
		courseEnglishEligibilityDtoList.add(courseEnglishEligibilityDtoo);

		List<String> linked_course_ids = new ArrayList<>();
		linked_course_ids.add("ef5f52ab-d303-477f-9db4-e0633fba23bc");
		requestWrapper.setCourseEnglishEligibilityDtos(courseEnglishEligibilityDtoList);
		requestWrapper.setLinkedCourseIds(linked_course_ids);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);

		HttpEntity<CourseEnglishEligibilityRequestWrapper> entity = new HttpEntity<>(requestWrapper, headers);
		ResponseEntity<CourseEnglishEligibilityRequestWrapper> response = testRestTemplate
				.exchange(
						COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "ef5f52ab-d303-477f-9db4-e0633fba23bc"
								+ PATH_SEPARATOR + "english-eligibility",
						HttpMethod.POST, entity, CourseEnglishEligibilityRequestWrapper.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

	}


	@DisplayName("Send wrong_CourseID")
	@Test
	 void sendWrongCourseId() {

		CourseEnglishEligibilityDto courseEnglishEligibilityDtoo = new CourseEnglishEligibilityDto();
		CourseEnglishEligibilityRequestWrapper requestWrapper = new CourseEnglishEligibilityRequestWrapper();
		ValidList<CourseEnglishEligibilityDto> courseEnglishEligibilityDtoList = new ValidList<>();
		courseEnglishEligibilityDtoo.setEnglishType("demo");
		courseEnglishEligibilityDtoo.setReading(8.5);
		courseEnglishEligibilityDtoo.setWriting(9.5);
		courseEnglishEligibilityDtoo.setSpeaking(7.5);
		courseEnglishEligibilityDtoo.setListening(4.5);
		courseEnglishEligibilityDtoo.setOverall(8.5);
		courseEnglishEligibilityDtoList.add(courseEnglishEligibilityDtoo);

		List<String> linked_course_ids = new ArrayList<>();
		linked_course_ids.add("ef5f52ab-d303-477f-9db4-e0633fba23bc");
		requestWrapper.setCourseEnglishEligibilityDtos(courseEnglishEligibilityDtoList);
		requestWrapper.setLinkedCourseIds(linked_course_ids);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);

		HttpEntity<CourseEnglishEligibilityRequestWrapper> entity = new HttpEntity<>(requestWrapper, headers);
		ResponseEntity<CourseEnglishEligibilityRequestWrapper> response = testRestTemplate
				.exchange(
						COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "63330d3859be418deeb59ll"
								+ PATH_SEPARATOR + "english-eligibility",
						HttpMethod.POST, entity, CourseEnglishEligibilityRequestWrapper.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}
}
