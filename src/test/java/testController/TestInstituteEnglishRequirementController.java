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

import com.yuzee.app.YuzeeApplication;
import com.yuzee.app.bean.Location;
import com.yuzee.app.controller.v1.InstituteBasicInfoController;
import com.yuzee.app.dto.InstituteEnglishRequirementsDto;
import com.yuzee.app.dto.InstituteFundingDto;
import com.yuzee.app.dto.InstituteRequestDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.repository.InstituteEnglishRequirementRepository;
import com.yuzee.app.repository.InstituteRepository;
import com.yuzee.common.lib.dto.institute.ProviderCodeDto;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;

import lombok.extern.slf4j.Slf4j;

public class TestInstituteEnglishRequirementController extends CreateCourseAndInstitute {

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
        String instituteId = testCreateInstitute();
		InstituteEnglishRequirementsDto instituteEnglishRequirementsDto = new InstituteEnglishRequirementsDto();
		try {
			String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR + instituteId;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("userId", userId);
			instituteEnglishRequirementsDto.setInstituteId("f5663321-354d-44f3-8d31-b7aea650586c");
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
			instituteRepository.deleteById(instituteId);
			instituteEnglishRequirementRepository.deleteById(instituteEnglishRequirementsDto.getInstituteId());

		}
	}

//	
//	
	@DisplayName("WrongIdaddInstituteEnglishRequirementWith")
	@Test
	void wrongiDaddInstituteEnglishRequirements() throws IOException {
	    String instituteId = testCreateInstitute();
		InstituteEnglishRequirementsDto instituteEnglishRequirementsDto = new InstituteEnglishRequirementsDto();
		try {
			String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR
					+ instituteId;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("userId", userId);
			instituteEnglishRequirementsDto.setInstituteId("f5663321-354d-44f3-8d31-b");
			instituteEnglishRequirementsDto.setExamName("testExamName");
			instituteEnglishRequirementsDto.setListeningMarks(54.34);
			instituteEnglishRequirementsDto.setOralMarks(89.334);
			instituteEnglishRequirementsDto.setReadingMarks(67.321);
			instituteEnglishRequirementsDto.setWritingMarks(88.90);
			instituteEnglishRequirementsDto.getInstituteId();
			HttpEntity<InstituteEnglishRequirementsDto> entity = new HttpEntity<>(instituteEnglishRequirementsDto,
					headers);
			ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.POST, entity, String.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		} finally {
			// clean up code
			instituteRepository.deleteById(instituteId);
			instituteEnglishRequirementRepository.deleteById(instituteEnglishRequirementsDto.getInstituteId());
		}
	}

//	
//
	@DisplayName("updateInstituteEnglishRequirements test success")
	@Test
	void updateInstituteEnglishRequirements() throws IOException {

		String instituteId = testCreateInstitute();
		InstituteEnglishRequirementsDto instituteEnglishRequirementsDto = new InstituteEnglishRequirementsDto();
		try {
			String paths = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR + instituteId;

			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			header.set("userId", userId);
			instituteEnglishRequirementsDto.setInstituteId("561ba731-1f44-4cdd-8776-4addcc562fbc");
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
			instituteRepository.deleteById(instituteId);
			instituteEnglishRequirementRepository.deleteById(instituteEnglishRequirementsDto.getInstituteId());
		}
	}


	@DisplayName("getInstitutePublicEnglishRequirementsByInstituteId test success")
	@Test
	void getInstitutePublicEnglishRequirementsByInstituteId() throws IOException {
		String instituteId = testCreateInstitute();
		InstituteEnglishRequirementsDto instituteEnglishRequirementsDto = new InstituteEnglishRequirementsDto();

		/// create new objject
		String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR
				+ instituteId;
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
				+ PATH_SEPARATOR + instituteId;
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.set("userId", userId);
		HttpEntity<InstituteEnglishRequirementsDto> entitys = new HttpEntity<>(header);
		ResponseEntity<String> responses = testRestTemplate.exchange(pathe, HttpMethod.GET, entitys, String.class);
		assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
		// clean up codefinally {
		finally {
			// clean up code
			instituteRepository.deleteById(instituteId);
			instituteEnglishRequirementRepository.deleteById(instituteEnglishRequirementsDto.getInstituteId());
	}
	}

//
	@DisplayName("deleteInstituteEnglishRequirementsByRequirementsId test success")
	@Test
	void deleteInstituteEnglishRequirementsByRequirementsId() throws IOException {
		String instituteId = testCreateInstitute();
		String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR
				+ instituteId;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);
		HttpEntity<InstituteEnglishRequirementsDto> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.DELETE, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	
	@DisplayName("WrongIddeleteInstituteEnglishRequirementsByRequirementsWrongId")
	@Test
	  void wrongIddeleteInstituteEnglishRequirementsByRequirementsId() throws IOException {
		String instituteId = testCreateInstitute();
		String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR
				+ instituteId;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);
		HttpEntity<InstituteEnglishRequirementsDto> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.DELETE, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
}