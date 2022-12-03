package com.yuzee.app.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.BeforeClass;
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
import com.yuzee.app.controller.v1.InstituteController;
import com.yuzee.app.dto.InstituteAdditionalInfoDto;
import com.yuzee.app.dto.InstituteFundingDto;
import com.yuzee.app.dto.InstituteRequestDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.processor.InstituteProcessor;
import com.yuzee.app.repository.InstituteRepository;
import com.yuzee.common.lib.dto.GenericWrapperDto;
import com.yuzee.common.lib.dto.institute.ProviderCodeDto;
import com.yuzee.common.lib.enumeration.EntitySubTypeEnum;
import com.yuzee.common.lib.enumeration.EntityTypeEnum;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;
import com.yuzee.common.lib.handler.StorageHandler;
import com.yuzee.common.lib.util.ObjectMapperHelper;

import lombok.extern.slf4j.Slf4j;

class InstituteAdditionalInfoControllerTest extends CreateCourseAndInstitute {

	private static final String entityId = UUID.randomUUID().toString();
	private static final String userId = "8d7c017d-37e3-4317-a8b5-9ae6d9cdcb49";
	private static final String INSTITUTE_PRE_PATH = "/api/v1";
	private static final String PATH_SEPARATOR = "/";
	private static final UUID IDS = UUID.fromString("1e348e15-45b6-477f-a457-883738227e05");

	@Autowired
	private TestRestTemplate testRestTemplate;

	@MockBean
	private PublishSystemEventHandler publishSystemEventHandler;
	@MockBean
	private StorageHandler storageHandler;
	@Autowired
	InstituteRepository instituteRepository;
	@Autowired
	InstituteProcessor instituteProcessor;
	private String instituteId;

	@BeforeEach
	public void deleteAllInstitute() throws IOException {
		instituteId = testCreateInstitute();
	}

	@BeforeClass
	public static void main() {
		SpringApplication.run(InstituteController.class);
	}

	/// additional/info/{instituteId}
	@DisplayName("addInstituteAdditionalInfo test success")
	@Test
	void addInstituteAdditionalInfo() throws IOException {
			InstituteAdditionalInfoDto instituteAdditionalInfoDto = new InstituteAdditionalInfoDto();
			instituteAdditionalInfoDto.setNumberOfStudent(5);
			instituteAdditionalInfoDto.setNumberOfEmployee(8);
			instituteAdditionalInfoDto.setNumberOfTeacher(2);
			instituteAdditionalInfoDto.setNumberOfClassRoom(2);
			instituteAdditionalInfoDto.setSizeOfCampus(122);
			instituteAdditionalInfoDto.setNumberOfLectureHall(2);
			instituteAdditionalInfoDto.setNumberOfFaculty(2);
			instituteAdditionalInfoDto.setRateOfEmployment(10);
			instituteAdditionalInfoDto.setAboutInfo("addUpdateInstituteAdditionalInfo");

			HttpHeaders header = new HttpHeaders();
			header.set("userId", userId);
			header.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<InstituteAdditionalInfoDto> entitys = new HttpEntity<>(instituteAdditionalInfoDto, header);
			ResponseEntity<String> responses = testRestTemplate.exchange(INSTITUTE_PRE_PATH + PATH_SEPARATOR
					+ "additional" + PATH_SEPARATOR + "info" + PATH_SEPARATOR + instituteId, HttpMethod.POST, entitys,
					String.class);
			assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("WrongIdaddInstituteAdditionalInfo")
	@Test
	void wrongIdInstituteAdditionalInfo() throws IOException {
			InstituteAdditionalInfoDto instituteAdditionalInfoDto = new InstituteAdditionalInfoDto();
			instituteAdditionalInfoDto.setNumberOfStudent(5);
			instituteAdditionalInfoDto.setNumberOfEmployee(8);
			instituteAdditionalInfoDto.setNumberOfTeacher(2);
			instituteAdditionalInfoDto.setNumberOfClassRoom(2);
			instituteAdditionalInfoDto.setSizeOfCampus(122);
			instituteAdditionalInfoDto.setNumberOfLectureHall(2);
			instituteAdditionalInfoDto.setNumberOfFaculty(2);
			instituteAdditionalInfoDto.setRateOfEmployment(10);
			instituteAdditionalInfoDto.setAboutInfo("addUpdateInstituteAdditionalInfo");

			HttpHeaders header = new HttpHeaders();
			header.set("userId", userId);
			header.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<InstituteAdditionalInfoDto> entitys = new HttpEntity<>(instituteAdditionalInfoDto, header);
			ResponseEntity<String> responses = testRestTemplate.exchange(INSTITUTE_PRE_PATH + PATH_SEPARATOR
					+ "additional" + PATH_SEPARATOR + "info" + PATH_SEPARATOR + instituteId, HttpMethod.POST, entitys,
					String.class);
			assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
		} 

	@DisplayName("getInstituteAdditionalInfo test success")
	@Test
	void getInstituteAdditionalInfo() throws IOException {
			HttpHeaders headers = new HttpHeaders();
			headers.set("userId", userId);
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entityy = new HttpEntity<>(headers);
			Mockito.when(storageHandler.getStorages(instituteId, EntityTypeEnum.INSTITUTE, EntitySubTypeEnum.ABOUT_US))
					.thenReturn(new ArrayList());

			ResponseEntity<String> responses = testRestTemplate.exchange(INSTITUTE_PRE_PATH + PATH_SEPARATOR
					+ "additional" + PATH_SEPARATOR + "info" + PATH_SEPARATOR + instituteId, HttpMethod.GET, entityy,
					String.class);
			assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("WrongIDgetInstituteAdditionalInfo")
	@Test
	void wrongIDgetInstituteAdditionalInfo() throws IOException {

		HttpHeaders headers = new HttpHeaders();
		headers.set("userId", userId);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entityy = new HttpEntity<>(headers);
		ResponseEntity<String> responses = testRestTemplate.exchange(INSTITUTE_PRE_PATH + PATH_SEPARATOR + "additional"
				+ PATH_SEPARATOR + "info" + PATH_SEPARATOR + "jdiuihfdjifnj574h53y36n5j65uy676htjgjiogu",
				HttpMethod.GET, entityy, String.class);

	}
}
