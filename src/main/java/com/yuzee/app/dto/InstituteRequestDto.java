package com.yuzee.app.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class InstituteRequestDto extends InstituteDto {
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("logo_url")
	private String logoUrl;
	
	@JsonProperty("avg_cost_of_living")
	private String avgCostOfLiving;
	
	@JsonProperty("enrolment_link")
	private String enrolmentLink;
	
	@JsonProperty("tuition_fess_payment_plan")
	private String tuitionFessPaymentPlan;
	
	@JsonProperty("scholarship_financing_assistance")
	private String scholarshipFinancingAssistance;
	
	@JsonProperty("institute_category_type_id")
	@NotBlank(message = "institute_category_type_id should not be blank")
	private String instituteCategoryTypeId;
	
	@JsonProperty("campus_name")
	private String campusName;
	
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
	
	@JsonProperty("followers_count")
	private long followersCount;

	@JsonProperty("state_name")
	private String stateName;

	@JsonProperty("postal_code")
	private Integer postalCode;

	public InstituteRequestDto(String id, String name, Integer worldRanking, String cityName, String countryName,
			String stateName, String campusName, String website, String aboutInfo,
			Double latitude, Double longitude, String phoneNumber, String whatsNo, Long totalCourses, String email, String address,
			Integer domesticRanking, String tagLine) {
		super.setId(id);
		super.setName(name);
		super.setWorldRanking(worldRanking);
		super.setCityName(cityName);
		super.setCountryName(countryName);
		this.stateName = stateName;
		this.campusName = campusName;
		super.setWebsite(website);
		this.aboutInfo = aboutInfo;
		super.setTotalCourses(totalCourses.intValue());
		super.setLatitude(latitude);
		super.setLongitude(longitude);
		super.setPhoneNumber(phoneNumber);
		this.whatsNo = whatsNo;
		super.setEmail(email);
		super.setAddress(address);
		super.setDomesticRanking(domesticRanking);
		super.setTagLine(tagLine);
	}
}