package com.yuzee.app.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuzee.common.lib.dto.application.ProcedureDto;
import com.yuzee.common.lib.dto.institute.CourseCareerOutcomeDto;
import com.yuzee.common.lib.dto.institute.CourseContactPersonDto;
import com.yuzee.common.lib.dto.institute.CourseDeliveryModesDto;
import com.yuzee.common.lib.dto.institute.CourseEnglishEligibilityDto;
import com.yuzee.common.lib.dto.institute.CourseFundingDto;
import com.yuzee.common.lib.dto.institute.CourseIntakeDto;
import com.yuzee.common.lib.dto.institute.CourseMinRequirementDto;
import com.yuzee.common.lib.dto.institute.CoursePaymentDto;
import com.yuzee.common.lib.dto.institute.CoursePreRequisiteDto;
import com.yuzee.common.lib.dto.institute.CourseSemesterDto;
import com.yuzee.common.lib.dto.institute.FacultyDto;
import com.yuzee.common.lib.dto.institute.LevelDto;
import com.yuzee.common.lib.dto.institute.OffCampusCourseDto;
import com.yuzee.common.lib.dto.institute.ProviderCodeDto;
import com.yuzee.common.lib.dto.storage.StorageDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {

	@JsonProperty("id")

	private String id;

	@JsonProperty("instituteId")
	private String instituteId;

	@JsonProperty("faculty_id")
	@NotBlank(message = "{faculty_id.is_required}")
	private String facultyId;

	@JsonProperty("faculty")
	private FacultyDto faculty;

	@JsonProperty("name")
	@NotBlank(message = "{name.is_required}")
	private String name;

	@JsonProperty("description")
	private String description;

	@JsonProperty("language")
	private List<String> language;

	@JsonProperty("grades")
	private String grades;

	@JsonProperty("document_url")
	private String documentUrl;

	@JsonProperty("website")
	private String website;

	@JsonProperty("last_updated")
	private String lastUpdated;

	@JsonProperty("institute_name")
	private String instituteName;

//	@JsonProperty("location")
//	private String locations;

	@JsonProperty("world_ranking")
	private String worldRanking;

	@JsonProperty("stars")
	private Double stars;

	@JsonProperty("cost")
	private String cost;

	@JsonProperty("totalcount")
	private String totalCount;

	@JsonProperty("currency")
	private String currency;

	@JsonProperty("currencytime")
	private String currencyTime;

	@JsonProperty("levelIds")
	@NotBlank(message = "{level_id.is_required}")
	private String levelIds;

	@JsonProperty("level")
	private LevelDto level;

	@JsonProperty("availability")
	private String availability;

	@JsonProperty("recognition")
	private String recognition;

	@JsonProperty("recognitionType")
	private String recognitionType;

	@JsonProperty("abbreviation")
	private String abbreviation;

	@JsonProperty("remarks")
	private String remarks;

	@JsonProperty("applied")
	private Boolean applied;

	@JsonProperty("viewCourse")
	private Boolean viewCourse;

	@JsonProperty("latitude")
	private Double latitude;

	@JsonProperty("longitude")
	private Double longitude;

	@JsonProperty("countryName")
	private String countryName;

	@JsonProperty("cityName")
	private String cityName;

	@DBRef
	@JsonProperty("storageList")
	private List<StorageDto> storageList = new ArrayList<>();

	@Valid

	@JsonProperty("courseDeliveryModes")
	@DBRef
	private ValidList<CourseDeliveryModesDto> courseDeliveryModes = new ValidList<>();

	@Valid
	@JsonProperty("prerequisites")
	@DBRef
	private List<CoursePreRequisiteDto> prerequisites;

	@Valid
	@JsonProperty("englishEligibility")
	@DBRef
	private ValidList<CourseEnglishEligibilityDto> englishEligibility = new ValidList<>();

	@JsonProperty("examinationBoard")
	private String examinationBoard;

	@JsonProperty("domestic_applicationFee")
	private Double domesticApplicationFee;

	@JsonProperty("international_application_fee")
	private Double internationalApplicationFee;

	@JsonProperty("domestic_enrollment_fee")
	private Double domesticEnrollmentFee;

	@JsonProperty("international_enrollment_fee")
	private Double internationalEnrollmentFee;

	@JsonProperty("domestic_boarding_fee")
	private Double domesticBoardingFee;

	@JsonProperty("international_boarding_fee")
	private Double internationalBoardingFee;

	@JsonProperty("entrance_exam")
	private String entranceExam;

	@JsonProperty("phone_number")
	private String phoneNumber;

	@JsonProperty("global_gpa")
	private Double globalGpa;

	@JsonProperty("content")
	private String content;

	@JsonProperty("email")
	private String email;

	@JsonProperty("rec_date")
	private Date recDate;

	@JsonProperty("has_edit_access")
	private Boolean hasEditAccess;

	@JsonProperty("favorite_course")
	private Boolean favoriteCourse;

	@JsonProperty("reviews_count")
	private Long reviewsCount;

	@JsonProperty("audience")
	private String audience;

	@JsonProperty("fundings_count")
	private int fundingsCount;

	@JsonProperty("off_campus_course")
	@DBRef
	private OffCampusCourseDto offCampusCourse;

	@Valid
	@JsonProperty("course_semesters")
	@NotNull(message = "{course_semesters.is_required}")
	@DBRef
	private ValidList<CourseSemesterDto> courseSemesters = new ValidList<>();

	@Valid
	@JsonProperty("course_timings")
	@NotNull(message = "{course_timings.is_required}")
	@DBRef
	private ValidList<TimingRequestDto> courseTimings = new ValidList<>();

	@Valid
	@JsonProperty("course_career_outcomes")
	@DBRef
	private ValidList<CourseCareerOutcomeDto> courseCareerOutcomes = new ValidList<>();

	@Valid
	@JsonProperty("course_fundings")
	@DBRef
	private ValidList<CourseFundingDto> courseFundings = new ValidList<>();

	@JsonProperty("course_payment")
	@DBRef
	private CoursePaymentDto coursePayment;

	@Valid
	@JsonProperty("institute")
	@DBRef
	private InstituteResponseDto institute;

	@Valid
	@JsonProperty("course_contact_persons")
	@NotNull(message = "{course_contact_persons.is_required}")
	@DBRef
	private ValidList<CourseContactPersonDto> courseContactPersons = new ValidList<>();

	@Valid
	@JsonProperty("provider_codes")
	@DBRef
	private ValidList<ProviderCodeDto> providerCodes = new ValidList<>();

	@JsonProperty("course_intake")
	@DBRef
	private CourseIntakeDto intake;

	@JsonProperty("international_student_procedure")
	@DBRef
	private ProcedureDto internationalStudentProcedure;

	@JsonProperty("domestic_student_procedure")
	@DBRef
	private ProcedureDto domesticStudentProcedure;

	@JsonProperty("international_student_procedure_id")
	private String internationalStudentProcedureId;

	@JsonProperty("domestic_student_procedure_id")
	private String domesticStudentProcedureId;

	@Valid
	@NotNull(message = "{course_min_requirements.is_required}")
	@JsonProperty("course_min_requirements")
	@DBRef
	List<CourseMinRequirementDto> courseMinRequirementDtos;

}