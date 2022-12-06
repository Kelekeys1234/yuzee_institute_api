package com.yuzee.app.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.yuzee.app.dto.CoursePreRequisiteRequestWrapper;
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.dto.ValidList;
import com.yuzee.common.lib.dto.institute.CoursePreRequisiteDto;

@TestInstance(Lifecycle.PER_CLASS)
class CoursePreRequisiteIdsTest extends CreateCourseAndInstitute {
	@Autowired
	private TestRestTemplate testRestTemplate;
	private String instituteId;
	private CourseRequest courseId;

	@BeforeAll
	public void deleteAllInstitute() throws IOException {
		instituteId = testCreateInstitute();
		courseId = createCourses(instituteId);
	}

	@DisplayName("saveCoursePreRequisiteDtos")
	@Test
	void savePreRequisiteIds() throws IOException {
		ValidList<CoursePreRequisiteDto> coursePreRequisiteDtos = new ValidList<CoursePreRequisiteDto>();
		List<String> linkedCourseId = Arrays.asList(courseId.getId());
		CoursePreRequisiteDto coursePreRequisiteDto = new CoursePreRequisiteDto();
		coursePreRequisiteDto.setDescription("Description");
		coursePreRequisiteDtos.add(coursePreRequisiteDto);
		CoursePreRequisiteRequestWrapper request = new CoursePreRequisiteRequestWrapper();
		request.setCoursePreRequisiteDtos(coursePreRequisiteDtos);
		request.setLinkedCourseIds(linkedCourseId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CoursePreRequisiteRequestWrapper> entity = new HttpEntity<>(request, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + "pre-requisite", HttpMethod.POST, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("WrongIdCoursePreRequisiteDtos")
	@Test
	void WrongIdPreRequisiteIds() throws IOException {
		ValidList<CoursePreRequisiteDto> coursePreRequisiteDtos = new ValidList<CoursePreRequisiteDto>();
		List<String> linkedCourseId = Arrays.asList(courseId.getId());
		CoursePreRequisiteDto coursePreRequisiteDto = new CoursePreRequisiteDto();
		coursePreRequisiteDto.setDescription("Description");
		coursePreRequisiteDtos.add(coursePreRequisiteDto);
		CoursePreRequisiteRequestWrapper request = new CoursePreRequisiteRequestWrapper();
		request.setCoursePreRequisiteDtos(coursePreRequisiteDtos);
		request.setLinkedCourseIds(linkedCourseId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CoursePreRequisiteRequestWrapper> entity = new HttpEntity<>(request, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + "pre-requisite", HttpMethod.POST, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("deleteCoursePreRequisiteDtos")
	@Test
	void deletePreRequisiteIds() throws IOException {
		ValidList<CoursePreRequisiteDto> coursePreRequisiteDtos = new ValidList<CoursePreRequisiteDto>();
		List<String> linkedCourseId = Arrays.asList(courseId.getId());
		CoursePreRequisiteDto coursePreRequisiteDto = new CoursePreRequisiteDto();
		coursePreRequisiteDto.setDescription("Description");
		coursePreRequisiteDtos.add(coursePreRequisiteDto);
		CoursePreRequisiteRequestWrapper request = new CoursePreRequisiteRequestWrapper();
		request.setCoursePreRequisiteDtos(coursePreRequisiteDtos);
		request.setLinkedCourseIds(linkedCourseId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CoursePreRequisiteRequestWrapper> entity = new HttpEntity<>(request, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + "pre-requisite", HttpMethod.POST, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
