package testController;

import static org.assertj.core.api.Assertions.assertThat;



import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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
import com.yuzee.app.dto.CourseDeliveryModeRequestWrapper;
import com.yuzee.common.lib.dto.ValidList;
import com.yuzee.common.lib.dto.institute.CourseDeliveryModeFundingDto;
import com.yuzee.common.lib.dto.institute.CourseDeliveryModesDto;
import com.yuzee.common.lib.dto.institute.CourseFeesDto;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RunWith(JUnitPlatform.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = YuzeeApplication.class)

 class TestCourseDeliveryModeControllerTest {
	private static final String userId = "8d7c017d-37e3-4317-a8b5-9ae6d9cdcb49";
	private static final String PATH_SEPARATOR = "/";
	private static final String COURSE_PATH = "/api/v1";

	@Autowired
	private TestRestTemplate testRestTemplate;

	@DisplayName("Add CourseDeliveryMode")
	@Test
	 void addCourseDeliveryMode() {
		CourseDeliveryModeRequestWrapper requestWrapper = new CourseDeliveryModeRequestWrapper();
		CourseDeliveryModesDto courseDeliveryModesDto = new CourseDeliveryModesDto();
		courseDeliveryModesDto.setDeliveryType("demo");
		courseDeliveryModesDto.setStudyMode("online");
		courseDeliveryModesDto.setDuration(7.5);
		courseDeliveryModesDto.setDurationTime("hour");
		courseDeliveryModesDto.setAccessibility("yes");
		courseDeliveryModesDto.setIsGovernmentEligible(true);

		ValidList<CourseFeesDto> fees = new ValidList<>();
		CourseFeesDto dto = new CourseFeesDto();
		dto.setName("root");
		dto.setAmount(4.00);
		dto.setCurrency("INR");
		fees.add(dto);
		courseDeliveryModesDto.setFees(fees);

		CourseDeliveryModeFundingDto courseDeliveryModeFundingDto = new CourseDeliveryModeFundingDto();
		ValidList<CourseDeliveryModeFundingDto> fundings = new ValidList<>();
		courseDeliveryModeFundingDto.setName("root");
		courseDeliveryModeFundingDto.setFundingNameId("rootId");
		courseDeliveryModeFundingDto.setAmount(4.00);
		courseDeliveryModeFundingDto.setCurrency("INR");
		fundings.add(courseDeliveryModeFundingDto);
		courseDeliveryModesDto.setFundings(fundings);

		ValidList<CourseDeliveryModesDto> courseDeliveryModesDtoList = new ValidList<>();
		courseDeliveryModesDtoList.add(courseDeliveryModesDto);

		List<String> linked_course_ids = new ArrayList<>();
		linked_course_ids.add("527cf280-9206-461e-ad2a-49b00d9d5be2");
		requestWrapper.setLinkedCourseIds(linked_course_ids);
		requestWrapper.setCourseDelieveryModeDtos(courseDeliveryModesDtoList);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);

		HttpEntity<CourseDeliveryModeRequestWrapper> entity = new HttpEntity<>(requestWrapper, headers);
		ResponseEntity<CourseDeliveryModeRequestWrapper> response = testRestTemplate
				.exchange(
						COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "527cf280-9206-461e-ad2a-49b00d9d5be2"
								+ PATH_SEPARATOR + "delivery-mode",
						HttpMethod.POST, entity, CourseDeliveryModeRequestWrapper.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName("Update CourseDeliveryMode")
	@Test
	  void updateCourseDeliveryMode() {
		CourseDeliveryModeRequestWrapper requestWrapper = new CourseDeliveryModeRequestWrapper();
		CourseDeliveryModesDto courseDeliveryModesDto = new CourseDeliveryModesDto();
		courseDeliveryModesDto.setDeliveryType("demo");
		courseDeliveryModesDto.setStudyMode("offline");
		courseDeliveryModesDto.setDuration(8.5);
		courseDeliveryModesDto.setDurationTime("hour");
		courseDeliveryModesDto.setAccessibility("yes");
		courseDeliveryModesDto.setIsGovernmentEligible(true);

		ValidList<CourseFeesDto> fees = new ValidList<>();
		CourseFeesDto dto = new CourseFeesDto();
		dto.setName("root");
		dto.setAmount(5.00);
		dto.setCurrency("INR");
		fees.add(dto);
		courseDeliveryModesDto.setFees(fees);

		CourseDeliveryModeFundingDto courseDeliveryModeFundingDto = new CourseDeliveryModeFundingDto();
		ValidList<CourseDeliveryModeFundingDto> fundings = new ValidList<>();
		courseDeliveryModeFundingDto.setName("root");
		courseDeliveryModeFundingDto.setFundingNameId("rootId");
		courseDeliveryModeFundingDto.setAmount(5.00);
		courseDeliveryModeFundingDto.setCurrency("INR");
		fundings.add(courseDeliveryModeFundingDto);
		courseDeliveryModesDto.setFundings(fundings);

		ValidList<CourseDeliveryModesDto> courseDeliveryModesDtoList = new ValidList<>();
		courseDeliveryModesDtoList.add(courseDeliveryModesDto);
		requestWrapper.setCourseDelieveryModeDtos(courseDeliveryModesDtoList);

		List<String> linked_course_ids = new ArrayList<>();
		linked_course_ids.add("fda8c495-f2df-4f0e-a154-169f982eb292");
		requestWrapper.setLinkedCourseIds(linked_course_ids);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);

		HttpEntity<CourseDeliveryModeRequestWrapper> entity = new HttpEntity<>(requestWrapper, headers);
		ResponseEntity<CourseDeliveryModeRequestWrapper> response = testRestTemplate
				.exchange(
						COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "fda8c495-f2df-4f0e-a154-169f982eb292"
								+ PATH_SEPARATOR + "delivery-mode",
						HttpMethod.POST, entity, CourseDeliveryModeRequestWrapper.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName("send  multipleCourseDeliveryMode")
	@Test
	 void sendMultipleCourseDeliveryMode() {
		CourseDeliveryModeRequestWrapper requestWrapper = new CourseDeliveryModeRequestWrapper();
		CourseDeliveryModesDto courseDeliveryModesFirstList = new CourseDeliveryModesDto();
		courseDeliveryModesFirstList.setDeliveryType("demo");
		courseDeliveryModesFirstList.setStudyMode("offline");
		courseDeliveryModesFirstList.setDuration(8.5);
		courseDeliveryModesFirstList.setDurationTime("hour");
		courseDeliveryModesFirstList.setAccessibility("yes");
		courseDeliveryModesFirstList.setIsGovernmentEligible(true);

		ValidList<CourseFeesDto> firstFeeesList = new ValidList<>();
		CourseFeesDto dto = new CourseFeesDto();
		dto.setName("root");
		dto.setAmount(5.00);
		dto.setCurrency("INR");
		firstFeeesList.add(dto);
		courseDeliveryModesFirstList.setFees(firstFeeesList);

		CourseDeliveryModeFundingDto courseDeliveryModeFundingDto = new CourseDeliveryModeFundingDto();
		ValidList<CourseDeliveryModeFundingDto> firstFundingsList = new ValidList<>();
		courseDeliveryModeFundingDto.setName("root");
		courseDeliveryModeFundingDto.setFundingNameId("rootId");
		courseDeliveryModeFundingDto.setAmount(5.00);
		courseDeliveryModeFundingDto.setCurrency("INR");
		firstFundingsList.add(courseDeliveryModeFundingDto);
		courseDeliveryModesFirstList.setFundings(firstFundingsList);

		CourseDeliveryModesDto courseDeliveryModesSecondList = new CourseDeliveryModesDto();
		courseDeliveryModesSecondList.setDeliveryType("easy");
		courseDeliveryModesSecondList.setStudyMode("online");
		courseDeliveryModesSecondList.setDuration(7.5);
		courseDeliveryModesSecondList.setDurationTime("hour");
		courseDeliveryModesSecondList.setAccessibility("yes");
		courseDeliveryModesSecondList.setIsGovernmentEligible(true);

		ValidList<CourseFeesDto> secondFeesList = new ValidList<>();
		CourseFeesDto dtoo = new CourseFeesDto();
		dtoo.setName("root");
		dtoo.setAmount(4.00);
		dtoo.setCurrency("INR");
		secondFeesList.add(dtoo);
		courseDeliveryModesSecondList.setFees(secondFeesList);

		CourseDeliveryModeFundingDto secondcourseDeliveryModeFunding = new CourseDeliveryModeFundingDto();
		ValidList<CourseDeliveryModeFundingDto> secondfundings = new ValidList<>();
		secondcourseDeliveryModeFunding.setName("root");
		secondcourseDeliveryModeFunding.setFundingNameId("rootId");
		secondcourseDeliveryModeFunding.setAmount(4.00);
		secondcourseDeliveryModeFunding.setCurrency("INR");
		secondfundings.add(secondcourseDeliveryModeFunding);
		courseDeliveryModesSecondList.setFundings(secondfundings);

		ValidList<CourseDeliveryModesDto> courseDeliveryModesDtoList = new ValidList<>();
		courseDeliveryModesDtoList.add(courseDeliveryModesFirstList);
		courseDeliveryModesDtoList.add(courseDeliveryModesSecondList);
		requestWrapper.setCourseDelieveryModeDtos(courseDeliveryModesDtoList);

		List<String> linked_course_ids = new ArrayList<>();
		linked_course_ids.add("75a34cb9-1034-404c-9d7c-db704cf5b659");
		requestWrapper.setLinkedCourseIds(linked_course_ids);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);

		HttpEntity<CourseDeliveryModeRequestWrapper> entity = new HttpEntity<>(requestWrapper, headers);
		ResponseEntity<CourseDeliveryModeRequestWrapper> response = testRestTemplate
				.exchange(
						COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "75a34cb9-1034-404c-9d7c-db704cf5b659"
								+ PATH_SEPARATOR + "delivery-mode",
						HttpMethod.POST, entity, CourseDeliveryModeRequestWrapper.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName("Remove  SingalCourseDeliveryMode")
	@Test
	 void removeSingalCourseDeliveryMode() {
		CourseDeliveryModeRequestWrapper requestWrapper = new CourseDeliveryModeRequestWrapper();
		CourseDeliveryModesDto courseDeliveryModesDto = new CourseDeliveryModesDto();

		courseDeliveryModesDto.setDeliveryType("demo");
		courseDeliveryModesDto.setStudyMode("offline");
		courseDeliveryModesDto.setDuration(8.5);
		courseDeliveryModesDto.setDurationTime("hour");
		courseDeliveryModesDto.setAccessibility("yes");
		courseDeliveryModesDto.setIsGovernmentEligible(true);

		ValidList<CourseFeesDto> fess = new ValidList<>();
		CourseFeesDto dto = new CourseFeesDto();
		dto.setName("root");
		dto.setAmount(5.00);
		dto.setCurrency("INR");
		fess.add(dto);
		courseDeliveryModesDto.setFees(fess);

		CourseDeliveryModeFundingDto courseDeliveryModeFundingDto = new CourseDeliveryModeFundingDto();
		ValidList<CourseDeliveryModeFundingDto> fundings = new ValidList<>();
		courseDeliveryModeFundingDto.setName("root");
		courseDeliveryModeFundingDto.setFundingNameId("rootId");
		courseDeliveryModeFundingDto.setAmount(5.00);
		courseDeliveryModeFundingDto.setCurrency("INR");
		fundings.add(courseDeliveryModeFundingDto);
		courseDeliveryModesDto.setFundings(fundings);

		ValidList<CourseDeliveryModesDto> courseDeliveryModesDtoList = new ValidList<>();
		courseDeliveryModesDtoList.add(courseDeliveryModesDto);
		requestWrapper.setCourseDelieveryModeDtos(courseDeliveryModesDtoList);

		List<String> linked_course_ids = new ArrayList<>();
		linked_course_ids.add("ef5f52ab-d303-477f-9db4-e0633fba23bc");
		requestWrapper.setLinkedCourseIds(linked_course_ids);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);

		HttpEntity<CourseDeliveryModeRequestWrapper> entity = new HttpEntity<>(requestWrapper, headers);
		ResponseEntity<CourseDeliveryModeRequestWrapper> response = testRestTemplate
				.exchange(
						COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "ef5f52ab-d303-477f-9db4-e0633fba23bc"
								+ PATH_SEPARATOR + "delivery-mode",
						HttpMethod.POST, entity, CourseDeliveryModeRequestWrapper.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName("DELETE All CourseDeliveryMode")
	@Test
	 void deleteAllCourseDeliveryMode() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);

		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate
				.exchange(
						COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR
								+ "96a2e11b-d64b-4964-9d28-2a4d7a41d944" + PATH_SEPARATOR + "delivery-mode",
						HttpMethod.DELETE, entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName("Send Wrong_Id")
	@Test
	  void wrongIdCourseDeliveryMode() {
		CourseDeliveryModeRequestWrapper requestWrapper = new CourseDeliveryModeRequestWrapper();
		CourseDeliveryModesDto courseDeliveryModesDto = new CourseDeliveryModesDto();
		courseDeliveryModesDto.setDeliveryType("demo");
		courseDeliveryModesDto.setStudyMode("offline");
		courseDeliveryModesDto.setDuration(8.5);
		courseDeliveryModesDto.setDurationTime("hour");
		courseDeliveryModesDto.setAccessibility("yes");
		courseDeliveryModesDto.setIsGovernmentEligible(true);

		ValidList<CourseFeesDto> fees = new ValidList<>();
		CourseFeesDto dto = new CourseFeesDto();
		dto.setName("root");
		dto.setAmount(4.00);
		dto.setCurrency("INR");
		fees.add(dto);
		courseDeliveryModesDto.setFees(fees);

		CourseDeliveryModeFundingDto courseDeliveryModeFundingDto = new CourseDeliveryModeFundingDto();
		ValidList<CourseDeliveryModeFundingDto> fundings = new ValidList<>();
		courseDeliveryModeFundingDto.setName("root");
		courseDeliveryModeFundingDto.setFundingNameId("rootId");
		courseDeliveryModeFundingDto.setAmount(4.00);
		courseDeliveryModeFundingDto.setCurrency("INR");
		fundings.add(courseDeliveryModeFundingDto);
		courseDeliveryModesDto.setFundings(fundings);

		ValidList<CourseDeliveryModesDto> courseDeliveryModesDtoList = new ValidList<>();
		courseDeliveryModesDtoList.add(courseDeliveryModesDto);

		requestWrapper.setCourseDelieveryModeDtos(courseDeliveryModesDtoList);

		List<String> linked_course_ids = new ArrayList<>();
		linked_course_ids.add("632dfa59c7b4046a632adbe5");
		requestWrapper.setLinkedCourseIds(linked_course_ids);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);

		HttpEntity<CourseDeliveryModeRequestWrapper> entity = new HttpEntity<>(requestWrapper, headers);
		ResponseEntity<CourseDeliveryModeRequestWrapper> response = testRestTemplate
				.exchange(
						COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "hhfadhsfhaundfhfffjh"
								+ PATH_SEPARATOR + "delivery-mode",
						HttpMethod.POST, entity, CourseDeliveryModeRequestWrapper.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

	}

	@DisplayName("Send EmptyCourseDelivery")
	@Test
	 void sendEmptyCourseDeliveryMode() {
		CourseDeliveryModeRequestWrapper requestWrapper = new CourseDeliveryModeRequestWrapper();
		CourseDeliveryModesDto courseDeliveryModesDto = new CourseDeliveryModesDto();
		courseDeliveryModesDto.setDeliveryType("");
		courseDeliveryModesDto.setStudyMode("");
		courseDeliveryModesDto.setDuration(null);
		courseDeliveryModesDto.setDurationTime("");
		courseDeliveryModesDto.setAccessibility("");
		courseDeliveryModesDto.setIsGovernmentEligible(true);

		ValidList<CourseFeesDto> fees = new ValidList<>();
		CourseFeesDto dto = new CourseFeesDto();
		dto.setName("");
		dto.setAmount(null);
		dto.setCurrency("");
		fees.add(dto);
		courseDeliveryModesDto.setFees(fees);

		CourseDeliveryModeFundingDto courseDeliveryModeFundingDto = new CourseDeliveryModeFundingDto();
		ValidList<CourseDeliveryModeFundingDto> fundings = new ValidList<>();
		courseDeliveryModeFundingDto.setName("");
		courseDeliveryModeFundingDto.setFundingNameId("");
		courseDeliveryModeFundingDto.setAmount(null);
		courseDeliveryModeFundingDto.setCurrency("");
		fundings.add(courseDeliveryModeFundingDto);
		courseDeliveryModesDto.setFundings(fundings);

		ValidList<CourseDeliveryModesDto> courseDeliveryModesDtoList = new ValidList<>();
		courseDeliveryModesDtoList.add(courseDeliveryModesDto);

		requestWrapper.setCourseDelieveryModeDtos(courseDeliveryModesDtoList);

		List<String> linked_course_ids = new ArrayList<>();
		linked_course_ids.add("96a2e11b-d64b-4964-9d28-2a4d7a41d944");
		requestWrapper.setLinkedCourseIds(linked_course_ids);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);

		HttpEntity<CourseDeliveryModeRequestWrapper> entity = new HttpEntity<>(requestWrapper, headers);
		ResponseEntity<CourseDeliveryModeRequestWrapper> response = testRestTemplate
				.exchange(
						COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "96a2e11b-d64b-4964-9d28-2a4d7a41d944"
								+ PATH_SEPARATOR + "delivery-mode",
						HttpMethod.POST, entity, CourseDeliveryModeRequestWrapper.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

	}

	@DisplayName("Send Wrong CourseID")
	@Test
	 void sendWrongCourseIDForDeleteALL() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", userId);

		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(COURSE_PATH + PATH_SEPARATOR + "course"
				+ PATH_SEPARATOR + "jhdfhnsdffsdnfndfnhfn" + PATH_SEPARATOR + "delivery-mode", HttpMethod.DELETE,
				entity, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

	}

}
