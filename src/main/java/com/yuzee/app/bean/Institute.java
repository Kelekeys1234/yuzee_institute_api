package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "newInstitute")
@CompoundIndexes({ @CompoundIndex(name = "UK_UI_T", def = "{'id' : 1}", unique = true) })
@JsonIgnoreProperties(ignoreUnknown = true)
public class Institute implements Serializable {

	@Id
	private String id;

	private String readableId;

	private String instituteType;

	@Indexed(unique = true)
	private String name;

	private Boolean isActive;

	private Date createdOn;

	private Date updatedOn;

	private Date deletedOn;

	private String createdBy;

	private String updatedBy;

	private Boolean isDeleted;

	private Integer worldRanking;

	private String phoneNumber;

	private String email;

	private String website;

	private Double latitude;

	private Double longitude;

	private String address;

	private String description;

	@Indexed(unique = true)
	private String countryName;

	@Indexed(unique = true)
	private String cityName;

	private String avgCostOfLiving;

	private String tuitionFeesPaymentPlan;
	private String enrolmentLink;

	private String aboutInfo;

	private String courseStart;

	private String whatsNo;

	private InstituteCategoryType instituteCategoryType;

	private String scholarshipFinancingAssistance;

	private Integer domesticRanking;

	private String admissionEmail;

	private String boardingAvailable;

	private String boarding;

	private String state;

	private Integer postalCode;

	private String englishPartners;

	private String imageCount;

	private String climate;

	private String youtubeLink;

	private String internationalPhoneNumber;

	private String domesticPhoneNumber;

	private String accreditation;

	private String officeHoursFrom;

	private String officeHoursTo;

	private String link;

	private String contact;

	private String curriculum;

	private Double domesticBoardingFee;

	private Double internationalBoardingFee;

	private String tagLine;

	private Boolean showSuggestion;

	@DBRef
	private List<InstituteEnglishRequirements> instituteEnglishRequirements = new ArrayList<>();

	@DBRef
	private List<InstituteCampus> instituteCampuses = new ArrayList<>();
	@DBRef
	private InstituteAdditionalInfo instituteAdditionalInfo;
	@DBRef
	private List<InstituteDomesticRankingHistory> instituteDomesticRankingHistories = new ArrayList<>();
	@DBRef
	private List<InstituteFacility> instituteFacilities = new ArrayList<>();

	@DBRef
	private List<InstituteService> instituteServices = new ArrayList<>();
	@DBRef
	private List<String> instituteIntakes = new ArrayList<>();
	@DBRef
	private List<InstituteWorldRankingHistory> instituteWorldRankingHistories = new ArrayList<>();
	@DBRef
	private List<String> instituteFundings = new ArrayList<>();
	@DBRef
	private List<InstituteProviderCode> instituteProviderCodes = new ArrayList<>();

	private boolean verified;

}