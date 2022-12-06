package com.yuzee.app.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.yuzee.app.dto.CourseOtherRequirementDto;
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.dto.CourseVaccineRequirementDto;
import com.yuzee.app.dto.CourseWorkExperienceRequirementDto;
import com.yuzee.app.dto.CourseWorkPlacementRequirementDto;
import com.yuzee.common.lib.dto.PaginationResponseDto;
import com.yuzee.common.lib.dto.common.VaccinationDto;
import com.yuzee.common.lib.handler.CommonHandler;

@TestInstance(Lifecycle.PER_CLASS)
class CourseOtherRequirementControllerTest extends CreateCourseAndInstitute {
	UUID uuid = UUID.randomUUID();
	@Autowired
	private TestRestTemplate testRestTemplate;

	@MockBean
	private CommonHandler commonHandler;

	private String instituteId;
	private CourseRequest courseId;

	@BeforeAll
	public void createAllIntituteAndCourse() throws IOException {
		instituteId = testCreateInstitute();
		courseId = createCourses(instituteId);
	}

	@DisplayName("Add work_experience_work_placement")
	@Test
	void addWorkExperienceWorkPlacement() throws IOException {
		Mockito.when(commonHandler.getVaccinationByFilters(1, 1, null)).thenReturn(new PaginationResponseDto());
		CourseOtherRequirementDto courseOtherRequirementDto = new CourseOtherRequirementDto();
		CourseVaccineRequirementDto vaccine = new CourseVaccineRequirementDto();
		vaccine.setDescription("hello this is vaccine");
		Set<VaccinationDto> vacId = new HashSet<VaccinationDto>();
		VaccinationDto dto = new VaccinationDto();
		dto.set_id(uuid);
		vacId.add(dto);
		Set<String> id = new HashSet(vacId);
		vaccine.setVaccination(vacId);
		courseOtherRequirementDto.setVaccine(vaccine);

		CourseWorkExperienceRequirementDto workExperience = new CourseWorkExperienceRequirementDto();
		workExperience.setDescription("Hello this is my am work_experience ");
		workExperience.setDuration(4.5);
		workExperience.setDurationType("month");
		List<String> workExperienceFields = new ArrayList<>();
		workExperienceFields.add("IT");
		workExperience.setFields(workExperienceFields);
		courseOtherRequirementDto.setWorkExperience(workExperience);

		CourseWorkPlacementRequirementDto workPlacement = new CourseWorkPlacementRequirementDto();
		workPlacement.setDescription("Hello this is my am workPlacement ");
		workPlacement.setDuration(4.5);
		workPlacement.setDurationType("month");
		List<String> workPlacementFields = new ArrayList<>();
		workPlacementFields.add("IT");
		workPlacement.setFields(workPlacementFields);
		courseOtherRequirementDto.setWorkPlacement(workPlacement);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);
		HttpEntity<CourseOtherRequirementDto> entity = new HttpEntity<>(courseOtherRequirementDto, headers);
		ResponseEntity<CourseOtherRequirementDto> response = testRestTemplate
				.exchange(
						INSTITUTE_PRE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId.getId()
								+ PATH_SEPARATOR + "other-requirement",
						HttpMethod.POST, entity, CourseOtherRequirementDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName("Update work_experience_work_placement")
	@Test
	void updateWorkExperienceWorkPlacement() throws IOException {
		CourseOtherRequirementDto courseOtherRequirementDto = new CourseOtherRequirementDto();
		CourseWorkExperienceRequirementDto workExperience = new CourseWorkExperienceRequirementDto();
		workExperience.setDescription("Hello this is my  workExperience ");
		workExperience.setDuration(5.5);
		workExperience.setDurationType("month");
		List<String> workExperienceFields = new ArrayList<>();
		workExperienceFields.add("IT");
		workExperience.setFields(workExperienceFields);
		courseOtherRequirementDto.setWorkExperience(workExperience);

		CourseWorkPlacementRequirementDto workPlacement = new CourseWorkPlacementRequirementDto();
		workPlacement.setDescription("Hello this is my workPlacement ");
		workPlacement.setDuration(5.5);
		workPlacement.setDurationType("month");
		List<String> workPlacementFields = new ArrayList<>();
		workPlacementFields.add("IT");
		workPlacement.setFields(workPlacementFields);
		courseOtherRequirementDto.setWorkPlacement(workPlacement);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);

		HttpEntity<CourseOtherRequirementDto> entity = new HttpEntity<>(courseOtherRequirementDto, headers);
		ResponseEntity<CourseOtherRequirementDto> response = testRestTemplate
				.exchange(
						INSTITUTE_PRE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId.getId()
								+ PATH_SEPARATOR + "other-requirement",
						HttpMethod.POST, entity, CourseOtherRequirementDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName("Add multiple_fields_work_experience_work_placement")
	@Test
	void addMultipleFieldsWorkExperienceWorkPlacement() throws IOException {
		String instituteId = testCreateInstitute();
		CourseRequest courseId = createCourses(instituteId);
		CourseOtherRequirementDto courseOtherRequirementDto = new CourseOtherRequirementDto();
		CourseWorkExperienceRequirementDto workExperience = new CourseWorkExperienceRequirementDto();
		workExperience.setDescription("Hello this is my  work_experience ");
		workExperience.setDuration(5.5);
		workExperience.setDurationType("month");
		List<String> workExperienceFields = new ArrayList<>();
		workExperienceFields.add("IT");
		workExperienceFields.add("NIT");
		workExperience.setFields(workExperienceFields);
		courseOtherRequirementDto.setWorkExperience(workExperience);

		CourseWorkPlacementRequirementDto workPlacement = new CourseWorkPlacementRequirementDto();
		workPlacement.setDescription("Hello this is my workPlacement");
		workPlacement.setDuration(5.5);
		workPlacement.setDurationType("month");
		List<String> workPlacementFields = new ArrayList<>();
		workPlacementFields.add("IT");
		workPlacementFields.add("NIT");
		workPlacement.setFields(workPlacementFields);
		courseOtherRequirementDto.setWorkPlacement(workPlacement);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);

		HttpEntity<CourseOtherRequirementDto> entity = new HttpEntity<>(courseOtherRequirementDto, headers);
		ResponseEntity<CourseOtherRequirementDto> response = testRestTemplate
				.exchange(
						INSTITUTE_PRE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId.getId()
								+ PATH_SEPARATOR + "other-requirement",
						HttpMethod.POST, entity, CourseOtherRequirementDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName("Remove singal_fields_work_experience_work_placement")
	@Test
	void removeSingalFieldsWorkExperienceWorkPlacement() throws IOException {
		CourseOtherRequirementDto courseOtherRequirementDto = new CourseOtherRequirementDto();
		CourseWorkExperienceRequirementDto workExperience = new CourseWorkExperienceRequirementDto();
		workExperience.setDescription("Hello this is my  workExperience ");
		workExperience.setDuration(5.5);
		workExperience.setDurationType("month");
		List<String> workExperienceFields = new ArrayList<>();
		workExperienceFields.add("NIT");
		workExperience.setFields(workExperienceFields);
		courseOtherRequirementDto.setWorkExperience(workExperience);

		CourseWorkPlacementRequirementDto workPlacement = new CourseWorkPlacementRequirementDto();
		workPlacement.setDescription("Hello this is my workPlacement");
		workPlacement.setDuration(5.5);
		workPlacement.setDurationType("month");
		List<String> workPlacementFields = new ArrayList<>();
		workPlacementFields.add("NIT");
		workPlacement.setFields(workPlacementFields);
		courseOtherRequirementDto.setWorkPlacement(workPlacement);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);

		HttpEntity<CourseOtherRequirementDto> entity = new HttpEntity<>(courseOtherRequirementDto, headers);
		ResponseEntity<CourseOtherRequirementDto> response = testRestTemplate
				.exchange(
						INSTITUTE_PRE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId.getId()
								+ PATH_SEPARATOR + "other-requirement",
						HttpMethod.POST, entity, CourseOtherRequirementDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@DisplayName("Get all_work_experience_work_placement")
	@Test
	void getAllWorkExperienceWorkPlacement() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);

		HttpEntity<CourseOtherRequirementDto> entity = new HttpEntity<>(headers);
		ResponseEntity<CourseOtherRequirementDto> response = testRestTemplate
				.exchange(
						INSTITUTE_PRE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId.getId()
								+ PATH_SEPARATOR + "other-requirement",
						HttpMethod.GET, entity, CourseOtherRequirementDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("Send wrong_courseId")
	@Test
	void sendWrongCourseId() {
		CourseOtherRequirementDto courseOtherRequirementDto = new CourseOtherRequirementDto();
		CourseWorkExperienceRequirementDto workExperience = new CourseWorkExperienceRequirementDto();
		workExperience.setDescription("Hello this is my  workExperience ");
		workExperience.setDuration(5.5);
		workExperience.setDurationType("month");
		List<String> workExperienceFields = new ArrayList<>();
		workExperienceFields.add("NIT");
		workExperience.setFields(workExperienceFields);
		courseOtherRequirementDto.setWorkExperience(workExperience);

		CourseWorkPlacementRequirementDto workPlacement = new CourseWorkPlacementRequirementDto();
		workPlacement.setDescription("Hello this is my workPlacement");
		workPlacement.setDuration(5.5);
		workPlacement.setDurationType("month");
		List<String> workPlacementFields = new ArrayList<>();
		workPlacementFields.add("NIT");
		workPlacement.setFields(workPlacementFields);
		courseOtherRequirementDto.setWorkPlacement(workPlacement);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);

		HttpEntity<CourseOtherRequirementDto> entity = new HttpEntity<>(courseOtherRequirementDto, headers);
		ResponseEntity<CourseOtherRequirementDto> response = testRestTemplate.exchange(
				INSTITUTE_PRE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "63317979a56987febcebdfjds5"
						+ PATH_SEPARATOR + "other-requirement",
				HttpMethod.POST, entity, CourseOtherRequirementDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}

	@DisplayName("WrongIdForGetAll")
	@Test
	void SendWrongIDgetAll() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);

		HttpEntity<CourseOtherRequirementDto> entity = new HttpEntity<>(headers);
		ResponseEntity<CourseOtherRequirementDto> response = testRestTemplate.exchange(
				INSTITUTE_PRE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + "63317979a56987febcebdfjdjfds5"
						+ PATH_SEPARATOR + "other-requirement",
				HttpMethod.GET, entity, CourseOtherRequirementDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}

	@DisplayName("Send NullWorkExp_WorkPlac")
	@Test
	void SendNullWorkExperienceWorkPlacement() throws IOException {
		CourseOtherRequirementDto courseOtherRequirementDto = new CourseOtherRequirementDto();
		CourseVaccineRequirementDto vaccine = new CourseVaccineRequirementDto();
		vaccine = null;
		courseOtherRequirementDto.setVaccine(vaccine);
		CourseWorkExperienceRequirementDto workExperience = new CourseWorkExperienceRequirementDto();
		workExperience = null;
		courseOtherRequirementDto.setWorkExperience(workExperience);

		CourseWorkPlacementRequirementDto workPlacement = new CourseWorkPlacementRequirementDto();
		workPlacement = null;
		courseOtherRequirementDto.setWorkPlacement(workPlacement);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", userId);

		HttpEntity<CourseOtherRequirementDto> entity = new HttpEntity<>(courseOtherRequirementDto, headers);
		ResponseEntity<CourseOtherRequirementDto> response = testRestTemplate
				.exchange(
						INSTITUTE_PRE_PATH + PATH_SEPARATOR + "course" + PATH_SEPARATOR + courseId.getId()
								+ PATH_SEPARATOR + "other-requirement",
						HttpMethod.POST, entity, CourseOtherRequirementDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

}
