package testController;

import static org.assertj.core.api.Assertions.assertThat;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.BeforeClass;
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

@Slf4j
@RunWith(JUnitPlatform.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)
 class TestInstituteBasicInfoController {

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
	private static final UUID IDS = UUID.fromString("1e348e15-45b6-477f-a457-883738227e05");

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

	@BeforeClass
	public static void main() {
		SpringApplication.run(InstituteBasicInfoController.class);
	}

	@DisplayName("addUpdateInstituteBasicInfo test success")
	@Test
	 void addUpdateInstituteBasicInfo() throws IOException {
		ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
		ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
		instituteFundingDto.add(0, new InstituteFundingDto(UUID.randomUUID().toString()));

		List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
		ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
		instituteProviderCode.setName("TestProviderName");
		instituteProviderCode.setValue(("TestProviderValue"));
		listOfInstituteProviderCode.add(instituteProviderCode);

		InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
		instituteRequestDto.setName("IIM");
		instituteRequestDto.setCityName("AHMEDABAD");
		instituteRequestDto.setCountryName("INDIA");
		instituteRequestDto.setEditAccess(true);
		instituteRequestDto.setAboutInfo(
				"Domestic Language School, Cambridge, is accredited by the French Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
		instituteRequestDto.setDescription("Test update method Description");
		instituteRequestDto.setInstituteFundings(instituteFundingDto);
		instituteRequestDto.setEnrolmentLink("https://www.centrallanguageschool.com/enrol");
		instituteRequestDto.setWhatsNo("https://api.whatsapp.com/send?phone=60173010314");
		instituteRequestDto.setCourseStart("March, April, May");
		instituteRequestDto.setWebsite("https://www.centrallanguageschool.com/");
		instituteRequestDto.setAddress("41 St Andrew's St, Cambridge CB2 3AR, UK");
		Location location = new Location(UUID.randomUUID().toString(), new GeoJsonPoint(25.32, 12.56));
		instituteRequestDto.setLatitude(location.getLocation().getY());
		instituteRequestDto.setLongitude(location.getLocation().getX());
		instituteRequestDto.setEmail("info@testEmail.com");
		instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
		instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
		instituteRequestDto.setReadableId("DMS");
		instituteRequestDto.setInstituteId(IDS.toString());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		listOfInstituteRequestDto.add(instituteRequestDto);
		listOfInstituteProviderCode.add(instituteProviderCode);
		instituteRequestDto.setProviderCodes(listOfInstituteProviderCode);
		HttpEntity<ValidList<InstituteRequestDto>> entity = new HttpEntity<>(listOfInstituteRequestDto, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PRE_PATH, HttpMethod.POST, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		GenericWrapperDto<ValidList<InstituteRequestDto>> genericResponse = ObjectMapperHelper.readValueFromJSON(
				response.getBody(), new TypeReference<GenericWrapperDto<ValidList<InstituteRequestDto>>>() {
				});
		ValidList<InstituteRequestDto> r = genericResponse.getData();
		for (InstituteRequestDto data : r) {

//			try {
//				InstituteBasicInfoDto instituteBasicInfoDto = new InstituteBasicInfoDto("logo path","OXFORD","NAME OF CATEGORIES",INSTITUTE_ID,"This is Decription"
//					,"INDIA","Amedded","newYork","12 okdoew","by AZGuards",3,2,40,30.0,12345L,true);
//			
//				HttpHeaders header = new HttpHeaders();
//				header.setContentType(MediaType.APPLICATION_JSON);
//				header.set("userId", userId);
//				HttpEntity<InstituteBasicInfoDto> entitys = new HttpEntity<>(instituteBasicInfoDto, header);
//				String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "basic" + PATH_SEPARATOR + "info" + PATH_SEPARATOR
//						+ data.getInstituteId();
//				ResponseEntity<String> responses = testRestTemplate.exchange(path, HttpMethod.POST, entitys,
//						String.class);
//				assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
//			} finally {
//				// clean up code
//				ResponseEntity<String> responses = testRestTemplate.exchange(
//						INSTITUTE_PRE_PATH + PATH_SEPARATOR + data.getInstituteId(), HttpMethod.DELETE, null,
//						String.class);
//				instituteProcessor.deleteInstitute(data.getInstituteId());
//				assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
//			}
		}
	}

	@DisplayName("getInstituteBasicInfo test success")
	@Test
     void getInstituteBasicInfo() throws IOException {
		boolean status = true;
		ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
		ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
		instituteFundingDto.add(0, new InstituteFundingDto(UUID.randomUUID().toString()));

		List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
		ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
		instituteProviderCode.setName("TestName");
		instituteProviderCode.setValue(("TestValue"));
		listOfInstituteProviderCode.add(instituteProviderCode);
		Mockito.when(reviewHandler.getAverageReview(EntityTypeEnum.INSTITUTE.name(),
				List.of("795592f1-3665-4649-89b0-39cb844e78d0"))).thenReturn(new HashMap());
		Mockito.when(storageHandler.getStorages("795592f1-3665-4649-89b0-39cb844e78d0", EntityTypeEnum.INSTITUTE,
				EntitySubTypeEnum.LOGO)).thenReturn(new ArrayList());

		InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
		instituteRequestDto.setName("OCB");
		instituteRequestDto.setCityName("JAMNAGAR");
		instituteRequestDto.setCountryName("INDIA");
		instituteRequestDto.setEditAccess(true);
		instituteRequestDto.setAboutInfo(
				"INTERNATIONAL Language School, Cambridge, is accredited by the French Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
		instituteRequestDto.setDescription("Test update method Description");
		instituteRequestDto.setInstituteFundings(instituteFundingDto);
		instituteRequestDto.setEnrolmentLink("https://www.centrallanguageschool.com/enrol");
		instituteRequestDto.setWhatsNo("https://api.whatsapp.com/send?phone=60173010314");
		instituteRequestDto.setCourseStart("March, April, May");
		instituteRequestDto.setWebsite("https://www.centrallanguageschool.com/");
		instituteRequestDto.setAddress("41 St Andrew's St, Cambridge CB2 3AR, UK");
		Location location = new Location(UUID.randomUUID().toString(), new GeoJsonPoint(25.32, 12.56));
		instituteRequestDto.setLatitude(location.getLocation().getY());
		instituteRequestDto.setLongitude(location.getLocation().getX());
		instituteRequestDto.setEmail("OCB@testEmail.com");
		instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
		instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
		instituteRequestDto.setReadableId("OCB");
		instituteRequestDto.setInstituteId(IDS.toString());
		HttpHeaders createHeaders = new HttpHeaders();
		createHeaders.setContentType(MediaType.APPLICATION_JSON);
		listOfInstituteRequestDto.add(instituteRequestDto);
		listOfInstituteProviderCode.add(instituteProviderCode);
//6f91fa9b-6911-4fd3-beec-894d83545f35
		instituteRequestDto.setProviderCodes(listOfInstituteProviderCode);
		HttpEntity<ValidList<InstituteRequestDto>> createEntity = new HttpEntity<>(listOfInstituteRequestDto,
				createHeaders);
		ResponseEntity<String> responseInstitute = testRestTemplate.exchange(INSTITUTE_PRE_PATH, HttpMethod.POST,
				createEntity, String.class);
		assertThat(responseInstitute.getStatusCode()).isEqualTo(HttpStatus.OK);
		try {
			String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "basic" + PATH_SEPARATOR + "info" + PATH_SEPARATOR
					+ instituteRequestDto.getInstituteId();
			HttpHeaders headers = new HttpHeaders();
			createHeaders.setContentType(MediaType.APPLICATION_JSON);
			headers.set("userId", userId);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.GET, entity, String.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		} finally {
			// clean up code
			ResponseEntity<String> responses = testRestTemplate.exchange(
					INSTITUTE_PRE_PATH + PATH_SEPARATOR + IDS.toString(), HttpMethod.DELETE, null, String.class);
			instituteProcessor.deleteInstitute(instituteRequestDto.getInstituteId());
			assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
		}
	}

	@DisplayName("getInstitutePublicBasicInfo test success")
	@Test
	 void getInstitutePublicBasicInfo() throws IOException {
		boolean status = true;

		Mockito.when(reviewHandler.getAverageReview(EntityTypeEnum.INSTITUTE.name(),
				List.of("795592f1-3665-4649-89b0-39cb844e78d0"))).thenReturn(new HashMap());
		Mockito.when(storageHandler.getStorages("795592f1-3665-4649-89b0-39cb844e78d0", EntityTypeEnum.INSTITUTE,
				EntitySubTypeEnum.LOGO)).thenReturn(new ArrayList());
		ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
		ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
		instituteFundingDto.add(0, new InstituteFundingDto(UUID.randomUUID().toString()));

		List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
		ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
		instituteProviderCode.setName("ProviderName");
		instituteProviderCode.setValue(("ProviderValue"));
		listOfInstituteProviderCode.add(instituteProviderCode);

		InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
		instituteRequestDto.setName("JEE");
		instituteRequestDto.setCityName("KOTA");
		instituteRequestDto.setCountryName("INDIA");
		instituteRequestDto.setEditAccess(true);
		instituteRequestDto.setAboutInfo(
				"INTERNATIONAL Engineering College, KOTA, is accredited by the KOTA Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
		instituteRequestDto.setDescription("Test update method Description");
		instituteRequestDto.setInstituteFundings(instituteFundingDto);
		instituteRequestDto.setEnrolmentLink("https://www.centrallanguageschool.com/enrol");
		instituteRequestDto.setWhatsNo("https://api.whatsapp.com/send?phone=60173010314");
		instituteRequestDto.setCourseStart("March, April, May");
		instituteRequestDto.setWebsite("https://www.centrallanguageschool.com/");
		instituteRequestDto.setAddress("41 St Andrew's St, Cambridge CB2 3AR, UK");
		Location location = new Location(UUID.randomUUID().toString(), new GeoJsonPoint(25.32, 12.56));
		instituteRequestDto.setLatitude(location.getLocation().getY());
		instituteRequestDto.setLongitude(location.getLocation().getX());
		instituteRequestDto.setEmail("JEE@testEmail.com");
		instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
		instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
		instituteRequestDto.setReadableId("JEE");
		instituteRequestDto.setInstituteId(IDS.toString());
		HttpHeaders createHeaders = new HttpHeaders();
		createHeaders.setContentType(MediaType.APPLICATION_JSON);
		listOfInstituteRequestDto.add(instituteRequestDto);
		listOfInstituteProviderCode.add(instituteProviderCode);
		instituteRequestDto.setProviderCodes(listOfInstituteProviderCode);
		HttpEntity<ValidList<InstituteRequestDto>> createEntity = new HttpEntity<>(listOfInstituteRequestDto,
				createHeaders);
		ResponseEntity<String> responseInstitute = testRestTemplate.exchange(INSTITUTE_PRE_PATH, HttpMethod.POST,
				createEntity, String.class);
		assertThat(responseInstitute.getStatusCode()).isEqualTo(HttpStatus.OK);
		try {
			String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "public" + PATH_SEPARATOR + "basic" + PATH_SEPARATOR
					+ "info" + PATH_SEPARATOR + instituteRequestDto.getInstituteId();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("userId", userId);
			Map<String, Boolean> params = new HashMap();
			params.put("includeInstituteLogo", false);
			params.put("includeDetail", false);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.GET, entity, String.class,
					params);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		} finally {
			// clean up code
			ResponseEntity<String> response = testRestTemplate.exchange(
					INSTITUTE_PRE_PATH + PATH_SEPARATOR + instituteRequestDto.getInstituteId(), HttpMethod.DELETE, null,
					String.class);
			instituteProcessor.deleteInstitute(instituteRequestDto.getInstituteId());
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		}
	}
}
