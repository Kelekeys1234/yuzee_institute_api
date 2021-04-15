package com.yuzee.app.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InstituteElasticSearchDto {
	
	@JsonProperty("institute_id")
	private String id;

	@JsonProperty("institute_type")
	private String instituteType;

	@JsonProperty("name")
	private String name;

	@JsonProperty("world_ranking")
	private Integer worldRanking;

	@JsonProperty("phone_number")
	private String phoneNumber;

	@JsonProperty("email")
	private String email;

	@JsonProperty("website")
	private String website;

	@JsonProperty("latitude")
	private Double latitude;

	@JsonProperty("longitude")
	private Double longitude;

	@JsonProperty( "address")
	private String address;

	@JsonProperty("description")
	private String description;

	@JsonProperty("country_name")
	private String countryName;

	@JsonProperty("city_name")
	private String cityName;

	@JsonProperty("avg_cost_of_living")
	private String avgCostOfLiving;

	@JsonProperty("tution_fees_plan")
	private String tuitionFeesPaymentPlan;

	@JsonProperty( "enrolment_link")
	private String enrolmentLink;

	@JsonProperty("about_us_info")
	private String aboutInfo;

	@JsonProperty("course_start")
	private String courseStart;

	@JsonProperty("whatsapp_no")
	private String whatsNo;

	@JsonProperty("institute_category")
	private String instituteCategory;

	@JsonProperty("campus_name")
	private String campusName;

	@JsonProperty("scholarship_financing_assistance")
	private String scholarshipFinancingAssistance;

	@JsonProperty( "domestic_ranking")
	private Integer domesticRanking;

	@JsonProperty("admission_email")
	private String admissionEmail;

	@JsonProperty("boarding_available")
	private String boardingAvailable;

	@JsonProperty("boarding")
	private String boarding;

	@JsonProperty("state_name")
	private String stateName;

	@JsonProperty("postal_code")
	private Integer postalCode;

	@JsonProperty("english_partners")
	private String englishPartners;

	@JsonProperty("img_count")
	private String imageCount;

	@JsonProperty("climate")
	private String climate;

	@JsonProperty("youtube_link")
	private String youtubeLink;

	@JsonProperty("international_phone_number")
	private String internationalPhoneNumber;

	@JsonProperty("domestic_phone_number")
	private String domesticPhoneNumber;

	@JsonProperty("accreditation")
	private String accreditation;
	
	@JsonProperty("link")
	private String link;
	
	@JsonProperty("contact")
	private String contact;
	
	@JsonProperty( "curriculum")
	private String curriculum;
	
	@JsonProperty("domestic_boarding_fee")
	private Double domesticBoardingFee;
	
	@JsonProperty("international_boarding_fee")
	private Double internationalBoardingFee;
	
	@JsonProperty("institute_english_requirement")
	private List<InstituteEnglishRequirementsElasticDto> instituteEnglishRequirements = new ArrayList<>();
	
	@JsonProperty("institute_additional_info")
	private InstituteAdditionalInfoElasticDto instituteAdditionalInfoElasticDto;
	
	@JsonProperty("institute_timing")
	private InstituteTimingElasticDto instituteTimingElasticDto;
	
	@JsonProperty("institute_facility")
	private List<String> instituteFacilities = new ArrayList<>();
	
	@JsonProperty("institute_service")
	private List<String> instituteServices = new ArrayList<>();
	
	@JsonProperty("institute_intakes")
	private List<String> instituteIntakes = new ArrayList<>();
}
