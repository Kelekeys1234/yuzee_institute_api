package com.yuzee.app.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import com.yuzee.app.bean.Service;
import com.yuzee.app.controller.v1.InstituteBasicInfoController;
import com.yuzee.app.dto.InstituteFundingDto;
import com.yuzee.app.dto.InstituteRequestDto;
import com.yuzee.app.dto.InstituteServiceDto;
import com.yuzee.app.dto.ServiceDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.processor.InstituteProcessor;
import com.yuzee.app.repository.InstituteRepository;
import com.yuzee.app.repository.InstituteServiceRepository;
import com.yuzee.app.repository.ServiceRepository;
import com.yuzee.common.lib.dto.GenericWrapperDto;
import com.yuzee.common.lib.dto.institute.ProviderCodeDto;
import com.yuzee.common.lib.enumeration.EntitySubTypeEnum;
import com.yuzee.common.lib.enumeration.EntityTypeEnum;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;
import com.yuzee.common.lib.handler.StorageHandler;
import com.yuzee.common.lib.util.ObjectMapperHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(JUnitPlatform.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)
class InstituteServiceControllerTest extends CreateCourseAndInstitute {

	// cf13eef7-b188-4e84-baee-7f98a7c33e7b
	@Autowired
	private TestRestTemplate testRestTemplate;
	@MockBean
	StorageHandler storageHandler;
	@MockBean
	private PublishSystemEventHandler publishSystemEventHandler;


	@BeforeClass
	public static void main() {
		SpringApplication.run(InstituteBasicInfoController.class);
	}

	private String instituteId;

	@BeforeEach
	public void deleteAllInstitute() throws IOException {
		instituteId = testCreateInstitute();
	}

	/// institute/service/instituteId/{instituteId}
	@DisplayName("addInstituteService test success")
	@Test
	void addInstituteService() throws IOException {
		String serviceId = service();
		ServiceDto service = new ServiceDto();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);
		String path = INSTITUTE_PATH + PATH_SEPARATOR + "service" + PATH_SEPARATOR + "instituteId" + PATH_SEPARATOR
				+ instituteId;
		List<InstituteServiceDto> instituteServiceDto = new ArrayList<>();
		service.setServiceId(serviceId);
		service.setDescription("test service controller jUnit description");
		service.setServiceName("testServiceName");
		InstituteServiceDto dto = new InstituteServiceDto();
		dto.setDescription("mydescription");
		dto.setInstituteServiceId(UUID.randomUUID().toString());
		dto.setService(service);
		instituteServiceDto.add(dto);
		HttpEntity<List<InstituteServiceDto>> entity = new HttpEntity<>(instituteServiceDto, headers);
		ResponseEntity<InstituteServiceDto> response = testRestTemplate.exchange(path, HttpMethod.POST, entity,
				InstituteServiceDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("deleteById test success")
	@Test
	void deleteById() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);
		/// institute/service/{instituteServiceId}
		String instituteServiceId = "";
		String path = INSTITUTE_PATH + PATH_SEPARATOR + "service" + PATH_SEPARATOR
				+ "6dc37172-4a10-48b6-b110-6970c187238d";
		List<InstituteServiceDto> instituteServiceDto = new ArrayList<>();
		ServiceDto service = new ServiceDto();
		service.setDescription("test service controller jUnit description");
		service.setServiceName("testServiceName");
		instituteServiceDto.add(new InstituteServiceDto(instituteId, service, "testServiceDescription"));
		HttpEntity<List<InstituteServiceDto>> entity = new HttpEntity<>(instituteServiceDto, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.DELETE, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("getInstituteServiceById")
	@Test
	void getInstituteServiceById() throws IOException {
		String serviceId = service();
		ServiceDto service = new ServiceDto();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);
		String paths = INSTITUTE_PATH + PATH_SEPARATOR + "service" + PATH_SEPARATOR + "instituteId" + PATH_SEPARATOR
				+ serviceId;
		Mockito.when(storageHandler.getStorages(service.getServiceId(), EntityTypeEnum.SERVICE, EntitySubTypeEnum.LOGO))
				.thenReturn(new ArrayList());
		HttpEntity<List<InstituteServiceDto>> entityys = new HttpEntity<>(null, headers);
		ResponseEntity<String> responsess = testRestTemplate.exchange(paths, HttpMethod.GET, entityys, String.class);
		assertThat(responsess.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
