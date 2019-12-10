package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class CourseDTOElasticSearch implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3521506877760833299L;

	private BigInteger id;
	private Integer cId;
	private String name;
	private Integer worldRanking;
	private Integer stars;
	private String recognition;
	private String recognitionType;
	private Double duration;
	private String durationTime;
	private String website;
	private List<String> language;
	private String abbreviation;
	private String remarks;
	private String description;

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

	private String openingHourFrom;
	private String openingHourTo;
	private List<Date> intake;
	private List<String> deliveryMethod;

	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
		this.id = id;
	}

	public Integer getcId() {
		return cId;
	}

	public void setcId(final Integer cId) {
		this.cId = cId;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
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

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(final String recognition) {
		this.recognition = recognition;
	}

	public String getRecognitionType() {
		return recognitionType;
	}

	public void setRecognitionType(final String recognitionType) {
		this.recognitionType = recognitionType;
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

	public String getWebsite() {
		return website;
	}

	public void setWebsite(final String website) {
		this.website = website;
	}

	public List<String> getLanguage() {
		return language;
	}

	public void setLanguage(final List<String> language) {
		this.language = language;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(final String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(final String remarks) {
		this.remarks = remarks;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getFacultyName() {
		return facultyName;
	}

	public void setFacultyName(final String facultyName) {
		this.facultyName = facultyName;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(final String instituteName) {
		this.instituteName = instituteName;
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

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(final String levelCode) {
		this.levelCode = levelCode;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(final String levelName) {
		this.levelName = levelName;
	}

	public String getAvailbilty() {
		return availbilty;
	}

	public void setAvailbilty(final String availbilty) {
		this.availbilty = availbilty;
	}

	public String getPartFull() {
		return partFull;
	}

	public void setPartFull(final String partFull) {
		this.partFull = partFull;
	}

	public String getStudyMode() {
		return studyMode;
	}

	public void setStudyMode(final String studyMode) {
		this.studyMode = studyMode;
	}

	public Double getInternationalFee() {
		return internationalFee;
	}

	public void setInternationalFee(final Double internationalFee) {
		this.internationalFee = internationalFee;
	}

	public Double getDomesticFee() {
		return domesticFee;
	}

	public void setDomesticFee(final Double domesticFee) {
		this.domesticFee = domesticFee;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	public String getCurrencyTime() {
		return currencyTime;
	}

	public void setCurrencyTime(final String currencyTime) {
		this.currencyTime = currencyTime;
	}

	public Double getUsdInternationFee() {
		return usdInternationFee;
	}

	public void setUsdInternationFee(final Double usdInternationFee) {
		this.usdInternationFee = usdInternationFee;
	}

	public Double getUsdDomasticFee() {
		return usdDomasticFee;
	}

	public void setUsdDomasticFee(final Double usdDomasticFee) {
		this.usdDomasticFee = usdDomasticFee;
	}

	public Double getCostRange() {
		return costRange;
	}

	public void setCostRange(final Double costRange) {
		this.costRange = costRange;
	}

	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public String getFacultyDescription() {
		return facultyDescription;
	}

	public void setFacultyDescription(final String facultyDescription) {
		this.facultyDescription = facultyDescription;
	}

	public List<String> getInstituteImageUrl() {
		return instituteImageUrl;
	}

	public void setInstituteImageUrl(final List<String> instituteImageUrl) {
		this.instituteImageUrl = instituteImageUrl;
	}

	public String getInstituteLogoUrl() {
		return instituteLogoUrl;
	}

	public void setInstituteLogoUrl(final String instituteLogoUrl) {
		this.instituteLogoUrl = instituteLogoUrl;
	}

	public String getLatitute() {
		return latitute;
	}

	public void setLatitute(final String latitute) {
		this.latitute = latitute;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(final String longitude) {
		this.longitude = longitude;
	}

	public String getOpeningHourFrom() {
		return openingHourFrom;
	}

	public void setOpeningHourFrom(final String openingHourFrom) {
		this.openingHourFrom = openingHourFrom;
	}

	public String getOpeningHourTo() {
		return openingHourTo;
	}

	public void setOpeningHourTo(final String openingHourTo) {
		this.openingHourTo = openingHourTo;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CourseDTOElasticSearch [id=").append(id).append(", cId=").append(cId).append(", name=").append(name).append(", worldRanking=")
				.append(worldRanking).append(", stars=").append(stars).append(", recognition=").append(recognition).append(", recognitionType=")
				.append(recognitionType).append(", duration=").append(duration).append(", durationTime=").append(durationTime).append(", website=")
				.append(website).append(", language=").append(language).append(", abbreviation=").append(abbreviation).append(", remarks=").append(remarks)
				.append(", description=").append(description).append(", facultyName=").append(facultyName).append(", instituteName=").append(instituteName)
				.append(", countryName=").append(countryName).append(", cityName=").append(cityName).append(", levelCode=").append(levelCode)
				.append(", levelName=").append(levelName).append(", availbilty=").append(availbilty).append(", partFull=").append(partFull)
				.append(", studyMode=").append(studyMode).append(", internationalFee=").append(internationalFee).append(", domesticFee=").append(domesticFee)
				.append(", currency=").append(currency).append(", currencyTime=").append(currencyTime).append(", usdInternationFee=").append(usdInternationFee)
				.append(", usdDomasticFee=").append(usdDomasticFee).append(", costRange=").append(costRange).append(", content=").append(content)
				.append(", facultyDescription=").append(facultyDescription).append(", instituteImageUrl=").append(instituteImageUrl)
				.append(", instituteLogoUrl=").append(instituteLogoUrl).append(", latitute=").append(latitute).append(", longitude=").append(longitude)
				.append(", openingHourFrom=").append(openingHourFrom).append(", openingHourTo=").append(openingHourTo).append(", intake=").append(intake)
				.append(", deliveryMethod=").append(deliveryMethod).append("]");
		return builder.toString();
	}

}
