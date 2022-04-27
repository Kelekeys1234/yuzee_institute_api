package testController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yuzee.app.YuzeeApplication;
import com.yuzee.app.dto.*;
import com.yuzee.common.lib.dto.GenericWrapperDto;
import com.yuzee.common.lib.dto.PaginationResponseDto;
import com.yuzee.common.lib.dto.institute.ProviderCodeDto;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;
import com.yuzee.common.lib.util.ObjectMapperHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)
public class InstituteController {

    private static final String entityId = UUID.randomUUID().toString();
    private static final String instituteId = "a2a00b2a-6b2d-41f1-8501-8ba882ee2b2a";
    private static final String INSTITUTE_ID = "instituteId";
    private static final String userId = "8d7c017d-37e3-4317-a8b5-9ae6d9cdcb49";
    private static final String USER_ID = "userId";
    private static final String INSTITUTE_PRE_PATH = "/api/v1";
    private static final String INSTITUTE_PATH = INSTITUTE_PRE_PATH + "/institute";
    private static final String PATH_SEPARATOR = "/";
    private static final String PAGE_NUMBER_PATH = "/pageNumber";
    private static final String PAGE_SIZE_PATH = "/pageSize";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private PublishSystemEventHandler publishSystemEventHandler;

    @BeforeClass
    public static void main() {
        SpringApplication.run(InstituteController.class);
    }

    @DisplayName("Add Institute test success")
    @Test
    public void getRandomInstituteIdByCountry() {

        HttpHeaders headers = new HttpHeaders();
        headers.add(USER_ID, userId);
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    // institute/api/v1/institute/status/{instituteId}
    @DisplayName("change Institute status test success")
    @Test
    public void changeInstituteStatus() throws IOException {
        //TODO create institute first, get instituteId then change status
        boolean status = true;
        ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
        ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
        instituteFundingDto.add(0,new InstituteFundingDto( UUID.randomUUID().toString()));

        List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
        ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
        instituteProviderCode.setName("TestName");
        instituteProviderCode.setValue(("TestValue"));
        listOfInstituteProviderCode.add(instituteProviderCode);

        InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
        instituteRequestDto.setName("MIT");
        instituteRequestDto.setCityName("RAJKOT");
        instituteRequestDto.setCountryName("INDIA");
        instituteRequestDto.setEditAccess(true);
        instituteRequestDto.setAboutInfo("INTERNATIONAL Language School, Cambridge, is accredited by the French Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
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
        instituteRequestDto.setId(UUID.randomUUID());
        HttpHeaders createHeaders = new HttpHeaders();
        createHeaders.setContentType(MediaType.APPLICATION_JSON);
        listOfInstituteRequestDto.add(instituteRequestDto);
        listOfInstituteProviderCode.add(instituteProviderCode);
//6f91fa9b-6911-4fd3-beec-894d83545f35
        instituteRequestDto.setProviderCodes(listOfInstituteProviderCode);
        HttpEntity<ValidList<InstituteRequestDto>> createEntity = new HttpEntity<>(listOfInstituteRequestDto, createHeaders);
        ResponseEntity<InstituteRequestDto> responseInstitute = testRestTemplate.exchange(INSTITUTE_PRE_PATH, HttpMethod.POST, createEntity,
                InstituteRequestDto.class);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(USER_ID, userId);
        Map<String, Boolean> params = new HashMap<>();
        params.put("status", status);
        HttpEntity<ValidList<InstituteRequestDto>> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PATH + PATH_SEPARATOR + "status" + PATH_SEPARATOR + instituteRequestDto.getId().toString(), HttpMethod.PUT, entity,
                String.class, params);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("save instituteType")
    @Test
    public void saveInstituteType() throws IOException {
        ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
        ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
        instituteFundingDto.add(0,new InstituteFundingDto( UUID.randomUUID().toString()));

        List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
        ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
        instituteProviderCode.setName("TestName");
        instituteProviderCode.setValue(("TestValue"));
        listOfInstituteProviderCode.add(instituteProviderCode);

        InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
        instituteRequestDto.setName("ATMIYA");
        instituteRequestDto.setCityName("RAJKOT");
        instituteRequestDto.setCountryName("INDIA");
        instituteRequestDto.setEditAccess(true);
        instituteRequestDto.setAboutInfo("INTERNATIONAL Language School, RAJKOT, is accredited by the French Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
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
        instituteRequestDto.setId(UUID.randomUUID());
        HttpHeaders createHeaders = new HttpHeaders();
        createHeaders.setContentType(MediaType.APPLICATION_JSON);
        listOfInstituteRequestDto.add(instituteRequestDto);
        listOfInstituteProviderCode.add(instituteProviderCode);
        instituteRequestDto.setProviderCodes(listOfInstituteProviderCode);
        HttpEntity<ValidList<InstituteRequestDto>> createEntity = new HttpEntity<>(listOfInstituteRequestDto, createHeaders);
        ResponseEntity<String> responseInstitute = testRestTemplate.exchange(INSTITUTE_PRE_PATH, HttpMethod.POST, createEntity,
                String.class);
        InstituteTypeDto instituteTypeDto = new InstituteTypeDto();
        instituteTypeDto.setCountryName("INDIA");
        instituteTypeDto.setDescription("Test save instituteType description");
        instituteTypeDto.setType("School");
        HttpHeaders headers = new HttpHeaders();
        headers.set("instituteId", instituteRequestDto.getId().toString());
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> params = new HashMap<>();
        params.put("instituteType", "SCHOOL");
        HttpEntity<InstituteTypeDto> entity = new HttpEntity<>(instituteTypeDto, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PATH + PATH_SEPARATOR + "instituteType", HttpMethod.POST, entity,
                String.class, params);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("get InstituteType by CountryName")
    @Test
    public void testGetInstituteTypeByCountryName() throws IOException {
        ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
        ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
        instituteFundingDto.add(0,new InstituteFundingDto( UUID.randomUUID().toString()));

        List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
        ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
        instituteProviderCode.setName("ProviderName");
        instituteProviderCode.setValue(("ProviderValue"));
        listOfInstituteProviderCode.add(instituteProviderCode);

        InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
        instituteRequestDto.setName("G.U.I.T");
        instituteRequestDto.setCityName("Toronto");
        instituteRequestDto.setCountryName("CANADA");
        instituteRequestDto.setEditAccess(true);
        instituteRequestDto.setAboutInfo("INTERNATIONAL Language School, Toronto, is accredited by the French Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
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
        instituteRequestDto.setReadableId("RRR");
        instituteRequestDto.setId(UUID.randomUUID());
        HttpHeaders createHeaders = new HttpHeaders();
        createHeaders.setContentType(MediaType.APPLICATION_JSON);
        listOfInstituteRequestDto.add(instituteRequestDto);
        listOfInstituteProviderCode.add(instituteProviderCode);
        instituteRequestDto.setProviderCodes(listOfInstituteProviderCode);
        HttpEntity<ValidList<InstituteRequestDto>> createEntity = new HttpEntity<>(listOfInstituteRequestDto, createHeaders);
        ResponseEntity<String> responseInstitute = testRestTemplate.exchange(INSTITUTE_PRE_PATH, HttpMethod.POST, createEntity,
                String.class);
        String countryName = "CANADA";
        Map<String, String> params = new HashMap<>();
        params.put("countryName", countryName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<InstituteTypeDto> response = testRestTemplate.exchange(INSTITUTE_PRE_PATH + PATH_SEPARATOR + "type", HttpMethod.GET, entity,
                InstituteTypeDto.class, params);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("get Institute Types")
    @Test
    public void getInstituteType() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InstituteTypeDto> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PATH + PATH_SEPARATOR + "institute" + PATH_SEPARATOR + "type", HttpMethod.POST, entity,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("InstituteSearch")
    @Test
    public void instituteSearch() {
        CourseSearchDto courseSearchDto = new CourseSearchDto();
        courseSearchDto.setCityNames(Arrays.asList("MUMBAI", "DELHI", "TORONTO", "NEW YORK", "HONGKONG", "PERIS"));
        courseSearchDto.setCountryNames(Arrays.asList("INDIA", "USA", "UK", "GERMANY", "SPAIN", "ITALY", "JAPAN"));
        courseSearchDto.setCourseName("Test courseName");
        courseSearchDto.setInstituteId(instituteId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CourseSearchDto> entity = new HttpEntity<>(courseSearchDto, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PATH + PATH_SEPARATOR + "search", HttpMethod.POST, entity,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    ///search/pageNumber/{pageNumber}/pageSize/{pageSize}
    @DisplayName("Dynamic InstituteSearch")
    @Test
    public void testDynamicInstituteSearch() throws IOException {
        List<String> countryName = Arrays.asList("USA", "ITALY", "ICELAND", "GERMANY", "BRAZIL");
        List<String> facultyIds = Arrays.asList("5b05d529-ec0a-11ea-a757-02f6d1a05b4e", "5b8fac9f-d00f-3309-bd3a-f13616229bae", "7d25b4c8-0935-11eb-a757-02f6d1a05b4e", "7d293037-ec0a-11ea-a757-02f6d1a05b4e", "7f474257-4680-4ff1-82f8-179bee4ee402");
        List<String> levelIds = Arrays.asList("5b05d529-ec0a-11ea-a757-02f6d1a05b4e", "5b8fac9f-d00f-3309-bd3a-f13616229bae", "7d25b4c8-0935-11eb-a757-02f6d1a05b4e", "7d293037-ec0a-11ea-a757-02f6d1a05b4e", "7f474257-4680-4ff1-82f8-179bee4ee402");
        String cityName = "NEW YORK";
        String instituteType = "SCHOOL";
        Boolean isActive = true;
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat updatedOn = new SimpleDateFormat(pattern);
        Integer fromWorldRanking = 1123;
        Integer toWorldRanking = 5643;
        int pageNumber = 1;
        int pageSize = 2;
        //TODO add more fields
        StringBuilder path = new StringBuilder();
        path.append(INSTITUTE_PATH).append(PATH_SEPARATOR).append("search").append(PATH_SEPARATOR)
                .append("pageNumber").append(PATH_SEPARATOR).append(pageNumber).append(PATH_SEPARATOR)
                .append("pageSize").append(PATH_SEPARATOR).append(pageSize);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(path.toString())
                .queryParam("countryName", countryName).queryParam("facultyIds", facultyIds)
                .queryParam("levelIds", levelIds).queryParam("cityName", cityName)
                .queryParam("updatedOn", updatedOn).queryParam("fromWorldRanking", fromWorldRanking)
                .queryParam("toWorldRanking", toWorldRanking);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CourseSearchDto> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(builder.path(path.toString()).toUriString(), HttpMethod.GET, entity,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        GenericWrapperDto<List<PaginationResponseDto>> genericGetPostByUserIdResponse = ObjectMapperHelper.readValueFromJSON(response.getBody(), new TypeReference<>() {
        });
        assertThat(genericGetPostByUserIdResponse.getMessage()).isEqualTo("Post retrived successfully");
        assertThat(genericGetPostByUserIdResponse.getData()).isNotNull();
        assertThat(genericGetPostByUserIdResponse.getData()).isNotEmpty();
    }
    @DisplayName("getAllRecommendedInstitutes")
    @Test
    public void testGetAllRecommendedInstitutes(){
        CourseSearchDto courseSearchDto = new CourseSearchDto();
        courseSearchDto.setCityNames(Arrays.asList("MUMBAI", "DELHI", "TORONTO", "NEW YORK", "HONGKONG", "PERIS"));
        courseSearchDto.setCountryNames(Arrays.asList("INDIA", "USA", "UK", "GERMANY", "SPAIN", "ITALY", "JAPAN"));
        courseSearchDto.setCourseName("Test courseName");
        courseSearchDto.setInstituteId(instituteId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CourseSearchDto> entity = new HttpEntity<>(courseSearchDto, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PATH + PATH_SEPARATOR + "recommended", HttpMethod.POST, entity,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("getInstituteByCityName")
    @Test
    public void testGetInstituteByCityName(){
        ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
        ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
        instituteFundingDto.add(0,new InstituteFundingDto( UUID.randomUUID().toString()));

        List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
        ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
        instituteProviderCode.setName("ProviderName");
        instituteProviderCode.setValue(("ProviderValue"));
        listOfInstituteProviderCode.add(instituteProviderCode);

        InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
        instituteRequestDto.setName("JNU");
        instituteRequestDto.setCityName("New Delhi");
        instituteRequestDto.setCountryName("INDIA");
        instituteRequestDto.setEditAccess(true);
        instituteRequestDto.setAboutInfo("INTERNATIONAL Language School, New Delhi, is accredited by the French Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
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
        instituteRequestDto.setReadableId("KGF");
        instituteRequestDto.setId(UUID.randomUUID());
        HttpHeaders createHeaders = new HttpHeaders();
        createHeaders.setContentType(MediaType.APPLICATION_JSON);
        listOfInstituteRequestDto.add(instituteRequestDto);
        listOfInstituteProviderCode.add(instituteProviderCode);
        instituteRequestDto.setProviderCodes(listOfInstituteProviderCode);
        HttpEntity<ValidList<InstituteRequestDto>> createEntity = new HttpEntity<>(listOfInstituteRequestDto, createHeaders);
        ResponseEntity<String> responseInstitute = testRestTemplate.exchange(INSTITUTE_PRE_PATH, HttpMethod.POST, createEntity,
                String.class);
        String cityName = "New Delhi";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InstituteTypeDto> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PRE_PATH + PATH_SEPARATOR + "city" + PATH_SEPARATOR + cityName, HttpMethod.GET, entity,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("save institutes")
    @Test
    public void testCreateInstitute(){
        ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
        ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
        instituteFundingDto.add(0,new InstituteFundingDto( UUID.randomUUID().toString()));

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
        instituteRequestDto.setAboutInfo("Domestic Language School, Cambridge, is accredited by the French Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
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
        instituteRequestDto.setId(UUID.randomUUID());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        listOfInstituteRequestDto.add(instituteRequestDto);
        listOfInstituteProviderCode.add(instituteProviderCode);
//6f91fa9b-6911-4fd3-beec-894d83545f35
        instituteRequestDto.setProviderCodes(listOfInstituteProviderCode);
        HttpEntity<ValidList<InstituteRequestDto>> entity = new HttpEntity<>(listOfInstituteRequestDto, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PRE_PATH, HttpMethod.POST, entity,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @DisplayName("update")
    @Test
    public void testUpdate(){
        String instituteID = "6f91fa9b-6911-4fd3-beec-894d83545f35";
        ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
        instituteFundingDto.add(0,new InstituteFundingDto( UUID.randomUUID().toString()));

        List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
        ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
        instituteProviderCode.setName("TestUpdateProviderName");
        instituteProviderCode.setValue(("TestUpdateProviderValue"));
        listOfInstituteProviderCode.add(instituteProviderCode);

        InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
        instituteRequestDto.setName("IIM");
        instituteRequestDto.setCityName("AHMEDABAD");
        instituteRequestDto.setCountryName("INDIA");
        instituteRequestDto.setEditAccess(true);
        instituteRequestDto.setAboutInfo("International Language School, Howard, is accredited by the American Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
        instituteRequestDto.setDescription("Test update method Description114");
        instituteRequestDto.setInstituteFundings(instituteFundingDto);
        instituteRequestDto.setEnrolmentLink("https://www.centrallanguageschool.com/enrol");
        instituteRequestDto.setWhatsNo("https://api.whatsapp.com/send?phone=60173010314");
        instituteRequestDto.setCourseStart("March, April, May");
        instituteRequestDto.setWebsite("https://www.centrallanguageschool.com/");
        instituteRequestDto.setAddress("41 St Andrew's St, Cambridge CB2 3AR, UK");
        instituteRequestDto.setLatitude(19.202743);
        instituteRequestDto.setLongitude(65.124018);
        instituteRequestDto.setEmail("iim@testEmail.com");
        instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
        instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
        instituteRequestDto.setReadableId("DMS");
        instituteRequestDto.setId(UUID.randomUUID());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("userId", userId);
        listOfInstituteProviderCode.add(instituteProviderCode);
        Map<String, String> params = new HashMap<>();
        params.put("instituteId", instituteID);
//instituteID= 6f91fa9b-6911-4fd3-beec-894d83545f35
        instituteRequestDto.setProviderCodes(listOfInstituteProviderCode);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InstituteRequestDto> entity = new HttpEntity<>(instituteRequestDto, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PRE_PATH + PATH_SEPARATOR + instituteID, HttpMethod.PUT, entity,
                String.class, params);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    ///autoSearch/{searchKey}/pageNumber/{pageNumber}/pageSize/{pageSize}

    @DisplayName("")
    @Test
    public void testGetAllInstitute(){
        ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
        ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
        instituteFundingDto.add(0,new InstituteFundingDto( UUID.randomUUID().toString()));

        List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
        ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
        instituteProviderCode.setName("ProviderName");
        instituteProviderCode.setValue(("ProviderValue"));
        listOfInstituteProviderCode.add(instituteProviderCode);

        InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
        instituteRequestDto.setName("JNU");
        instituteRequestDto.setCityName("New Delhi");
        instituteRequestDto.setCountryName("INDIA");
        instituteRequestDto.setEditAccess(true);
        instituteRequestDto.setAboutInfo("INTERNATIONAL Language School, New Delhi, is accredited by the French Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
        instituteRequestDto.setDescription("Test update method Description");
        instituteRequestDto.setInstituteFundings(instituteFundingDto);
        instituteRequestDto.setEnrolmentLink("https://www.centrallanguageschool.com/enrol");
        instituteRequestDto.setWhatsNo("https://api.whatsapp.com/send?phone=60173010314");
        instituteRequestDto.setCourseStart("March, April, May");
        instituteRequestDto.setWebsite("https://www.centrallanguageschool.com/");
        instituteRequestDto.setAddress("41 St Andrew's St, Cambridge CB2 3AR, UK");
        instituteRequestDto.setLatitude(19.202743);
        instituteRequestDto.setLongitude(65.124018);
        instituteRequestDto.setEmail("JNU@testEmail.com");
        instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
        instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
        instituteRequestDto.setReadableId("KGF");
        instituteRequestDto.setId(UUID.randomUUID());
        HttpHeaders createHeaders = new HttpHeaders();
        createHeaders.setContentType(MediaType.APPLICATION_JSON);
        listOfInstituteRequestDto.add(instituteRequestDto);
        listOfInstituteProviderCode.add(instituteProviderCode);
        instituteRequestDto.setProviderCodes(listOfInstituteProviderCode);
        HttpEntity<ValidList<InstituteRequestDto>> createEntity = new HttpEntity<>(listOfInstituteRequestDto, createHeaders);
        ResponseEntity<String> responseInstitute = testRestTemplate.exchange(INSTITUTE_PRE_PATH, HttpMethod.POST, createEntity,
                String.class);
        int pageNumber = 1;
        int pageSize = 2;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR
                + PAGE_NUMBER_PATH + PATH_SEPARATOR + pageNumber + PATH_SEPARATOR
                + PAGE_SIZE_PATH + PATH_SEPARATOR + pageSize ;
        ResponseEntity<PaginationResponseDto> response = testRestTemplate.exchange(path, HttpMethod.GET, entity,
                PaginationResponseDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @DisplayName("")
    @Test
    public void testGetAllInstituteAutoSearch(){
        ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
        ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
        instituteFundingDto.add(0,new InstituteFundingDto( UUID.randomUUID().toString()));

        List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
        ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
        instituteProviderCode.setName("ProviderName");
        instituteProviderCode.setValue(("ProviderValue"));
        listOfInstituteProviderCode.add(instituteProviderCode);

        InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
        instituteRequestDto.setName("PDU");
        instituteRequestDto.setCityName("Chandigarh");
        instituteRequestDto.setCountryName("INDIA");
        instituteRequestDto.setEditAccess(true);
        instituteRequestDto.setAboutInfo("INTERNATIONAL Medical College, Chandigarh, is accredited by the French Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
        instituteRequestDto.setDescription("Test update method Description");
        instituteRequestDto.setInstituteFundings(instituteFundingDto);
        instituteRequestDto.setEnrolmentLink("https://www.centrallanguageschool.com/enrol");
        instituteRequestDto.setWhatsNo("https://api.whatsapp.com/send?phone=60173010314");
        instituteRequestDto.setCourseStart("March, April, May");
        instituteRequestDto.setWebsite("https://www.centrallanguageschool.com/");
        instituteRequestDto.setAddress("41 St Andrew's St, Cambridge CB2 3AR, UK");
        instituteRequestDto.setLatitude(19.202743);
        instituteRequestDto.setLongitude(65.124018);
        instituteRequestDto.setEmail("PDU@testEmail.com");
        instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
        instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
        instituteRequestDto.setReadableId("MMA");
        instituteRequestDto.setId(UUID.randomUUID());
        HttpHeaders createHeaders = new HttpHeaders();
        createHeaders.setContentType(MediaType.APPLICATION_JSON);
        listOfInstituteRequestDto.add(instituteRequestDto);
        listOfInstituteProviderCode.add(instituteProviderCode);
        instituteRequestDto.setProviderCodes(listOfInstituteProviderCode);
        HttpEntity<ValidList<InstituteRequestDto>> createEntity = new HttpEntity<>(listOfInstituteRequestDto, createHeaders);
        ResponseEntity<String> responseInstitute = testRestTemplate.exchange(INSTITUTE_PRE_PATH, HttpMethod.POST, createEntity,
                String.class);
        String searchKey = "chandigarh";
        int pageNumber = 1;
        int pageSize = 2;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "autoSearch" + PATH_SEPARATOR + searchKey
                + PAGE_NUMBER_PATH + PATH_SEPARATOR + pageNumber
                + PAGE_SIZE_PATH + PATH_SEPARATOR + pageSize ;
        ResponseEntity<PaginationResponseDto> response = testRestTemplate.exchange(path, HttpMethod.GET, entity,
                PaginationResponseDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("get by id")
    @Test
    public void testGetById(){
        ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
        ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
        instituteFundingDto.add(0,new InstituteFundingDto( UUID.randomUUID().toString()));

        List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
        ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
        instituteProviderCode.setName("ProviderName");
        instituteProviderCode.setValue(("ProviderValue"));
        listOfInstituteProviderCode.add(instituteProviderCode);

        InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
        instituteRequestDto.setName("PDU");
        instituteRequestDto.setCityName("Chandigarh");
        instituteRequestDto.setCountryName("INDIA");
        instituteRequestDto.setEditAccess(true);
        instituteRequestDto.setAboutInfo("INTERNATIONAL Medical College, Chandigarh, is accredited by the French Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
        instituteRequestDto.setDescription("Test update method Description");
        instituteRequestDto.setInstituteFundings(instituteFundingDto);
        instituteRequestDto.setEnrolmentLink("https://www.centrallanguageschool.com/enrol");
        instituteRequestDto.setWhatsNo("https://api.whatsapp.com/send?phone=60173010314");
        instituteRequestDto.setCourseStart("March, April, May");
        instituteRequestDto.setWebsite("https://www.centrallanguageschool.com/");
        instituteRequestDto.setAddress("41 St Andrew's St, Cambridge CB2 3AR, UK");
        instituteRequestDto.setLatitude(19.202743);
        instituteRequestDto.setLongitude(65.124018);
        instituteRequestDto.setEmail("PDU@testEmail.com");
        instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
        instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
        instituteRequestDto.setReadableId("MMA");
        instituteRequestDto.setId(UUID.randomUUID());
        HttpHeaders createHeaders = new HttpHeaders();
        createHeaders.setContentType(MediaType.APPLICATION_JSON);
        listOfInstituteRequestDto.add(instituteRequestDto);
        listOfInstituteProviderCode.add(instituteProviderCode);
        instituteRequestDto.setProviderCodes(listOfInstituteProviderCode);
        HttpEntity<ValidList<InstituteRequestDto>> createEntity = new HttpEntity<>(listOfInstituteRequestDto, createHeaders);
        ResponseEntity<InstituteRequestDto> responseInstitute = testRestTemplate.exchange(INSTITUTE_PRE_PATH, HttpMethod.POST, createEntity,
                InstituteRequestDto.class);
        String instituteID = Objects.requireNonNull(responseInstitute.getBody()).getInstituteId();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InstituteResponseDto> entity = new HttpEntity<>(null, headers);
        ResponseEntity<InstituteRequestDto> response = testRestTemplate.exchange(INSTITUTE_PRE_PATH + PATH_SEPARATOR + instituteID, HttpMethod.GET, entity, InstituteRequestDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}