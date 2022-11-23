package testController;

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
import com.yuzee.app.dto.InstituteEnglishRequirementsDto;
import com.yuzee.app.dto.InstituteFundingDto;
import com.yuzee.app.dto.InstituteRequestDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.repository.InstituteEnglishRequirementRepository;
import com.yuzee.app.repository.InstituteRepository;
import com.yuzee.common.lib.dto.GenericWrapperDto;
import com.yuzee.common.lib.dto.institute.ProviderCodeDto;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;
import com.yuzee.common.lib.util.ObjectMapperHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(JUnitPlatform.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)
public class TestInstituteEnglishRequirementController {

	private static final String entityId = UUID.randomUUID().toString();
	private static final String instituteId = "714964e7-ce52-4c07-ac0b-2e8dc6d7d444";
	private static final String INSTITUTE_ID = "instituteId";
	private static final String userId = "8d7c017d-37e3-4317-a8b5-9ae6d9cdcb49";
	private static final String USER_ID = "userId";
	private static final String INSTITUTE_PRE_PATH = "/api/v1";
	private static final String INSTITUTE_PATH = INSTITUTE_PRE_PATH + "/institute";
	private static final String PATH_SEPARATOR = "/";
	private static final String PAGE_NUMBER_PATH = "/pageNumber";
	private static final String PAGE_SIZE_PATH = "/pageSize";
	private static final String instituteEnglishRequirementId = "259d60e4-6b1d-48ea-9e43-a8e3f585ecb6";
//cf13eef7-b188-4e84-baee-7f98a7c33e7b
	@Autowired
	private TestRestTemplate testRestTemplate;
	@Autowired
	InstituteRepository instituteRepository;
	@MockBean
	private PublishSystemEventHandler publishSystemEventHandler;
	@Autowired
	private InstituteEnglishRequirementRepository instituteEnglishRequirementRepository;

	@BeforeClass
	public static void main() {
		SpringApplication.run(InstituteBasicInfoController.class);
	}

	/// englishRequirements/{instituteId}
	@DisplayName("addInstituteEnglishRequirements test success")
	@Test
	void addInstituteEnglishRequirements() throws IOException {
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
		instituteRequestDto.setWebsite("https://www.centrallanguageschool.com/");
		instituteRequestDto.setAddress("41 St Andrew's St, Cambridge CB2 3AR, UK");
		instituteRequestDto.setLatitude(92.5);
		instituteRequestDto.setLongitude(93.5);
		instituteRequestDto.setEmail("info@testEmail.com");
		instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
		instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
		instituteRequestDto.setReadableId(UUID.randomUUID().toString());
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
		InstituteEnglishRequirementsDto instituteEnglishRequirementsDto = new InstituteEnglishRequirementsDto();
		for (InstituteRequestDto data : r) {
			try {
				String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR
						+ data.getInstituteId();

				instituteEnglishRequirementsDto.setInstituteId(data.getInstituteId());
				instituteEnglishRequirementsDto.setExamName("testExamName");
				instituteEnglishRequirementsDto.setListeningMarks(54.34);
				instituteEnglishRequirementsDto.setOralMarks(89.334);
				instituteEnglishRequirementsDto.setReadingMarks(67.321);
				instituteEnglishRequirementsDto.setWritingMarks(88.90);
				HttpEntity<InstituteEnglishRequirementsDto> entityy = new HttpEntity<>(instituteEnglishRequirementsDto,
						headers);
				ResponseEntity<String> responses = testRestTemplate.exchange(path, HttpMethod.POST, entityy,
						String.class);
				assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
			} finally {
				// clean up code
				ResponseEntity<String> responsed = testRestTemplate.exchange(
						INSTITUTE_PRE_PATH + PATH_SEPARATOR + data.getInstituteId(), HttpMethod.DELETE, null,
						String.class);
				instituteRepository.deleteById(instituteRequestDto.getInstituteId());
				instituteEnglishRequirementRepository.deleteById(data.getInstituteId());
				assertThat(responsed.getStatusCode()).isEqualTo(HttpStatus.OK);
			}
		}
	}

	@DisplayName("WrongIdaddInstituteEnglishRequirementWith")
	@Test
	void wrongiDaddInstituteEnglishRequirements() {

		InstituteEnglishRequirementsDto instituteEnglishRequirementsDto = new InstituteEnglishRequirementsDto();

		String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR
				+ "f5663321-354d-44f3-8d31-b";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);
		instituteEnglishRequirementsDto.setInstituteId("f5663321-354d-44f3-8d31-b");
		instituteEnglishRequirementsDto.setExamName("testExamName");
		instituteEnglishRequirementsDto.setListeningMarks(54.34);
		instituteEnglishRequirementsDto.setOralMarks(89.334);
		instituteEnglishRequirementsDto.setReadingMarks(67.321);
		instituteEnglishRequirementsDto.setWritingMarks(88.90);
		HttpEntity<InstituteEnglishRequirementsDto> entity = new HttpEntity<>(instituteEnglishRequirementsDto, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.POST, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}
	

	@DisplayName("updateInstituteEnglishRequirements test success")
	@Test
	  void updateInstituteEnglishRequirements() throws IOException {
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
			instituteRequestDto.setWebsite("https://www.centrallanguageschool.com/");
			instituteRequestDto.setAddress("41 St Andrew's St, Cambridge CB2 3AR, UK");
			instituteRequestDto.setLatitude(92.5);
			instituteRequestDto.setLongitude(93.5);
			instituteRequestDto.setEmail("info@testEmail.com");
			instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
			instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
			instituteRequestDto.setReadableId(UUID.randomUUID().toString());
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
			InstituteEnglishRequirementsDto instituteEnglishRequirementsDto = new InstituteEnglishRequirementsDto();
			for (InstituteRequestDto data : r) {
				String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR
						+ data.getInstituteId();

				instituteEnglishRequirementsDto.setInstituteId(data.getInstituteId());
				instituteEnglishRequirementsDto.setExamName("testExamName");
				instituteEnglishRequirementsDto.setListeningMarks(54.34);
				instituteEnglishRequirementsDto.setOralMarks(89.334);
				instituteEnglishRequirementsDto.setReadingMarks(67.321);
				instituteEnglishRequirementsDto.setWritingMarks(88.90);
				HttpEntity<InstituteEnglishRequirementsDto> entityy = new HttpEntity<>(instituteEnglishRequirementsDto,
						headers);
				ResponseEntity<String> responses = testRestTemplate.exchange(path, HttpMethod.POST, entityy,
						String.class);
				assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
				try {

					String paths = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR
							+ data.getInstituteId();

					HttpHeaders header = new HttpHeaders();
					header.setContentType(MediaType.APPLICATION_JSON);
					header.set("userId", userId);
					instituteEnglishRequirementsDto.setInstituteId(data.getInstituteId());
					instituteEnglishRequirementsDto.setExamName("update");
					instituteEnglishRequirementsDto.setListeningMarks(66.88);
					instituteEnglishRequirementsDto.setOralMarks(66.88);
					instituteEnglishRequirementsDto.setReadingMarks(66.88);
					instituteEnglishRequirementsDto.setWritingMarks(88.66);
					HttpEntity<InstituteEnglishRequirementsDto> entitys = new HttpEntity<>(
							instituteEnglishRequirementsDto, header);
					ResponseEntity<String> responsesd = testRestTemplate.exchange(paths, HttpMethod.PUT, entitys,
							String.class);
					assertThat(responsesd.getStatusCode()).isEqualTo(HttpStatus.OK);

				} finally {
					// clean up code
					ResponseEntity<String> responsed = testRestTemplate.exchange(
							INSTITUTE_PRE_PATH + PATH_SEPARATOR + data.getInstituteId(), HttpMethod.DELETE, null,
							String.class);
					instituteRepository.deleteById(instituteRequestDto.getInstituteId());
					instituteEnglishRequirementRepository.deleteById(data.getInstituteId());
					assertThat(responsed.getStatusCode()).isEqualTo(HttpStatus.OK);
				}
			}
		}

	@DisplayName("getInstitutePublicEnglishRequirementsByInstituteId test success")
	@Test
	  void getInstitutePublicEnglishRequirementsByInstituteId() throws IOException {
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
			instituteRequestDto.setWebsite("https://www.centrallanguageschool.com/");
			instituteRequestDto.setAddress("41 St Andrew's St, Cambridge CB2 3AR, UK");
			instituteRequestDto.setLatitude(92.5);
			instituteRequestDto.setLongitude(93.5);
			instituteRequestDto.setEmail("info@testEmail.com");
			instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
			instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
			instituteRequestDto.setReadableId(UUID.randomUUID().toString());
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
			InstituteEnglishRequirementsDto instituteEnglishRequirementsDto = new InstituteEnglishRequirementsDto();
			for (InstituteRequestDto data : r) {
				String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR
						+ data.getInstituteId();

				instituteEnglishRequirementsDto.setInstituteId(data.getInstituteId());
				instituteEnglishRequirementsDto.setExamName("testExamName");
				instituteEnglishRequirementsDto.setListeningMarks(54.34);
				instituteEnglishRequirementsDto.setOralMarks(89.334);
				instituteEnglishRequirementsDto.setReadingMarks(67.321);
				instituteEnglishRequirementsDto.setWritingMarks(88.90);
				HttpEntity<InstituteEnglishRequirementsDto> entityy = new HttpEntity<>(instituteEnglishRequirementsDto,
						headers);
				ResponseEntity<String> responses = testRestTemplate.exchange(path, HttpMethod.POST, entityy,
						String.class);
				assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
				try {

					String pathe = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "public" + PATH_SEPARATOR
							+ "englishRequirements" + PATH_SEPARATOR + data.getInstituteId();
					HttpHeaders header = new HttpHeaders();
					header.setContentType(MediaType.APPLICATION_JSON);
					header.set("userId", userId);
					HttpEntity<InstituteEnglishRequirementsDto> entitys = new HttpEntity<>(header);
					ResponseEntity<String> responsesd = testRestTemplate.exchange(pathe, HttpMethod.GET, entitys,
							String.class);
					assertThat(responsesd.getStatusCode()).isEqualTo(HttpStatus.OK);

				} finally {
					// clean up code
					ResponseEntity<String> responsed = testRestTemplate.exchange(
							INSTITUTE_PRE_PATH + PATH_SEPARATOR + data.getInstituteId(), HttpMethod.DELETE, null,
							String.class);
					instituteRepository.deleteById(data.getInstituteId());
					instituteEnglishRequirementRepository.deleteById(data.getInstituteId());
					assertThat(responsed.getStatusCode()).isEqualTo(HttpStatus.OK);
				}
			}

		}

	@DisplayName("deleteInstituteEnglishRequirementsByRequirementsId test success")
	@Test
	  void deleteInstituteEnglishRequirementsByRequirementsId() {
			String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR
					+ "39bcdaeb-005a-4bb8-844a-fc343c602f4e";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("userId", userId);
			HttpEntity<InstituteEnglishRequirementsDto> entity = new HttpEntity<>(headers);
			ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.DELETE, entity, String.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		}
	}
