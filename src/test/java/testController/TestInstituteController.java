package testController;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mongodb.client.MongoClients;
import com.yuzee.app.YuzeeApplication;
import com.yuzee.app.bean.InstituteDomesticRankingHistory;
import com.yuzee.app.bean.InstituteWorldRankingHistory;
import com.yuzee.app.bean.Location;
import com.yuzee.app.controller.v1.InstituteController;
import com.yuzee.app.dao.impl.InstituteDaoImpl;
import com.yuzee.app.dto.AccrediatedDetailDto;
import com.yuzee.app.dto.CourseSearchDto;
import com.yuzee.app.dto.InstituteDomesticRankingHistoryDto;
import com.yuzee.app.dto.InstituteFilterDto;
import com.yuzee.app.dto.InstituteFundingDto;
import com.yuzee.app.dto.InstituteRequestDto;
import com.yuzee.app.dto.InstituteResponseDto;
import com.yuzee.app.dto.InstituteTypeDto;
import com.yuzee.app.dto.InstituteWorldRankingHistoryDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.processor.CommonProcessor;
import com.yuzee.app.processor.InstituteProcessor;
import com.yuzee.app.repository.InstituteRepository;
import com.yuzee.common.lib.dto.GenericWrapperDto;
import com.yuzee.common.lib.dto.PaginationResponseDto;
import com.yuzee.common.lib.dto.connection.FollowerCountDto;
import com.yuzee.common.lib.dto.institute.InstituteSyncDTO;
import com.yuzee.common.lib.dto.institute.ProviderCodeDto;
import com.yuzee.common.lib.dto.storage.StorageDto;
import com.yuzee.common.lib.enumeration.EntitySubTypeEnum;
import com.yuzee.common.lib.enumeration.EntityTypeEnum;
import com.yuzee.common.lib.handler.ConnectionHandler;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;
import com.yuzee.common.lib.handler.ReviewHandler;
import com.yuzee.common.lib.handler.StorageHandler;
import com.yuzee.common.lib.util.ObjectMapperHelper;
import com.yuzee.common.lib.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)
class TestInstituteController extends CreateCourseAndInstitute {

	@MockBean
	ReviewHandler reviewHandler;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@MockBean
	private PublishSystemEventHandler publishSystemEventHandler;
	@MockBean
	private ConnectionHandler connectionHandler;
	@MockBean
	private StorageHandler storageHandler;
	@Autowired
	InstituteProcessor instituteProcessor;
	@MockBean
	CommonProcessor commonProcessor;
	@MockBean
	PaginationUtil paginationUtil;

	@Autowired
	private InstituteRepository instituteRepository;

	public static void main() {
		SpringApplication.run(InstituteController.class);
	}

	@AfterEach
	public void deleteAllInstitute() {
		// instituteRepository.deleteAll();
	}

	@DisplayName("change Institute status test success")
	@Test
	void changeInstituteStatus() throws IOException {

		boolean status = true;
		String instituteId = testCreateInstitute();
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.set(USER_ID, userId);
		Map<String, Boolean> params = new HashMap<>();
		params.put("status", status);
		HttpEntity<String> entitys = new HttpEntity<>(header);
		ResponseEntity<String> respons = testRestTemplate.exchange(
				INSTITUTE_PATH + PATH_SEPARATOR + "status" + PATH_SEPARATOR + instituteId, HttpMethod.PUT, entitys,
				String.class, params);
		assertThat(respons.getStatusCode()).isEqualTo(HttpStatus.OK);

	}
	@DisplayName("wrongId Institute status test success")
	@Test
	void wrongIdchangeInstituteStatus() throws IOException {

		boolean status = true;
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.set(USER_ID, userId);
		Map<String, Boolean> params = new HashMap<>();
		params.put("status", status);
		HttpEntity<String> entitys = new HttpEntity<>(header);
		ResponseEntity<String> respons = testRestTemplate.exchange(
				INSTITUTE_PATH + PATH_SEPARATOR + "status" + PATH_SEPARATOR + "djasuidniu55n36n56ghgbgh",
				HttpMethod.PUT, entitys, String.class, params);
		assertThat(respons.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}

	@DisplayName("save instituteType")
	@Test
	void saveInstituteType() throws IOException {
		String instituteId = testCreateInstitute();
		try {

			InstituteTypeDto instituteTypeDto = new InstituteTypeDto();
			instituteTypeDto.setCountryName("INDIA");
			instituteTypeDto.setDescription("Test save instituteType description");
			instituteTypeDto.setType("School");
			HttpHeaders header = new HttpHeaders();
			header.set("instituteId", instituteId);
			header.setContentType(MediaType.APPLICATION_JSON);
			Map<String, String> params = new HashMap<>();
			params.put("instituteType", "SCHOOL");
			HttpEntity<InstituteTypeDto> entitys = new HttpEntity<>(instituteTypeDto, header);
			ResponseEntity<String> responsed = testRestTemplate.exchange(
					INSTITUTE_PRE_PATH + PATH_SEPARATOR + "instituteType?instituteType=SMALL_MEDIUM_PRIVATE_SCHOOL",
					HttpMethod.POST, entitys, String.class, params);
			assertThat(responsed.getStatusCode()).isEqualTo(HttpStatus.OK);
		} finally {
			// clean up code
			instituteProcessor.deleteInstitute(instituteId);
		}
	}

	@DisplayName("get InstituteType by CountryName")
	@Test
	void testGetInstituteTypeByCountryName() throws IOException {

		InstituteTypeDto instituteTypeDto = new InstituteTypeDto();
		instituteTypeDto.setCountryName("INDIA");
		instituteTypeDto.setDescription("Test save instituteType description");
		instituteTypeDto.setType("School");
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		Map<String, String> params = new HashMap<>();
		params.put("countryName", "INDIA");
		String countryName = "INDIA";
		Map<String, String> param = new HashMap<>();
		params.put("countryName", countryName);
		HttpHeaders headerer = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entityer = new HttpEntity<>(headerer);
		ResponseEntity<String> responseds = testRestTemplate.exchange(
				INSTITUTE_PRE_PATH + PATH_SEPARATOR + "type?countryName=INDIA", HttpMethod.GET, entityer, String.class,
				param);
		assertThat(responseds.getStatusCode()).isEqualTo(HttpStatus.OK);
	}


	@DisplayName("get Institute Types")
	@Test
	void getInstituteType() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<InstituteTypeDto> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				INSTITUTE_PRE_PATH + PATH_SEPARATOR + "institute" + PATH_SEPARATOR + "type", HttpMethod.GET, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("InstituteSearch")
	@Test
	void instituteSearch() {
		CourseSearchDto courseSearchDto = new CourseSearchDto();
		courseSearchDto.setCityNames(Arrays.asList("MUMBAI", "DELHI", "TORONTO", "NEW YORK", "HONGKONG", "PERIS"));
		courseSearchDto.setCountryNames(Arrays.asList("INDIA", "USA", "UK", "GERMANY", "SPAIN", "ITALY", "JAPAN"));
		courseSearchDto.setCourseName("Testing course changesdda");
		courseSearchDto.setPageNumber(1);
		courseSearchDto.setMaxSizePerPage(2);
		courseSearchDto.setInstituteId("606ac3c4-c7c1-4c2b-9425-fa88fae75ce9");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CourseSearchDto> entity = new HttpEntity<>(courseSearchDto, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PRE_PATH + PATH_SEPARATOR + "search",
				HttpMethod.POST, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}


//	/// search/pageNumber/{pageNumber}/pageSize/{pageSize}
	@DisplayName("Dynamic InstituteSearch")
	@Test
	  void testDynamicInstituteSearch() throws IOException {
		List<String> countryName = Arrays.asList("USA", "ITALY", "ICELAND", "GERMANY", "BRAZIL");
		List<String> facultyIds = Arrays.asList("5b05d529-ec0a-11ea-a757-02f6d1a05b4e",
				"5b8fac9f-d00f-3309-bd3a-f13616229bae", "7d25b4c8-0935-11eb-a757-02f6d1a05b4e",
				"7d293037-ec0a-11ea-a757-02f6d1a05b4e", "7f474257-4680-4ff1-82f8-179bee4ee402");
		List<String> levelIds = Arrays.asList("5b05d529-ec0a-11ea-a757-02f6d1a05b4e",
				"5b8fac9f-d00f-3309-bd3a-f13616229bae", "7d25b4c8-0935-11eb-a757-02f6d1a05b4e",
				"7d293037-ec0a-11ea-a757-02f6d1a05b4e", "7f474257-4680-4ff1-82f8-179bee4ee402");
		String cityName = "NEW YORK";
		String instituteType = "SCHOOL";
		Boolean isActive = true;
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat updatedOn = new SimpleDateFormat(pattern);
		Integer fromWorldRanking = 1123;
		Integer toWorldRanking = 5643;
		int pageNumber = 1;
		int pageSize = 2;
		
		StringBuilder path = new StringBuilder();
		path.append(INSTITUTE_PRE_PATH).append(PATH_SEPARATOR).append("search").append(PATH_SEPARATOR)
				.append("pageNumber").append(PATH_SEPARATOR).append(pageNumber).append(PATH_SEPARATOR)
				.append("pageSize").append(PATH_SEPARATOR).append(pageSize);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(path.toString())
				.queryParam("countryName", countryName).queryParam("facultyIds", facultyIds)
				.queryParam("levelIds", levelIds).queryParam("cityName", cityName).queryParam("updatedOn", updatedOn)
				.queryParam("fromWorldRanking", fromWorldRanking).queryParam("toWorldRanking", toWorldRanking);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CourseSearchDto> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PRE_PATH + PATH_SEPARATOR + "autoSearch"+PATH_SEPARATOR+"Indore"+PATH_SEPARATOR+"pageNumber"+PATH_SEPARATOR+2+PATH_SEPARATOR+"pageSize"+PATH_SEPARATOR+2,
				HttpMethod.GET, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName("getAllRecommendedInstitutes")
	@Test
	void testGetAllRecommendedInstitutes() {
		CourseSearchDto courseSearchDto = new CourseSearchDto();
		courseSearchDto.setCityNames(Arrays.asList("indore"));
		courseSearchDto.setCountryNames(Arrays.asList("INDIA", "GERMANY"));
		courseSearchDto.setCourseName("Testing course changesdda");
		courseSearchDto.setPageNumber(1);
		courseSearchDto.setMaxSizePerPage(2);
		courseSearchDto.setInstituteId("694ddd0f-1e0b-43d9-9401-24403112e161");
		courseSearchDto.setUserId("8d7c017d-37e3-4317-a8b5-9ae6d9cdcb49");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CourseSearchDto> entity = new HttpEntity<>(courseSearchDto, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PRE_PATH + PATH_SEPARATOR + "recommended",
				HttpMethod.POST, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	

	

	@DisplayName("getInstituteByCityName")
	@Test
	void testGetInstituteByCityName() throws IOException {
		String instituteId = testCreateInstitute();
		String cityName = "AHMEDABAD";
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<InstituteTypeDto> entitys = new HttpEntity<>(null, header);
		ResponseEntity<String> respons = testRestTemplate.exchange(
				INSTITUTE_PRE_PATH + PATH_SEPARATOR + "city" + PATH_SEPARATOR + cityName, HttpMethod.GET, entitys,
				String.class);
		assertThat(respons.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName("update")
	@Test
	void testUpdate() throws IOException {
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
		for (InstituteRequestDto data : r) {

			try {

				instituteProviderCode.setName("TestProviderName");
				instituteProviderCode.setValue(("TestProviderValue"));
				listOfInstituteProviderCode.add(instituteProviderCode);
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
				listOfInstituteRequestDto.add(instituteRequestDto);
				listOfInstituteProviderCode.add(instituteProviderCode);
				instituteRequestDto.setProviderCodes(listOfInstituteProviderCode);

				HttpHeaders header = new HttpHeaders();
				header.setContentType(MediaType.APPLICATION_JSON);
				header.add("userId", userId);
				header.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<InstituteRequestDto> entitys = new HttpEntity<>(instituteRequestDto, header);
				ResponseEntity<String> responses = testRestTemplate.exchange(
						INSTITUTE_PRE_PATH + PATH_SEPARATOR + data.getInstituteId(), HttpMethod.PUT, entitys,
						String.class);
				assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);

			} finally {
				// clean up code
				ResponseEntity<String> responses = testRestTemplate.exchange(
						INSTITUTE_PRE_PATH + PATH_SEPARATOR + data.getInstituteId(), HttpMethod.DELETE, null,
						String.class);
				instituteProcessor.deleteInstitute(data.getInstituteId());
				assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
			}
		}

	}

	@DisplayName("testGetAllCategoryType")
	@Test
	void testGetAllCategoryType() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				INSTITUTE_PRE_PATH + PATH_SEPARATOR + "allCategoryType", HttpMethod.GET, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("WrongIdtestDeleteInstitute")
	@Test
	  void wrongIdtestDeleteInstitute() {
		
		HttpHeaders createHeaders = new HttpHeaders();
		createHeaders.setContentType(MediaType.APPLICATION_JSON);
				ResponseEntity<String> response = testRestTemplate
				.exchange(INSTITUTE_PRE_PATH + PATH_SEPARATOR +"d7e9ab4d-dedc-4759-acf2-7197f4", HttpMethod.DELETE, null, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	
	@DisplayName("testGetAllInstitute")
	@Test
	void testGetAllInstitute() {

		int pageNumber = 2;
		int pageSize = 2;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		String path = INSTITUTE_PRE_PATH + PAGE_NUMBER_PATH + PATH_SEPARATOR + pageNumber + PAGE_SIZE_PATH
				+ PATH_SEPARATOR + pageSize;
		ResponseEntity<PaginationResponseDto> response = testRestTemplate.exchange(path, HttpMethod.GET, entity,
				PaginationResponseDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName("testGetAllInstituteAutoSearch")
	@Test
	void testGetAllInstituteAutoSearch() {

		int pageNumber = 1;
		int pageSize = 2;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "autoSearch" + PATH_SEPARATOR + "INDIA" + PAGE_NUMBER_PATH
				+ PATH_SEPARATOR + 2 + PAGE_SIZE_PATH + PATH_SEPARATOR + 2;
		ResponseEntity<PaginationResponseDto> response = testRestTemplate.exchange(path, HttpMethod.GET, entity,
				PaginationResponseDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("get by id")
	@Test
	void testGetById() throws IOException {

		Boolean is_readable_id = true;
		String instituteId = testCreateInstitute();
		Map<String, Boolean> params = new HashMap<>();
		params.put("is_readable_id", is_readable_id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		List<EntitySubTypeEnum> news = new ArrayList<>();
		news.add(EntitySubTypeEnum.LOGO);
		news.add(EntitySubTypeEnum.COVER_PHOTO);
		List<String> id = new ArrayList<>();
		id.add("795592f1-3665-4649-89b0-39cb844e78d0");

		Mockito.when(connectionHandler.checkFollowerExist(userId, "795592f1-3665-4649-89b0-39cb844e78d0"))
				.thenReturn(true);
		Mockito.when(storageHandler.getStorages(id, EntityTypeEnum.INSTITUTE, news))
				.thenReturn(new ArrayList<StorageDto>());
		Mockito.when(reviewHandler.getAverageReview("newInstitute", id)).thenReturn(new HashMap());
		HttpEntity<InstituteResponseDto> entity = new HttpEntity<>(null, headers);
		ResponseEntity<InstituteRequestDto> response = testRestTemplate.exchange(
				INSTITUTE_PRE_PATH + PATH_SEPARATOR + instituteId, HttpMethod.GET, entity, InstituteRequestDto.class,
				params);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName("instituteFilter")
	@Test
	void testInstituteFilter() throws IOException {

		InstituteFilterDto instituteFilterDto = new InstituteFilterDto();
		instituteFilterDto.setCityName("indore");
		instituteFilterDto.setCountryName("INDIA");
		instituteFilterDto.setInstituteId("694ddd0f-1e0b-43d9-9401-24403112e161");
		instituteFilterDto.setDatePosted("2021-02-17");
		instituteFilterDto.setName("Rgpv");
		instituteFilterDto.setMaxSizePerPage(2);
		instituteFilterDto.setPageNumber(2);
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<InstituteFilterDto> entitys = new HttpEntity<>(instituteFilterDto, header);
		ResponseEntity<InstituteFilterDto> responses = testRestTemplate.exchange(
				INSTITUTE_PRE_PATH + PATH_SEPARATOR + "filter", HttpMethod.POST, entitys, InstituteFilterDto.class);
		assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	

	@DisplayName("testGetAllInstituteType")
	@Test
	void testGetAllInstituteType() throws IOException {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PRE_PATH, HttpMethod.GET, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("testDeleteInstitute")
	@Test
	void testDeleteInstitute() throws IOException {
		String instituteId = testCreateInstitute();
		ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PRE_PATH + PATH_SEPARATOR + instituteId,
				HttpMethod.DELETE, null, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("testGetHistoryOfDomesticRanking")
	@Test
	void testGetHistoryOfDomesticRanking() throws IOException {

		String instituteId = testCreateInstitute();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<InstituteDomesticRankingHistoryDto> responses = testRestTemplate.exchange(
					INSTITUTE_PRE_PATH + PATH_SEPARATOR + "history" + PATH_SEPARATOR + "domestic" + PATH_SEPARATOR
							+ "ranking?instituteId=" + instituteId,
					HttpMethod.GET, entity, InstituteDomesticRankingHistoryDto.class);
			InstituteDomesticRankingHistoryDto domesticRankingHistoryDto = new InstituteDomesticRankingHistoryDto();

			assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);

		} finally {
			// clean up code

			instituteProcessor.deleteInstitute(instituteId);
		}
	}

	@DisplayName("testGetHistoryOfWorldRanking")
	@Test
	void testGetHistoryOfWorldRanking() throws IOException {
		String instituteId = testCreateInstitute();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<InstituteWorldRankingHistoryDto> responses = testRestTemplate.exchange(
					INSTITUTE_PRE_PATH + PATH_SEPARATOR + "history" + PATH_SEPARATOR + "world" + PATH_SEPARATOR
							+ "ranking?instituteId=" + instituteId,
					HttpMethod.GET, entity, InstituteWorldRankingHistoryDto.class);
			InstituteWorldRankingHistoryDto domesticRankingHistoryDto = new InstituteWorldRankingHistoryDto();
			assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);

		} finally {
			
			
			// clean up code
			instituteProcessor.deleteInstitute(instituteId);

		}
	}

	@DisplayName("testGetInstituteFaculties")
	@Test
	void testGetInstituteFaculties() throws IOException {
		String instituteId = testCreateInstitute();
		try {
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entitys = new HttpEntity<>(header);
			ResponseEntity<String> responses = testRestTemplate.exchange(INSTITUTE_PRE_PATH + PATH_SEPARATOR + "faculty"
					+ PATH_SEPARATOR + "instituteId" + PATH_SEPARATOR + instituteId, HttpMethod.GET, entitys,
					String.class);
			assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);

		} finally {
			// clean up code
			instituteProcessor.deleteInstitute(instituteId);
		}
	}

	@DisplayName("getDistinctInstitutes")
	@Test
	void getDistinctInstitutes() throws IOException {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entitys = new HttpEntity<>(header);
		List<String> instituteIds = new ArrayList();
		instituteIds.add(instituteId);
		List<EntitySubTypeEnum> logo = new ArrayList<>();
		logo.add(EntitySubTypeEnum.LOGO );
		logo.add(EntitySubTypeEnum.COVER_PHOTO);
		Mockito.when(storageHandler.getStorages(instituteIds, EntityTypeEnum.INSTITUTE, logo ))
		.thenReturn(new ArrayList<StorageDto>());
		Mockito.when(reviewHandler.getAverageReview("INSTITUTE", instituteIds)).thenReturn(new HashMap());
		ResponseEntity<String> responses = testRestTemplate.exchange(
				INSTITUTE_PRE_PATH + PATH_SEPARATOR + "names" + PATH_SEPARATOR + "distinct" + PATH_SEPARATOR
						+ "pageNumber" + PATH_SEPARATOR + 2 + PATH_SEPARATOR + "pageSize" + PATH_SEPARATOR + 2,
				HttpMethod.GET, entitys, String.class);
		assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("getInstituteByCountryName")
	@Test
	void getInstituteByCountryName() throws IOException {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entitys = new HttpEntity<>(header);
		ResponseEntity<String> responses = testRestTemplate.exchange(
				INSTITUTE_PRE_PATH + PATH_SEPARATOR + "institute" + PATH_SEPARATOR + "pageNumber" + PATH_SEPARATOR + 2
						+ PATH_SEPARATOR + "pageSize" + PATH_SEPARATOR + 2 + PATH_SEPARATOR + "INDIA",
				HttpMethod.GET, entitys, String.class);
		assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName("getInstituteCourseScholarshipAndFacultyCount")
	@Test
	void getInstituteCourseScholarshipAndFacultyCount() throws IOException {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entitys = new HttpEntity<>(header);
		ResponseEntity<String> responses = testRestTemplate.exchange(
				INSTITUTE_PRE_PATH + PATH_SEPARATOR + "course-faculty-scholarship" + PATH_SEPARATOR + "count"
						+ PATH_SEPARATOR + "instituteId" + PATH_SEPARATOR + "694ddd0f-1e0b-43d9-9401-24403112e161",
				HttpMethod.GET, entitys, String.class);
		assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName("EmptyCityNamesaveinstitutes")
	@Test
	void emptyCityNametestCreateInstitute() throws IOException {

		ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
		ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();

		List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
		ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
		instituteProviderCode.setName("TestProviderName");
		instituteProviderCode.setValue(("TestProviderValue"));
		listOfInstituteProviderCode.add(instituteProviderCode);
		InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
		instituteRequestDto.setName("DMPds");
		instituteRequestDto.setCountryName("INDIA");
		instituteRequestDto.setWebsite("https://www.centrallanguageschool.com/");
		instituteRequestDto.setLatitude(91.202743);
		instituteRequestDto.setLongitude(56.1240);
		instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
		instituteRequestDto.setPostalCode(1234);
		instituteRequestDto.setReadableId("3889fdc-c292-69ea-a757-09f6d1a0mrvh");
		instituteRequestDto.setTagLine("Inspirings");
		instituteRequestDto.setShowSuggestion(true);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		listOfInstituteRequestDto.add(instituteRequestDto);
		instituteRequestDto.setProviderCodes(listOfInstituteProviderCode);
		HttpEntity<ValidList<InstituteRequestDto>> entity = new HttpEntity<>(listOfInstituteRequestDto, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PRE_PATH, HttpMethod.POST, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

	}

	@DisplayName("EmptyNamesaveinstitutes")
	@Test
	void emptyNametestCreateInstitute() throws IOException {

		ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
		ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();

		List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
		ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
		instituteProviderCode.setName("TestProviderName");
		instituteProviderCode.setValue(("TestProviderValue"));
		listOfInstituteProviderCode.add(instituteProviderCode);
		InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
		instituteRequestDto.setCountryName("INDIA");
		instituteRequestDto.setCityName("indore");
		instituteRequestDto.setWebsite("https://www.centrallanguageschool.com/");
		instituteRequestDto.setLatitude(91.202743);
		instituteRequestDto.setLongitude(56.1240);
		instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
		instituteRequestDto.setPostalCode(1234);
		instituteRequestDto.setReadableId("3889fdc-c292-69ea-a857-05f6d1a04rvh");
		instituteRequestDto.setTagLine("Inspirings");
		instituteRequestDto.setShowSuggestion(true);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		listOfInstituteRequestDto.add(instituteRequestDto);
		instituteRequestDto.setProviderCodes(listOfInstituteProviderCode);
		HttpEntity<ValidList<InstituteRequestDto>> entity = new HttpEntity<>(listOfInstituteRequestDto, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PRE_PATH, HttpMethod.POST, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

	}

	@DisplayName("Emptycountry_nameveinstitutes")
	@Test
	void emptycountryNameCreateInstitute() throws IOException {

		ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
		ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();

		List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
		ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
		instituteProviderCode.setName("TestProviderName");
		instituteProviderCode.setValue(("TestProviderValue"));
		listOfInstituteProviderCode.add(instituteProviderCode);
		InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
		instituteRequestDto.setName("pdm");
		instituteRequestDto.setCityName("indore");
		instituteRequestDto.setWebsite("https://www.centrallanguageschool.com/");
		instituteRequestDto.setLatitude(91.202743);
		instituteRequestDto.setLongitude(56.1240);
		instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
		instituteRequestDto.setPostalCode(1234);
		instituteRequestDto.setReadableId("3889fdc-c292-69ea-a757-09f6d1a04rpo");
		instituteRequestDto.setTagLine("Inspirings");
		instituteRequestDto.setShowSuggestion(true);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		listOfInstituteRequestDto.add(instituteRequestDto);
		instituteRequestDto.setProviderCodes(listOfInstituteProviderCode);
		HttpEntity<ValidList<InstituteRequestDto>> entity = new HttpEntity<>(listOfInstituteRequestDto, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PRE_PATH, HttpMethod.POST, entity,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

	}

}