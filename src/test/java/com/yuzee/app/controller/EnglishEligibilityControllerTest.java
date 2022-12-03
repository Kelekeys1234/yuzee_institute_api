package com.yuzee.app.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dto.CourseEnglishEligibilityRequestWrapper;
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.dto.ValidList;
import com.yuzee.common.lib.dto.institute.CourseEnglishEligibilityDto;

class EnglishEligibilityControllerTest extends CreateCourseAndInstitute {

	@Autowired
	private TestRestTemplate testRestTemplate;

	private String instituteId;
    private CourseRequest courseId;

	@BeforeEach
	public void deleteAllInstitute() throws IOException {
		instituteId = testCreateInstitute();
		courseId = createCourses(instituteId);
	}

	@Autowired
	CourseDao courseDao;

	@DisplayName("Add EnglishEligibility")
	@Test
	void addEnglishEligibility() throws IOException {
	
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
			linked_course_ids.add(courseId.getId());
			requestWrapper.setCourseEnglishEligibilityDtos(courseEnglishEligibilityDtoList);
			requestWrapper.setLinkedCourseIds(linked_course_ids);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("userId", userId);

			HttpEntity<CourseEnglishEligibilityRequestWrapper> entity = new HttpEntity<>(requestWrapper, headers);
			ResponseEntity<CourseEnglishEligibilityRequestWrapper> response = testRestTemplate.exchange(
					COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR
							+ "english-eligibility",
					HttpMethod.POST, entity, CourseEnglishEligibilityRequestWrapper.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("Update EnglishEligibility")
	@Test
	void updateEnglishEligibility() throws IOException {
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
		linked_course_ids.add(courseId.getId());
		requestWrapper.setCourseEnglishEligibilityDtos(courseEnglishEligibilityDtoList);
		requestWrapper.setLinkedCourseIds(linked_course_ids);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);

		HttpEntity<CourseEnglishEligibilityRequestWrapper> entity = new HttpEntity<>(requestWrapper, headers);
		ResponseEntity<CourseEnglishEligibilityRequestWrapper> response = testRestTemplate
				.exchange(
						COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR
								+ "english-eligibility",
						HttpMethod.POST, entity, CourseEnglishEligibilityRequestWrapper.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

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
			ResponseEntity<CourseEnglishEligibilityRequestWrapper> responses = testRestTemplate.exchange(
					COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR
							+ "english-eligibility",
					HttpMethod.POST, entityy, CourseEnglishEligibilityRequestWrapper.class);
			assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("Remove EnglishEligibility")
	@Test
	void removeEnglishEligibility() throws IOException {
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
			linked_course_ids.add(courseId.getId());
			requestWrapper.setCourseEnglishEligibilityDtos(courseEnglishEligibilityDtoList);
			requestWrapper.setLinkedCourseIds(linked_course_ids);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("userId", userId);

			HttpEntity<CourseEnglishEligibilityRequestWrapper> entity = new HttpEntity<>(requestWrapper, headers);
			ResponseEntity<CourseEnglishEligibilityRequestWrapper> response = testRestTemplate.exchange(
					COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR
							+ "english-eligibility",
					HttpMethod.POST, entity, CourseEnglishEligibilityRequestWrapper.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("DELETE All EnglishEligibility")
	@Test
	void deleteAllEnglishEligibility() throws IOException {
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
			linked_course_ids.add(courseId.getId());
			requestWrapper.setCourseEnglishEligibilityDtos(courseEnglishEligibilityDtoList);
			requestWrapper.setLinkedCourseIds(linked_course_ids);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("userId", userId);

			HttpEntity<CourseEnglishEligibilityRequestWrapper> entity = new HttpEntity<>(requestWrapper, headers);
			ResponseEntity<CourseEnglishEligibilityRequestWrapper> response = testRestTemplate.exchange(
					COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR
							+ "english-eligibility",
					HttpMethod.POST, entity, CourseEnglishEligibilityRequestWrapper.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

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
		ResponseEntity<CourseEnglishEligibilityRequestWrapper> response = testRestTemplate.exchange(
				COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "ef5f52ab-d303-477f-9db4-e0633fba23bc"
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
						COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "63330d3859be418deeb59ll"
								+ PATH_SEPARATOR + "english-eligibility",
						HttpMethod.POST, entity, CourseEnglishEligibilityRequestWrapper.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}
}
