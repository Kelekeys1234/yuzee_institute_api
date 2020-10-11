package com.yuzee.app.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InstituteRequestDto {
	
	@JsonProperty("institute_id")
	private String id;
	
	@JsonProperty("name")
	@NotBlank(message = "name should not be blank")
	private String name;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("city_name")
	@NotBlank(message = "city_name should not be blank")
	private String cityName;
	
	@JsonProperty("country_name")
	@NotBlank(message = "country_name should not be blank")
	private String countryName;
	
	@JsonProperty("world_ranking")
	private Integer worldRanking;
	
	@JsonProperty("logo_url")
	private String logoUrl;
	
	@JsonProperty("avg_cost_of_living")
	private String avgCostOfLiving;
	
	@JsonProperty("institute_type")
	@NotBlank(message = "institute_type should not be blank")
	private String instituteType;
	
	@JsonProperty("enrolment_link")
	private String enrolmentLink;
	
	@JsonProperty("tuition_fess_payment_plan")
	private String tuitionFessPaymentPlan;
	
	@JsonProperty("scholarship_financing_assistance")
	private String scholarshipFinancingAssistance;
	
	@JsonProperty("website")
	private String website;
	
	@JsonProperty("institute_category_type_id")
	@NotBlank(message = "institute_category_type_id should not be blank")
	private String instituteCategoryTypeId;
	
	@JsonProperty("campus_name")
	private String campusName;
	
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
	
	@JsonProperty("offer_service")
	private List<String> offerService;
	
	@JsonProperty("offer_service_name")
	private List<String> offerServiceName;
	
	@JsonProperty("accreditation")
	private List<String> accreditation;
	
	@JsonProperty("accreditation_details")
	private List<AccrediatedDetailDto> accreditationDetails;
	
	@JsonProperty("intakes")
	private List<String> intakes;
	
	@JsonProperty("whatsapp_number")
	private String whatsNo;
	
	@JsonProperty("domestic_ranking")
	private Integer domesticRanking;
	
	@JsonProperty("institute_timings")
	private List<InstituteTimingDto> instituteTimings; 
	
	/**
	 * There is no use of below fields in Admin panel.
	 */
	@JsonProperty("about_info")
	private String aboutInfo;
	
	@JsonProperty("course_start")
	private String courseStart;
	
	@JsonProperty("link")
	private String link;
	
	@JsonProperty("contact")
	private String contact;
	
	@JsonProperty("curriculum")
	private String curriculum;
	
	@JsonProperty("domestic_boarding_fee")
	private Double domesticBoardingFee;
	
	@JsonProperty("international_boarding_fee")
	private Double internationalBoardingFee;
	
	@JsonProperty("tag_line")
	private String tagLine;
	
	@JsonProperty("followers_count")
	private long followersCount;
	
}
