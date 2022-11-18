//package testController;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.UUID;
//
//import org.assertj.core.util.Arrays;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.platform.runner.JUnitPlatform;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.yuzee.app.YuzeeApplication;
//import com.yuzee.app.dto.CourseOtherRequirementDto;
//import com.yuzee.app.dto.CourseVaccineRequirementDto;
//import com.yuzee.app.dto.CourseWorkExperienceRequirementDto;
//import com.yuzee.app.dto.CourseWorkPlacementRequirementDto;
//import com.yuzee.common.lib.dto.common.VaccinationDto;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RunWith(JUnitPlatform.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//@ContextConfiguration(classes = YuzeeApplication.class)
//
// class TestCourseOtherRequirementController {
//	private static final String userId = "8d7c017d-37e3-4317-a8b5-9ae6d9cdcb49";
//	private static final String COURSE_PATH = "/api/v1";
//	private static final String PATH_SEPARATOR = "/";
//	 UUID uuid = UUID.randomUUID();
//	@Autowired
//	private TestRestTemplate testRestTemplate;
//
//	@DisplayName("Add work_experience_work_placement")
//	@Test
//	 void addWorkExperienceWorkPlacement() {
//		CourseOtherRequirementDto courseOtherRequirementDto = new CourseOtherRequirementDto();
//		CourseVaccineRequirementDto vaccine =new CourseVaccineRequirementDto();
//		vaccine.setDescription("hello this is vaccine");
//		Set<VaccinationDto> vacId = new HashSet<VaccinationDto>();
//		VaccinationDto dto=new VaccinationDto();
//		dto.set_id(uuid);
//		vacId.add(dto);
//		vaccine.setVaccination(vacId);
//		courseOtherRequirementDto.setVaccine(vaccine);
//		
//		CourseWorkExperienceRequirementDto workExperience = new CourseWorkExperienceRequirementDto();
//		workExperience.setDescription("Hello this is my am work_experience ");
//		workExperience.setDuration(4.5);
//		workExperience.setDurationType("month");
//		List<String> workExperienceFields = new ArrayList<>();
//		workExperienceFields.add("IT");
//		workExperience.setFields(workExperienceFields);
//		courseOtherRequirementDto.setWorkExperience(workExperience);
//
//		CourseWorkPlacementRequirementDto workPlacement = new CourseWorkPlacementRequirementDto();
//		workPlacement.setDescription("Hello this is my am workPlacement ");
//		workPlacement.setDuration(4.5);
//		workPlacement.setDurationType("month");
//		List<String> workPlacementFields = new ArrayList<>();
//		workPlacementFields.add("IT");
//		workPlacement.setFields(workPlacementFields);
//		courseOtherRequirementDto.setWorkPlacement(workPlacement);
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.set("userId", userId);
//
//		HttpEntity<CourseOtherRequirementDto> entity = new HttpEntity<>(courseOtherRequirementDto, headers);
//		ResponseEntity<CourseOtherRequirementDto> response = testRestTemplate.exchange(
//				COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "23d459e9-7942-4cd1-9a29-bfd7cb22cd39"
//						+ PATH_SEPARATOR + "other-requirement",
//				HttpMethod.POST, entity, CourseOtherRequirementDto.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//	}
//
//	@DisplayName("Update work_experience_work_placement")
//	@Test
//     void updateWorkExperienceWorkPlacement() {
//		CourseOtherRequirementDto courseOtherRequirementDto = new CourseOtherRequirementDto();
//		CourseWorkExperienceRequirementDto workExperience = new CourseWorkExperienceRequirementDto();
//		workExperience.setDescription("Hello this is my  workExperience ");
//		workExperience.setDuration(5.5);
//		workExperience.setDurationType("month");
//		List<String> workExperienceFields = new ArrayList<>();
//		workExperienceFields.add("IT");
//		workExperience.setFields(workExperienceFields);
//		courseOtherRequirementDto.setWorkExperience(workExperience);
//
//		CourseWorkPlacementRequirementDto workPlacement = new CourseWorkPlacementRequirementDto();
//		workPlacement.setDescription("Hello this is my workPlacement ");
//		workPlacement.setDuration(5.5);
//		workPlacement.setDurationType("month");
//		List<String> workPlacementFields = new ArrayList<>();
//		workPlacementFields.add("IT");
//		workPlacement.setFields(workPlacementFields);
//		courseOtherRequirementDto.setWorkPlacement(workPlacement);
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.set("userId", userId);
//
//		HttpEntity<CourseOtherRequirementDto> entity = new HttpEntity<>(courseOtherRequirementDto, headers);
//		ResponseEntity<CourseOtherRequirementDto> response = testRestTemplate.exchange(
//				COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "d9a7072b-5c51-4373-82e0-c632b1f64e51"
//						+ PATH_SEPARATOR + "other-requirement",
//				HttpMethod.POST, entity, CourseOtherRequirementDto.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//	}
//
//	@DisplayName("Add multiple_fields_work_experience_work_placement")
//	@Test
//	  void addMultipleFieldsWorkExperienceWorkPlacement() {
//		CourseOtherRequirementDto courseOtherRequirementDto = new CourseOtherRequirementDto();
//		CourseWorkExperienceRequirementDto workExperience = new CourseWorkExperienceRequirementDto();
//		workExperience.setDescription("Hello this is my  work_experience ");
//		workExperience.setDuration(5.5);
//		workExperience.setDurationType("month");
//		List<String> workExperienceFields = new ArrayList<>();
//		workExperienceFields.add("IT");
//		workExperienceFields.add("NIT");
//		workExperience.setFields(workExperienceFields);
//		courseOtherRequirementDto.setWorkExperience(workExperience);
//
//		CourseWorkPlacementRequirementDto workPlacement = new CourseWorkPlacementRequirementDto();
//		workPlacement.setDescription("Hello this is my workPlacement");
//		workPlacement.setDuration(5.5);
//		workPlacement.setDurationType("month");
//		List<String> workPlacementFields = new ArrayList<>();
//		workPlacementFields.add("IT");
//		workPlacementFields.add("NIT");
//		workPlacement.setFields(workPlacementFields);
//		courseOtherRequirementDto.setWorkPlacement(workPlacement);
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.set("userId", userId);
//
//		HttpEntity<CourseOtherRequirementDto> entity = new HttpEntity<>(courseOtherRequirementDto, headers);
//		ResponseEntity<CourseOtherRequirementDto> response = testRestTemplate.exchange(
//				COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "2db27264-a0f4-42d3-b4f2-98776b11a03b"
//						+ PATH_SEPARATOR + "other-requirement",
//				HttpMethod.POST, entity, CourseOtherRequirementDto.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//	}
//
//	@DisplayName("Remove singal_fields_work_experience_work_placement")
//	@Test
//     void removeSingalFieldsWorkExperienceWorkPlacement() {
//		CourseOtherRequirementDto courseOtherRequirementDto = new CourseOtherRequirementDto();
//		CourseWorkExperienceRequirementDto workExperience = new CourseWorkExperienceRequirementDto();
//		workExperience.setDescription("Hello this is my  workExperience ");
//		workExperience.setDuration(5.5);
//		workExperience.setDurationType("month");
//		List<String> workExperienceFields = new ArrayList<>();
//		workExperienceFields.add("NIT");
//		workExperience.setFields(workExperienceFields);
//		courseOtherRequirementDto.setWorkExperience(workExperience);
//
//		CourseWorkPlacementRequirementDto workPlacement = new CourseWorkPlacementRequirementDto();
//		workPlacement.setDescription("Hello this is my workPlacement");
//		workPlacement.setDuration(5.5);
//		workPlacement.setDurationType("month");
//		List<String> workPlacementFields = new ArrayList<>();
//		workPlacementFields.add("NIT");
//		workPlacement.setFields(workPlacementFields);
//		courseOtherRequirementDto.setWorkPlacement(workPlacement);
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.set("userId", userId);
//
//		HttpEntity<CourseOtherRequirementDto> entity = new HttpEntity<>(courseOtherRequirementDto, headers);
//		ResponseEntity<CourseOtherRequirementDto> response = testRestTemplate.exchange(
//				COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "96a2e11b-d64b-4964-9d28-2a4d7a41d944"
//						+ PATH_SEPARATOR + "other-requirement",
//				HttpMethod.POST, entity, CourseOtherRequirementDto.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//	}
//
//	@DisplayName("Get all_work_experience_work_placement")
//	@Test
//     void getAllWorkExperienceWorkPlacement() {
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.set("userId", userId);
//
//		HttpEntity<CourseOtherRequirementDto> entity = new HttpEntity<>(headers);
//		ResponseEntity<CourseOtherRequirementDto> response = testRestTemplate.exchange(
//				COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "2db27264-a0f4-42d3-b4f2-98776b11a03b"
//						+ PATH_SEPARATOR + "other-requirement",
//				HttpMethod.GET, entity, CourseOtherRequirementDto.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//	}
//
//	@DisplayName("Send wrong_courseId")
//	@Test
//	  void sendWrongCourseId() {
//		CourseOtherRequirementDto courseOtherRequirementDto = new CourseOtherRequirementDto();
//		CourseWorkExperienceRequirementDto workExperience = new CourseWorkExperienceRequirementDto();
//		workExperience.setDescription("Hello this is my  workExperience ");
//		workExperience.setDuration(5.5);
//		workExperience.setDurationType("month");
//		List<String> workExperienceFields = new ArrayList<>();
//		workExperienceFields.add("NIT");
//		workExperience.setFields(workExperienceFields);
//		courseOtherRequirementDto.setWorkExperience(workExperience);
//
//		CourseWorkPlacementRequirementDto workPlacement = new CourseWorkPlacementRequirementDto();
//		workPlacement.setDescription("Hello this is my workPlacement");
//		workPlacement.setDuration(5.5);
//		workPlacement.setDurationType("month");
//		List<String> workPlacementFields = new ArrayList<>();
//		workPlacementFields.add("NIT");
//		workPlacement.setFields(workPlacementFields);
//		courseOtherRequirementDto.setWorkPlacement(workPlacement);
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.set("userId", userId);
//
//		HttpEntity<CourseOtherRequirementDto> entity = new HttpEntity<>(courseOtherRequirementDto, headers);
//		ResponseEntity<CourseOtherRequirementDto> response = testRestTemplate.exchange(
//				COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "63317979a56987febcebdfjdjfds5"
//						+ PATH_SEPARATOR + "other-requirement",
//				HttpMethod.POST, entity, CourseOtherRequirementDto.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//
//	}
//
//	@DisplayName("WrongIdForGetAll")
//	@Test
//     void SendWrongIDgetAll() {
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.set("userId", userId);
//
//		HttpEntity<CourseOtherRequirementDto> entity = new HttpEntity<>(headers);
//		ResponseEntity<CourseOtherRequirementDto> response = testRestTemplate.exchange(
//				COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "63317979a56987febcebdfjdjfds5"
//						+ PATH_SEPARATOR + "other-requirement",
//				HttpMethod.GET, entity, CourseOtherRequirementDto.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//
//	}
//	
//	@DisplayName("Send NullWorkExp_WorkPlac")
//	@Test
//	 void SendNullWorkExperienceWorkPlacement() {
//		CourseOtherRequirementDto courseOtherRequirementDto = new CourseOtherRequirementDto();
//		CourseVaccineRequirementDto vaccine =new CourseVaccineRequirementDto();
//		vaccine=null;
//		courseOtherRequirementDto.setVaccine(vaccine);
//		CourseWorkExperienceRequirementDto workExperience = new CourseWorkExperienceRequirementDto();
//		workExperience=null;
//		courseOtherRequirementDto.setWorkExperience(workExperience);
//		
//		CourseWorkPlacementRequirementDto workPlacement = new CourseWorkPlacementRequirementDto();
//		workPlacement=null;
//		courseOtherRequirementDto.setWorkPlacement(workPlacement);
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.set("userId", userId);
//
//		HttpEntity<CourseOtherRequirementDto> entity = new HttpEntity<>(courseOtherRequirementDto, headers);
//		ResponseEntity<CourseOtherRequirementDto> response = testRestTemplate.exchange(
//				COURSE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "23d459e9-7942-4cd1-9a29-bfd7cb22cd39"
//						+ PATH_SEPARATOR + "other-requirement",
//				HttpMethod.POST, entity, CourseOtherRequirementDto.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//	}
//
//}
