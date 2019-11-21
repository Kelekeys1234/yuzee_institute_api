package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class CourseDTOElasticSearch implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3521506877760833299L;
	
	private BigInteger id;
    private Integer cId;
    private String name;
    private Integer courseRanking;
	private Integer stars;
    private String recognition;
    private String recognitionType;
    private Double duration;
    private String durationTime;
    private String website;
    private String language;
    private String abbreviation;
    private Date recDate;
    private String remarks;
    private String description;
    private Boolean isActive;
    private Date createdOn;
    private Date updatedOn;
    private Date deletedOn;
    private String createdBy;
    private String updatedBy;
    private Boolean isDeleted;

    private String facultyName;
    private String instituteName;
    private String countryName;
    private String cityName;
    private String levelCode;
    private String levelName;

    // Course Details fields

    private String availbilty;
    private String partFull;
    private String studyMode;
    private Double internationalFee;
    private Double domesticFee;
    private String currency;
    private String currencyTime;
    private Double usdInternationFee;
    private Double usdDomasticFee;
    private Double costRange;
    private String content;
    private String facultyDescription;
    private List<String> instituteImageUrl;
    private String instituteLogoUrl;
    private String latitute;
    private String longitude;
    

    public String getLatitute() {
		return latitute;
	}

	public void setLatitute(String latitute) {
		this.latitute = latitute;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public List<String> getInstituteImageUrl() {
		return instituteImageUrl;
	}

	public void setInstituteImageUrl(List<String> instituteImageUrl) {
		this.instituteImageUrl = instituteImageUrl;
	}

	public String getInstituteLogoUrl() {
		return instituteLogoUrl;
	}

	public void setInstituteLogoUrl(String instituteLogoUrl) {
		this.instituteLogoUrl = instituteLogoUrl;
	}

	/**
     * @return the id
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
    /**
     * @return the cId
     */
    public Integer getcId() {
        return cId;
    }

    /**
     * @param cId
     *            the cId to set
     */
    public void setcId(Integer cId) {
        this.cId = cId;
    }

   /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return the stars
     */
    public Integer getStars() {
        return stars;
    }

    /**
     * @param stars
     *            the stars to set
     */
    public void setStars(Integer stars) {
        this.stars = stars;
    }

    /**
     * @return the recognition
     */
    public String getRecognition() {
        return recognition;
    }

    /**
     * @param recognition
     *            the recognition to set
     */
    public void setRecognition(String recognition) {
        this.recognition = recognition;
    }

    /**
     * @return the recognitionType
     */
    public String getRecognitionType() {
        return recognitionType;
    }

    /**
     * @param recognitionType
     *            the recognitionType to set
     */
    public void setRecognitionType(String recognitionType) {
        this.recognitionType = recognitionType;
    }

    public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	/**
     * @return the durationTime
     */
    public String getDurationTime() {
        return durationTime;
    }

    /**
     * @param durationTime
     *            the durationTime to set
     */
    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    /**
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website
     *            the website to set
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language
     *            the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the abbreviation
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * @param abbreviation
     *            the abbreviation to set
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * @return the recDate
     */
    public Date getRecDate() {
        return recDate;
    }

    /**
     * @param recDate
     *            the recDate to set
     */
    public void setRecDate(Date recDate) {
        this.recDate = recDate;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks
     *            the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the isActive
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * @param isActive
     *            the isActive to set
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return the createdOn
     */
    public Date getCreatedOn() {
        return createdOn;
    }

    /**
     * @param createdOn
     *            the createdOn to set
     */
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * @return the updatedOn
     */
    public Date getUpdatedOn() {
        return updatedOn;
    }

    /**
     * @param updatedOn
     *            the updatedOn to set
     */
    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    /**
     * @return the deletedOn
     */
    public Date getDeletedOn() {
        return deletedOn;
    }

    /**
     * @param deletedOn
     *            the deletedOn to set
     */
    public void setDeletedOn(Date deletedOn) {
        this.deletedOn = deletedOn;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     *            the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy
     *            the updatedBy to set
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the isDeleted
     */
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted
     *            the isDeleted to set
     */
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return the facultyName
     */
    public String getFacultyName() {
        return facultyName;
    }

    /**
     * @param facultyName
     *            the facultyName to set
     */
    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    /**
     * @return the instituteName
     */
    public String getInstituteName() {
        return instituteName;
    }

    /**
     * @param instituteName
     *            the instituteName to set
     */
    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    /**
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName
     *            the countryName to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return the cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName
     *            the cityName to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * @return the levelCode
     */
    public String getLevelCode() {
        return levelCode;
    }

    /**
     * @param levelCode
     *            the levelCode to set
     */
    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    /**
     * @return the availbilty
     */
    public String getAvailbilty() {
        return availbilty;
    }

    /**
     * @param availbilty
     *            the availbilty to set
     */
    public void setAvailbilty(String availbilty) {
        this.availbilty = availbilty;
    }

    /**
     * @return the partFull
     */
    public String getPartFull() {
        return partFull;
    }

    /**
     * @param partFull
     *            the partFull to set
     */
    public void setPartFull(String partFull) {
        this.partFull = partFull;
    }

    /**
     * @return the studyMode
     */
    public String getStudyMode() {
        return studyMode;
    }

    /**
     * @param studyMode
     *            the studyMode to set
     */
    public void setStudyMode(String studyMode) {
        this.studyMode = studyMode;
    }

    public Integer getCourseRanking() {
		return courseRanking;
	}

	public void setCourseRanking(Integer courseRanking) {
		this.courseRanking = courseRanking;
	}
	
    public Double getInternationalFee() {
		return internationalFee;
	}

	public void setInternationalFee(Double internationalFee) {
		this.internationalFee = internationalFee;
	}

	public Double getDomesticFee() {
		return domesticFee;
	}

	public void setDomesticFee(Double domesticFee) {
		this.domesticFee = domesticFee;
	}

	/**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency
     *            the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return the currencyTime
     */
    public String getCurrencyTime() {
        return currencyTime;
    }

    /**
     * @param currencyTime
     *            the currencyTime to set
     */
    public void setCurrencyTime(String currencyTime) {
        this.currencyTime = currencyTime;
    }

    /**
     * @return the usdInternationFee
     */
    public Double getUsdInternationFee() {
        return usdInternationFee;
    }

    /**
     * @param usdInternationFee
     *            the usdInternationFee to set
     */
    public void setUsdInternationFee(Double usdInternationFee) {
        this.usdInternationFee = usdInternationFee;
    }

    /**
     * @return the usdDomasticFee
     */
    public Double getUsdDomasticFee() {
        return usdDomasticFee;
    }

    /**
     * @param usdDomasticFee
     *            the usdDomasticFee to set
     */
    public void setUsdDomasticFee(Double usdDomasticFee) {
        this.usdDomasticFee = usdDomasticFee;
    }

    /**
     * @return the costRange
     */
    public Double getCostRange() {
        return costRange;
    }

    /**
     * @param costRange
     *            the costRange to set
     */
    public void setCostRange(Double costRange) {
        this.costRange = costRange;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }
    
	public String getFacultyDescription() {
		return facultyDescription;
	}

	public void setFacultyDescription(String facultyDescription) {
		this.facultyDescription = facultyDescription;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CourseDTOElasticSearch [id=").append(id).append(", cId=").append(cId).append(", name=")
				.append(name).append(", courseRanking=").append(courseRanking).append(", stars=").append(stars)
				.append(", recognition=").append(recognition).append(", recognitionType=").append(recognitionType)
				.append(", duration=").append(duration).append(", durationTime=").append(durationTime)
				.append(", website=").append(website).append(", language=").append(language).append(", abbreviation=")
				.append(abbreviation).append(", recDate=").append(recDate).append(", remarks=").append(remarks)
				.append(", description=").append(description).append(", isActive=").append(isActive)
				.append(", createdOn=").append(createdOn).append(", updatedOn=").append(updatedOn)
				.append(", deletedOn=").append(deletedOn).append(", createdBy=").append(createdBy)
				.append(", updatedBy=").append(updatedBy).append(", isDeleted=").append(isDeleted)
				.append(", facultyName=").append(facultyName).append(", instituteName=").append(instituteName)
				.append(", countryName=").append(countryName).append(", cityName=").append(cityName)
				.append(", levelCode=").append(levelCode).append(", levelName=").append(levelName)
				.append(", availbilty=").append(availbilty).append(", partFull=").append(partFull)
				.append(", studyMode=").append(studyMode).append(", internationalFee=").append(internationalFee)
				.append(", domesticFee=").append(domesticFee).append(", currency=").append(currency)
				.append(", currencyTime=").append(currencyTime).append(", usdInternationFee=").append(usdInternationFee)
				.append(", usdDomasticFee=").append(usdDomasticFee).append(", costRange=").append(costRange)
				.append(", content=").append(content).append(", facultyDescription=").append(facultyDescription)
				.append(", instituteImageUrl=").append(instituteImageUrl).append(", instituteLogoUrl=")
				.append(instituteLogoUrl).append(", latitute=").append(latitute).append(", longitude=")
				.append(longitude).append("]");
		return builder.toString();
	}

}
