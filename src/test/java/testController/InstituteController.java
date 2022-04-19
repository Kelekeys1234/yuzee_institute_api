package testController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yuzee.app.YuzeeApplication;
import com.yuzee.app.dto.*;
import com.yuzee.common.lib.dto.GenericWrapperDto;
import com.yuzee.common.lib.dto.PaginationResponseDto;
import com.yuzee.common.lib.dto.post.PostExportDto;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;
import com.yuzee.common.lib.util.ObjectMapperHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)
public class InstituteController {

    private static final String entityId = UUID.randomUUID().toString();
    private static final String instituteId = "18724ba0-4eea-11ea-8c4f-0280efceab7c";
    private static final String INSTITUTE_ID = "instituteId";
    private static final String userId = "8d7c017d-37e3-4317-a8b5-9ae6d9cdcb49";
    private static final String USER_ID = "userId";
    private static final String INSTITUTE_PRE_PATH = "/institute/api/v1";
    private static final String INSTITUTE_PATH = INSTITUTE_PRE_PATH + "/institute";
    private static final String PATH_SEPARATOR = "/";
    private static final String PAGE_NUMBER_PATH = "/pageNumber";
    private static final String PAGE_SIZE_PATH = "/pageSize";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private PublishSystemEventHandler publishSystemEventHandler;

    @DisplayName("Add Institute test success")
    @Test
    public List<String> getRandomInstituteIdByCountry(final List<String> countryIdList/* , Long startIndex, Long pageSize */) {


        HttpHeaders headers = new HttpHeaders();
        headers.add(USER_ID, userId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return null;
    }

    // institute/api/v1/institute/status/{instituteId}
    @DisplayName("change Institute status test success")
    @Test
    public void changeInstituteStatus() throws IOException {
        boolean status = true;
        String mentionUserId = UUID.randomUUID().toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add(USER_ID, userId);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PATH + PATH_SEPARATOR + "status" + PATH_SEPARATOR + instituteId, HttpMethod.PUT, entity,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("save instituteType")
    @Test
    public void saveInstituteType() throws IOException {
        InstituteTypeDto instituteTypeDto = new InstituteTypeDto();
        instituteTypeDto.setCountryName("INDIA");
        instituteTypeDto.setDescription("Test save instituteType description");
        instituteTypeDto.setType("School");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InstituteTypeDto> entity = new HttpEntity<>(instituteTypeDto, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PATH + PATH_SEPARATOR + "instituteType", HttpMethod.POST, entity,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("get InstituteType by CountryName")
    @Test
    public void getInstituteTypeByCountryName() throws IOException {
        String instituteType = "School";
        Map<String, String> params = new HashMap<>();
        params.put("instituteType", instituteType);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        StringBuilder path = new StringBuilder();
        path.append(INSTITUTE_PATH).append(PATH_SEPARATOR).append("type");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(path.toString());
        ResponseEntity<String> response = testRestTemplate.exchange(path.toString(), HttpMethod.POST, entity,
                String.class);
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
    public void dynamicInstituteSearch() throws IOException {
        List<String> countryName = Arrays.asList("USA", "ITALY", "ICELAND", "GERMANY", "BRAZIL").;
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
    public void getAllRecommendedInstitutes(){
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
    public void getInstituteByCityName(){
        String cityName = "NEW YORK";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InstituteTypeDto> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PATH + PATH_SEPARATOR + "city" + PATH_SEPARATOR + cityName, HttpMethod.GET, entity,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @DisplayName("save institutes")
    @Test
    public void save(){
        ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
        instituteFundingDto.add(0,new InstituteFundingDto("", UUID.randomUUID().toString()));
        InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
        instituteRequestDto.setEditAccess(true);
        instituteRequestDto.setAboutInfo("Test update method AboutInfo");
        instituteRequestDto.setDescription("Test update method Description");
        instituteRequestDto.setInstituteFundings(instituteFundingDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InstituteRequestDto> entity = new HttpEntity<>(instituteRequestDto, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PATH, HttpMethod.POST, entity,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @DisplayName("update")
    @Test
    public void update(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InstituteTypeDto> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(INSTITUTE_PATH, HttpMethod.POST, entity,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}