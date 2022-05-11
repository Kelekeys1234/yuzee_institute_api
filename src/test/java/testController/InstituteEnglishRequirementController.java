package testController;

import com.yuzee.app.YuzeeApplication;
import com.yuzee.app.bean.InstituteEnglishRequirements;
import com.yuzee.app.dto.*;
import com.yuzee.common.lib.dto.institute.ProviderCodeDto;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;
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

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)
public class InstituteEnglishRequirementController {

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

    @MockBean
    private PublishSystemEventHandler publishSystemEventHandler;

    @BeforeClass
    public static void main() {
        SpringApplication.run(InstituteBasicInfoController.class);
    }
    ///englishRequirements/{instituteId}
    @DisplayName("addInstituteEnglishRequirements test success")
    @Test
    public void addInstituteEnglishRequirements() {
        ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
        ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
        instituteFundingDto.add(0, new InstituteFundingDto(UUID.randomUUID().toString()));

        List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
        ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
        instituteProviderCode.setName("TestName");
        instituteProviderCode.setValue(("TestValue"));
        listOfInstituteProviderCode.add(instituteProviderCode);

        InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
        instituteRequestDto.setName("CA");
        instituteRequestDto.setCityName("BHARUCH");
        instituteRequestDto.setCountryName("INDIA");
        instituteRequestDto.setEditAccess(true);
        instituteRequestDto.setAboutInfo("INTERNATIONAL Language School, BHARUCH, is accredited by the French Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
        instituteRequestDto.setDescription("Test update method Description");
        instituteRequestDto.setInstituteFundings(instituteFundingDto);
        instituteRequestDto.setEnrolmentLink("https://www.centrallanguageschool.com/enrol");
        instituteRequestDto.setWhatsNo("https://api.whatsapp.com/send?phone=60173010314");
        instituteRequestDto.setCourseStart("March, April, May");
        instituteRequestDto.setWebsite("https://www.centrallanguageschool.com/");
        instituteRequestDto.setAddress("41 St Andrew's St, Cambridge CB2 3AR, UK");
        instituteRequestDto.setLatitude(19.202743);
        instituteRequestDto.setLongitude(65.124018);
        instituteRequestDto.setEmail("CA@testEmail.com");
        instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
        instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
        instituteRequestDto.setReadableId("CA");
        instituteRequestDto.setId(UUID.randomUUID());
        HttpHeaders createHeaders = new HttpHeaders();
        createHeaders.setContentType(MediaType.APPLICATION_JSON);
        listOfInstituteRequestDto.add(instituteRequestDto);
        listOfInstituteProviderCode.add(instituteProviderCode);
        instituteRequestDto.setProviderCodes(listOfInstituteProviderCode);
        HttpEntity<ValidList<InstituteRequestDto>> createEntity = new HttpEntity<>(listOfInstituteRequestDto, createHeaders);
        ResponseEntity<InstituteRequestDto> responseInstitute = testRestTemplate.exchange(INSTITUTE_PRE_PATH, HttpMethod.POST, createEntity,
                InstituteRequestDto.class);
        assertThat(responseInstitute.getStatusCode()).isEqualTo(HttpStatus.OK);
        String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirement" + PATH_SEPARATOR + instituteRequestDto.getId();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("userId", userId);
        InstituteEnglishRequirementsDto instituteEnglishRequirementsDto = new InstituteEnglishRequirementsDto();
        instituteEnglishRequirementsDto.setExamName("testExamName");
        instituteEnglishRequirementsDto.setListeningMarks(54.34);
        instituteEnglishRequirementsDto.setOralMarks(89.334);
        instituteEnglishRequirementsDto.setReadingMarks(67.321);
        instituteEnglishRequirementsDto.setWritingMarks(88.90);
        HttpEntity<InstituteEnglishRequirementsDto> entity = new HttpEntity<>(instituteEnglishRequirementsDto, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.POST, entity,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
   }

    @DisplayName("updateInstituteEnglishRequirements test success")
    @Test
    public void updateInstituteEnglishRequirements() {
        String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirement" + PATH_SEPARATOR + instituteEnglishRequirementId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("userId", userId);
        InstituteEnglishRequirementsDto instituteEnglishRequirementsDto = new InstituteEnglishRequirementsDto();
        instituteEnglishRequirementsDto.setExamName("testExamName");
        instituteEnglishRequirementsDto.setListeningMarks(66.88);
        instituteEnglishRequirementsDto.setOralMarks(66.88);
        instituteEnglishRequirementsDto.setReadingMarks(66.88);
        instituteEnglishRequirementsDto.setWritingMarks(88.66);
        HttpEntity<InstituteEnglishRequirementsDto> entity = new HttpEntity<>(instituteEnglishRequirementsDto, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.PUT, entity,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("getInstituteEnglishRequirementsByInstituteId test success")
    @Test
    public void getInstituteEnglishRequirementsByInstituteId() {
        String addEnglishRequirementPath = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirement" + PATH_SEPARATOR + "6f91fa9b-6911-4fd3-beec-894d83545f35";
        HttpHeaders addEnglishRequirementHeaders = new HttpHeaders();
        addEnglishRequirementHeaders.setContentType(MediaType.APPLICATION_JSON);
        addEnglishRequirementHeaders.set("userId", userId);
        InstituteEnglishRequirementsDto instituteEnglishRequirementsDto = new InstituteEnglishRequirementsDto();
        instituteEnglishRequirementsDto.setExamName("testExamName");
        instituteEnglishRequirementsDto.setListeningMarks(9.1);
        instituteEnglishRequirementsDto.setOralMarks(3.5);
        instituteEnglishRequirementsDto.setReadingMarks(7.3);
        instituteEnglishRequirementsDto.setWritingMarks(8.9);
        HttpEntity<InstituteEnglishRequirementsDto> addEntity = new HttpEntity<>(instituteEnglishRequirementsDto, addEnglishRequirementHeaders);
        ResponseEntity<InstituteEnglishRequirements> responseEnglishRequirement = testRestTemplate.exchange(addEnglishRequirementPath, HttpMethod.POST, addEntity,
                InstituteEnglishRequirements.class);
        assertThat(responseEnglishRequirement.getStatusCode()).isEqualTo(HttpStatus.OK);
        String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirement" + PATH_SEPARATOR + Objects.requireNonNull(responseEnglishRequirement.getBody()).getId();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("userId", userId);
        HttpEntity<InstituteEnglishRequirementsDto> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.GET, entity,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("getInstitutePublicEnglishRequirementsByInstituteId test success")
    @Test
    public void getInstitutePublicEnglishRequirementsByInstituteId() {
        String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "public" + PATH_SEPARATOR + "englishRequirement" + PATH_SEPARATOR + instituteEnglishRequirementId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("userId", userId);
        HttpEntity<InstituteEnglishRequirementsDto> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.GET, entity,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("deleteInstituteEnglishRequirementsByRequirementsId test success")
    @Test
    public void deleteInstituteEnglishRequirementsByRequirementsId() {
        String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "englishRequirement" + PATH_SEPARATOR + "cf13eef7-b188-4e84-baee-7f98a7c33e7b";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("userId", userId);
        HttpEntity<InstituteEnglishRequirementsDto> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.DELETE, entity,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}