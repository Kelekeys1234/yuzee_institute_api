package com.seeka.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class InstituteRequestDto {
	private String id;
	private String name;
	private String description;
	private String cityName;
	private String countryName;
	private Integer worldRanking;
	private String logoUrl;
	private String avgCostOfLiving;
	private String instituteType;
	private String enrolmentLink;
	private String tuitionFessPaymentPlan;
	private String scholarshipFinancingAssistance;
	private String website;
	private String instituteCategoryTypeId;
	private String campusType;
	private String campusName;
	private Double latitude;
	private Double longitude;
	private Integer totalStudent;
	private String openingFrom;
	private String openingTo;
	private String email;
	private String phoneNumber;
	private String address;
	private List<String> offerService;
	private List<String> offerServiceName;
	private List<String> accreditation;
	private List<AccrediatedDetailDto> accreditationDetails;
	private List<String> intakes;
	private String createdBy;
	private String updatedBy;
	private String whatsNo;
	private Integer domesticRanking;
	/**
	 * There is no use of below fields in Admin panel.
	 */
	private String aboutInfo;
	private String courseStart;

	private List<String> facultyIds;
	private List<String> facultyNames;
	private List<String> levelIds;
	private List<String> levelNames;
}
