package com.yuzee.app.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
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
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.processor.CommonProcessor;
import com.yuzee.app.repository.CourseRepository;
import com.yuzee.common.lib.dto.institute.CourseContactPersonDto;
import com.yuzee.common.lib.dto.user.UserInitialInfoDto;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

@RunWith(SpringRunner.class)
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)
class CourseContactPersonTest extends CreateCourseAndInstitute {

	@Autowired
	private TestRestTemplate testRestTemplate;
	@MockBean
	private CommonProcessor commonProcessor;

	@Autowired
	private CourseRepository courseRepository;
	private String instituteId;
	private CourseRequest courseId;

	@BeforeAll
	public void createAllIntituteAndCourse() throws IOException {
		instituteId = testCreateInstitute();
		courseId = createCourses(instituteId);
	}
	

	@DisplayName("saveCourseContactPerson")
	@Test
	void saveCourseContactPerson() throws IOException {
			ValidList<CourseContactPersonDto> courseContactPersonDtos = new ValidList<CourseContactPersonDto>();
			new UserInitialInfoDto();
			CourseContactPersonDto courseContactPersonDto = new CourseContactPersonDto();
			courseContactPersonDto.setUserId(userId);
			courseContactPersonDtos.add(courseContactPersonDto);

			List<String> linkedCourseId = Arrays.asList(courseId.getId());
			CourseContactPersonRequestWrapper courseContactPersonRequestWrapper = new CourseContactPersonRequestWrapper();
			courseContactPersonRequestWrapper.setCourseContactPersonDtos(courseContactPersonDtos);
			courseContactPersonRequestWrapper.setLinkedCourseIds(linkedCourseId);
			Mockito.when(commonProcessor.validateAndGetUsersByUserIds(userId, courseContactPersonDtos.stream()
					.map(CourseContactPersonDto::getUserId).collect(Collectors.toList()))).thenReturn(new HashMap<>());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("userId", userId);
			HttpEntity<CourseContactPersonRequestWrapper> entity = new HttpEntity<>(courseContactPersonRequestWrapper,
					headers);
			ResponseEntity<CourseContactPersonRequestWrapper> response = testRestTemplate.exchange(
					api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + "contact-person", HttpMethod.POST,
					entity, CourseContactPersonRequestWrapper.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("deleteContactPerson")
	@Test
	void deleteSaveContactPerson() throws IOException {
		Map<String, List<String>> params = new HashMap<>();
		params.put("userIds", Arrays.asList(userId));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<CourseContactPersonRequestWrapper> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + "contact-person?user_ids=",
				HttpMethod.DELETE, entity, CourseContactPersonRequestWrapper.class, params);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("WrongIdContactPerson")
	@Test
	void wrongIdSaveContactPerson() {
		Map<String, List<String>> params = new HashMap<>();
		params.put("userIds", Arrays.asList(userId));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<CourseContactPersonRequestWrapper> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR + "c4c5d73b-3eaf-4528-a3bb-2e09a7000" + PATH_SEPARATOR
						+ "contact-person?user_ids=",
				HttpMethod.DELETE, entity, CourseContactPersonRequestWrapper.class, params);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
}
