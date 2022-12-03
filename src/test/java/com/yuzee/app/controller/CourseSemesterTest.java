package com.yuzee.app.controller;

import static org.assertj.core.api.Assertions.assertThat;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
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
import com.yuzee.app.dto.CoursePreRequisiteRequestWrapper;
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.dto.CourseSemesterRequestWrapper;
import com.yuzee.common.lib.dto.ValidList;
import com.yuzee.common.lib.dto.institute.CourseSemesterDto;
import com.yuzee.common.lib.dto.institute.SemesterSubjectDto;

import lombok.extern.slf4j.Slf4j;


 class CourseSemesterTest extends CreateCourseAndInstitute{
	private static final String userId = "8d7c017d-37e3-4317-a8b5-9ae6d9cdcb49";
	private static final String Id = "1e348e15-45b6-477f-a457-883738227e05";
	private static final String jobsId= "7132d88e-cf2c-4f48-ac6e-82214208f677";
	private static final String api= "/api/v1/course";
	private static final String PATH_SEPARATOR = "/";
	private static final String courseid="9230cdd1-7d12-41c6-bbd0-38e52b3595a4";
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@DisplayName("saveCourseSemster")
	@Test
	  void saveCourseSemesters() throws IOException {
		saveCourseSemester();
	}
	private String instituteId;
    private CourseRequest courseId;

	@BeforeEach
	public void deleteAllInstitute() throws IOException {
		instituteId = testCreateInstitute();
		courseId = createCourses(instituteId);
	}
	
	@DisplayName("deleteCourseSemster")
	@Test
	  void deleteCourseSemester() throws IOException {
		String semesterId= saveCourseSemester();
	
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api +PATH_SEPARATOR+ courseId.getId()
				+PATH_SEPARATOR +"semester",
				HttpMethod.DELETE, entity, String.class);
		
		}
	
	@DisplayName("EmptyCourseSemster")
	@Test
	  void emptyCourseSemester() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api +PATH_SEPARATOR+"829af0d4-8b28-4f8b-82b1-7b32f1308967"
				+PATH_SEPARATOR +"semester?=course_semester_ids=1e348e15-45b6-477f-a457-883738227",
				HttpMethod.DELETE, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
}
