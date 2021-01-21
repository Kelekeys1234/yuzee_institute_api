package com.yuzee.app.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CourseRequest {

	@JsonProperty("course_id")
	private String id;

	@JsonProperty("institute_id")
	private String instituteId;

	@JsonProperty("faculty_id")
	@NotBlank(message = "faculty_id should not be blank")
	private String facultyId;

	@JsonProperty("faculty")
	private FacultyDto faculty;

	@JsonProperty("curriculum_id")
	private String curriculumId;

	@JsonProperty("name")
	@NotBlank(message = "name should not be blank")
	private String name;

	@JsonProperty("description")
	private String description;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty("intake")
	private List<Date> intake;

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

	@JsonProperty("location")
	private String location;

	@JsonProperty("world_ranking")
	private String worldRanking;

	@JsonProperty("stars")
	private Double stars;

	@JsonProperty("cost")
	private String cost;

	@JsonProperty("total_count")
	private String totalCount;

	@JsonProperty("requirements")
	private String requirements;

	@JsonProperty("currency")
	@NotBlank(message = "currency should not be blank")
	private String currency;

	@JsonProperty("currency_time")
	private String currencyTime;

	@JsonProperty("level_id")
	@NotBlank(message = "level_id should not be blank")
	private String levelId;

	@JsonProperty("level")
	private LevelDto level;

	@JsonProperty("availability")
	private String availability;

	@JsonProperty("recognition")
	private String recognition;

	@JsonProperty("recognition_type")
	private String recognitionType;

	@JsonProperty("abbreviation")
	private String abbreviation;

	@JsonProperty("remarks")
	private String remarks;

	@JsonProperty("applied")
	private Boolean applied;

	@JsonProperty("view_course")
	private Boolean viewCourse;

	@JsonProperty("latitude")
	private Double latitude;

	@JsonProperty("longitude")
	private Double longitude;

	@JsonProperty("country_name")
	private String countryName;

	@JsonProperty("city_name")
	private String cityName;

	@JsonProperty("storage_list")
	private List<StorageDto> storageList = new ArrayList<>();

	@Valid
	@JsonProperty("course_delivery_modes")
	private ValidList<CourseDeliveryModesDto> courseDeliveryModes = new ValidList<>();

	@Valid
	@JsonProperty("course_prerequisite_subjects")
	private ValidList<CoursePrerequisiteSubjectDto> prerequisiteSubjects = new ValidList<>();

	@Valid
	@JsonProperty("english_eligibility")
	private ValidList<CourseEnglishEligibilityDto> englishEligibility = new ValidList<>();

	@JsonProperty("examination_board")
	private String examinationBoard;

	@JsonProperty("domestic_application_fee")
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

	@JsonProperty("fundings_count")
	private int fundingsCount;

	@JsonProperty("off_campus_course")
	private OffCampusCourseDto offCampusCourse;

	@Valid
	@JsonProperty("course_subjects")
	@NotNull(message = "course_subjects must not be null")
	private ValidList<CourseSubjectDto> courseSubjects = new ValidList<>();

	@Valid
	@JsonProperty("course_timings")
	@NotNull(message = "course_timings must not be null")
	private ValidList<TimingRequestDto> courseTimings = new ValidList<>();

	@Valid
	@JsonProperty("course_career_outcomes")
	@NotNull(message = "course_career_outcomes must not be null")
	private ValidList<CourseCareerOutcomeDto> courseCareerOutcomes = new ValidList<>();

	@Valid
	@JsonProperty("course_fundings")
	@NotNull(message = "course_fundings must not be null")
	private ValidList<CourseFundingDto> courseFundings = new ValidList<>();

	@JsonProperty("course_payment")
	private CoursePaymentDto coursePayment;

	@Valid
	@JsonProperty("institute")
	private InstituteResponseDto institute;
}