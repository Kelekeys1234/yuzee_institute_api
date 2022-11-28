package com.yuzee.app.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.yuzee.app.YuzeeApplication;
import com.yuzee.app.dto.CourseCareerOutcomeRequestWrapper;
import com.yuzee.app.dto.CourseFundingRequestWrapper;
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.processor.CommonProcessor;
import com.yuzee.common.lib.dto.institute.CareerDto;
import com.yuzee.common.lib.dto.institute.CourseCareerOutcomeDto;
import com.yuzee.common.lib.dto.institute.CourseContactPersonDto;
import com.yuzee.common.lib.dto.institute.CourseFundingDto;

import lombok.extern.slf4j.Slf4j;

class CourseFundingTest extends CreateCourseAndInstitute {

	private static final String Id = "1e348e15-45b6-477f-a457-883738227e05";

	@Autowired
	private TestRestTemplate testRestTemplate;
	@MockBean
	CommonProcessor commonProcessor;

	@DisplayName("savecourseFunding")
	@Test
	void saveCourseFunding() throws IOException {
		String instituteId = testCreateInstitute();
		CourseRequest courseId = createCourses(instituteId);
		List<String> fundingId = new ArrayList<>();
		fundingId.add(Id);
		Map<String, String> courseCareer = new HashMap<>();
		courseCareer.put("course_career_outcome_ids", Id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		Mockito.when(commonProcessor.getFundingsByFundingNameIds(fundingId, true)).thenReturn(new HashMap<>());
		HttpEntity<List<String>> entity = new HttpEntity<>(fundingId, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(api + PATH_SEPARATOR + "funding" + PATH_SEPARATOR
				+ "instituteId" + PATH_SEPARATOR + instituteId + PATH_SEPARATOR + "add-funding-to-all-courses",
				HttpMethod.POST, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("saveAllcourseFunding")
	@Test
	void saveAllFunding() throws IOException {
		String instituteId = testCreateInstitute();
		CourseRequest courseId = createCourses(instituteId);
		ValidList<CourseFundingDto> courseFundingDtos = new ValidList<>();
		CourseFundingRequestWrapper request = new CourseFundingRequestWrapper();
		List<String> fundingId = new ArrayList<>();
		fundingId.add(Id);
		CourseFundingDto courseFundingDto = new CourseFundingDto();
		courseFundingDto.setFundingNameId(Id);
		courseFundingDtos.add(courseFundingDto);
		request.setCourseFundingDtos(courseFundingDtos);
		Mockito.when(commonProcessor.getFundingsByFundingNameIds(fundingId, true)).thenReturn(new HashMap<>());
		Map<String, String> courseCareer = new HashMap<>();
		courseCareer.put("course_career_outcome_ids", Id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseFundingRequestWrapper> entity = new HttpEntity<>(request, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + "funding", HttpMethod.POST, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	public void deleteFunding() throws IOException {
		String instituteId = testCreateInstitute();
		CourseRequest courseId = createCourses(instituteId);
		Map<String, String> courseCareer = new HashMap<>();
		courseCareer.put("course_career_outcome_ids", Id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<CourseRequest> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + "career-outcome", HttpMethod.DELETE, entity,
				CourseRequest.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
