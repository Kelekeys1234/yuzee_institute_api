
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
import com.yuzee.app.controller.v1.InstituteBasicInfoController;
import com.yuzee.app.dto.InstituteFundingDto;
import com.yuzee.app.dto.InstituteRequestDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.processor.InstituteProcessor;
import com.yuzee.app.repository.InstituteRepository;
import com.yuzee.common.lib.dto.GenericWrapperDto;
import com.yuzee.common.lib.dto.institute.InstituteBasicInfoDto;
import com.yuzee.common.lib.dto.institute.ProviderCodeDto;
import com.yuzee.common.lib.enumeration.EntitySubTypeEnum;
import com.yuzee.common.lib.enumeration.EntityTypeEnum;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;
import com.yuzee.common.lib.handler.ReviewHandler;
import com.yuzee.common.lib.handler.StorageHandler;
import com.yuzee.common.lib.util.ObjectMapperHelper;

import lombok.extern.slf4j.Slf4j;


public class InstituteBasicInfoControllerTest extends CreateCourseAndInstitute{

	@Autowired
	private TestRestTemplate testRestTemplate;

	@MockBean
	private PublishSystemEventHandler publishSystemEventHandler;
	@MockBean
	private ReviewHandler reviewHandler;
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
		SpringApplication.run(InstituteBasicInfoController.class);
	}

	@DisplayName("addUpdateInstituteBasicInfo test success")
	@Test
	 void addUpdateInstituteBasicInfo() throws IOException {
				InstituteBasicInfoDto instituteBasicInfoDto = new InstituteBasicInfoDto();
				instituteBasicInfoDto.setInstituteLogoPath("logopath");
				instituteBasicInfoDto.setNameOfUniversity("rgpvv");
				instituteBasicInfoDto.setInstituteCategoryTypeId("gdfhdjhhdfsddvsd4");
				instituteBasicInfoDto.setInstituteCategoryTypeName("dfuhsdugdf");
				instituteBasicInfoDto.setDescription("mudescrption");
				instituteBasicInfoDto.setCountryName("india");
				instituteBasicInfoDto.setStateName("mp");
				instituteBasicInfoDto.setCityName("indore");
				instituteBasicInfoDto.setAddress("myaddress");
			
				HttpHeaders header = new HttpHeaders();
				header.setContentType(MediaType.APPLICATION_JSON);
				header.set("userId", userId);
				HttpEntity<InstituteBasicInfoDto> entitys = new HttpEntity<>(instituteBasicInfoDto, header);
				String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "basic" + PATH_SEPARATOR + "info" + PATH_SEPARATOR
						+ instituteId;
				ResponseEntity<String> responses = testRestTemplate.exchange(path, HttpMethod.POST, entitys,
						String.class);
				assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);			
	}

	@DisplayName("WrongidaddUpdateInstituteBasicInfo")
	@Test
	 void wrongIdaddUpdateInstituteBasicInfo() throws IOException {
		
				InstituteBasicInfoDto instituteBasicInfoDto = new InstituteBasicInfoDto();
				instituteBasicInfoDto.setInstituteLogoPath("logopath");
				instituteBasicInfoDto.setNameOfUniversity("rgpvv");
				instituteBasicInfoDto.setInstituteCategoryTypeId("gdfhdjhhdfsddvsd4");
				instituteBasicInfoDto.setInstituteCategoryTypeName("dfuhsdugdf");
				instituteBasicInfoDto.setDescription("mudescrption");
				instituteBasicInfoDto.setCountryName("india");
				instituteBasicInfoDto.setStateName("mp");
				instituteBasicInfoDto.setCityName("indore");
				instituteBasicInfoDto.setAddress("myaddress");
				
				HttpHeaders header = new HttpHeaders();
				header.setContentType(MediaType.APPLICATION_JSON);
				header.set("userId", userId);
				HttpEntity<InstituteBasicInfoDto> entitys = new HttpEntity<>(instituteBasicInfoDto, header);
				String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "basic" + PATH_SEPARATOR + "info" + PATH_SEPARATOR
						+ "561ba731-1f44-4cdd-8776-4addcc";
				ResponseEntity<String> responses = testRestTemplate.exchange(path, HttpMethod.POST, entitys,
						String.class);
				assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

		}

	@DisplayName("getInstituteBasicInfo test success")
	@Test
     void getInstituteBasicInfo() throws IOException {
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			header.set("userId", userId);
			String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "basic" + PATH_SEPARATOR + "info" + PATH_SEPARATOR
					+ instituteId;
			HttpEntity<String> entityy = new HttpEntity<>(header);
			ResponseEntity<String> responsed = testRestTemplate.exchange(path, HttpMethod.GET, entityy, String.class);
			assertThat(responsed.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("WrongIdgetInstituteBasicInfo")
	@Test
     void wrongIdgetInstituteBasicInfo() throws IOException {
	
			String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "basic" + PATH_SEPARATOR + "info" + PATH_SEPARATOR
					+ "f5663321-354d-44f3-8d31-b7aea650";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("userId", userId);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.GET, entity, String.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}
	
	@DisplayName("getInstitutePublicBasicInfo test success")
	@Test
	void getInstitutePublicBasicInfo() throws IOException {
			List<String>instituteIds=new ArrayList();
			instituteIds.add(instituteId);
			String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "public" + PATH_SEPARATOR + "basic" + PATH_SEPARATOR
					+ "info" + PATH_SEPARATOR + instituteId;
			Mockito.when(storageHandler.getStorages(instituteId, EntityTypeEnum.INSTITUTE, EntitySubTypeEnum.LOGO))
			.thenReturn(new ArrayList());
			Mockito.when(reviewHandler.getAverageReview(EntityTypeEnum.INSTITUTE.name(), instituteIds)).thenReturn(new HashMap<>());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("userId", userId);
			Map<String, Boolean> params = new HashMap();
			params.put("includeInstituteLogo", false);
			params.put("includeDetail", false);
			HttpEntity<String> entityy = new HttpEntity<>(headers);
			ResponseEntity<String> responsed = testRestTemplate.exchange(path, HttpMethod.GET, entityy, String.class,
					params);
			assertThat(responsed.getStatusCode()).isEqualTo(HttpStatus.OK);
		} 

}
