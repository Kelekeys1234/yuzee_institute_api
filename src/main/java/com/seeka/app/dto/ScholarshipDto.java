package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class ScholarshipDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2633639341414502096L;
	private BigInteger id;
    private BigInteger countryId;
    private BigInteger instituteId;
    private BigInteger levelId;
    private String description;
    private String student;
    private String website;
    private String scholarshipTitle;
    private String benefits;
    private String requirements;
    private String eligibility;
    private String intake;
    private String language;
    private String validity;
    private String gender;
    private String applicationDeadline;
    private String scholarshipAmount;
    private Integer numberOfAvaliability;
    private String headquaters;
    private String email;
    private String address;
    private Boolean isActive;
    private Boolean isDeleted;
    private String coverage;
    private String type;
    private BigInteger offeredByInstitute;
    private BigInteger offeredByCourse;
    private String award;
    private String howToApply;
    private String offeredByInstituteName;
    private String offerByCourseName;
    private String instituteName;
    private String countryName;
    private String levelName;
    private String name;
    private String amount;
    
	public String getOfferedByInstituteName() {
		return offeredByInstituteName;
	}
	public void setOfferedByInstituteName(String offeredByInstituteName) {
		this.offeredByInstituteName = offeredByInstituteName;
	}
	public String getOfferByCourseName() {
		return offerByCourseName;
	}
	public void setOfferByCourseName(String offerByCourseName) {
		this.offerByCourseName = offerByCourseName;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
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
	public String getScholarshipAmount() {
		return scholarshipAmount;
	}
	public void setScholarshipAmount(String scholarshipAmount) {
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
    /**
     * @return the coverage
     */
    public String getCoverage() {
        return coverage;
    }
    /**
     * @param coverage the coverage to set
     */
    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * @return the offeredByInstitute
     */
    public BigInteger getOfferedByInstitute() {
        return offeredByInstitute;
    }
    /**
     * @param offeredByInstitute the offeredByInstitute to set
     */
    public void setOfferedByInstitute(BigInteger offeredByInstitute) {
        this.offeredByInstitute = offeredByInstitute;
    }
    /**
     * @return the offeredByCourse
     */
    public BigInteger getOfferedByCourse() {
        return offeredByCourse;
    }
    /**
     * @param offeredByCourse the offeredByCourse to set
     */
    public void setOfferedByCourse(BigInteger offeredByCourse) {
        this.offeredByCourse = offeredByCourse;
    }
    /**
     * @return the award
     */
    public String getAward() {
        return award;
    }
    /**
     * @param award the award to set
     */
    public void setAward(String award) {
        this.award = award;
    }
    /**
     * @return the howToApply
     */
    public String getHowToApply() {
        return howToApply;
    }
    /**
     * @param howToApply the howToApply to set
     */
    public void setHowToApply(String howToApply) {
        this.howToApply = howToApply;
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

    
    
    
    
}
