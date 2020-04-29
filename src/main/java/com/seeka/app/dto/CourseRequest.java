package com.seeka.app.dto;

import java.util.Date;
import java.util.List;

import com.seeka.app.bean.CourseEnglishEligibility;

public class CourseRequest {

	private String id;
	private String instituteId;
    private String cityId;
    private String countryId;
	private String facultyId;
	private String name;
	private String description;
	private String duration;
	private List<Date> intake;
	private List<String> deliveryMethod;
	private List<String> language;
	private Double domasticFee;
	private Double internationalFee;
	private String grades;
	private String documentUrl;
	private String contact;
	private String openingHourFrom;
	private String openingHourTo;
	private String jobFullTime;
	private String jobPartTime;
	private String campusLocation;
	private String website;
	private String link;
	private String lastUpdated;
	private String instituteName;
	private String location;
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
	private String levelId;
	private String levelName;
	private String availbility;
	private List<CourseEnglishEligibility> englishEligibility;
	private List<StorageDto> storageList;

	private Boolean applied;
	private Boolean viewCourse;
	private Double latitude;
	private Double longitude;
	private List<UserReviewResultDto> userReviewResult;
	
	private String countryName;
	private String cityName;
	private StudentVisaDto studentVisa;

	public List<UserReviewResultDto> getUserReviewResult() {
		return userReviewResult;
	}

	public void setUserReviewResult(final List<UserReviewResultDto> userReviewResult) {
		this.userReviewResult = userReviewResult;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(final Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(final Double longitude) {
		this.longitude = longitude;
	}

	public Boolean getViewCourse() {
		return viewCourse;
	}

	public void setViewCourse(final Boolean viewCourse) {
		this.viewCourse = viewCourse;
	}

	/**
	 *
	 * @return
	 */
	public Boolean getApplied() {
		return applied;
	}

	/**
	 *
	 * @param applied
	 */
	public void setApplied(final Boolean applied) {
		this.applied = applied;
	}

	/**
	 * @return the instituteId
	 */
	public String getInstituteId() {
		return instituteId;
	}

	/**
	 * @param instituteId the instituteId to set
	 */
	public void setInstituteId(final String instituteId) {
		this.instituteId = instituteId;
	}

    /**
     * @return the cityId
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(final String cityId) {
        this.cityId = cityId;
    }

    /**
     * @return the countryId
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     * @param countryId the countryId to set
     */
    public void setCountryId(final String countryId) {
        this.countryId = countryId;
    }

	/**
	 * @return the facultyId
	 */
	public String getFacultyId() {
		return facultyId;
	}

	/**
	 * @param facultyId the facultyId to set
	 */
	public void setFacultyId(final String facultyId) {
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
	public List<Date> getIntake() {
		return intake;
	}

	/**
	 * @param intake the intake to set
	 */
	public void setIntake(final List<Date> intake) {
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

	public void setEnglishEligibility(final List<CourseEnglishEligibility> englishEligibility) {
		this.englishEligibility = englishEligibility;
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
	public void setFacultyName(final String facultyName) {
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
	public void setStudyMode(final String studyMode) {
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
	public void setPartFull(final String partFull) {
		this.partFull = partFull;
	}

	/**
	 * @return the levelId
	 */
	public String getLevelId() {
		return levelId;
	}

	/**
	 * @param levelId the levelId to set
	 */
	public void setLevelId(final String levelId) {
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
	public void setLevelName(final String levelName) {
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
	public void setAvailbility(final String availbility) {
		this.availbility = availbility;
	}

	public List<StorageDto> getStorageList() {
		return storageList;
	}

	public void setStorageList(final List<StorageDto> storageList) {
		this.storageList = storageList;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
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
	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	/**
	 * @return the language
	 */
	public List<String> getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(final List<String> language) {
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
	public void setLink(final String link) {
		this.link = link;
	}

	public List<String> getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(final List<String> deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	public String getJobFullTime() {
		return jobFullTime;
	}

	public void setJobFullTime(final String jobFullTime) {
		this.jobFullTime = jobFullTime;
	}

	public String getJobPartTime() {
		return jobPartTime;
	}

	public void setJobPartTime(final String jobPartTime) {
		this.jobPartTime = jobPartTime;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public StudentVisaDto getStudentVisa() {
		return studentVisa;
	}

	public void setStudentVisa(StudentVisaDto studentVisa) {
		this.studentVisa = studentVisa;
	}

}
