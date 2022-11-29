package com.yuzee.app.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.mapping.Language;
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
import com.yuzee.app.dto.CourseMinRequirementRequestWrapper;
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.common.lib.dto.ValidList;
import com.yuzee.common.lib.dto.institute.CourseIntakeDto;
import com.yuzee.common.lib.dto.institute.CourseMinRequirementDto;
import com.yuzee.common.lib.dto.institute.CourseMinRequirementSubjectDto;

import lombok.extern.slf4j.Slf4j;

class CourseMinRequirementTest extends CreateCourseAndInstitute {
	private static final String userId = "8d7c017d-37e3-4317-a8b5-9ae6d9cdcb49";
	private static final String Id = "1e348e15-45b6-477f-a457-883738227e06";
	private static final String jobsId = "7132d88e-cf2c-4f48-ac6e-82214208f677";
	private static final String api = "/api/v1/course";
	private static final String PATH_SEPARATOR = "/";
	private static final String courseId = "9230cdd1-7d12-41c6-bbd0-38e52b3595a4";
	@Autowired
	private TestRestTemplate testRestTemplate;

	@DisplayName("saveMinRequirement")
	@Test
	void saveMinRequirement() throws IOException {
		String instituteId = testCreateInstitute();
		CourseRequest courseId = createCourses(instituteId);
		List<CourseMinRequirementDto> coursePreRequisiteDtos = new ArrayList<>();
		ValidList<CourseMinRequirementSubjectDto> minRequirementSubjects = new ValidList<>();
		CourseMinRequirementSubjectDto subjectDto = new CourseMinRequirementSubjectDto();
		subjectDto.setName("name");
		subjectDto.setGrade("grade");
		try {
			Set<String> language = new HashSet<>();
			language.add("English");
			List<String> linkedCourseId = new ArrayList<>();
			linkedCourseId.add(courseId.getId());
			CourseMinRequirementDto minRequirementDto = new CourseMinRequirementDto();
			minRequirementDto.setId(UUID.randomUUID().toString());
			minRequirementDto.setCountryName("India");
			minRequirementDto.setStateName("MP");
			minRequirementDto.setEducationSystemId(UUID.randomUUID().toString());
			minRequirementDto.setGradePoint(15.3);
			minRequirementDto.setMinRequirementSubjects(minRequirementSubjects);
			minRequirementDto.setStudyLanguages(language);
			coursePreRequisiteDtos.add(minRequirementDto);
			CourseMinRequirementRequestWrapper request = new CourseMinRequirementRequestWrapper();
			request.setCoursePreRequisiteDtos(coursePreRequisiteDtos);
			request.setLinkedCourseIds(linkedCourseId);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("userId", userId);
			HttpEntity<CourseMinRequirementRequestWrapper> entity = new HttpEntity<>(request, headers);
			ResponseEntity<String> response = testRestTemplate.exchange(
					api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + "min-requirement", HttpMethod.POST,
					entity, String.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		} finally {
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			header.add("userId", userId);
			HttpEntity<String> entityer = new HttpEntity<>(header);
			ResponseEntity<String> responseds = testRestTemplate.exchange(
					COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + courseId,
					HttpMethod.DELETE, entityer, String.class);
			assertThat(responseds.getStatusCode()).isEqualTo(HttpStatus.OK);
		}
	}

	@DisplayName("updateMinRequirement")
	@Test
	void updateMinRequirement() throws IOException {
		String instituteId = testCreateInstitute();
		CourseRequest courseId = createCourses(instituteId);
		List<CourseMinRequirementDto> coursePreRequisiteDtos = new ArrayList<>();
		ValidList<CourseMinRequirementSubjectDto> minRequirementSubjects = new ValidList<>();
		CourseMinRequirementSubjectDto subjectDto = new CourseMinRequirementSubjectDto();
		subjectDto.setName("name");
		subjectDto.setGrade("grade");
		Set<String> language = new HashSet<>();
		language.add("English");
		List<String> linkedCourseId = new ArrayList<>();
		linkedCourseId.add(courseId.getId());
		CourseMinRequirementDto minRequirementDto = new CourseMinRequirementDto();
		minRequirementDto.setId(UUID.randomUUID().toString());
		minRequirementDto.setCountryName("India");
		minRequirementDto.setStateName("MP");
		minRequirementDto.setEducationSystemId(UUID.randomUUID().toString());
		minRequirementDto.setGradePoint(15.3);
		minRequirementDto.setMinRequirementSubjects(minRequirementSubjects);
		minRequirementDto.setStudyLanguages(language);
		coursePreRequisiteDtos.add(minRequirementDto);
		CourseMinRequirementRequestWrapper request = new CourseMinRequirementRequestWrapper();
		request.setCoursePreRequisiteDtos(coursePreRequisiteDtos);
		request.setLinkedCourseIds(linkedCourseId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseMinRequirementRequestWrapper> entity = new HttpEntity<>(request, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + "min-requirement", HttpMethod.POST, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		try {
			language.add("Hindi");
			linkedCourseId.add(courseId.getId());
			minRequirementDto.setId(minRequirementDto.getId());
			minRequirementDto.setCountryName("India");
			minRequirementDto.setStateName("Pune");
			minRequirementDto.setEducationSystemId(minRequirementDto.getEducationSystemId());
			minRequirementDto.setGradePoint(16.3);
			minRequirementDto.setMinRequirementSubjects(minRequirementSubjects);
			minRequirementDto.setStudyLanguages(language);
			coursePreRequisiteDtos.add(minRequirementDto);
			request.setCoursePreRequisiteDtos(coursePreRequisiteDtos);
			request.setLinkedCourseIds(linkedCourseId);
			HttpEntity<CourseMinRequirementRequestWrapper> entityy = new HttpEntity<>(request, headers);
			ResponseEntity<String> responsed = testRestTemplate.exchange(
					api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + "min-requirement", HttpMethod.POST, entityy,
					String.class);
			assertThat(responsed.getStatusCode()).isEqualTo(HttpStatus.OK);
		} finally {
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			header.add("userId", userId);
			HttpEntity<String> entityer = new HttpEntity<>(header);
			ResponseEntity<String> responseds = testRestTemplate.exchange(
					COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + courseId,
					HttpMethod.DELETE, entityer, String.class);
			assertThat(responseds.getStatusCode()).isEqualTo(HttpStatus.OK);
		}
	}

	@DisplayName("getMinRequirement")
	@Test
	void getCourseMinRequirement() throws IOException {
		String instituteId = testCreateInstitute();
		CourseRequest courseId = createCourses(instituteId);
		List<CourseMinRequirementDto> coursePreRequisiteDtos = new ArrayList<>();
		ValidList<CourseMinRequirementSubjectDto> minRequirementSubjects = new ValidList<>();
		CourseMinRequirementSubjectDto subjectDto = new CourseMinRequirementSubjectDto();
		Set<String> language = new HashSet<>();
		language.add("English");
		List<String> linkedCourseId = new ArrayList<>();
		linkedCourseId.add(courseId.getId());
		CourseMinRequirementDto minRequirementDto = new CourseMinRequirementDto();
		minRequirementDto.setId(UUID.randomUUID().toString());
		minRequirementDto.setCountryName("India");
		minRequirementDto.setStateName("MP");
		minRequirementDto.setEducationSystemId(UUID.randomUUID().toString());
		minRequirementDto.setGradePoint(15.3);
		minRequirementDto.setMinRequirementSubjects(minRequirementSubjects);
		minRequirementDto.setStudyLanguages(language);
		coursePreRequisiteDtos.add(minRequirementDto);
		CourseMinRequirementRequestWrapper request = new CourseMinRequirementRequestWrapper();
		request.setCoursePreRequisiteDtos(coursePreRequisiteDtos);
		request.setLinkedCourseIds(linkedCourseId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseMinRequirementRequestWrapper> entity = new HttpEntity<>(request, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + "min-requirement", HttpMethod.POST, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		try {

			HttpEntity<String> entityy = new HttpEntity<>(headers);
			ResponseEntity<String> responses = testRestTemplate.exchange(
					api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + "min-requirement", HttpMethod.GET,
					entityy, String.class);
			assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
		} finally {

			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			header.add("userId", userId);
			HttpEntity<String> entityer = new HttpEntity<>(header);
			ResponseEntity<String> responseds = testRestTemplate.exchange(
					COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + courseId,
					HttpMethod.DELETE, entityer, String.class);
			assertThat(responseds.getStatusCode()).isEqualTo(HttpStatus.OK);
		}

	}

	@DisplayName("deleteMinRequrement")
	@Test
	void deleteMinRequrement() throws IOException {
		String instituteId = testCreateInstitute();
		CourseRequest courseId = createCourses(instituteId);
		List<CourseMinRequirementDto> coursePreRequisiteDtos = new ArrayList<>();
		ValidList<CourseMinRequirementSubjectDto> minRequirementSubjects = new ValidList<>();
		CourseMinRequirementSubjectDto subjectDto = new CourseMinRequirementSubjectDto();
		subjectDto.setName("name");
		subjectDto.setGrade("grade");
		Set<String> language = new HashSet<>();
		language.add("English");
		List<String> linkedCourseId = new ArrayList<>();
		linkedCourseId.add(courseId.getId());
		CourseMinRequirementDto minRequirementDto = new CourseMinRequirementDto();
		minRequirementDto.setId(UUID.randomUUID().toString());
		minRequirementDto.setCountryName("India");
		minRequirementDto.setStateName("MP");
		minRequirementDto.setEducationSystemId(UUID.randomUUID().toString());
		minRequirementDto.setGradePoint(15.3);
		minRequirementDto.setMinRequirementSubjects(minRequirementSubjects);
		minRequirementDto.setStudyLanguages(language);
		coursePreRequisiteDtos.add(minRequirementDto);
		CourseMinRequirementRequestWrapper request = new CourseMinRequirementRequestWrapper();
		request.setCoursePreRequisiteDtos(coursePreRequisiteDtos);
		request.setLinkedCourseIds(linkedCourseId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseMinRequirementRequestWrapper> entity = new HttpEntity<>(request, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + "min-requirement", HttpMethod.POST, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		try {
			Map<String, String> params = new HashMap<>();
			params.put("course_min_requirement_ids", Id);
			HttpEntity<String> entityy = new HttpEntity<>(headers);
			ResponseEntity<String> responses = testRestTemplate
					.exchange(
							api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR
									+ "min-requirement?course_min_requirement_ids=" + minRequirementDto.getId(),
							HttpMethod.DELETE, entityy, String.class, params);
			assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
		}

		finally {
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			header.add("userId", userId);
			HttpEntity<String> entityer = new HttpEntity<>(header);
			ResponseEntity<String> responseds = testRestTemplate.exchange(
					COURSE + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + courseId,
					HttpMethod.DELETE, entityer, String.class);
			assertThat(responseds.getStatusCode()).isEqualTo(HttpStatus.OK);

		}
	}
}
