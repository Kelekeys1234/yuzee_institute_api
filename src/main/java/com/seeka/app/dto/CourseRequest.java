package com.seeka.app.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CourseRequest {

	@JsonProperty("id")
	private String id;

	@JsonProperty("institute_id")
	@NotBlank(message = "institute_id should not be blank")
	private String instituteId;

	@JsonProperty("faculty_id")
	@NotBlank(message = "faculty_id should not be blank")
	private String facultyId;

	@JsonProperty("name")
	@NotBlank(message = "name should not be blank")
	private String name;

	@JsonProperty("description")
	private String description;

	@JsonProperty("intake")
	private List<Date> intake;

	@JsonProperty("language")
	private List<String> language;

	@JsonProperty("grades")
	private String grades;

	@JsonProperty("document_url")
	private String documentUrl;

	@JsonProperty("contact")
	private String contact;

	@JsonProperty("opening_hour_from")
	private String openingHourFrom;

	@JsonProperty("opening_hour_to")
	private String openingHourTo;

	@JsonProperty("job_full_time")
	private String jobFullTime;

	@JsonProperty("job_part_time")
	private String jobPartTime;

	@JsonProperty("campus_location")
	private String campusLocation;

	@JsonProperty("website")
	private String website;

	@JsonProperty("link")
	private String link;

	@JsonProperty("last_updated")
	private String lastUpdated;

	@JsonProperty("institute_name")
	private String instituteName;

	@JsonProperty("location")
	private String location;

	@JsonProperty("world_ranking")
	private String worldRanking;

	@JsonProperty("stars")
	private String stars;

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

	@JsonProperty("faculty_ame")
	private String facultyName;

	@JsonProperty("level_id")
	@NotBlank(message = "level_id should not be blank")
	private String levelId;

	@JsonProperty("level_name")
	private String levelName;

	@JsonProperty("availability")
	private String availbility;

	@JsonProperty("recognition")
	private String recognition;

	@JsonProperty("recognition_type")
	private String recognitionType;

	@JsonProperty("abbreviation")
	private String abbreviation;

	@JsonProperty("english_eligibility")
	private List<CourseEnglishEligibilityDto> englishEligibility;

	@JsonProperty("storage_list")
	private List<StorageDto> storageList;

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

	@JsonProperty("user_review_result")
	private List<UserReviewResultDto> userReviewResult;

	@JsonProperty("country_name")
	private String countryName;

	@JsonProperty("city_name")
	private String cityName;

	@JsonProperty("student_visa")
	private StudentVisaDto studentVisa;

	@JsonProperty("accrediated_detail")
	private List<AccrediatedDetailDto> accrediatedDetail;

	@JsonProperty("course_delivery_modes")
	private List<CourseDeliveryModesDto> courseAdditionalInfo;
}
