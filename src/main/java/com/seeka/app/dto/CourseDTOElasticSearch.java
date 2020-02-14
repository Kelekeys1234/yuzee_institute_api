package com.seeka.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CourseDTOElasticSearch implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3521506877760833299L;

	private String id;
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

	private String contact;
	private String link;

	public String getContact() {
		return contact;
	}

	public void setContact(final String contact) {
		this.contact = contact;
	}

	public String getLink() {
		return link;
	}

	public void setLink(final String link) {
		this.link = link;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
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
		builder.append("CourseDTOElasticSearch [id=").append(id).append(", name=").append(name).append(", worldRanking=")
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
				.append(", deliveryMethod=").append(deliveryMethod).append(", contact=").append(contact).append(", link=").append(link).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((abbreviation == null) ? 0 : abbreviation.hashCode());
		result = (prime * result) + ((availbilty == null) ? 0 : availbilty.hashCode());
		result = (prime * result) + ((cityName == null) ? 0 : cityName.hashCode());
		result = (prime * result) + ((contact == null) ? 0 : contact.hashCode());
		result = (prime * result) + ((content == null) ? 0 : content.hashCode());
		result = (prime * result) + ((costRange == null) ? 0 : costRange.hashCode());
		result = (prime * result) + ((countryName == null) ? 0 : countryName.hashCode());
		result = (prime * result) + ((currency == null) ? 0 : currency.hashCode());
		result = (prime * result) + ((currencyTime == null) ? 0 : currencyTime.hashCode());
		result = (prime * result) + ((deliveryMethod == null) ? 0 : deliveryMethod.hashCode());
		result = (prime * result) + ((description == null) ? 0 : description.hashCode());
		result = (prime * result) + ((domesticFee == null) ? 0 : domesticFee.hashCode());
		result = (prime * result) + ((duration == null) ? 0 : duration.hashCode());
		result = (prime * result) + ((durationTime == null) ? 0 : durationTime.hashCode());
		result = (prime * result) + ((facultyDescription == null) ? 0 : facultyDescription.hashCode());
		result = (prime * result) + ((facultyName == null) ? 0 : facultyName.hashCode());
		result = (prime * result) + ((id == null) ? 0 : id.hashCode());
		result = (prime * result) + ((instituteImageUrl == null) ? 0 : instituteImageUrl.hashCode());
		result = (prime * result) + ((instituteLogoUrl == null) ? 0 : instituteLogoUrl.hashCode());
		result = (prime * result) + ((instituteName == null) ? 0 : instituteName.hashCode());
		result = (prime * result) + ((intake == null) ? 0 : intake.hashCode());
		result = (prime * result) + ((internationalFee == null) ? 0 : internationalFee.hashCode());
		result = (prime * result) + ((language == null) ? 0 : language.hashCode());
		result = (prime * result) + ((latitute == null) ? 0 : latitute.hashCode());
		result = (prime * result) + ((levelCode == null) ? 0 : levelCode.hashCode());
		result = (prime * result) + ((levelName == null) ? 0 : levelName.hashCode());
		result = (prime * result) + ((link == null) ? 0 : link.hashCode());
		result = (prime * result) + ((longitude == null) ? 0 : longitude.hashCode());
		result = (prime * result) + ((name == null) ? 0 : name.hashCode());
		result = (prime * result) + ((openingHourFrom == null) ? 0 : openingHourFrom.hashCode());
		result = (prime * result) + ((openingHourTo == null) ? 0 : openingHourTo.hashCode());
		result = (prime * result) + ((partFull == null) ? 0 : partFull.hashCode());
		result = (prime * result) + ((recognition == null) ? 0 : recognition.hashCode());
		result = (prime * result) + ((recognitionType == null) ? 0 : recognitionType.hashCode());
		result = (prime * result) + ((remarks == null) ? 0 : remarks.hashCode());
		result = (prime * result) + ((stars == null) ? 0 : stars.hashCode());
		result = (prime * result) + ((studyMode == null) ? 0 : studyMode.hashCode());
		result = (prime * result) + ((usdDomasticFee == null) ? 0 : usdDomasticFee.hashCode());
		result = (prime * result) + ((usdInternationFee == null) ? 0 : usdInternationFee.hashCode());
		result = (prime * result) + ((website == null) ? 0 : website.hashCode());
		result = (prime * result) + ((worldRanking == null) ? 0 : worldRanking.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CourseDTOElasticSearch other = (CourseDTOElasticSearch) obj;
		if (abbreviation == null) {
			if (other.abbreviation != null) {
				return false;
			}
		} else if (!abbreviation.equals(other.abbreviation)) {
			return false;
		}
		if (availbilty == null) {
			if (other.availbilty != null) {
				return false;
			}
		} else if (!availbilty.equals(other.availbilty)) {
			return false;
		}
		if (cityName == null) {
			if (other.cityName != null) {
				return false;
			}
		} else if (!cityName.equals(other.cityName)) {
			return false;
		}
		if (contact == null) {
			if (other.contact != null) {
				return false;
			}
		} else if (!contact.equals(other.contact)) {
			return false;
		}
		if (content == null) {
			if (other.content != null) {
				return false;
			}
		} else if (!content.equals(other.content)) {
			return false;
		}
		if (costRange == null) {
			if (other.costRange != null) {
				return false;
			}
		} else if (!costRange.equals(other.costRange)) {
			return false;
		}
		if (countryName == null) {
			if (other.countryName != null) {
				return false;
			}
		} else if (!countryName.equals(other.countryName)) {
			return false;
		}
		if (currency == null) {
			if (other.currency != null) {
				return false;
			}
		} else if (!currency.equals(other.currency)) {
			return false;
		}
		if (currencyTime == null) {
			if (other.currencyTime != null) {
				return false;
			}
		} else if (!currencyTime.equals(other.currencyTime)) {
			return false;
		}
		if (deliveryMethod == null) {
			if (other.deliveryMethod != null) {
				return false;
			}
		} else if (!deliveryMethod.equals(other.deliveryMethod)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (domesticFee == null) {
			if (other.domesticFee != null) {
				return false;
			}
		} else if (!domesticFee.equals(other.domesticFee)) {
			return false;
		}
		if (duration == null) {
			if (other.duration != null) {
				return false;
			}
		} else if (!duration.equals(other.duration)) {
			return false;
		}
		if (durationTime == null) {
			if (other.durationTime != null) {
				return false;
			}
		} else if (!durationTime.equals(other.durationTime)) {
			return false;
		}
		if (facultyDescription == null) {
			if (other.facultyDescription != null) {
				return false;
			}
		} else if (!facultyDescription.equals(other.facultyDescription)) {
			return false;
		}
		if (facultyName == null) {
			if (other.facultyName != null) {
				return false;
			}
		} else if (!facultyName.equals(other.facultyName)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (instituteImageUrl == null) {
			if (other.instituteImageUrl != null) {
				return false;
			}
		} else if (!instituteImageUrl.equals(other.instituteImageUrl)) {
			return false;
		}
		if (instituteLogoUrl == null) {
			if (other.instituteLogoUrl != null) {
				return false;
			}
		} else if (!instituteLogoUrl.equals(other.instituteLogoUrl)) {
			return false;
		}
		if (instituteName == null) {
			if (other.instituteName != null) {
				return false;
			}
		} else if (!instituteName.equals(other.instituteName)) {
			return false;
		}
		if (intake == null) {
			if (other.intake != null) {
				return false;
			}
		} else if (!intake.equals(other.intake)) {
			return false;
		}
		if (internationalFee == null) {
			if (other.internationalFee != null) {
				return false;
			}
		} else if (!internationalFee.equals(other.internationalFee)) {
			return false;
		}
		if (language == null) {
			if (other.language != null) {
				return false;
			}
		} else if (!language.equals(other.language)) {
			return false;
		}
		if (latitute == null) {
			if (other.latitute != null) {
				return false;
			}
		} else if (!latitute.equals(other.latitute)) {
			return false;
		}
		if (levelCode == null) {
			if (other.levelCode != null) {
				return false;
			}
		} else if (!levelCode.equals(other.levelCode)) {
			return false;
		}
		if (levelName == null) {
			if (other.levelName != null) {
				return false;
			}
		} else if (!levelName.equals(other.levelName)) {
			return false;
		}
		if (link == null) {
			if (other.link != null) {
				return false;
			}
		} else if (!link.equals(other.link)) {
			return false;
		}
		if (longitude == null) {
			if (other.longitude != null) {
				return false;
			}
		} else if (!longitude.equals(other.longitude)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (openingHourFrom == null) {
			if (other.openingHourFrom != null) {
				return false;
			}
		} else if (!openingHourFrom.equals(other.openingHourFrom)) {
			return false;
		}
		if (openingHourTo == null) {
			if (other.openingHourTo != null) {
				return false;
			}
		} else if (!openingHourTo.equals(other.openingHourTo)) {
			return false;
		}
		if (partFull == null) {
			if (other.partFull != null) {
				return false;
			}
		} else if (!partFull.equals(other.partFull)) {
			return false;
		}
		if (recognition == null) {
			if (other.recognition != null) {
				return false;
			}
		} else if (!recognition.equals(other.recognition)) {
			return false;
		}
		if (recognitionType == null) {
			if (other.recognitionType != null) {
				return false;
			}
		} else if (!recognitionType.equals(other.recognitionType)) {
			return false;
		}
		if (remarks == null) {
			if (other.remarks != null) {
				return false;
			}
		} else if (!remarks.equals(other.remarks)) {
			return false;
		}
		if (stars == null) {
			if (other.stars != null) {
				return false;
			}
		} else if (!stars.equals(other.stars)) {
			return false;
		}
		if (studyMode == null) {
			if (other.studyMode != null) {
				return false;
			}
		} else if (!studyMode.equals(other.studyMode)) {
			return false;
		}
		if (usdDomasticFee == null) {
			if (other.usdDomasticFee != null) {
				return false;
			}
		} else if (!usdDomasticFee.equals(other.usdDomasticFee)) {
			return false;
		}
		if (usdInternationFee == null) {
			if (other.usdInternationFee != null) {
				return false;
			}
		} else if (!usdInternationFee.equals(other.usdInternationFee)) {
			return false;
		}
		if (website == null) {
			if (other.website != null) {
				return false;
			}
		} else if (!website.equals(other.website)) {
			return false;
		}
		if (worldRanking == null) {
			if (other.worldRanking != null) {
				return false;
			}
		} else if (!worldRanking.equals(other.worldRanking)) {
			return false;
		}
		return true;
	}

}
