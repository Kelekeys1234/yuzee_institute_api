package com.yuzee.app.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.BeforeClass;
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
import com.yuzee.app.bean.Location;
import com.yuzee.app.controller.v1.InstituteBasicInfoController;
import com.yuzee.app.dto.FacilityDto;
import com.yuzee.app.dto.InstituteFacilityDto;
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
@Slf4j
@RunWith(JUnitPlatform.class)
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)
public class InstituteFacilitiesControllerTest extends CreateCourseAndInstitute {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@MockBean
	private PublishSystemEventHandler publishSystemEventHandler;
	@Autowired
	private InstituteProcessor instituteProcessor;
	private String instituteId;

	@BeforeAll
	public void deleteAllInstitute() throws IOException {
		instituteId = testCreateInstitute();
	}

	@BeforeClass
	public static void main() {
		SpringApplication.run(InstituteBasicInfoController.class);
	}

	// institute/facilities/{instituteId}
	@DisplayName("addInstituteFacilities test success")
	@Test
	void addInstituteFacilities() throws IOException {
		String serviceId = service();
		/// add facility
		List<FacilityDto> facilityDtoList = new ArrayList<>();
		facilityDtoList.add(new FacilityDto("fda4786c-9882-4959-83c5-293e2ff189dd", "testFacilityName", serviceId));
		InstituteFacilityDto instituteFacilityDto = new InstituteFacilityDto();
		instituteFacilityDto.setFacilities(facilityDtoList);

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		String paths = INSTITUTE_PATH + PATH_SEPARATOR + "facilities" + PATH_SEPARATOR + instituteId;
		HttpEntity<InstituteFacilityDto> entitys = new HttpEntity<>(instituteFacilityDto, header);
		ResponseEntity<InstituteFacilityDto> responses = testRestTemplate.exchange(paths, HttpMethod.POST, entitys,
				InstituteFacilityDto.class);
		assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("getInstituteFacilities test success")
	@Test
	void getInstituteFacilities() throws IOException {
			/// add facility
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String path = INSTITUTE_PATH + PATH_SEPARATOR + "getFacilities" + PATH_SEPARATOR + instituteId;
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.GET, entity, String.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("deleteInstituteFacilitiesById test success")
	@Test
	void deleteInstituteFacilitiesById() throws IOException {
			/// add facility

			Map<String, List<String>> params = new HashMap<>();
			params.put("institute_facility_id", Arrays.asList(UUID.randomUUID().toString()));
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String path = INSTITUTE_PATH + PATH_SEPARATOR + "facilities" + PATH_SEPARATOR + instituteId;
			HttpEntity<List<FacilityDto>> entity = new HttpEntity<>(null, headers);
			ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.DELETE, entity, String.class,
					params);
			// assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("getInstitutePublicFacilities test success")
	@Test
	void getInstitutePublicFacilities() throws IOException {
			/// add facility
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String path = INSTITUTE_PATH + PATH_SEPARATOR + "public" + PATH_SEPARATOR + "facilities" + PATH_SEPARATOR
					+ instituteId;
			HttpEntity<List<FacilityDto>> entity = new HttpEntity<>(null, headers);
			ResponseEntity<InstituteFacilityDto> response = testRestTemplate.exchange(path, HttpMethod.GET, entity,
					InstituteFacilityDto.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		
	}
}
