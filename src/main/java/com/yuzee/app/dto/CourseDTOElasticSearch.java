package com.yuzee.app.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CourseDTOElasticSearch {

	@JsonProperty("id")
	private String id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("faculty")
	private FacultyDto faculty;

	@JsonProperty("institute")
	private InstituteElasticSearchDTO institute;

	@JsonProperty("level")
	private LevelDto level;

	@JsonProperty("course_curriculum")
	private CourseCurriculumDto courseCurriculum;

	@JsonProperty("world_ranking")
	private Integer worldRanking;

	@JsonProperty("stars")
	private Integer stars;

	@JsonProperty("recognition")
	private String recognition;

	@JsonProperty("recognition_type")
	private String recognitionType;

	@JsonProperty("website")
	private String website;

	@JsonProperty("phone_number")
	private String phoneNumber;

	@JsonProperty("email")
	private String email;

	@JsonProperty("abbreviation")
	private String abbreviation;

	@JsonProperty("remarks")
	private String remarks;

	@JsonProperty("description")
	private String description;

	@JsonProperty("availabilty")
	private String availabilty;

	@JsonProperty("currency")
	private String currency;

	@JsonProperty("currency_time")
	private String currencyTime;

	@JsonProperty("global_gpa")
	private Double globalGpa;

	@JsonProperty("rec_date")
	private Date recDate;

	@JsonProperty("content")
	private String content;

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

	@JsonProperty("usd_domestic_application_fee")
	private Double usdDomesticApplicationFee;

	@JsonProperty("usd_international_application_fee")
	private Double usdInternationalApplicationFee;

	@JsonProperty("usd_domestic_enrollment_fee")
	private Double usdDomesticEnrollmentFee;

	@JsonProperty("usd_international_enrollment_fee")
	private Double usdInternationalEnrollmentFee;

	@JsonProperty("entrance_exam")
	private String entranceExam;

	@JsonProperty("code")
	private String code;

	@JsonProperty("course_type")
	private String courseType;

	@JsonProperty("off_campus_course")
	private OffCampusCourseDto offCampusCourse;

	@JsonProperty("course_payment")
	private CoursePaymentDto coursePayment;

	@JsonProperty("course_delivery_modes")
	private List<CourseDeliveryModesDto> courseDeliveryModes;

	@JsonProperty("course_english_eligibilities")
	private List<CourseEnglishEligibilityDto> courseEnglishEligibilities;

	@JsonProperty("course_intakes")
	private List<CourseIntakeDto> courseIntakes;

	@JsonProperty("course_languages")
	private List<String> languages;

	@JsonProperty("course_min_requirements")
	private List<CourseMinRequirementDto> courseMinRequirements;

	@JsonProperty("course_prerequisites")
	private List<CoursePreresquisiteDto> coursePrerequisites;

	@JsonProperty("course_career_outcomes")
	private List<CourseCareerOutcomeDto> courseCareerOutcomes;

	@JsonProperty("course_subjects")
	private List<CourseSubjectDto> courseSubjects;

	@JsonProperty("course_fundings")
	private List<CourseFundingDto> courseFundings;
	
	@JsonProperty("course_scholarships")
	private List<CourseScholarshipElasticDto> courseScholarships;
}
