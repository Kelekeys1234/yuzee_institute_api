
package com.yuzee.app.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
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
import com.yuzee.app.YuzeeApplication;
import com.yuzee.app.dto.CourseCareerOutcomeRequestWrapper;
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.repository.CareerRepository;
import com.yuzee.app.repository.CourseRepository;
import com.yuzee.app.repository.FacultyRepository;
import com.yuzee.app.repository.InstituteRepository;
import com.yuzee.app.repository.InstituteServiceRepository;
import com.yuzee.app.repository.LevelRepository;
import com.yuzee.app.repository.ServiceRepository;
import com.yuzee.common.lib.dto.institute.CareerDto;
import com.yuzee.common.lib.dto.institute.CourseCareerOutcomeDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(JUnitPlatform.class)
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)
class CourseCareerOutcomeTest extends CreateCourseAndInstitute {
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private InstituteRepository instituteRepository;
	@Autowired
	private CareerRepository careersRepository;
	@Autowired
	private FacultyRepository facultyRepository;
	@Autowired
	private LevelRepository levelRepository;
	@Autowired
	private ServiceRepository serviceRepository;
	@Autowired
	private InstituteServiceRepository instituteServiceRepository;
	
	private String instituteId;
	private CourseRequest courseId;
	private String careerId; 

	@BeforeAll
	public void createAllIntituteAndCourse() throws IOException {
		instituteId = testCreateInstitute();
		courseId = createCourses(instituteId);
		careerId = saveCareer();
	}


	@Autowired
	private TestRestTemplate testRestTemplate;

	@DisplayName("CourseCareerOutcome")
	@Test
	void TestSaveCourseCareerOutCome() throws IOException {
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