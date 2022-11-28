
package com.yuzee.app.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.yuzee.app.YuzeeApplication;
import com.yuzee.app.bean.Location;
import com.yuzee.app.controller.v1.InstituteBasicInfoController;
import com.yuzee.app.dto.InstituteContactInfoDto;
import com.yuzee.app.dto.InstituteFundingDto;
import com.yuzee.app.dto.InstituteRequestDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.processor.InstituteProcessor;
import com.yuzee.app.repository.InstituteRepository;
import com.yuzee.common.lib.dto.GenericWrapperDto;
import com.yuzee.common.lib.dto.institute.ProviderCodeDto;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;
import com.yuzee.common.lib.util.ObjectMapperHelper;

import lombok.extern.slf4j.Slf4j;

public class InstituteContactInfoControllerTest extends CreateCourseAndInstitute {

	private static final String entityId = UUID.randomUUID().toString();
	private static final String instituteId = "a2a00b2a-6b2d-41f1-8501-8ba882ee2b2a";
	private static final String INSTITUTE_ID = "instituteId";
	private static final String userId = "8d7c017d-37e3-4317-a8b5-9ae6d9cdcb49";
	private static final String USER_ID = "userId";
	private static final String INSTITUTE_PRE_PATH = "/api/v1";
	private static final String INSTITUTE_PATH = INSTITUTE_PRE_PATH + "/institute";
	private static final String PATH_SEPARATOR = "/";
	private static final String PAGE_NUMBER_PATH = "/pageNumber";
	private static final String PAGE_SIZE_PATH = "/pageSize";

	@Autowired
	private TestRestTemplate testRestTemplate;
	@Autowired
	InstituteProcessor instituteProcessor;

	@MockBean
	private PublishSystemEventHandler publishSystemEventHandler;

	@BeforeClass
	public static void main() {
		SpringApplication.run(InstituteBasicInfoController.class);
	}

	/// contact/info/{instituteId}
	@DisplayName("addUpdateInstituteContactInfo test success")
	@Test
	void addUpdateInstituteContactInfo() throws IOException {
		String instituteId = testCreateInstitute();
		try {
			String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "contact" + PATH_SEPARATOR + "info" + PATH_SEPARATOR
					+ instituteId;
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			header.set("userId", userId);
			InstituteContactInfoDto instituteContactInfoDto = new InstituteContactInfoDto();
			instituteContactInfoDto.setEmail("testContrr@email.com");
			instituteContactInfoDto.setContactNumber("5428923957");
			instituteContactInfoDto.setLatitude(82.0);
			instituteContactInfoDto.setLongitude(93.5);
			instituteContactInfoDto.setWebsite("testWebsite.com");
			instituteContactInfoDto.setAddress("test t-8, new test road, testNagar");
			instituteContactInfoDto.setAverageLivingCost("4500");
			instituteContactInfoDto.setOfficeHoursFrom("8 O'clock");
			instituteContactInfoDto.setOfficeHoursTo("17 O'clock");
			HttpEntity<InstituteContactInfoDto> entitys = new HttpEntity<>(instituteContactInfoDto, header);

			ResponseEntity<String> responses = testRestTemplate.exchange(path, HttpMethod.POST, entitys, String.class);

			assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
		} finally {
			// clean up code
			instituteProcessor.deleteInstitute(instituteId);
		}
	}

	@DisplayName("WrongidaddUpdateInstituteContactInfo test success")
	@Test
	void wrongIdaddUpdateInstituteContactInfo() throws IOException {
		String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "contact" + PATH_SEPARATOR + "info" + PATH_SEPARATOR
				+ "98ae5e1d-7499-43cb-a18d-86d4";
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.set("userId", userId);
		InstituteContactInfoDto instituteContactInfoDto = new InstituteContactInfoDto();
		instituteContactInfoDto.setEmail("testContrr@email.com");
		instituteContactInfoDto.setContactNumber("5428923957");
		instituteContactInfoDto.setLatitude(32.2);
		instituteContactInfoDto.setLongitude(42.5);
		instituteContactInfoDto.setWebsite("testWebsite.com");
		instituteContactInfoDto.setAddress("test t-8, new test road, testNagar");
		instituteContactInfoDto.setAverageLivingCost("4500");
		instituteContactInfoDto.setOfficeHoursFrom("8 O'clock");
		instituteContactInfoDto.setOfficeHoursTo("17 O'clock");
		HttpEntity<InstituteContactInfoDto> entitys = new HttpEntity<>(instituteContactInfoDto, header);

		ResponseEntity<String> responses = testRestTemplate.exchange(path, HttpMethod.POST, entitys, String.class);

		assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}

	@DisplayName("getInstituteContactInfo test success")
	@Test
	void getInstituteContactInfo() throws IOException {
		String instituteId = testCreateInstitute();
		try {
			String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "contact" + PATH_SEPARATOR + "info" + PATH_SEPARATOR
					+ instituteId;
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			header.set("userId", userId);
			HttpEntity<InstituteContactInfoDto> entitys = new HttpEntity<>(header);
			ResponseEntity<String> responses = testRestTemplate.exchange(path, HttpMethod.GET, entitys, String.class);
			assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
		} finally {
			// clean up code

			instituteProcessor.deleteInstitute(instituteId);

		}

	}

	@DisplayName("WrongIdgetInstituteContactInfo")
	@Test
	void wrongIdgetInstituteContactInfo() throws IOException {

		String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "contact" + PATH_SEPARATOR + "info" + PATH_SEPARATOR
				+ "54317df3-5515-4341-bf15-810818";
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.set("userId", userId);
		HttpEntity<InstituteContactInfoDto> entitys = new HttpEntity<>(header);
		ResponseEntity<String> responses = testRestTemplate.exchange(path, HttpMethod.GET, entitys, String.class);
		assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}

	@DisplayName("getInstitutePublicContactInfo test success")
	@Test
	void getInstitutePublicContactInfo() throws IOException {
		String instituteId = testCreateInstitute();
		try {
			String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "public" + PATH_SEPARATOR + "contact" + PATH_SEPARATOR
					+ "info" + PATH_SEPARATOR + instituteId;
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			header.set("userId", userId);
			HttpEntity<InstituteContactInfoDto> entitys = new HttpEntity<>(header);
			ResponseEntity<String> respons = testRestTemplate.exchange(path, HttpMethod.GET, entitys, String.class);
			assertThat(respons.getStatusCode()).isEqualTo(HttpStatus.OK);
		} finally {
			// clean up code
			instituteProcessor.deleteInstitute(instituteId);

		}
	}
}
