package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

public class CourseResponseDto {

	private BigInteger id;
	private String name;
	private String language;
	private String languageShortKey;
	private BigInteger instituteId;
	private String instituteName;
	private Integer worldRanking;
	private Integer stars;
	private Double costRange;
	private Double duration;
	private String durationTime;
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

	public Integer getWorldRanking() {
		return worldRanking;
	}

	public void setWorldRanking(final Integer worldRanking) {
		this.worldRanking = worldRanking;
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

}
