package testController;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.yuzee.app.dto.CoursePreRequisiteRequestWrapper;
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.dto.ValidList;
import com.yuzee.common.lib.dto.institute.CourseIntakeDto;
import com.yuzee.common.lib.dto.institute.CoursePreRequisiteDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(JUnitPlatform.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)
 class TestCoursePreRequisiteIds {
	private static final String userId = "8d7c017d-37e3-4317-a8b5-9ae6d9cdcb49";
	private static final String Id = "1e348e15-45b6-477f-a457-883738227e05";
	private static final String jobsId= "7132d88e-cf2c-4f48-ac6e-82214208f677";
	private static final String api= "/api/v1/course";
	private static final String PATH_SEPARATOR = "/";
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@DisplayName("saveCoursePreRequisiteDtos")
	@Test
     void savePreRequisiteIds() {
		ValidList<CoursePreRequisiteDto> coursePreRequisiteDtos = new 	ValidList<CoursePreRequisiteDto>();
		List<String> linkedCourseId = Arrays.asList("96a2e11b-d64b-4964-9d28-2a4d7a41d944");
		CoursePreRequisiteDto coursePreRequisiteDto = new CoursePreRequisiteDto();
		coursePreRequisiteDto.setDescription("Description");
		coursePreRequisiteDtos.add(coursePreRequisiteDto);
		CoursePreRequisiteRequestWrapper request=new CoursePreRequisiteRequestWrapper();
		request.setCoursePreRequisiteDtos(coursePreRequisiteDtos);
		request.setLinkedCourseIds(linkedCourseId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CoursePreRequisiteRequestWrapper> entity = new HttpEntity<>(request, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api +PATH_SEPARATOR+"96a2e11b-d64b-4964-9d28-2a4d7a41d944"
				+PATH_SEPARATOR +"pre-requisite",
				HttpMethod.POST, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@DisplayName("deleteCoursePreRequisiteDtos")
	@Test
	  void deletePreRequisiteIds() {
		Map<String,String> param= new HashMap<>();
		param.put("course_pre_requisite_ids", Id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api +PATH_SEPARATOR+"96a2e11b-d64b-4964-9d28-2a4d7a41d944"
				+PATH_SEPARATOR +"pre-requisite",
				HttpMethod.DELETE, entity,String.class,param);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
