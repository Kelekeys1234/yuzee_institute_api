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
import com.yuzee.app.dto.InstituteServiceDto;
import com.yuzee.app.dto.ServiceDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.processor.InstituteProcessor;
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
public class TestInstituteFacilitiesController {

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
			ServiceDto service = new ServiceDto();
			InstituteServiceDto dto = new InstituteServiceDto();

			for (InstituteRequestDto data : r) {

				HttpHeaders header = new HttpHeaders();
				header.set("userId", userId);
				header.setContentType(MediaType.APPLICATION_JSON);

				String path = INSTITUTE_PATH + PATH_SEPARATOR + "service" + PATH_SEPARATOR + "instituteId"
						+ PATH_SEPARATOR + data.getInstituteId();
				List<InstituteServiceDto> instituteServiceDto = new ArrayList<>();
				service.setServiceId(UUID.randomUUID().toString());
				service.setDescription("test service controller jUnit description");
				service.setServiceName("testServiceName");
				dto.setDescription("mydescription");
				dto.setInstituteServiceId(UUID.randomUUID().toString());
				dto.setService(service);
				instituteServiceDto.add(dto);
				HttpEntity<List<InstituteServiceDto>> entityy = new HttpEntity<>(instituteServiceDto, header);
				ResponseEntity<InstituteServiceDto> responses = testRestTemplate.exchange(path, HttpMethod.POST,
						entityy, InstituteServiceDto.class);
				assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);

				try {
					/// add facility
					List<FacilityDto> facilityDtoList = new ArrayList<>();
					FacilityDto fdto = new FacilityDto();
					fdto.setFacilityName("myfacilityname");
					fdto.setFacilityId(service.getServiceId());
					facilityDtoList.add(fdto);
					InstituteFacilityDto instituteFacilityDto = new InstituteFacilityDto();
					instituteFacilityDto.setFacilities(facilityDtoList);
					String paths = INSTITUTE_PATH + PATH_SEPARATOR + "facilities" + PATH_SEPARATOR
							+ data.getInstituteId();
					HttpEntity<InstituteFacilityDto> entitys = new HttpEntity<>(instituteFacilityDto, headers);
					ResponseEntity<InstituteFacilityDto> responsess = testRestTemplate.exchange(paths, HttpMethod.POST,
							entitys, InstituteFacilityDto.class);
					assertThat(responsess.getStatusCode()).isEqualTo(HttpStatus.OK);
				} finally {
					// clean up code

					ResponseEntity<String> respons = testRestTemplate.exchange(
							INSTITUTE_PRE_PATH + PATH_SEPARATOR + data.getInstituteId(), HttpMethod.DELETE, null,
							String.class);
					instituteProcessor.deleteInstitute(data.getInstituteId());
					assertThat(respons.getStatusCode()).isEqualTo(HttpStatus.OK);

				}
			}
	}

	@DisplayName("getInstituteFacilities test success")
	@Test
	  void getInstituteFacilities() throws IOException {
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
		ServiceDto service = new ServiceDto();
		InstituteServiceDto dto = new InstituteServiceDto();

		for (InstituteRequestDto data : r) {
			HttpHeaders header = new HttpHeaders();
			header.set("userId", userId);
			header.setContentType(MediaType.APPLICATION_JSON);
			
			String path = INSTITUTE_PATH + PATH_SEPARATOR + "service" + PATH_SEPARATOR + "instituteId" + PATH_SEPARATOR
					+ data.getInstituteId();
			List<InstituteServiceDto> instituteServiceDto = new ArrayList<>();
			service.setServiceId(UUID.randomUUID().toString());
			service.setDescription("test service controller jUnit description");
			service.setServiceName("testServiceName");
			dto.setDescription("mydescription");
			dto.setInstituteServiceId(UUID.randomUUID().toString());
			dto.setService(service);
			instituteServiceDto.add(dto);
			HttpEntity<List<InstituteServiceDto>> entityy = new HttpEntity<>(instituteServiceDto, header);
			ResponseEntity<InstituteServiceDto> responses = testRestTemplate.exchange(path, HttpMethod.POST, entityy,
					InstituteServiceDto.class);
			assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);

			/// add facility
			List<FacilityDto> facilityDtoList = new ArrayList<>();
			FacilityDto fdto = new FacilityDto();
			fdto.setFacilityName("myfacilityname");
			fdto.setFacilityId(service.getServiceId());
			facilityDtoList.add(fdto);

			InstituteFacilityDto instituteFacilityDto = new InstituteFacilityDto();
			instituteFacilityDto.setFacilities(facilityDtoList);
			String paths = INSTITUTE_PATH + PATH_SEPARATOR + "facilities" + PATH_SEPARATOR + data.getInstituteId();
			HttpEntity<InstituteFacilityDto> entitys = new HttpEntity<>(instituteFacilityDto, headers);
			ResponseEntity<InstituteFacilityDto> responsess = testRestTemplate.exchange(paths, HttpMethod.POST, entitys,
					InstituteFacilityDto.class);
			assertThat(responsess.getStatusCode()).isEqualTo(HttpStatus.OK);

			try {
				String pathss = INSTITUTE_PATH + PATH_SEPARATOR + "getFacilities" + PATH_SEPARATOR
						+ data.getInstituteId();
				HttpEntity<List<FacilityDto>> entitysy = new HttpEntity<>(facilityDtoList, headers);
				ResponseEntity<String> responsdss = testRestTemplate.exchange(pathss, HttpMethod.GET, entitysy,
						String.class);
				assertThat(responsdss.getStatusCode()).isEqualTo(HttpStatus.OK);
			} finally {
				// clean up code

				ResponseEntity<String> respons = testRestTemplate.exchange(
						INSTITUTE_PRE_PATH + PATH_SEPARATOR + data.getInstituteId(), HttpMethod.DELETE, null,
						String.class);
				instituteProcessor.deleteInstitute(data.getInstituteId());
				assertThat(respons.getStatusCode()).isEqualTo(HttpStatus.OK);
			}
		}
	}

	@DisplayName("deleteInstituteFacilitiesById test success")
	@Test
	  void deleteInstituteFacilitiesById() throws IOException {
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
			ServiceDto service = new ServiceDto();
			InstituteServiceDto dto = new InstituteServiceDto();

			for (InstituteRequestDto data : r) {
				HttpHeaders header = new HttpHeaders();
				header.set("userId", userId);
				header.setContentType(MediaType.APPLICATION_JSON);
				
				String path = INSTITUTE_PATH + PATH_SEPARATOR + "service" + PATH_SEPARATOR + "instituteId"
						+ PATH_SEPARATOR + data.getInstituteId();
				List<InstituteServiceDto> instituteServiceDto = new ArrayList<>();
				service.setServiceId(UUID.randomUUID().toString());
				service.setDescription("test service controller jUnit description");
				service.setServiceName("testServiceName");
				dto.setDescription("mydescription");
				dto.setInstituteServiceId(UUID.randomUUID().toString());
				dto.setService(service);
				instituteServiceDto.add(dto);
				HttpEntity<List<InstituteServiceDto>> entityy = new HttpEntity<>(instituteServiceDto, header);
				ResponseEntity<InstituteServiceDto> responses = testRestTemplate.exchange(path, HttpMethod.POST,
						entityy, InstituteServiceDto.class);
				assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);

				/// add facility
				List<FacilityDto> facilityDtoList = new ArrayList<>();
				FacilityDto fdto = new FacilityDto();
				fdto.setFacilityName("myfacilityname");
				fdto.setFacilityId(service.getServiceId());
				facilityDtoList.add(fdto);

				InstituteFacilityDto instituteFacilityDto = new InstituteFacilityDto();
				instituteFacilityDto.setFacilities(facilityDtoList);
				String paths = INSTITUTE_PATH + PATH_SEPARATOR + "facilities" + PATH_SEPARATOR + data.getInstituteId();
				HttpEntity<InstituteFacilityDto> entitys = new HttpEntity<>(instituteFacilityDto, headers);
				ResponseEntity<InstituteFacilityDto> responsess = testRestTemplate.exchange(paths, HttpMethod.POST,
						entitys, InstituteFacilityDto.class);
				assertThat(responsess.getStatusCode()).isEqualTo(HttpStatus.OK);

				try {

					Map<String, List<String>> params = new HashMap<>();
					params.put("institute_facility_id", Arrays.asList("6f91fa9b-6911-4fd3-beec-894d83545f35"));
					HttpHeaders headerss = new HttpHeaders();
					headerss.setContentType(MediaType.APPLICATION_JSON);
					String pathss = INSTITUTE_PATH + PATH_SEPARATOR + "facilities" + PATH_SEPARATOR
							+ data.getInstituteId() + "?institute_facility_id=" + fdto.getFacilityId();
					HttpEntity<List<FacilityDto>> entityys = new HttpEntity<>(headerss);
					ResponseEntity<String> responseed = testRestTemplate.exchange(pathss, HttpMethod.DELETE, entityys,
							String.class, params);
					assertThat(responseed.getStatusCode()).isEqualTo(HttpStatus.OK);
				} finally {
					// clean up code

					ResponseEntity<String> respons = testRestTemplate.exchange(
							INSTITUTE_PRE_PATH + PATH_SEPARATOR + data.getInstituteId(), HttpMethod.DELETE, null,
							String.class);
					instituteProcessor.deleteInstitute(data.getInstituteId());
					assertThat(respons.getStatusCode()).isEqualTo(HttpStatus.OK);
				}
			}
	}

	@DisplayName("getInstitutePublicFacilities test success")
	@Test
	  void getInstitutePublicFacilities() throws IOException {
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
			ServiceDto service = new ServiceDto();
			InstituteServiceDto dto = new InstituteServiceDto();

			for (InstituteRequestDto data : r) {
				try {
					HttpHeaders header = new HttpHeaders();
					header.set("userId", userId);
					header.setContentType(MediaType.APPLICATION_JSON);
					String path = INSTITUTE_PATH + PATH_SEPARATOR + "service" + PATH_SEPARATOR + "instituteId"
							+ PATH_SEPARATOR + data.getInstituteId();
					List<InstituteServiceDto> instituteServiceDto = new ArrayList<>();
					service.setServiceId(UUID.randomUUID().toString());
					service.setDescription("test service controller jUnit description");
					service.setServiceName("testServiceName");
					dto.setDescription("mydescription");
					dto.setInstituteServiceId(UUID.randomUUID().toString());
					dto.setService(service);
					instituteServiceDto.add(dto);
					HttpEntity<List<InstituteServiceDto>> entityy = new HttpEntity<>(instituteServiceDto, header);
					ResponseEntity<InstituteServiceDto> responses = testRestTemplate.exchange(path, HttpMethod.POST,
							entityy, InstituteServiceDto.class);
					assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
					/// add facility
					List<FacilityDto> facilityDtoList = new ArrayList<>();
					FacilityDto fdto = new FacilityDto();

					fdto.setFacilityName("myfacilityname");
					fdto.setFacilityId(service.getServiceId());
					facilityDtoList.add(fdto);

					InstituteFacilityDto instituteFacilityDto = new InstituteFacilityDto();
					instituteFacilityDto.setFacilities(facilityDtoList);
					String paths = INSTITUTE_PATH + PATH_SEPARATOR + "facilities" + PATH_SEPARATOR
							+ data.getInstituteId();
					HttpEntity<InstituteFacilityDto> entitys = new HttpEntity<>(instituteFacilityDto, headers);
					ResponseEntity<InstituteFacilityDto> responsess = testRestTemplate.exchange(paths, HttpMethod.POST,
							entitys, InstituteFacilityDto.class);
					assertThat(responsess.getStatusCode()).isEqualTo(HttpStatus.OK);

					String pats = INSTITUTE_PATH + PATH_SEPARATOR + "public" + PATH_SEPARATOR + "facilities"
							+ PATH_SEPARATOR + data.getInstituteId();
					HttpEntity<List<FacilityDto>> entityys = new HttpEntity<>(null, headers);
					ResponseEntity<InstituteFacilityDto> responsed = testRestTemplate.exchange(pats, HttpMethod.GET,
							entityys, InstituteFacilityDto.class);
					assertThat(responsed.getStatusCode()).isEqualTo(HttpStatus.OK);
				} finally {
					// clean up code

					ResponseEntity<String> respons = testRestTemplate.exchange(
							INSTITUTE_PRE_PATH + PATH_SEPARATOR + data.getInstituteId(), HttpMethod.DELETE, null,
							String.class);
					instituteProcessor.deleteInstitute(data.getInstituteId());
					assertThat(respons.getStatusCode()).isEqualTo(HttpStatus.OK);
				}
			}
		}
}