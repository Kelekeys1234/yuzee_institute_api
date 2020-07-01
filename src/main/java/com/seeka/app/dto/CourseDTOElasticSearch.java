package com.seeka.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class CourseDTOElasticSearch implements Serializable {

	private static final long serialVersionUID = 3521506877760833299L;

	@JsonProperty("id")
	private String id;

	@JsonProperty("name")
	@NotBlank(message = "name should not be blank")
	private String name;

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

	@JsonProperty("abbreviation")
	private String abbreviation;

	@JsonProperty("remarks")
	private String remarks;

	@JsonProperty("description")
	private String description;

	@JsonProperty("faculty_name")
	@NotBlank(message = "faculty_name should not be blank")
	private String facultyName;

	@JsonProperty("institute_name")
	@NotBlank(message = "institute_name should not be blank")
	private String instituteName;

	@JsonProperty("country_name")
	@NotBlank(message = "country_name should not be blank")
	private String countryName;

	@JsonProperty("city_name")
	@NotBlank(message = "city_name should not be blank")
	private String cityName;

	@JsonProperty("level_code")
	private String levelCode;

	@JsonProperty("level_name")
	@NotBlank(message = "level_name should not be blank")
	private String levelName;

	@JsonProperty("availabilty")
	private String availabilty;

	@JsonProperty("currency")
	@NotBlank(message = "currency should not be blank")
	private String currency;

	@JsonProperty("currency_time")
	private String currencyTime;

	@JsonProperty("cost_range")
	private Double costRange;

	@JsonProperty("content")
	private String content;

	@JsonProperty("faculty_description")
	private String facultyDescription;

	@JsonProperty("institute_image_url")
	private List<String> instituteImageUrl;

	@JsonProperty("institute_logo_url")
	private String instituteLogoUrl;

	@JsonProperty("latitute")
	private String latitute;

	@JsonProperty("longitude")
	private String longitude;

	@JsonProperty("opening_hour_from")
	private String openingHourFrom;

	@JsonProperty("opening_hour_to")
	private String openingHourTo;

	@JsonProperty("intake")
	private List<Date> intake;

	@JsonProperty("language")
	private List<String> language;

	@JsonProperty("course_delivery_modes")
	private List<CourseDeliveryModesElasticDto> courseAdditionalInfo;
}
