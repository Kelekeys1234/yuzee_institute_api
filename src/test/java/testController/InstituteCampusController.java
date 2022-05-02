package testController;

import com.yuzee.app.YuzeeApplication;
import com.yuzee.app.dto.InstituteFundingDto;
import com.yuzee.app.dto.InstituteRequestDto;
import com.yuzee.app.dto.ValidList;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)
public class InstituteCampusController {

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

    ///campus/instituteId/{instituteId}
    @DisplayName("addCampus test success")
    @Test
    public void addCampus() throws IOException {
        //TODO create institute first, get instituteId then change status
        boolean status = true;
        ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
        ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
        instituteFundingDto.add(0, new InstituteFundingDto(UUID.randomUUID().toString()));

        List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
        ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
        instituteProviderCode.setName("TestName");
        instituteProviderCode.setValue(("TestValue"));
        listOfInstituteProviderCode.add(instituteProviderCode);

        InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
        instituteRequestDto.setName("LNK");
        instituteRequestDto.setCityName("JUNAGARH");
        instituteRequestDto.setCountryName("INDIA");
        instituteRequestDto.setEditAccess(true);
        instituteRequestDto.setAboutInfo("INTERNATIONAL Language School, is accredited by the GUJARATI Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
        instituteRequestDto.setDescription("Test update method Description");
        instituteRequestDto.setInstituteFundings(instituteFundingDto);
        instituteRequestDto.setEnrolmentLink("https://www.centrallanguageschool.com/enrol");
        instituteRequestDto.setWhatsNo("https://api.whatsapp.com/send?phone=60173010314");
        instituteRequestDto.setCourseStart("March, April, May");
        instituteRequestDto.setWebsite("https://www.centrallanguageschool.com/");
        instituteRequestDto.setAddress("41 St Andrew's St, Cambridge CB2 3AR, UK");
        instituteRequestDto.setLatitude(19.202743);
        instituteRequestDto.setLongitude(65.124018);
        instituteRequestDto.setEmail("LNK@testEmail.com");
        instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
        instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
        instituteRequestDto.setReadableId("LNK");
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
        HttpHeaders headers = new HttpHeaders();
        headers.set("userId" , userId);
        createHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<String> instituteIds = Arrays.asList(userId, instituteId, instituteRequestDto.getInstituteId());
        HttpEntity<List<String>> entity = new HttpEntity<>(instituteIds, headers);
        String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "campus" + PATH_SEPARATOR + "instituteId" + PATH_SEPARATOR + instituteRequestDto.getId();
        ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.POST, entity,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @DisplayName("getInstituteCampuses test success")
    @Test
    public void getInstituteCampuses() throws IOException {
        //TODO create institute first, get instituteId then change status
        boolean status = true;
        ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
        ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
        instituteFundingDto.add(0, new InstituteFundingDto(UUID.randomUUID().toString()));

        List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
        ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
        instituteProviderCode.setName("TestName");
        instituteProviderCode.setValue(("TestValue"));
        listOfInstituteProviderCode.add(instituteProviderCode);

        InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
        instituteRequestDto.setName("NTPC");
        instituteRequestDto.setCityName("JAMNAGAR");
        instituteRequestDto.setCountryName("INDIA");
        instituteRequestDto.setEditAccess(true);
        instituteRequestDto.setAboutInfo("INTERNATIONAL Language School, is accredited by the GUJARATI Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
        instituteRequestDto.setDescription("Test update method Description");
        instituteRequestDto.setInstituteFundings(instituteFundingDto);
        instituteRequestDto.setEnrolmentLink("https://www.centrallanguageschool.com/enrol");
        instituteRequestDto.setWhatsNo("https://api.whatsapp.com/send?phone=60173010314");
        instituteRequestDto.setCourseStart("March, April, May");
        instituteRequestDto.setWebsite("https://www.centrallanguageschool.com/");
        instituteRequestDto.setAddress("41 St Andrew's St, Cambridge CB2 3AR, UK");
        instituteRequestDto.setLatitude(19.202743);
        instituteRequestDto.setLongitude(65.124018);
        instituteRequestDto.setEmail("NTPC@testEmail.com");
        instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
        instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
        instituteRequestDto.setReadableId("NTPC");
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
        HttpHeaders headers = new HttpHeaders();
        headers.set("userId" , userId);
        createHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<String> instituteIds = Arrays.asList(userId, instituteId, instituteRequestDto.getInstituteId());
        HttpEntity<List<String>> entity = new HttpEntity<>(instituteIds, headers);
        String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "campus" + PATH_SEPARATOR + "instituteId" + PATH_SEPARATOR + instituteRequestDto.getId();
        ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.GET, entity,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @DisplayName("getInstituteCampuses test success")
    @Test
    public void removeCampus() throws IOException {
        ValidList<InstituteRequestDto> listOfInstituteRequestDto = new ValidList<>();
        ValidList<InstituteFundingDto> instituteFundingDto = new ValidList<>();
        instituteFundingDto.add(0, new InstituteFundingDto(UUID.randomUUID().toString()));

        List<ProviderCodeDto> listOfInstituteProviderCode = new ArrayList<>();
        ProviderCodeDto instituteProviderCode = new ProviderCodeDto();
        instituteProviderCode.setName("TestName");
        instituteProviderCode.setValue(("TestValue"));
        listOfInstituteProviderCode.add(instituteProviderCode);

        InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
        instituteRequestDto.setName("DRISHTI");
        instituteRequestDto.setCityName("PRAYAGRAJ");
        instituteRequestDto.setCountryName("INDIA");
        instituteRequestDto.setEditAccess(true);
        instituteRequestDto.setAboutInfo("INTERNATIONAL Language School, is accredited by the GUJARATI Council and is a small, friendly, city-centre English language school.Our aim is to give you a warm welcome and an excellent opportunity to learn English in a caring, friendly atmosphere. Our courses, from Beginner to Advanced level, run throughout the year. We also offer exam preparation. We only teach adults (from a minimum age of 18).The School is just 3 minutes' walk from the central bus station and near many restaurants, shops and the colleges of the University of Cambridge. Students from more than 90 different countries have studied with us and there is usually a good mix of nationalities in the school.The School was founded in 1996 by a group of Christians in Cambridge. ");
        instituteRequestDto.setDescription("Test update method Description");
        instituteRequestDto.setInstituteFundings(instituteFundingDto);
        instituteRequestDto.setEnrolmentLink("https://www.centrallanguageschool.com/enrol");
        instituteRequestDto.setWhatsNo("https://api.whatsapp.com/send?phone=60173010314");
        instituteRequestDto.setCourseStart("March, April, May");
        instituteRequestDto.setWebsite("https://www.centrallanguageschool.com/");
        instituteRequestDto.setAddress("41 St Andrew's St, Cambridge CB2 3AR, UK");
        instituteRequestDto.setLatitude(19.202743);
        instituteRequestDto.setLongitude(65.124018);
        instituteRequestDto.setEmail("DRISHTI@testEmail.com");
        instituteRequestDto.setIntakes(Arrays.asList("Dec", "Jan", "Feb"));
        instituteRequestDto.setInstituteType("SMALL_MEDIUM_PRIVATE_SCHOOL");
        instituteRequestDto.setReadableId("DRISHTI");
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
        HttpHeaders headers = new HttpHeaders();
        headers.set("userId" , userId);
        createHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<String> instituteIds = Arrays.asList(userId, instituteId, instituteRequestDto.getInstituteId());
        HttpEntity<List<String>> entity = new HttpEntity<>(instituteIds, headers);
        String path = INSTITUTE_PRE_PATH + PATH_SEPARATOR + "campus" + PATH_SEPARATOR + "instituteId" + PATH_SEPARATOR + instituteRequestDto.getId();
        ResponseEntity<String> response = testRestTemplate.exchange(path, HttpMethod.DELETE, entity,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}