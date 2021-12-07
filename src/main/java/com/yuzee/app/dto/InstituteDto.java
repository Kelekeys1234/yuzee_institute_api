package com.yuzee.app.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuzee.common.lib.dto.institute.ProviderCodeDto;
import com.yuzee.common.lib.enumeration.InstituteType;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InstituteDto {
	
	@JsonProperty("institute_id")
	private String id;
	
	@JsonProperty("name")
	@NotBlank(message = "name should not be blank")
	private String name;
	
	@JsonProperty("city_name")
	private String cityName;
	
	@JsonProperty("country_name")
	private String countryName;
	
	@JsonProperty("world_ranking")
	private Integer worldRanking;
	
	@JsonProperty("institute_type")
	@NotBlank(message = "institute_type should not be blank")
	private String instituteType;
	
	@JsonProperty("website")
	private String website;
	
	@JsonProperty("latitude")
	private Double latitude;
	
	@JsonProperty("longitude")
	private Double longitude;
	
	@JsonProperty("total_student")
	private Integer totalStudent;

	@JsonProperty("email")
	private String email;
	
	@JsonProperty("phone_number")
	private String phoneNumber;
	
	@JsonProperty("address")
	private String address;
	
	@JsonProperty("domestic_ranking")
	private Integer domesticRanking;
	
	@JsonProperty("tag_line")
	private String tagLine;

	@JsonProperty("stars")
	private Double stars;
	
	@JsonProperty("reviews_count")
	private Long reviewsCount;
	
	@JsonProperty("profile_permission")
	private String profilePermission;
	
	@JsonProperty("total_courses")
	private Integer totalCourses;
	
	@JsonProperty("campus_name")
	private String campusName;
	
	@JsonProperty("state_name")
	private String stateName;
	
	@JsonProperty("whatsapp_number")
	private String whatsNo;
	
	@JsonProperty("logo_url")
	private String logoUrl;
	
	@JsonProperty("cover_photo_url")
	private String coverPhotoUrl;
	
	@JsonProperty("readable_id")
	@NotBlank(message = "readable_id should not be blank")
	private String readableId;
	
	@JsonProperty("institute_type_obj")
	private InstituteType instituteTypeObj;
	
	@JsonProperty("show_suggestion")
	private Boolean showSuggestion;
	
	@JsonProperty("intakes")
	private List<String> intakes;	
	
	@JsonProperty("provider_codes")
	private List<ProviderCodeDto> providerCodes;	
}