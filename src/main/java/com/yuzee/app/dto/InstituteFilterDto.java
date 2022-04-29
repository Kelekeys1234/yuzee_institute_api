package com.yuzee.app.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class InstituteFilterDto {

	@JsonProperty("name")
	private String name;

	@JsonProperty("city_name")
	private String cityName;

	@JsonProperty("country_name")
	private String countryName;

	@JsonProperty("institute_id")
	private String instituteId;

	@JsonProperty("institute_type_Name")
	private String instituteTypeName;

	@JsonProperty("duration_date")
	private Date durationDate;

	@JsonProperty("max_size_per_page")
	@NotNull(message = "MaxPageSize required")
	private Integer maxSizePerPage;

	@JsonProperty("page_number")
	private Integer pageNumber;

	@JsonProperty("world_ranking")
	private Integer worldRanking;

	@JsonProperty("date_posted")
	private String datePosted;
}
