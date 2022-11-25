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
import com.yuzee.app.bean.InstituteCampus;
import com.yuzee.app.bean.Location;
import com.yuzee.app.controller.v1.InstituteController;
import com.yuzee.app.dto.InstituteFundingDto;
import com.yuzee.app.dto.InstituteRequestDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.processor.InstituteProcessor;
import com.yuzee.app.repository.InstituteCampusRepository;
import com.yuzee.app.repository.InstituteRepository;
import com.yuzee.common.lib.dto.GenericWrapperDto;
import com.yuzee.common.lib.dto.institute.ProviderCodeDto;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;
import com.yuzee.common.lib.util.ObjectMapperHelper;

import lombok.extern.slf4j.Slf4j;


class TestInstituteCampusController extends CreateCourseAndInstitute {

	private static final String entityId = UUID.randomUUID().toString();
	private static final String instituteId = "a2a00b2a-6b2d-41f1-8501-8ba882ee2b2a";
	private static final String userId = "8d7c017d-37e3-4317-a8b5-9ae6d9cdcb49";
	private static final String USER_ID = "userId";
	private static final String INSTITUTE_PRE_PATH = "/api/v1";
	private static final String PATH_SEPARATOR = "/";

	@Autowired
	private TestRestTemplate testRestTemplate;
	@Autowired
	InstituteRepository instituteRepository;
	@MockBean
	private PublishSystemEventHandler publishSystemEventHandler;

	@Autowired
	private InstituteProcessor instituteProcessor;

	@BeforeClass
	public static void main() {
		SpringApplication.run(InstituteController.class);
	}

	/// campus/instituteId/{instituteId}
	@DisplayName("addCampus test success")
	@Test
	void addCampus() throws IOException {
		String instituteId = testCreateInstitute();
		try {
			HttpHeaders header = new HttpHeaders();

			header.set("userId", userId);
			header.setContentType(MediaType.APPLICATION_JSON);
			List<String> instituteIds = Arrays.asList(userId, instituteId, instituteId);
			HttpEntity<List<String>> entitys = new HttpEntity<>(instituteIds, header);
			String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "campus" + PATH_SEPARATOR + "instituteId"
					+ PATH_SEPARATOR + instituteId;
			ResponseEntity<String> responsess = testRestTemplate.exchange(path, HttpMethod.POST, entitys, String.class);
			assertThat(responsess.getStatusCode()).isEqualTo(HttpStatus.OK);
		} finally {
			// clean up code
			//instituteProcessor.deleteInstitute(instituteId);
		}
	}

	@DisplayName("getInstituteCampuses test success")
	@Test
	void getInstituteCampuses() throws IOException {
		String instituteId = testCreateInstitute();
		// create new campus
		/*
		 * HttpHeaders header = new HttpHeaders();
		 * 
		 * header.set("userId", userId);
		 * header.setContentType(MediaType.APPLICATION_JSON); List<String> instituteIds
		 * = Arrays.asList(userId, "1e348e15-45b6-477f-a457-883738227e05");
		 * HttpEntity<List<String>> entitys = new HttpEntity<>(instituteIds, header);
		 * String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "campus" + PATH_SEPARATOR
		 * + "instituteId" + PATH_SEPARATOR + PATH_SEPARATOR + data.getInstituteId();
		 * ResponseEntity<String> responses = testRestTemplate.exchange(path,
		 * HttpMethod.POST, entitys, String.class);
		 * assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
		 * InstituteCampus instituteCampus = new InstituteCampus();
		 */
		try {
			// instituteCampus.setId(UUID.randomUUID().toString());
			HttpHeaders headerr = new HttpHeaders();
			headerr.set("userId", userId);
			headerr.setContentType(MediaType.APPLICATION_JSON);
			List<String> instituteIdss = Arrays.asList(userId, instituteId);
			HttpEntity<List<String>> entityss = new HttpEntity<>(instituteIdss, headerr);
			String paths = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "campus" + PATH_SEPARATOR + "instituteId"
					+ PATH_SEPARATOR + instituteId;
			ResponseEntity<String> responsess = testRestTemplate.exchange(paths, HttpMethod.GET, entityss,
					String.class);
			assertThat(responsess.getStatusCode()).isEqualTo(HttpStatus.OK);
		} finally {
			// clean up code
		//	instituteProcessor.deleteInstitute(instituteId);

		}
	}

	@DisplayName("deleteInstituteCampuses test success")
	@Test
	void removeCampus() throws IOException {
	      String instituteId = testCreateInstitute();
			// create new campus institute
		
			try {

				HttpHeaders heade = new HttpHeaders();
				heade.set("userId", userId);
				heade.setContentType(MediaType.APPLICATION_JSON);
				List<String> instituteIds = Arrays.asList(userId, instituteId, instituteId);
				HttpEntity<List<String>> entitys = new HttpEntity<>(instituteIds, heade);
				String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "campus" + PATH_SEPARATOR + "instituteId"
						+ PATH_SEPARATOR + instituteId;
				ResponseEntity<String> responses = testRestTemplate.exchange(path, HttpMethod.DELETE, entitys,
						String.class);
				assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
			} finally {
				// clean up code
				//instituteProcessor.deleteInstitute(instituteId);
			}
			}
}