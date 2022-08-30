package testController;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import org.springframework.test.context.junit4.SpringRunner;

import com.yuzee.app.YuzeeApplication;
import com.yuzee.app.bean.Location;
import com.yuzee.app.dto.InstituteEnglishRequirementsDto;
import com.yuzee.app.dto.InstituteFundingDto;
import com.yuzee.app.dto.InstituteRequestDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.repository.InstituteEnglishRequirementRepository;
import com.yuzee.app.repository.InstituteRepository;
import com.yuzee.common.lib.dto.institute.ProviderCodeDto;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)
public class InstituteEnglishRequirementController {

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
	public void addInstituteEnglishRequirements() {

		ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
		ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
		instituteFundingDto.add(0, new InstituteFundingDto(UUID.randomUUID().toString()));

		List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
		ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
		instituteProviderCode.setName("ProviderName");
		instituteProviderCode.setValue(("ProviderValue"));
		listOfInstituteProviderCode.add(instituteProviderCode);

		InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
		instituteRequestDto.setName("AIEEE");
		instituteRequestDto.setCityName("RAJENDRANAGAR");
		instituteRequestDto.setCountryName("INDIA");
		instituteRequestDto.setEditAccess(true);
		instituteRequestDto.setAboutInfo(
				"INTERNATIONAL Engineering College, RAJENDRANAGAR, is accredited by the RAJENDRANAGAR Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
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
		instituteRequestDto.setEmail("AIEEE@testEmail.com");
		instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
		instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
		instituteRequestDto.setReadableId("AIEEE");
		instituteRequestDto.setInstituteId(UUID.randomUUID().toString());
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
		InstituteEnglishRequirementsDto instituteEnglishRequirementsDto = new InstituteEnglishRequirementsDto();
		try {
			String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR
					+ instituteRequestDto.getInstituteId();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("userId", userId);
			instituteEnglishRequirementsDto.setInstituteId("18073b1e-1021-4ae1-91f0-f03c1f59b5b3");
			instituteEnglishRequirementsDto.setExamName("testExamName");
			instituteEnglishRequirementsDto.setListeningMarks(54.34);
			instituteEnglishRequirementsDto.setOralMarks(89.334);
			instituteEnglishRequirementsDto.setReadingMarks(67.321);
			instituteEnglishRequirementsDto.setWritingMarks(88.90);
			instituteEnglishRequirementsDto.getInstituteId();
			HttpEntity<InstituteEnglishRequirementsDto> entity = new HttpEntity<>(instituteEnglishRequirementsDto,
					headers);
			ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.POST, entity, String.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		} finally {
			// clean up code
			ResponseEntity<String> response = testRestTemplate.exchange(
					INSTITUTE_PRE_PATH + PATH_SEPARATOR + instituteRequestDto.getInstituteId(), HttpMethod.DELETE, null,
					String.class);
			instituteRepository.deleteById(instituteRequestDto.getInstituteId());
			instituteEnglishRequirementRepository.deleteById(instituteEnglishRequirementsDto.getInstituteId());
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		}
	}

	@DisplayName("updateInstituteEnglishRequirements test success")
	@Test
	public void updateInstituteEnglishRequirements() {
		ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
		ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
		instituteFundingDto.add(0, new InstituteFundingDto(UUID.randomUUID().toString()));

		List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
		ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
		instituteProviderCode.setName("ProviderName");
		instituteProviderCode.setValue(("ProviderValue"));
		listOfInstituteProviderCode.add(instituteProviderCode);

		InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
		instituteRequestDto.setName("AIEEE");
		instituteRequestDto.setCityName("RAJENDRANAGAR");
		instituteRequestDto.setCountryName("INDIA");
		instituteRequestDto.setEditAccess(true);
		instituteRequestDto.setAboutInfo(
				"INTERNATIONAL Engineering College, RAJENDRANAGAR, is accredited by the RAJENDRANAGAR Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
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
		instituteRequestDto.setEmail("AIEEE@testEmail.com");
		instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
		instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
		instituteRequestDto.setReadableId("AIEEE");
		instituteRequestDto.setInstituteId(UUID.randomUUID().toString());
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
		InstituteEnglishRequirementsDto instituteEnglishRequirementsDto = new InstituteEnglishRequirementsDto();

		/// create new objject
		String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR
				+ instituteRequestDto.getInstituteId();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);
		instituteEnglishRequirementsDto.setInstituteId("18073b1e-1021-4ae1-91f0-f03c1f59b5b3");
		instituteEnglishRequirementsDto.setExamName("testExamName");
		instituteEnglishRequirementsDto.setListeningMarks(54.34);
		instituteEnglishRequirementsDto.setOralMarks(89.334);
		instituteEnglishRequirementsDto.setReadingMarks(67.321);
		instituteEnglishRequirementsDto.setWritingMarks(88.90);
		instituteEnglishRequirementsDto.getInstituteId();
		HttpEntity<InstituteEnglishRequirementsDto> entity = new HttpEntity<>(instituteEnglishRequirementsDto, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.POST, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		try {
			String paths = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR
					+"6617fa7c-47a3-4a72-8017-d1e771220d47";

			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			header.set("userId", userId);
			instituteEnglishRequirementsDto.setInstituteId("230006b1-ef9d-4d33-a5b1-665e3202686f");
			instituteEnglishRequirementsDto.setExamName("update");
			instituteEnglishRequirementsDto.setListeningMarks(66.88);
			instituteEnglishRequirementsDto.setOralMarks(66.88);
			instituteEnglishRequirementsDto.setReadingMarks(66.88);
			instituteEnglishRequirementsDto.setWritingMarks(88.66);
			HttpEntity<InstituteEnglishRequirementsDto> entitys = new HttpEntity<>(instituteEnglishRequirementsDto,
					header);
			ResponseEntity<String> responses = testRestTemplate.exchange(paths, HttpMethod.PUT, entitys, String.class);
			assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
		} finally {
			// clean up code
//			ResponseEntity<String> responses = testRestTemplate.exchange(
//					INSTITUTE_PRE_PATH + PATH_SEPARATOR + instituteRequestDto.getInstituteId(), HttpMethod.DELETE, null,
//					String.class);
//			instituteProcessor.deleteInstitute(data.getInstituteId());
//			instituteEnglishRequirementRepository.deleteById(instituteEnglishRequirementsDto.getInstituteId());
//			assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
		}
	}

	@DisplayName("getInstitutePublicEnglishRequirementsByInstituteId test success")
	@Test
	public void getInstitutePublicEnglishRequirementsByInstituteId() {
		ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
		ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
		instituteFundingDto.add(0, new InstituteFundingDto(UUID.randomUUID().toString()));

		List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
		ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
		instituteProviderCode.setName("ProviderName");
		instituteProviderCode.setValue(("ProviderValue"));
		listOfInstituteProviderCode.add(instituteProviderCode);

		InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
		instituteRequestDto.setName("AIEEE");
		instituteRequestDto.setCityName("RAJENDRANAGAR");
		instituteRequestDto.setCountryName("INDIA");
		instituteRequestDto.setEditAccess(true);
		instituteRequestDto.setAboutInfo(
				"INTERNATIONAL Engineering College, RAJENDRANAGAR, is accredited by the RAJENDRANAGAR Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
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
		instituteRequestDto.setEmail("AIEEE@testEmail.com");
		instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
		instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
		instituteRequestDto.setReadableId("AIEEE");
		instituteRequestDto.setInstituteId(UUID.randomUUID().toString());
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
		InstituteEnglishRequirementsDto instituteEnglishRequirementsDto = new InstituteEnglishRequirementsDto();

		/// create new objject
		String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR
				+ instituteRequestDto.getInstituteId();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);
		instituteEnglishRequirementsDto.setInstituteId("18073b1e-1021-4ae1-91f0-f03c1f59b5b3");
		instituteEnglishRequirementsDto.setExamName("testExamName");
		instituteEnglishRequirementsDto.setListeningMarks(54.34);
		instituteEnglishRequirementsDto.setOralMarks(89.334);
		instituteEnglishRequirementsDto.setReadingMarks(67.321);
		instituteEnglishRequirementsDto.setWritingMarks(88.90);
		instituteEnglishRequirementsDto.getInstituteId();
		HttpEntity<InstituteEnglishRequirementsDto> entity = new HttpEntity<>(instituteEnglishRequirementsDto, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.POST, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		try {
			String pathe = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "public" + PATH_SEPARATOR + "englishRequirements"
					+ PATH_SEPARATOR + "6617fa7c-47a3-4a72-8017-d1e771220d47";
			HttpHeaders header = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("userId", userId);
			HttpEntity<InstituteEnglishRequirementsDto> entitys = new HttpEntity<>(null, header);
			ResponseEntity<String> responses = testRestTemplate.exchange(pathe, HttpMethod.GET, entitys, String.class);
			assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
		}
		// clean up codefinally {
		finally {
//			// clean up code
//			ResponseEntity<String> responses = testRestTemplate.exchange(
//					INSTITUTE_PRE_PATH + PATH_SEPARATOR + instituteRequestDto.getInstituteId(), HttpMethod.DELETE, null,
//					String.class);
//			instituteRepository.deleteById(instituteRequestDto.getInstituteId());
//			instituteEnglishRequirementRepository.deleteById(instituteEnglishRequirementsDto.getInstituteId());
//			assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	}

	@DisplayName("deleteInstituteEnglishRequirementsByRequirementsId test success")
	@Test
	public void deleteInstituteEnglishRequirementsByRequirementsId() {
		String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR
				+ "5296d989-45eb-4971-825e-67ed9248a717";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);
		HttpEntity<InstituteEnglishRequirementsDto> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.DELETE, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}