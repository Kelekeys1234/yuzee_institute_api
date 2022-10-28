//package testController;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//import org.junit.BeforeClass;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.platform.runner.JUnitPlatform;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.yuzee.app.YuzeeApplication;
//import com.yuzee.app.bean.InstituteCampus;
//import com.yuzee.app.bean.Location;
//import com.yuzee.app.controller.v1.InstituteController;
//import com.yuzee.app.dto.InstituteFundingDto;
//import com.yuzee.app.dto.InstituteRequestDto;
//import com.yuzee.app.dto.ValidList;
//import com.yuzee.app.processor.InstituteProcessor;
//import com.yuzee.app.repository.InstituteCampusRepository;
//import com.yuzee.app.repository.InstituteRepository;
//import com.yuzee.common.lib.dto.GenericWrapperDto;
//import com.yuzee.common.lib.dto.institute.ProviderCodeDto;
//import com.yuzee.common.lib.handler.PublishSystemEventHandler;
//import com.yuzee.common.lib.util.ObjectMapperHelper;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RunWith(JUnitPlatform.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//@ContextConfiguration(classes = YuzeeApplication.class)
// class TestInstituteCampusController {
//
//	private static final String entityId = UUID.randomUUID().toString();
//	private static final String instituteId = "a2a00b2a-6b2d-41f1-8501-8ba882ee2b2a";
//	private static final String userId = "8d7c017d-37e3-4317-a8b5-9ae6d9cdcb49";
//	private static final String USER_ID = "userId";
//	private static final String INSTITUTE_PRE_PATH = "/api/v1";
//	private static final String PATH_SEPARATOR = "/";
//
//	@Autowired
//	private TestRestTemplate testRestTemplate;
//	@Autowired
//	InstituteRepository instituteRepository;
//	@MockBean
//	private PublishSystemEventHandler publishSystemEventHandler;
//
//	@Autowired
//	private InstituteProcessor instituteProcessor;
//
//	@BeforeClass
//	public static void main() {
//		SpringApplication.run(InstituteController.class);
//	}
//
//	/// campus/instituteId/{instituteId}
//	@DisplayName("addCampus test success")
//	@Test
//	 void addCampus() throws IOException {
//		ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
//		ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
//		instituteFundingDto.add(0, new InstituteFundingDto(UUID.randomUUID().toString()));
//
//		List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
//		ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
//		instituteProviderCode.setName("TestProviderName");
//		instituteProviderCode.setValue(("TestProviderValue"));
//		listOfInstituteProviderCode.add(instituteProviderCode);
//
//		InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
//		instituteRequestDto.setName("IIM");
//		instituteRequestDto.setCityName("AHMEDABAD");
//		instituteRequestDto.setCountryName("INDIA");
//		instituteRequestDto.setEditAccess(true);
//		instituteRequestDto.setAboutInfo(
//				"Domestic Language School, Cambridge, is accredited by the French Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
//		instituteRequestDto.setDescription("Test update method Description");
//		instituteRequestDto.setInstituteFundings(instituteFundingDto);
//		instituteRequestDto.setEnrolmentLink("https://www.centrallanguageschool.com/enrol");
//		instituteRequestDto.setWhatsNo("https://api.whatsapp.com/send?phone=60173010314");
//		instituteRequestDto.setCourseStart("March, April, May");
//		instituteRequestDto.setWebsite("https://www.centrallanguageschool.com/");
//		instituteRequestDto.setAddress("41 St Andrew's St, Cambridge CB2 3AR, UK");
//		Location location = new Location(UUID.randomUUID().toString(), new GeoJsonPoint(25.32, 12.56));
//		instituteRequestDto.setLatitude(location.getLocation().getY());
//		instituteRequestDto.setLongitude(location.getLocation().getX());
//		instituteRequestDto.setEmail("info@testEmail.com");
//		instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
//		instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
//		instituteRequestDto.setReadableId("DMS");
//		instituteRequestDto.setInstituteId(UUID.randomUUID().toString());
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		listOfInstituteRequestDto.add(instituteRequestDto);
//		listOfInstituteProviderCode.add(instituteProviderCode);
//		instituteRequestDto.setProviderCodes(listOfInstituteProviderCode);
//		HttpEntity<ValidList<InstituteRequestDto>> entity = new HttpEntity<>(listOfInstituteRequestDto, headers);
//		ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PRE_PATH, HttpMethod.POST, entity,
//				String.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//		GenericWrapperDto<ValidList<InstituteRequestDto>> genericResponse = ObjectMapperHelper.readValueFromJSON(
//				response.getBody(), new TypeReference<GenericWrapperDto<ValidList<InstituteRequestDto>>>() {
//				});
//		ValidList<InstituteRequestDto> r = genericResponse.getData();
//		for (InstituteRequestDto data : r) {
//			try {
//				HttpHeaders header = new HttpHeaders();
//
//				header.set("userId", userId);
//				header.setContentType(MediaType.APPLICATION_JSON);
//				List<String> instituteIds = Arrays.asList(userId, instituteId, data.getInstituteId());
//				HttpEntity<List<String>> entitys = new HttpEntity<>(instituteIds, header);
//				String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "campus" + PATH_SEPARATOR + "instituteId"
//						+ PATH_SEPARATOR + data.getInstituteId();
//				ResponseEntity<String> responsess = testRestTemplate.exchange(path, HttpMethod.POST, entitys,
//						String.class);
//				assertThat(responsess.getStatusCode()).isEqualTo(HttpStatus.OK);
//			} finally {
//				// clean up code
//				ResponseEntity<String> responses = testRestTemplate.exchange(
//						INSTITUTE_PRE_PATH + PATH_SEPARATOR + instituteRequestDto.getInstituteId(), HttpMethod.DELETE, null,
//						String.class);
//				instituteProcessor.deleteInstitute(instituteRequestDto.getInstituteId());
//				assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
//			}
//		}
//	}
//
//	@DisplayName("getInstituteCampuses test success")
//	@Test
//	  void getInstituteCampuses() throws IOException {
//		ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
//		ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
//		instituteFundingDto.add(0, new InstituteFundingDto(UUID.randomUUID().toString()));
//
//		List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
//		ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
//		instituteProviderCode.setName("TestProviderName");
//		instituteProviderCode.setValue(("TestProviderValue"));
//		listOfInstituteProviderCode.add(instituteProviderCode);
//
//		InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
//		instituteRequestDto.setName("IIM");
//		instituteRequestDto.setCityName("AHMEDABAD");
//		instituteRequestDto.setCountryName("INDIA");
//		instituteRequestDto.setEditAccess(true);
//		instituteRequestDto.setAboutInfo(
//				"Domestic Language School, Cambridge, is accredited by the French Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
//		instituteRequestDto.setDescription("Test update method Description");
//		instituteRequestDto.setInstituteFundings(instituteFundingDto);
//		instituteRequestDto.setEnrolmentLink("https://www.centrallanguageschool.com/enrol");
//		instituteRequestDto.setWhatsNo("https://api.whatsapp.com/send?phone=60173010314");
//		instituteRequestDto.setCourseStart("March, April, May");
//		instituteRequestDto.setWebsite("https://www.centrallanguageschool.com/");
//		instituteRequestDto.setAddress("41 St Andrew's St, Cambridge CB2 3AR, UK");
//		Location location = new Location(UUID.randomUUID().toString(), new GeoJsonPoint(25.32, 12.56));
//		instituteRequestDto.setLatitude(location.getLocation().getY());
//		instituteRequestDto.setLongitude(location.getLocation().getX());
//		instituteRequestDto.setEmail("info@testEmail.com");
//		instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
//		instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
//		instituteRequestDto.setReadableId("DMS");
//		instituteRequestDto.setInstituteId(UUID.randomUUID().toString());
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		listOfInstituteRequestDto.add(instituteRequestDto);
//		listOfInstituteProviderCode.add(instituteProviderCode);
//		instituteRequestDto.setProviderCodes(listOfInstituteProviderCode);
//		HttpEntity<ValidList<InstituteRequestDto>> entity = new HttpEntity<>(listOfInstituteRequestDto, headers);
//		ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PRE_PATH, HttpMethod.POST, entity,
//				String.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//		GenericWrapperDto<ValidList<InstituteRequestDto>> genericResponse = ObjectMapperHelper.readValueFromJSON(
//				response.getBody(), new TypeReference<GenericWrapperDto<ValidList<InstituteRequestDto>>>() {
//				});
//		ValidList<InstituteRequestDto> r = genericResponse.getData();
//		// create new campus
//
//		for (InstituteRequestDto data : r) {
//			// create new campus
//			/*
//			 * HttpHeaders header = new HttpHeaders();
//			 * 
//			 * header.set("userId", userId);
//			 * header.setContentType(MediaType.APPLICATION_JSON); List<String> instituteIds
//			 * = Arrays.asList(userId, "1e348e15-45b6-477f-a457-883738227e05");
//			 * HttpEntity<List<String>> entitys = new HttpEntity<>(instituteIds, header);
//			 * String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "campus" + PATH_SEPARATOR
//			 * + "instituteId" + PATH_SEPARATOR + PATH_SEPARATOR + data.getInstituteId();
//			 * ResponseEntity<String> responses = testRestTemplate.exchange(path,
//			 * HttpMethod.POST, entitys, String.class);
//			 * assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
//			 * InstituteCampus instituteCampus = new InstituteCampus();
//			 */
//			try {
//				// instituteCampus.setId(UUID.randomUUID().toString());
//				HttpHeaders headerr = new HttpHeaders();
//				headerr.set("userId", userId);
//				headerr.setContentType(MediaType.APPLICATION_JSON);
//				List<String> instituteIdss = Arrays.asList(userId, data.getInstituteId());
//				HttpEntity<List<String>> entityss = new HttpEntity<>(instituteIdss, headerr);
//				String paths = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "campus" + PATH_SEPARATOR + "instituteId"
//						+ PATH_SEPARATOR + data.getInstituteId();
//				ResponseEntity<String> responsess = testRestTemplate.exchange(paths, HttpMethod.GET, entityss,
//						String.class);
//				assertThat(responsess.getStatusCode()).isEqualTo(HttpStatus.OK);
//			} finally {
//				// clean up code
//				ResponseEntity<String> responses = testRestTemplate.exchange(
//						INSTITUTE_PRE_PATH + PATH_SEPARATOR + instituteRequestDto.getInstituteId(), HttpMethod.DELETE, null,
//						String.class);
//				instituteProcessor.deleteInstitute(instituteRequestDto.getInstituteId());
//				assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
//			}
//		}
//	}
//
//	@DisplayName("deleteInstituteCampuses test success")
//	@Test
//	  void removeCampus() throws IOException {
//		ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
//		ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
//		instituteFundingDto.add(0, new InstituteFundingDto(UUID.randomUUID().toString()));
//
//		List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
//		ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
//		instituteProviderCode.setName("TestProviderName");
//		instituteProviderCode.setValue(("TestProviderValue"));
//		listOfInstituteProviderCode.add(instituteProviderCode);
//
//		InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
//		instituteRequestDto.setName("IIM");
//		instituteRequestDto.setCityName("AHMEDABAD");
//		instituteRequestDto.setCountryName("INDIA");
//		instituteRequestDto.setEditAccess(true);
//		instituteRequestDto.setAboutInfo(
//				"Domestic Language School, Cambridge, is accredited by the French Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
//		instituteRequestDto.setDescription("Test update method Description");
//		instituteRequestDto.setInstituteFundings(instituteFundingDto);
//		instituteRequestDto.setEnrolmentLink("https://www.centrallanguageschool.com/enrol");
//		instituteRequestDto.setWhatsNo("https://api.whatsapp.com/send?phone=60173010314");
//		instituteRequestDto.setCourseStart("March, April, May");
//		instituteRequestDto.setWebsite("https://www.centrallanguageschool.com/");
//		instituteRequestDto.setAddress("41 St Andrew's St, Cambridge CB2 3AR, UK");
//		Location location = new Location(UUID.randomUUID().toString(), new GeoJsonPoint(25.32, 12.56));
//		instituteRequestDto.setLatitude(location.getLocation().getY());
//		instituteRequestDto.setLongitude(location.getLocation().getX());
//		instituteRequestDto.setEmail("info@testEmail.com");
//		instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
//		instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
//		instituteRequestDto.setReadableId("DMS");
//		instituteRequestDto.setInstituteId(UUID.randomUUID().toString());
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		listOfInstituteRequestDto.add(instituteRequestDto);
//		listOfInstituteProviderCode.add(instituteProviderCode);
//		instituteRequestDto.setProviderCodes(listOfInstituteProviderCode);
//		HttpEntity<ValidList<InstituteRequestDto>> entity = new HttpEntity<>(listOfInstituteRequestDto, headers);
//		ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PRE_PATH, HttpMethod.POST, entity,
//				String.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//		GenericWrapperDto<ValidList<InstituteRequestDto>> genericResponse = ObjectMapperHelper.readValueFromJSON(
//				response.getBody(), new TypeReference<GenericWrapperDto<ValidList<InstituteRequestDto>>>() {
//				});
//		ValidList<InstituteRequestDto> r = genericResponse.getData();
//		for (InstituteRequestDto data : r) {
//			// create new campus institute
//			HttpHeaders headerr = new HttpHeaders();
//			headerr.set("userId", userId);
//			headerr.setContentType(MediaType.APPLICATION_JSON);
//			List<String> instituteIdss = Arrays.asList(userId, data.getInstituteId());
//			HttpEntity<List<String>> entityss = new HttpEntity<>(instituteIdss, headerr);
//			String paths = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "campus" + PATH_SEPARATOR + "instituteId"
//					+ PATH_SEPARATOR + data.getInstituteId();
//			ResponseEntity<String> responsess = testRestTemplate.exchange(paths, HttpMethod.GET, entityss,
//					String.class);
//			assertThat(responsess.getStatusCode()).isEqualTo(HttpStatus.OK);
//			try {
//			
//				HttpHeaders heade = new HttpHeaders();
//				heade.set("userId", userId);
//				heade.setContentType(MediaType.APPLICATION_JSON);
//				List<String> instituteIds = Arrays.asList(userId, instituteId, data.getInstituteId());
//				HttpEntity<List<String>> entitys = new HttpEntity<>(instituteIds, heade);
//				String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "campus" + PATH_SEPARATOR + "instituteId"
//						+ PATH_SEPARATOR + data.getInstituteId();
//				ResponseEntity<String> responses = testRestTemplate.exchange(path, HttpMethod.DELETE, entitys,
//						String.class);
//				assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
//			} finally {
//				// clean up code
//				ResponseEntity<String> responses = testRestTemplate.exchange(
//						INSTITUTE_PRE_PATH + PATH_SEPARATOR + instituteRequestDto.getInstituteId(), HttpMethod.DELETE, null,
//						String.class);
//				instituteProcessor.deleteInstitute(instituteRequestDto.getInstituteId());
//				assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//			}
//		}
//	}
//}