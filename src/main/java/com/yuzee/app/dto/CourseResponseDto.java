package com.yuzee.app.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CourseResponseDto {

	@JsonProperty("course_id")
	private String id;

	@JsonProperty("name")
	@NotBlank(message = "name should not be blank")
	private String name;

	@JsonProperty("course_ranking")
	private Integer courseRanking;

	@JsonProperty("stars")
	private Double stars;

	@JsonProperty("language")
	private List<String> language;

	@JsonProperty("language_short_key")
	private String languageShortKey;

	@JsonProperty("institute_id")
	@NotBlank(message = "institute_id should not be blank")
	private String instituteId;

	@JsonProperty("institute_name")
	@NotBlank(message = "institute_name should not be blank")
	private String instituteName;

	@JsonProperty("cost_range")
	private Double costRange;

	@JsonProperty("location")
	private String location;

	@JsonProperty("totalCount")
	private Integer totalCount;

	@JsonProperty("requirements")
	private String requirements;

	@JsonProperty("country_name")
	@NotBlank(message = "countryName should not be blank")
	private String countryName;

	@JsonProperty("city_name")
	@NotBlank(message = "cityName should not be blank")
	private String cityName;

	@JsonProperty("is_favourite")
	private Boolean isFavourite;

	@JsonProperty("currency_code")
	@NotBlank(message = "currency_code should not be blank")
	private String currencyCode;

	@JsonProperty("storage_list")
	private List<StorageDto> storageList;

	@JsonProperty("is_viewed")
	private Boolean isViewed = false;

	@JsonProperty("cost")
	private String cost;

	@JsonProperty("is_active")
	private Boolean isActive;

	@JsonProperty("updated_on")
	private Date updatedOn;

	@JsonProperty("intake")
	private List<Date> intake;

	@JsonProperty("faculty_name")
	@NotBlank(message = "faculty_name should not be blank")
	private String facultyName;

	@JsonProperty("faculty_id")
	@NotBlank(message = "faculty_id should not be blank")
	private String facultyId;

	@JsonProperty("distance")
	private Double distance;

	@JsonProperty("latitude")
	private Double latitude;

	@JsonProperty("longitude")
	private Double longitude;

	@JsonProperty("course_delivery_modes")
	private List<CourseDeliveryModesDto> courseDeliveryModes;
}
