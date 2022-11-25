package testController;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
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
import com.yuzee.app.dto.InstituteFundingDto;
import com.yuzee.app.dto.InstituteRequestDto;
import com.yuzee.app.dto.InstituteServiceDto;
import com.yuzee.app.dto.ServiceDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.processor.InstituteProcessor;
import com.yuzee.app.repository.InstituteRepository;
import com.yuzee.app.repository.InstituteServiceRepository;
import com.yuzee.app.repository.ServiceRepository;
import com.yuzee.common.lib.dto.institute.ProviderCodeDto;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(JUnitPlatform.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)
class TestInstituteServiceController extends CreateCourseAndInstitute {

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
	private static final UUID IDS = UUID.fromString("1e348e15-45b6-477f-a457-883738227e05");

	// cf13eef7-b188-4e84-baee-7f98a7c33e7b
	@Autowired
	private TestRestTemplate testRestTemplate;

	@MockBean
	private PublishSystemEventHandler publishSystemEventHandler;
	@Autowired
	private InstituteServiceRepository instituteServiceRepository;
	@Autowired
	private InstituteProcessor instituteProcessor;
	@Autowired
	private ServiceRepository serviceRepository;

	@BeforeClass
	public static void main() {
		SpringApplication.run(InstituteBasicInfoController.class);
	}

	@AfterEach
	public void deleteAllInstitute() {
		serviceRepository.deleteAll();
	}

	/// institute/service/instituteId/{instituteId}
	@DisplayName("addInstituteService test success")
	@Test
	void addInstituteService() throws IOException {
		try {
			String instituteId = testCreateInstitute();
			ServiceDto service = new ServiceDto();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("userId", userId);
			String path = INSTITUTE_PATH + PATH_SEPARATOR + "service" + PATH_SEPARATOR + "instituteId" + PATH_SEPARATOR
					+ instituteId;
			List<InstituteServiceDto> instituteServiceDto = new ArrayList<>();
			service.setServiceId("6dc37172-4a10-48b6-b110-6970c187238d");
			service.setDescription("test service controller jUnit description");
			service.setServiceName("testServiceName");
			InstituteServiceDto dto = new InstituteServiceDto();
			dto.setDescription("mydescription");
			dto.setInstituteServiceId("694ddd0f-1e0b-43d9-9401-24403112e162");
			dto.setService(service);
			instituteServiceDto.add(dto);
			HttpEntity<List<InstituteServiceDto>> entity = new HttpEntity<>(instituteServiceDto, headers);
			ResponseEntity<InstituteServiceDto> response = testRestTemplate.exchange(path, HttpMethod.POST, entity,
					InstituteServiceDto.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		} finally {
			// clean up code
			instituteProcessor.deleteInstitute(instituteId);
		}
	}

	@DisplayName("deleteById test success")
	@Test
	void deleteById() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);
		/// institute/service/{instituteServiceId}
		String instituteServiceId = "";
		String path = INSTITUTE_PATH + PATH_SEPARATOR + "service" + PATH_SEPARATOR + "6dc37172-4a10-48b6-b110-6970c187238d";
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
		String instituteId = testCreateInstitute();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// headers.set("userId", userId);
		String path = INSTITUTE_PATH + PATH_SEPARATOR + "service" + PATH_SEPARATOR + "instituteId" + PATH_SEPARATOR
				+ instituteId;
		HttpEntity<List<InstituteServiceDto>> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.GET, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
