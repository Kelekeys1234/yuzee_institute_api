package com.yuzee.app.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InstituteElasticSearchDTO {

	@JsonProperty("institute_id")
	@NotBlank(message = "institute_id should not be blank")
	private String id;

	@JsonProperty("country_name")
	@NotBlank(message = "country_name should not be blank")
	private String countryName;

	@JsonProperty("city_name")
	@NotBlank(message = "city_name should not be blank")
	private String cityName;

	@JsonProperty("institute_type_name")
	private String instituteTypeName;

	@JsonProperty("name")
	@NotBlank(message = "name should not be blank")
	private String name;

	@JsonProperty("world_ranking")
	private Integer worldRanking;

	@JsonProperty("latitude")
	private String latitude;

	@JsonProperty("longitude")
	private String longitude;

	@JsonProperty("description")
	private String description;

	@JsonProperty("campus")
	private String campus;

	@JsonProperty("level_code")
	private List<String> levelCode;

	@JsonProperty("stars")
	private Integer stars;

	@JsonProperty("faculty_names")
	private List<String> facultyNames;

	@JsonProperty("intakes")
	private List<String> intakes;

	@JsonProperty("level_name")
	private List<String> levelName;
}
