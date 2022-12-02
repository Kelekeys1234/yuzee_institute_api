
package com.yuzee.app.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.assertj.core.util.Arrays;
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
import com.yuzee.app.bean.Careers;
import com.yuzee.app.dto.CourseCareerOutcomeRequestWrapper;
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.repository.CareerRepository;
import com.yuzee.common.lib.dto.institute.CareerDto;
import com.yuzee.common.lib.dto.institute.CourseCareerOutcomeDto;
import com.yuzee.common.lib.dto.institute.CourseContactPersonDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(JUnitPlatform.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)
class CourseCareerOutcomeTest extends CreateCourseAndInstitute {
	private CourseRequest courseId;

	@AfterEach
	public void createAllCourse() throws IOException {
		String instituteId = testCreateInstitute();
		courseId = createCourses(instituteId);
	}

	@Autowired
	private TestRestTemplate testRestTemplate;

	@DisplayName("CourseCareerOutcome")
	@Test
	void TestSaveCourseCareerOutCome() throws IOException {
		String careerId = saveCareer();
		ValidList<CourseCareerOutcomeDto> courseContactPersonDtos = new ValidList<>();
		List<String> jobIds = new ArrayList<>();
		jobIds.add(courseId.getId());
		CareerDto careerDto = new CareerDto(careerId, "career", jobIds);
		CourseCareerOutcomeDto dto = new CourseCareerOutcomeDto();
		dto.setId(UUID.randomUUID().toString());
		dto.setCareerId(careerId);
		dto.setCareer(careerDto);
		courseContactPersonDtos.add(dto);
		CourseCareerOutcomeRequestWrapper request = new CourseCareerOutcomeRequestWrapper(courseContactPersonDtos,
				jobIds);
		request.setLinkedCourseIds(jobIds);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseCareerOutcomeRequestWrapper> entity = new HttpEntity<>(request, headers);
		ResponseEntity<CourseCareerOutcomeRequestWrapper> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + "career-outcome", HttpMethod.POST, entity,
				CourseCareerOutcomeRequestWrapper.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("deleteCourseCareerOutcome")
	@Test
	void deleteCourseCareerOutcome() throws IOException {
		String careerId = saveCareer();

		Map<String, String> courseCareer = new HashMap<>();
		courseCareer.put("course_career_outcome_ids", careerId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<CourseRequest> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + "career-outcome", HttpMethod.DELETE, entity,
				CourseRequest.class, courseCareer);
	}

}