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
import com.yuzee.app.dto.FacilityDto;
import com.yuzee.app.dto.InstituteFacilityDto;
import com.yuzee.app.dto.InstituteFundingDto;
import com.yuzee.app.dto.InstituteRequestDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.processor.InstituteProcessor;
import com.yuzee.app.repository.InstituteRepository;
import com.yuzee.common.lib.dto.GenericWrapperDto;
import com.yuzee.common.lib.dto.institute.ProviderCodeDto;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;
import com.yuzee.common.lib.util.ObjectMapperHelper;

import lombok.extern.slf4j.Slf4j;


public class TestInstituteFacilitiesController extends CreateCourseAndInstitute {

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
	private InstituteProcessor instituteProcessor;

	@BeforeClass
	public static void main() {
		SpringApplication.run(InstituteBasicInfoController.class);
	}

	// institute/facilities/{instituteId}
	@DisplayName("addInstituteFacilities test success")
	@Test
	void addInstituteFacilities() throws IOException {
		String instituteId = testCreateInstitute();
		try {
			/// add facility
			List<FacilityDto> facilityDtoList = new ArrayList<>();
			facilityDtoList.add(new FacilityDto("378b5724-8500-419f-80d4-fff7cbec3a2f", "testFacilityName",
					"378b5724-8500-419f-80d4-fff7cbec3a2f"));
			InstituteFacilityDto instituteFacilityDto = new InstituteFacilityDto();
			instituteFacilityDto.setFacilities(facilityDtoList);

			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			String paths = INSTITUTE_PATH + PATH_SEPARATOR + "facilities" + PATH_SEPARATOR + instituteId;
			HttpEntity<InstituteFacilityDto> entitys = new HttpEntity<>(instituteFacilityDto, header);
			ResponseEntity<InstituteFacilityDto> responses = testRestTemplate.exchange(paths, HttpMethod.POST, entitys,
					InstituteFacilityDto.class);
			assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
		} finally {
			// clean up code

			instituteProcessor.deleteInstitute(instituteId);

		}
	}

	@DisplayName("getInstituteFacilities test success")
	@Test
	void getInstituteFacilities() throws IOException {
		String instituteId = testCreateInstitute();
		try {
			/// add facility
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String path = INSTITUTE_PATH + PATH_SEPARATOR + "getFacilities" + PATH_SEPARATOR
					+ "5ee981a2-4a18-4ba1-957e-741e7934d1bc";
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.GET, entity, String.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		} finally {
			// clean up code
			instituteProcessor.deleteInstitute(instituteId);
			
		}
	}


	@DisplayName("deleteInstituteFacilitiesById test success")
	@Test
	void deleteInstituteFacilitiesById() throws IOException {
		String instituteId = testCreateInstitute();
			try {
				/// add facility

				Map<String, List<String>> params = new HashMap<>();
				params.put("institute_facility_id", Arrays.asList("6f91fa9b-6911-4fd3-beec-894d83545f35"));
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				String path = INSTITUTE_PATH + PATH_SEPARATOR + "facilities" + PATH_SEPARATOR + instituteId;
				HttpEntity<List<FacilityDto>> entity = new HttpEntity<>(null, headers);
				ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.DELETE, entity,
						String.class, params);
			//	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
			} finally {
				// clean up code
				instituteProcessor.deleteInstitute(instituteId);
					}
	}

	@DisplayName("getInstitutePublicFacilities test success")
	@Test
	void getInstitutePublicFacilities() throws IOException {
		String instituteId = testCreateInstitute();
			try {
				/// add facility
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				String path = INSTITUTE_PATH + PATH_SEPARATOR + "public" + PATH_SEPARATOR + "facilities"
						+ PATH_SEPARATOR + instituteId;
				HttpEntity<List<FacilityDto>> entity = new HttpEntity<>(null, headers);
				ResponseEntity<InstituteFacilityDto> response = testRestTemplate.exchange(path, HttpMethod.GET, entity,
						InstituteFacilityDto.class);
				assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

			} finally {
				// clean up code
				instituteProcessor.deleteInstitute(instituteId);
			
			}
		}
	}
