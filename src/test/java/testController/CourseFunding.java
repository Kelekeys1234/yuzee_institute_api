package testController;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;
import com.yuzee.app.YuzeeApplication;
import com.yuzee.app.dto.CourseCareerOutcomeRequestWrapper;
import com.yuzee.app.dto.CourseFundingRequestWrapper;
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.processor.CommonProcessor;
import com.yuzee.common.lib.dto.institute.CareerDto;
import com.yuzee.common.lib.dto.institute.CourseCareerOutcomeDto;
import com.yuzee.common.lib.dto.institute.CourseContactPersonDto;
import com.yuzee.common.lib.dto.institute.CourseFundingDto;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)
public class CourseFunding {
	private static final String userId = "8d7c017d-37e3-4317-a8b5-9ae6d9cdcb49";
	private static final String Id = "1e348e15-45b6-477f-a457-883738227e05";
	private static final String jobsId= "7132d88e-cf2c-4f48-ac6e-82214208f677";
	private static final String api= "/api/v1/course";
	private static final String PATH_SEPARATOR = "/";
	@Autowired
	private TestRestTemplate testRestTemplate;
	@MockBean
	CommonProcessor commonProcessor;
	@DisplayName("savecourseFunding")
	@Test
  public void saveCourseFunding() {
		List<String> fundingId= new ArrayList<>();
		fundingId.add(Id);
		Map<String,String> courseCareer= new HashMap<>();
		courseCareer.put("course_career_outcome_ids", Id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		Mockito.when( commonProcessor.getFundingsByFundingNameIds(fundingId, true)).thenReturn(new HashMap<>());
		HttpEntity<List<String> > entity = new HttpEntity<>(fundingId, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR +"funding"+PATH_SEPARATOR +"instituteId"+PATH_SEPARATOR+"ed767c27-0124-4c1f-968a-9b09244b5cb6"
				+PATH_SEPARATOR +"add-funding-to-all-courses",
				HttpMethod.POST, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
	@DisplayName("saveAllcourseFunding")
	@Test
	public void saveAllFunding() {
		ValidList<CourseFundingDto> courseFundingDtos = new ValidList<>();
		CourseFundingRequestWrapper request = new CourseFundingRequestWrapper();
		List<String> fundingId= new ArrayList<>();
		fundingId.add(Id);
		
		CourseFundingDto courseFundingDto= new CourseFundingDto(fundingId);
		courseFundingDtos.add(courseFundingDto);
		Mockito.when( commonProcessor.getFundingsByFundingNameIds(fundingId, true)).thenReturn(new HashMap<>());
		Map<String,String> courseCareer= new HashMap<>();
		courseCareer.put("course_career_outcome_ids", Id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<CourseFundingRequestWrapper> entity = new HttpEntity<>(request, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				api +PATH_SEPARATOR+"8772d763-4829-4000-a860-2c79a82905c7"
				+PATH_SEPARATOR +"funding",
				HttpMethod.POST, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
	public void deleteFunding() {
		Map<String,String> courseCareer= new HashMap<>();
		courseCareer.put("course_career_outcome_ids", Id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<CourseRequest> response = testRestTemplate.exchange(
				api + PATH_SEPARATOR +"8772d763-4829-4000-a860-2c79a82905c7" + PATH_SEPARATOR + "career-outcome",
				HttpMethod.DELETE, entity, CourseRequest.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
