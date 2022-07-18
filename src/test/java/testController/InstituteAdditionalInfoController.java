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
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yuzee.app.YuzeeApplication;
import com.yuzee.app.dto.InstituteAdditionalInfoDto;
import com.yuzee.app.dto.InstituteFundingDto;
import com.yuzee.app.dto.InstituteRequestDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.repository.InstituteRepository;
import com.yuzee.common.lib.dto.GenericWrapperDto;
import com.yuzee.common.lib.dto.institute.ProviderCodeDto;
import com.yuzee.common.lib.enumeration.EntitySubTypeEnum;
import com.yuzee.common.lib.enumeration.EntityTypeEnum;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;
import com.yuzee.common.lib.handler.StorageHandler;
import com.yuzee.common.lib.util.ObjectMapperHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)
public class InstituteAdditionalInfoController {

	private static final String entityId = UUID.randomUUID().toString();
	private static final String instituteId = "795592f1-3665-4649-89b0-39cb844e78d0";
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
	private StorageHandler storageHandler;
	@Autowired
	InstituteRepository instituteRepository;

	@BeforeClass
	public static void main() {
		SpringApplication.run(InstituteController.class);
	}

	/// additional/info/{instituteId}
	@DisplayName("addInstituteAdditionalInfo test success")
	@Test
	public void addInstituteAdditionalInfo() throws IOException {
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
		instituteRequestDto.setLatitude(19.202743);
		instituteRequestDto.setLongitude(65.124018);
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

			try {
				InstituteAdditionalInfoDto instituteAdditionalInfoDto = new InstituteAdditionalInfoDto();
				instituteAdditionalInfoDto.setNumberOfClassRoom(20);
				instituteAdditionalInfoDto.setNumberOfEmployee(8);
				instituteAdditionalInfoDto.setNumberOfLectureHall(2);
				instituteAdditionalInfoDto.setNumberOfFaculty(4);
				instituteAdditionalInfoDto.setAboutInfo("Test addUpdateInstituteAdditionalInfo aboutInfo");
				instituteAdditionalInfoDto.setNumberOfTeacher(8);
				instituteAdditionalInfoDto.setNumberOfStudent(400);
				instituteAdditionalInfoDto.setSizeOfCampus(122);
				instituteAdditionalInfoDto.setRateOfEmployment(1000);
				HttpHeaders header = new HttpHeaders();
				header.set("userId", userId);
				header.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<InstituteAdditionalInfoDto> entitys = new HttpEntity<>(instituteAdditionalInfoDto, header);
				ResponseEntity<String> responses = testRestTemplate
						.exchange(
								INSTITUTE_PRE_PATH + PATH_SEPARATOR + "additional" + PATH_SEPARATOR + "info"
										+ PATH_SEPARATOR + data.getInstituteId(),
								HttpMethod.POST, entitys, String.class);
				assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
			} finally {
				// clean up code
				ResponseEntity<String> responses = testRestTemplate.exchange(
						INSTITUTE_PRE_PATH + PATH_SEPARATOR + data.getInstituteId(), HttpMethod.DELETE, null,
						String.class);
				instituteRepository.deleteById(data.getInstituteId());
				assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
			}
		}
	}

	@DisplayName("getInstituteAdditionalInfo test success")
	@Test
	public void getInstituteAdditionalInfo() throws IOException {
		// TODO create institute first, get instituteId then change status
		boolean status = true;
		ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
		ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
		instituteFundingDto.add(0, new InstituteFundingDto(UUID.randomUUID().toString()));

		List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
		ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
		instituteProviderCode.setName("TestName");
		instituteProviderCode.setValue(("TestValue"));
		listOfInstituteProviderCode.add(instituteProviderCode);
		Mockito.when(storageHandler.getStorages("795592f1-3665-4649-89b0-39cb844e78d0", EntityTypeEnum.INSTITUTE,
				EntitySubTypeEnum.ABOUT_US)).thenReturn(new ArrayList());
		InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
		instituteRequestDto.setName("SMK");
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
		instituteRequestDto.setLatitude(19.202743);
		instituteRequestDto.setLongitude(65.124018);
		instituteRequestDto.setEmail("SMK@testEmail.com");
		instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
		instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
		instituteRequestDto.setReadableId("SMK");
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
			HttpHeaders headers = new HttpHeaders();
			createHeaders.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			ResponseEntity<String> response = testRestTemplate
					.exchange(
							INSTITUTE_PRE_PATH + PATH_SEPARATOR + "additional" + PATH_SEPARATOR + "info"
									+ PATH_SEPARATOR + instituteRequestDto.getInstituteId(),
							HttpMethod.GET, entity, String.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		} finally {
			// clean up code
			ResponseEntity<String> response = testRestTemplate.exchange(
					INSTITUTE_PRE_PATH + PATH_SEPARATOR + instituteRequestDto.getInstituteId(), HttpMethod.DELETE, null,
					String.class);
			instituteRepository.deleteById(instituteRequestDto.getInstituteId());
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		}
	}
}