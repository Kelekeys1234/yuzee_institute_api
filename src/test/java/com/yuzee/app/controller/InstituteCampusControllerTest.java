package com.yuzee.app.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
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
import com.yuzee.app.bean.InstituteCampus;
import com.yuzee.app.bean.Location;
import com.yuzee.app.controller.v1.InstituteController;
import com.yuzee.app.dto.InstituteFundingDto;
import com.yuzee.app.dto.InstituteRequestDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.processor.InstituteProcessor;
import com.yuzee.app.repository.InstituteCampusRepository;
import com.yuzee.app.repository.InstituteRepository;
import com.yuzee.common.lib.dto.GenericWrapperDto;
import com.yuzee.common.lib.dto.institute.ProviderCodeDto;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;
import com.yuzee.common.lib.util.ObjectMapperHelper;

import lombok.extern.slf4j.Slf4j;

@TestInstance(Lifecycle.PER_CLASS)
class InstituteCampusControllerTest extends CreateCourseAndInstitute {

	@Autowired
	private TestRestTemplate testRestTemplate;
	@Autowired
	InstituteRepository instituteRepository;
	@MockBean
	private PublishSystemEventHandler publishSystemEventHandler;
	@Autowired
	private InstituteCampusRepository campus;

	@Autowired
	private InstituteProcessor instituteProcessor;

	private String instituteId;

	@BeforeAll
	public void createAllInstitute() throws IOException {
		instituteId = testCreateInstitute();
	}
	  @AfterAll
	public void deleteCampus() {
		  campus.deleteAll();
	}

	@BeforeClass
	public static void main() {
		SpringApplication.run(InstituteController.class);
	}

	/// campus/instituteId/{instituteId}
	@DisplayName("addCampus test success")
	@Test
	void addCampus() throws IOException {
		HttpHeaders header = new HttpHeaders();

		header.set("userId", userId);
		header.setContentType(MediaType.APPLICATION_JSON);
		List<String> instituteIds = Arrays.asList(userId, instituteId, instituteId);
		HttpEntity<List<String>> entitys = new HttpEntity<>(instituteIds, header);
		String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "campus" + PATH_SEPARATOR + "instituteId" + PATH_SEPARATOR
				+ instituteId;
		ResponseEntity<String> responsess = testRestTemplate.exchange(path, HttpMethod.POST, entitys, String.class);
		assertThat(responsess.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("getInstituteCampuses test success")
	@Test

	void getInstituteCampuses() throws IOException {
		// create new campus
		/*
		 * HttpHeaders header = new HttpHeaders();
		 * 
		 * header.set("userId", userId);
		 * header.setContentType(MediaType.APPLICATION_JSON); List<String> instituteIds
		 * = Arrays.asList(userId, "1e348e15-45b6-477f-a457-883738227e05");
		 * HttpEntity<List<String>> entitys = new HttpEntity<>(instituteIds, header);
		 * String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "campus" + PATH_SEPARATOR
		 * + "instituteId" + PATH_SEPARATOR + PATH_SEPARATOR + data.getInstituteId();
		 * ResponseEntity<String> responses = testRestTemplate.exchange(path,
		 * HttpMethod.POST, entitys, String.class);
		 * assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
		 * InstituteCampus instituteCampus = new InstituteCampus();
		 */
		// instituteCampus.setId(UUID.randomUUID().toString());
		HttpHeaders headerr = new HttpHeaders();
		headerr.set("userId", userId);
		headerr.setContentType(MediaType.APPLICATION_JSON);
		List<String> instituteIdss = Arrays.asList(userId, instituteId);
		HttpEntity<List<String>> entityss = new HttpEntity<>(instituteIdss, headerr);
		String paths = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "campus" + PATH_SEPARATOR + "instituteId" + PATH_SEPARATOR
				+ instituteId;
		ResponseEntity<String> responsess = testRestTemplate.exchange(paths, HttpMethod.GET, entityss, String.class);
		assertThat(responsess.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("deleteInstituteCampuses test success")
	@Test
	void removeCampus() throws IOException {
		HttpHeaders heade = new HttpHeaders();
		heade.set("userId", userId);
		heade.setContentType(MediaType.APPLICATION_JSON);
		List<String> instituteIds = Arrays.asList(userId, instituteId, instituteId);
		HttpEntity<List<String>> entitys = new HttpEntity<>(instituteIds, heade);
		String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "campus" + PATH_SEPARATOR + "instituteId" + PATH_SEPARATOR
				+ instituteId;
		ResponseEntity<String> responses = testRestTemplate.exchange(path, HttpMethod.DELETE, entitys, String.class);
		// assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}