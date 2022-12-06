package com.yuzee.app.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.common.lib.dto.institute.CourseIntakeDto;

@TestInstance(Lifecycle.PER_CLASS)
class CourseIntakeTest extends CreateCourseAndInstitute {
	@Autowired
	private TestRestTemplate testRestTemplate;

	private String instituteId;
	private CourseRequest courseId;

	@BeforeAll
	public void createAllIntituteAndCourse() throws IOException {
		instituteId = testCreateInstitute();
		courseId = createCourses(instituteId);
	}

	@DisplayName("saveCourseIntake")
	@Test
	void saveCourseIntake() throws IOException {
		List<String> linkedCourseId = new ArrayList<>();
		linkedCourseId.add(courseId.getId());
		List<Date> date = new ArrayList<>();
		date.add(new Date());
		CourseIntakeDto courseIntake = new CourseIntakeDto();
		courseIntake.setType("SPECIFIC");
		courseIntake.setDates(date);
		courseIntake.setLinkedCourseIds(linkedCourseId);
		HttpHeaders headers = new HttpHeaders();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseIntakeDto> entity = new HttpEntity<>(courseIntake, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + "intake", HttpMethod.POST, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("sendWrongIdForSave")
	@Test
	void WrongIdForsendSaveCourseIntake() throws IOException {
		List<String> linkedCourseId = new ArrayList<>();
		linkedCourseId.add(courseId.getId());
		List<Date> date = new ArrayList<>();
		date.add(new Date());
		CourseIntakeDto courseIntake = new CourseIntakeDto();
		courseIntake.setType("SPECIFIC");
		courseIntake.setDates(date);
		courseIntake.setLinkedCourseIds(linkedCourseId);
		HttpHeaders headers = new HttpHeaders();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseIntakeDto> entity = new HttpEntity<>(courseIntake, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + "intake", HttpMethod.POST, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName("deleteCourseIntake")
	@Test
	void deleteIntake() throws IOException {
		List<String> linkedCourseId = new ArrayList<>();
		linkedCourseId.add("9230cdd1-7d12-41c6-bbd0-38e52b3595a4");
		List<Date> date = new ArrayList<>();
		date.add(new Date());
		CourseIntakeDto courseIntake = new CourseIntakeDto();
		courseIntake.setType("SPECIFIC");
		courseIntake.setDates(date);
		courseIntake.setLinkedCourseIds(linkedCourseId);
		HttpHeaders headers = new HttpHeaders();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseIntakeDto> entity = new HttpEntity<>(courseIntake, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + "intake", HttpMethod.POST, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);	}
}
