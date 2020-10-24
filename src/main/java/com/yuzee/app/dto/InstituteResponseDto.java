package com.yuzee.app.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InstituteResponseDto {

	@JsonProperty("institute_id")
	private String id;

	@JsonProperty("name")
	@NotBlank(message = "name should not be blank")
	private String name;

	@JsonProperty("world_ranking")
	private Integer worldRanking;

	@JsonProperty("location")
	private String location;

	@JsonProperty("total_courses")
	private Integer totalCourses;

	@JsonProperty("total_count")
	private Integer totalCount;

	@JsonProperty("website")
	private String website;

	@JsonProperty("about_us")
	private String aboutUs;

	@JsonProperty("total_student")
	private Integer totalStudent;

	@JsonProperty("latitude")
	private Double latitude;

	@JsonProperty("longitude")
	private Double longitude;

	@JsonProperty("phone_number")
	private String phoneNumber;

	@JsonProperty("email")
	private String email;

	@JsonProperty("address")
	private String address;

	@JsonProperty("visa_requirement")
	private String visaRequirement;

	@JsonProperty("total_available_jobs")
	private String totalAvailableJobs;

	@JsonProperty("country_name")
	@NotBlank(message = "country_name should not be blank")
	private String countryName;

	@JsonProperty("city_name")
	@NotBlank(message = "city_name should not be blank")
	private String cityName;

	@JsonProperty("institute_type")
	private String instituteType;

	@JsonProperty("storage_list")
	private List<StorageDto> storageList;

	@JsonProperty("stars")
	private Double stars;

	@JsonProperty("domestic_ranking")
	private Integer domesticRanking;

	@JsonProperty("distance")
	private Double distance;

	@JsonProperty("min_price_range")
	private Double minPriceRange;

	@JsonProperty("max_price_range")
	private Double maxPriceRange;

	@JsonProperty("currency")
	private String currency;

	@JsonProperty("institute_services")
	private List<String> instituteServices;

	@JsonProperty("accrediated_detail")
	private List<AccrediatedDetailDto> accrediatedDetail;

	@JsonProperty("institute_timing")
	private InstituteTimingResponseDto instituteTiming;
	
	@JsonProperty("tag_line")
	private String tagLine;
	
	@JsonProperty("profile_permission")
	private String profilePermission;
}
