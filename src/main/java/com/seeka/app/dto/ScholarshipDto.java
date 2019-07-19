package com.seeka.app.dto;

import java.math.BigInteger;

import com.seeka.app.bean.Country;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.Level;

public class ScholarshipDto {
	
	private BigInteger id;
    private BigInteger countryId;
    private BigInteger instituteId;
    private BigInteger levelId;
    private String name;
    private String amount;
    private String description;
    private String student;
    private String website;
    
    
    private String scholarshipTitle;
    private String offeredBy;
    private String benefits;
    private String requirements;
    private String eligibility;
    private String intake;
    private String language;
    private String validity;
    private String gender;
    private String applicationDeadline;
    private Double scholarshipAmount;
    private Integer numberOfAvaliability;
    private String headquaters;
    private String email;
    private String address;
    private Boolean isActive;
    private Boolean isDeleted;
    
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public BigInteger getCountryId() {
		return countryId;
	}
	public void setCountryId(BigInteger countryId) {
		this.countryId = countryId;
	}
	public BigInteger getInstituteId() {
		return instituteId;
	}
	public void setInstituteId(BigInteger instituteId) {
		this.instituteId = instituteId;
	}
	public BigInteger getLevelId() {
		return levelId;
	}
	public void setLevelId(BigInteger levelId) {
		this.levelId = levelId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStudent() {
		return student;
	}
	public void setStudent(String student) {
		this.student = student;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getScholarshipTitle() {
		return scholarshipTitle;
	}
	public void setScholarshipTitle(String scholarshipTitle) {
		this.scholarshipTitle = scholarshipTitle;
	}
	public String getOfferedBy() {
		return offeredBy;
	}
	public void setOfferedBy(String offeredBy) {
		this.offeredBy = offeredBy;
	}
	public String getBenefits() {
		return benefits;
	}
	public void setBenefits(String benefits) {
		this.benefits = benefits;
	}
	public String getRequirements() {
		return requirements;
	}
	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}
	public String getEligibility() {
		return eligibility;
	}
	public void setEligibility(String eligibility) {
		this.eligibility = eligibility;
	}
	public String getIntake() {
		return intake;
	}
	public void setIntake(String intake) {
		this.intake = intake;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getApplicationDeadline() {
		return applicationDeadline;
	}
	public void setApplicationDeadline(String applicationDeadline) {
		this.applicationDeadline = applicationDeadline;
	}
	public Double getScholarshipAmount() {
		return scholarshipAmount;
	}
	public void setScholarshipAmount(Double scholarshipAmount) {
		this.scholarshipAmount = scholarshipAmount;
	}
	public Integer getNumberOfAvaliability() {
		return numberOfAvaliability;
	}
	public void setNumberOfAvaliability(Integer numberOfAvaliability) {
		this.numberOfAvaliability = numberOfAvaliability;
	}
	public String getHeadquaters() {
		return headquaters;
	}
	public void setHeadquaters(String headquaters) {
		this.headquaters = headquaters;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

    
    
    
    
}
