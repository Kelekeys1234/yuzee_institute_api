package com.yuzee.app.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@Document(collection = "institute")
//@CompoundIndexes({ @CompoundIndex(name = "UK_UI_T", def = "{'id' : 1}", unique = true) })
@JsonIgnoreProperties(ignoreUnknown = true)
public class Institute implements Serializable {

    @Id
    private UUID id;

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

    private InstituteAdditionalInfo instituteAdditionalInfo;

    private List<InstituteDomesticRankingHistory> instituteDomesticRankingHistories = new ArrayList<>();

    private List<InstituteFacility> instituteFacilities = new ArrayList<>();

    @DBRef
    private List<InstituteService> instituteServices = new ArrayList<>();

    private List<String> instituteIntakes = new ArrayList<>();

    private List<InstituteWorldRankingHistory> instituteWorldRankingHistories = new ArrayList<>();

    private List<String> instituteFundings = new ArrayList<>();

    private List<InstituteProviderCode> instituteProviderCodes = new ArrayList<>();

    private boolean verified;

    public Institute(UUID id, String readableId, String instituteType, String name, Boolean isActive, Date createdOn, Date updatedOn, Date deletedOn, String createdBy, String updatedBy, Boolean isDeleted, Integer worldRanking, String phoneNumber, String email, String website, Double latitude, Double longitude, String address, String description, String countryName, String cityName, String avgCostOfLiving, String tuitionFeesPaymentPlan, String enrolmentLink, String aboutInfo, String courseStart, String whatsNo, InstituteCategoryType instituteCategoryType, String scholarshipFinancingAssistance, Integer domesticRanking, String admissionEmail, String boardingAvailable, String boarding, String state, Integer postalCode, String englishPartners, String imageCount, String climate, String youtubeLink, String internationalPhoneNumber, String domesticPhoneNumber, String accreditation, String link, String contact, String curriculum, Double domesticBoardingFee, Double internationalBoardingFee, String tagLine, Boolean showSuggestion, List<InstituteEnglishRequirements> instituteEnglishRequirements, InstituteAdditionalInfo instituteAdditionalInfo, List<InstituteDomesticRankingHistory> instituteDomesticRankingHistories, List<InstituteFacility> instituteFacilities, List<InstituteService> instituteServices, List<String> instituteIntakes, List<InstituteWorldRankingHistory> instituteWorldRankingHistories, List<String> instituteFundings, List<InstituteProviderCode> instituteProviderCodes, boolean verified) {
        this.id = id;
        this.readableId = readableId;
        this.instituteType = instituteType;
        this.name = name;
        this.isActive = isActive;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.deletedOn = deletedOn;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.isDeleted = isDeleted;
        this.worldRanking = worldRanking;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.website = website;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.description = description;
        this.countryName = countryName;
        this.cityName = cityName;
        this.avgCostOfLiving = avgCostOfLiving;
        this.tuitionFeesPaymentPlan = tuitionFeesPaymentPlan;
        this.enrolmentLink = enrolmentLink;
        this.aboutInfo = aboutInfo;
        this.courseStart = courseStart;
        this.whatsNo = whatsNo;
        this.instituteCategoryType = instituteCategoryType;
        this.scholarshipFinancingAssistance = scholarshipFinancingAssistance;
        this.domesticRanking = domesticRanking;
        this.admissionEmail = admissionEmail;
        this.boardingAvailable = boardingAvailable;
        this.boarding = boarding;
        this.state = state;
        this.postalCode = postalCode;
        this.englishPartners = englishPartners;
        this.imageCount = imageCount;
        this.climate = climate;
        this.youtubeLink = youtubeLink;
        this.internationalPhoneNumber = internationalPhoneNumber;
        this.domesticPhoneNumber = domesticPhoneNumber;
        this.accreditation = accreditation;
        this.link = link;
        this.contact = contact;
        this.curriculum = curriculum;
        this.domesticBoardingFee = domesticBoardingFee;
        this.internationalBoardingFee = internationalBoardingFee;
        this.tagLine = tagLine;
        this.showSuggestion = showSuggestion;
        this.instituteEnglishRequirements = instituteEnglishRequirements;
        this.instituteAdditionalInfo = instituteAdditionalInfo;
        this.instituteDomesticRankingHistories = instituteDomesticRankingHistories;
        this.instituteFacilities = instituteFacilities;
        this.instituteServices = instituteServices;
        this.instituteIntakes = instituteIntakes;
        this.instituteWorldRankingHistories = instituteWorldRankingHistories;
        this.instituteFundings = instituteFundings;
        this.instituteProviderCodes = instituteProviderCodes;
        this.verified = verified;
    }
}