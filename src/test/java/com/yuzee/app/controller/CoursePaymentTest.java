package com.yuzee.app.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.common.lib.dto.ValidList;
import com.yuzee.common.lib.dto.institute.CourseIntakeDto;
import com.yuzee.common.lib.dto.institute.CoursePaymentDto;
import com.yuzee.common.lib.dto.institute.CoursePaymentItemDto;

import lombok.extern.slf4j.Slf4j;

class CoursePaymentTest extends CreateCourseAndInstitute {
	private static final String Id = "1e348e15-45b6-477f-a457-883738227e06";
	@Autowired
	private TestRestTemplate testRestTemplate;
	private String instituteId;
	private CourseRequest courseId;

	@BeforeEach
	public void createAllIntituteAndCourse() throws IOException {
		instituteId = testCreateInstitute();
		courseId = createCourses(instituteId);
	}

	@DisplayName("saveCoursePayment")
	@Test
	void saveCoursePayment() throws IOException {
			ValidList<CoursePaymentItemDto> paymentItems = new ValidList<>();
			CoursePaymentItemDto coursePaymentItemDto = new CoursePaymentItemDto();
			List<String> linkedCourseId = Arrays.asList(courseId.getId());
			coursePaymentItemDto.setId(courseId.getId());
			coursePaymentItemDto.setAmount(123.00);
			paymentItems.add(coursePaymentItemDto);
			coursePaymentItemDto.setName("name");

			CoursePaymentDto coursePaymentDto = new CoursePaymentDto();
			coursePaymentDto.setId(courseId.getId());
			coursePaymentDto.setDescription("Description");
			coursePaymentDto.setPaymentItems(paymentItems);
			coursePaymentDto.setLinkedCourseIds(linkedCourseId);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("userId", userId);
			HttpEntity<CoursePaymentDto> entity = new HttpEntity<>(coursePaymentDto, headers);
			ResponseEntity<String> response = testRestTemplate.exchange(
					api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + "payment", HttpMethod.POST, entity,
					String.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("saveCoursePaymentWrongId")
	@Test
	void saveCoursePaymentWrongId() throws IOException {
		ValidList<CoursePaymentItemDto> paymentItems = new ValidList<>();
		CoursePaymentItemDto coursePaymentItemDto = new CoursePaymentItemDto();
		List<String> linkedCourseId = Arrays.asList(Id);
		coursePaymentItemDto.setId(Id);
		coursePaymentItemDto.setAmount(123.00);
		paymentItems.add(coursePaymentItemDto);
		coursePaymentItemDto.setName("name");

		CoursePaymentDto coursePaymentDto = new CoursePaymentDto();
		coursePaymentDto.setId(Id);
		coursePaymentDto.setDescription("Description");
		coursePaymentDto.setPaymentItems(paymentItems);
		coursePaymentDto.setLinkedCourseIds(linkedCourseId);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CoursePaymentDto> entity = new HttpEntity<>(coursePaymentDto, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR + courseId.getCountryName() + PATH_SEPARATOR + "payment", HttpMethod.DELETE,
				entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@DisplayName("deleteCoursePayment")
	@Test
	void delete() throws IOException {
			ValidList<CoursePaymentItemDto> paymentItems = new ValidList<>();
			CoursePaymentItemDto coursePaymentItemDto = new CoursePaymentItemDto();
			List<String> linkedCourseId = Arrays.asList(courseId.getId());
			coursePaymentItemDto.setId(courseId.getId());
			coursePaymentItemDto.setAmount(123.00);
			paymentItems.add(coursePaymentItemDto);
			coursePaymentItemDto.setName("name");

			CoursePaymentDto coursePaymentDto = new CoursePaymentDto();
			coursePaymentDto.setId(courseId.getId());
			coursePaymentDto.setDescription("Description");
			coursePaymentDto.setPaymentItems(paymentItems);
			coursePaymentDto.setLinkedCourseIds(linkedCourseId);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("userId", userId);
			HttpEntity<CoursePaymentDto> entity = new HttpEntity<>(coursePaymentDto, headers);
			ResponseEntity<String> response = testRestTemplate.exchange(
					api + PATH_SEPARATOR + courseId.getId() + PATH_SEPARATOR + "payment", HttpMethod.POST, entity,
					String.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("deleteCoursePaymentWrongId")
	@Test
	void deleteWithWrongId() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR + "96a2e11b-d64b-4964-9d28-2a4d7a41d85" + PATH_SEPARATOR + "payment",
				HttpMethod.DELETE, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@DisplayName("deleteEmptyCoursePayment")
	@Test
	void deleteEmpty() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR + "ef5f52ab-d303-477f-9db4-e0633fba23bc" + PATH_SEPARATOR + "payment",
				HttpMethod.DELETE, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

}
