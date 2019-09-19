package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.CourseEnglishEligibility;

public class CourseRequest {

	private BigInteger id;
	private Integer cId;
	private BigInteger instituteId;
	private BigInteger cityId;
	private BigInteger countryId;
	private BigInteger facultyId;
	private String name;
	private String description;
	private String duration;
	private String intake;
	private String language;
	private Double domasticFee;
	private Double internationalFee;
	private String grades;
	private String partTime;
	private String fullTime;
	private String documentUrl;
	private String contact;
	private String openingHourFrom;
	private String openingHourTo;
	private String campusLocation;
	private String website;
	private String link;
	private String lastUpdated;
	private String instituteName;
	private String location;
	private String instituteLogoUrl;
	private String worldRanking;
	private String stars;
	private String cost;
	private String durationTime;
	private String totalCount;
	private String requirements;
	private String currency;
	private String facultyName;
	private String studyMode;
	private String partFull;
	private BigInteger levelId;
	private String levelName;
	private String availbility;
	private List<CourseEnglishEligibility> englishEligibility;
	private List<ImageResponseDto> instituteImages;
	

	/**
	 * @return the cId
	 */
	public Integer getcId() {
		return cId;
	}

	/**
	 * @param cId the cId to set
	 */
	public void setcId(final Integer cId) {
		this.cId = cId;
	}

	/**
	 * @return the instituteId
	 */
	public BigInteger getInstituteId() {
		return instituteId;
	}

	/**
	 * @param instituteId the instituteId to set
	 */
	public void setInstituteId(final BigInteger instituteId) {
		this.instituteId = instituteId;
	}

	/**
	 * @return the cityId
	 */
	public BigInteger getCityId() {
		return cityId;
	}

	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(final BigInteger cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the facultyId
	 */
	public BigInteger getFacultyId() {
		return facultyId;
	}

	/**
	 * @param facultyId the facultyId to set
	 */
	public void setFacultyId(final BigInteger facultyId) {
		this.facultyId = facultyId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(final String duration) {
		this.duration = duration;
	}

	/**
	 * @return the intake
	 */
	public String getIntake() {
		return intake;
	}

	/**
	 * @param intake the intake to set
	 */
	public void setIntake(final String intake) {
		this.intake = intake;
	}

    /**
	 * @return the domasticFee
	 */
	public Double getDomasticFee() {
		return domasticFee;
	}

	/**
	 * @param domasticFee the domasticFee to set
	 */
	public void setDomasticFee(final Double domasticFee) {
		this.domasticFee = domasticFee;
	}

	/**
	 * @return the internationalFee
	 */
	public Double getInternationalFee() {
		return internationalFee;
	}

	/**
	 * @param internationalFee the internationalFee to set
	 */
	public void setInternationalFee(final Double internationalFee) {
		this.internationalFee = internationalFee;
	}

	/**
	 * @return the grades
	 */
	public String getGrades() {
		return grades;
	}

	/**
	 * @param grades the grades to set
	 */
	public void setGrades(final String grades) {
		this.grades = grades;
	}

	

	public List<CourseEnglishEligibility> getEnglishEligibility() {
        return englishEligibility;
    }

    public void setEnglishEligibility(List<CourseEnglishEligibility> englishEligibility) {
        this.englishEligibility = englishEligibility;
    }

    /**
	 * @return the partTime
	 */
	public String getPartTime() {
		return partTime;
	}

	/**
	 * @param partTime the partTime to set
	 */
	public void setPartTime(final String partTime) {
		this.partTime = partTime;
	}

	/**
	 * @return the fullTime
	 */
	public String getFullTime() {
		return fullTime;
	}

	/**
	 * @param fullTime the fullTime to set
	 */
	public void setFullTime(final String fullTime) {
		this.fullTime = fullTime;
	}

	/**
	 * @return the documentUrl
	 */
	public String getDocumentUrl() {
		return documentUrl;
	}

	/**
	 * @param documentUrl the documentUrl to set
	 */
	public void setDocumentUrl(final String documentUrl) {
		this.documentUrl = documentUrl;
	}

	/**
	 * @return the openingHourFrom
	 */
	public String getOpeningHourFrom() {
		return openingHourFrom;
	}

	/**
	 * @param openingHourFrom the openingHourFrom to set
	 */
	public void setOpeningHourFrom(final String openingHourFrom) {
		this.openingHourFrom = openingHourFrom;
	}

	/**
	 * @return the openingHourTo
	 */
	public String getOpeningHourTo() {
		return openingHourTo;
	}

	/**
	 * @param openingHourTo the openingHourTo to set
	 */
	public void setOpeningHourTo(final String openingHourTo) {
		this.openingHourTo = openingHourTo;
	}

	/**
	 * @return the campusLocation
	 */
	public String getCampusLocation() {
		return campusLocation;
	}

	/**
	 * @param campusLocation the campusLocation to set
	 */
	public void setCampusLocation(final String campusLocation) {
		this.campusLocation = campusLocation;
	}

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param website the website to set
	 */
	public void setWebsite(final String website) {
		this.website = website;
	}

	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(final String contact) {
		this.contact = contact;
	}

	/**
	 * @return the countryId
	 */
	public BigInteger getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(final BigInteger countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return the lastUpdated
	 */
	public String getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * @param lastUpdated the lastUpdated to set
	 */
	public void setLastUpdated(final String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	/**
	 * @return the instituteName
	 */
	public String getInstituteName() {
		return instituteName;
	}

	/**
	 * @param instituteName the instituteName to set
	 */
	public void setInstituteName(final String instituteName) {
		this.instituteName = instituteName;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(final String location) {
		this.location = location;
	}

	/**
	 * @return the worldRanking
	 */
	public String getWorldRanking() {
		return worldRanking;
	}

	/**
	 * @param worldRanking the worldRanking to set
	 */
	public void setWorldRanking(final String worldRanking) {
		this.worldRanking = worldRanking;
	}

	/**
	 * @return the stars
	 */
	public String getStars() {
		return stars;
	}

	/**
	 * @param stars the stars to set
	 */
	public void setStars(final String stars) {
		this.stars = stars;
	}

	/**
	 * @return the cost
	 */
	public String getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(final String cost) {
		this.cost = cost;
	}

	/**
	 * @return the durationTime
	 */
	public String getDurationTime() {
		return durationTime;
	}

	/**
	 * @param durationTime the durationTime to set
	 */
	public void setDurationTime(final String durationTime) {
		this.durationTime = durationTime;
	}

	/**
	 * @return the totalCount
	 */
	public String getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(final String totalCount) {
		this.totalCount = totalCount;
	}
	
	/**
	 * @return the requirements
	 */
	public String getRequirements() {
		return requirements;
	}

	/**
	 * @param requirements the requirements to set
	 */
	public void setRequirements(final String requirements) {
		this.requirements = requirements;
	}

    /**
     * @return the facultyName
     */
    public String getFacultyName() {
        return facultyName;
    }

    /**
     * @param facultyName the facultyName to set
     */
    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    /**
     * @return the studyMode
     */
    public String getStudyMode() {
        return studyMode;
    }

    /**
     * @param studyMode the studyMode to set
     */
    public void setStudyMode(String studyMode) {
        this.studyMode = studyMode;
    }

    /**
     * @return the partFull
     */
    public String getPartFull() {
        return partFull;
    }

    /**
     * @param partFull the partFull to set
     */
    public void setPartFull(String partFull) {
        this.partFull = partFull;
    }

    /**
     * @return the levelId
     */
    public BigInteger getLevelId() {
        return levelId;
    }

    /**
     * @param levelId the levelId to set
     */
    public void setLevelId(BigInteger levelId) {
        this.levelId = levelId;
    }

    /**
     * @return the levelName
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * @param levelName the levelName to set
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    /**
     * @return the availbility
     */
    public String getAvailbility() {
        return availbility;
    }

    /**
     * @param availbility the availbility to set
     */
    public void setAvailbility(String availbility) {
        this.availbility = availbility;
    }

    /**
     * @return the instituteImages
     */
    public List<ImageResponseDto> getInstituteImages() {
        return instituteImages;
    }

    /**
     * @param instituteImages the instituteImages to set
     */
    public void setInstituteImages(List<ImageResponseDto> instituteImages) {
        this.instituteImages = instituteImages;
    }

    /**
     * @return the instituteLogoUrl
     */
    public String getInstituteLogoUrl() {
        return instituteLogoUrl;
    }

    /**
     * @param instituteLogoUrl the instituteLogoUrl to set
     */
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
     * @param id the id to set
     */
    public void setId(BigInteger id) {
        this.id = id;
    }


    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }
}
