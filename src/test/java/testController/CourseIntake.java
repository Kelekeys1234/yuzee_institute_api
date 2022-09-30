package testController;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import org.springframework.test.context.junit4.SpringRunner;

import com.yuzee.app.YuzeeApplication;
import com.yuzee.app.dto.CourseFundingRequestWrapper;
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.common.lib.dto.institute.CourseIntakeDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)
public class CourseIntake {
	private static final String userId = "8d7c017d-37e3-4317-a8b5-9ae6d9cdcb49";
	private static final String Id = "1e348e15-45b6-477f-a457-883738227e05";
	private static final String jobsId= "7132d88e-cf2c-4f48-ac6e-82214208f677";
	private static final String api= "/api/v1/course";
	private static final String PATH_SEPARATOR = "/";
	@Autowired
	private TestRestTemplate testRestTemplate;
	@DisplayName("saveCourseIntake")
	@Test
	public void saveCourseIntake() {
		List<String>linkedCourseId = new ArrayList<>();
		linkedCourseId.add(Id);
		List<Date> date = new ArrayList<>();
		date.add(new Date());
		CourseIntakeDto courseIntake = new CourseIntakeDto("SPECIFIC", date,linkedCourseId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseIntakeDto> entity = new HttpEntity<>(courseIntake, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api +PATH_SEPARATOR+"96a2e11b-d64b-4964-9d28-2a4d7a41d944"
				+PATH_SEPARATOR +"intake",
				HttpMethod.POST, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@DisplayName("deleteCourseIntake")
	@Test
	public void deleteIntake() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api +PATH_SEPARATOR+"96a2e11b-d64b-4964-9d28-2a4d7a41d944"
				+PATH_SEPARATOR +"intake",
				HttpMethod.DELETE, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
