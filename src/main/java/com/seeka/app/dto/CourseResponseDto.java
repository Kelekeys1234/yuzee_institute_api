package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class CourseResponseDto {

	private BigInteger id;
	private String name;
	private Integer courseRanking;
	private Integer stars;
	private Double duration;
	private String durationTime;

	private String language;

	private String languageShortKey;
	private BigInteger instituteId;
	private String instituteName;

	private Double costRange;

	private String location;
	private BigInteger countryId;
	private BigInteger cityId;
	private Integer totalCount;
	private Double domesticFee;
	private Double internationalFee;
	private String requirements;
	private String countryName;
	private String cityName;
	private Boolean isFavourite;
	private String currencyCode;
	private List<StorageDto> storageList;
	private Boolean isViewed = false;
	private String cost;
	private Boolean isActive;
	private Date updatedOn;
	private List<Date> intake;
	private List<String> deliveryMethod;

	private String facultyName;
	private BigInteger facultyId;

	private Double distance;

	private Double latitude;
	private Double longitude;

	public Double getDistance() {
		return distance;
	}

	public void setDistance(final Double distance) {
		this.distance = distance;
	}

	public String getFacultyName() {
		return facultyName;
	}

	public void setFacultyName(final String facultyName) {
		this.facultyName = facultyName;
	}

	public BigInteger getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(final BigInteger facultyId) {
		this.facultyId = facultyId;
	}

	public BigInteger getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(final BigInteger instituteId) {
		this.instituteId = instituteId;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(final String instituteName) {
		this.instituteName = instituteName;
	}

	public Integer getCourseRanking() {
		return courseRanking;
	}

	public void setCourseRanking(final Integer courseRanking) {
		this.courseRanking = courseRanking;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(final Integer stars) {
		this.stars = stars;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(final Double duration) {
		this.duration = duration;
	}

	public String getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(final String durationTime) {
		this.durationTime = durationTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(final String location) {
		this.location = location;
	}

	public BigInteger getCountryId() {
		return countryId;
	}

	public void setCountryId(final BigInteger countryId) {
		this.countryId = countryId;
	}

	public BigInteger getCityId() {
		return cityId;
	}

	public void setCityId(final BigInteger cityId) {
		this.cityId = cityId;
	}

	public String getLanguageShortKey() {
		return languageShortKey;
	}

	public void setLanguageShortKey(final String languageShortKey) {
		this.languageShortKey = languageShortKey;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(final Integer totalCount) {
		this.totalCount = totalCount;
	}

	public String getRequirements() {
		if (null == requirements || requirements.isEmpty() || requirements.contains("0.0") || requirements.contains("0.00")) {
			requirements = "No Requirements";
		}
		return requirements;
	}

	public void setRequirements(final String requirements) {
		this.requirements = requirements;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(final String countryName) {
		this.countryName = countryName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(final String cityName) {
		this.cityName = cityName;
	}

	public Boolean getIsFavourite() {
		if (null == isFavourite) {
			isFavourite = false;
		}
		return isFavourite;
	}

	public void setIsFavourite(final Boolean isFavourite) {
		this.isFavourite = isFavourite;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(final String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public List<StorageDto> getStorageList() {
		return storageList;
	}

	public void setStorageList(final List<StorageDto> storageList) {
		this.storageList = storageList;
	}

	/**
	 * @return the domasticFee
	 */
	public Double getDomesticFee() {
		return domesticFee;
	}

	/**
	 * @param domasticFee the domasticFee to set
	 */
	public void setDomesticFee(final Double domasticFee) {
		this.domesticFee = domasticFee;
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
	 * @return the id
	 */
	public BigInteger getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final BigInteger id) {
		this.id = id;
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
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(final String language) {
		this.language = language;
	}

	/**
	 *
	 * @return gives details if course is watched before by user
	 */
	public Boolean getIsViewed() {
		return isViewed;
	}

	public void setIsViewed(final Boolean isViewed) {
		this.isViewed = isViewed;
	}

	public Double getCostRange() {
		return costRange;
	}

	public void setCostRange(final Double costRange) {
		this.costRange = costRange;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(final String cost) {
		this.cost = cost;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(final Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(final Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public List<Date> getIntake() {
		return intake;
	}

	public void setIntake(final List<Date> intake) {
		this.intake = intake;
	}

	public List<String> getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(final List<String> deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CourseResponseDto [id=").append(id).append(", name=").append(name).append(", courseRanking=").append(courseRanking).append(", stars=")
				.append(stars).append(", duration=").append(duration).append(", durationTime=").append(durationTime).append(", language=").append(language)
				.append(", languageShortKey=").append(languageShortKey).append(", instituteId=").append(instituteId).append(", instituteName=")
				.append(instituteName).append(", costRange=").append(costRange).append(", location=").append(location).append(", countryId=").append(countryId)
				.append(", cityId=").append(cityId).append(", totalCount=").append(totalCount).append(", domesticFee=").append(domesticFee)
				.append(", internationalFee=").append(internationalFee).append(", requirements=").append(requirements).append(", countryName=")
				.append(countryName).append(", cityName=").append(cityName).append(", isFavourite=").append(isFavourite).append(", currencyCode=")
				.append(currencyCode).append(", storageList=").append(storageList).append(", isViewed=").append(isViewed).append(", cost=").append(cost)
				.append(", isActive=").append(isActive).append(", updatedOn=").append(updatedOn).append(", intake=").append(intake).append(", deliveryMethod=")
				.append(deliveryMethod).append(", facultyName=").append(facultyName).append(", facultyId=").append(facultyId).append(", distance=")
				.append(distance).append(", latitude=").append(latitude).append(", longitude=").append(longitude).append("]");
		return builder.toString();
	}

}
