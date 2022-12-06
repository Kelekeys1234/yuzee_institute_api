package com.yuzee.app.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.yuzee.app.controller.v1.InstituteBasicInfoController;
import com.yuzee.app.dto.InstituteEnglishRequirementsDto;
import com.yuzee.app.repository.InstituteEnglishRequirementRepository;
import com.yuzee.app.repository.InstituteRepository;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;

@TestInstance(Lifecycle.PER_CLASS)
public class InstituteEnglishRequirementControllerTest extends CreateCourseAndInstitute {

	@Autowired
	private TestRestTemplate testRestTemplate;
	@Autowired
	InstituteRepository instituteRepository;
	@MockBean
	private PublishSystemEventHandler publishSystemEventHandler;
	@Autowired
	private InstituteEnglishRequirementRepository instituteEnglishRequirementRepository;
	InstituteEnglishRequirementsDto instituteEnglishRequirementsDto;
	private String instituteId;

	@BeforeClass
	public static void main() {
		SpringApplication.run(InstituteBasicInfoController.class);
	}

	@AfterAll
	public void deleteCampus() {
		instituteEnglishRequirementRepository.deleteAll();
	}

	@BeforeAll
	public void createAllIntituteAndCourse() throws IOException {
		instituteEnglishRequirementsDto = addInstituteEnglishRequirements();
		instituteId = testCreateInstitute();
	}

	/// englishRequirements/{instituteId}
	@DisplayName("addInstituteEnglishRequirements test success")
	@Test
	void createInstituteEnglishRequirements() throws IOException {
	}

	@DisplayName("WrongIdaddInstituteEnglishRequirementWith")
	@Test
	void wrongiDaddInstituteEnglishRequirements() throws IOException {
	}

	@DisplayName("updateInstituteEnglishRequirements test success")
	@Test
	void updateInstituteEnglishRequirements() throws IOException {

			String paths = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR
					+ instituteEnglishRequirementsDto.getInstituteId();

			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			header.set("userId", userId);
			HttpEntity<InstituteEnglishRequirementsDto> entitys = new HttpEntity<>(instituteEnglishRequirementsDto,
					header);
			ResponseEntity<String> responses = testRestTemplate.exchange(paths, HttpMethod.PUT, entitys, String.class);
			assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("getInstitutePublicEnglishRequirementsByInstituteId test success")
	@Test
	void getInstitutePublicEnglishRequirementsByInstituteId() throws IOException {
	
		/// create new objject
		String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR + instituteId;
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

	@DisplayName("deleteInstituteEnglishRequirementsByRequirementsId test success")
	@Test
	void deleteInstituteEnglishRequirementsByRequirementsId() throws IOException {

		String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR
				+ instituteEnglishRequirementsDto.getInstituteId();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);
		
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.DELETE, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("WrongIddeleteInstituteEnglishRequirementsByRequirementsWrongId")
	@Test
	void wrongIddeleteInstituteEnglishRequirementsByRequirementsId() throws IOException {
		InstituteEnglishRequirementsDto instituteEnglishRequirementsDto =addInstituteEnglishRequirements();
		String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirements" + PATH_SEPARATOR + instituteEnglishRequirementsDto.getInstituteId();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(null,headers);
		ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.DELETE, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}