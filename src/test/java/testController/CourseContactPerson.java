package testController;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;
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
import com.yuzee.app.dto.CourseContactPersonRequestWrapper;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.processor.CommonProcessor;
import com.yuzee.common.lib.dto.institute.CourseContactPersonDto;
import com.yuzee.common.lib.dto.user.UserInitialInfoDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)
public class CourseContactPerson {
	private static final String userId = "8d7c017d-37e3-4317-a8b5-9ae6d9cdcb49";
	private static final String api= "/api/v1/course";
	private static final String PATH_SEPARATOR = "/";
	@Autowired
	private TestRestTemplate testRestTemplate;
	@MockBean
	CommonProcessor commonProcessor;
	@DisplayName("saveCourseContactPerson")
	@Test
	public void saveCourseContactPerson() {
		ValidList<CourseContactPersonDto> courseContactPersonDtos = new ValidList<CourseContactPersonDto>();
		UserInitialInfoDto user= new UserInitialInfoDto();
		CourseContactPersonDto courseContactPersonDto = new CourseContactPersonDto(userId);
		courseContactPersonDtos.add(courseContactPersonDto);
		
		CourseContactPersonRequestWrapper courseContactPersonRequestWrapper= new CourseContactPersonRequestWrapper();
		courseContactPersonRequestWrapper.setCourseContactPersonDtos(courseContactPersonDtos);
		Mockito.when(commonProcessor.validateAndGetUsersByUserIds(userId,
				courseContactPersonDtos.stream().map(CourseContactPersonDto::getUserId).collect(Collectors.toList()))).thenReturn(new HashMap<>());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseContactPersonRequestWrapper> entity = new HttpEntity<>(courseContactPersonRequestWrapper, headers);
		ResponseEntity<CourseContactPersonRequestWrapper> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR +"8772d763-4829-4000-a860-2c79a82905c7" + PATH_SEPARATOR + "contact-person",
				HttpMethod.POST, entity, CourseContactPersonRequestWrapper.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
	}
	@DisplayName("deleteContactPerson")
	@Test
	public void deleteSaveContactPerson() {
		Map<String,List<String>> userIds= new HashMap<>();
	     List<String> userID =new ArrayList<>();
	     userID.add(userId);
		userIds.put("userIds", userID);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<CourseContactPersonRequestWrapper> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR +"8772d763-4829-4000-a860-2c79a82905c7" + PATH_SEPARATOR + "contact-person",
				HttpMethod.DELETE, entity, CourseContactPersonRequestWrapper.class,userIds);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
