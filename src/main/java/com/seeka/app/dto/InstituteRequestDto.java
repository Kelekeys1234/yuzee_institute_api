package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

public class InstituteRequestDto {

	private BigInteger id;
	private String name;
	private String description;
	private BigInteger cityId;
	private BigInteger countryId;
	private Integer worldRanking;
	private String logoUrl;
	private String avgCostOfLiving;
	private BigInteger instituteTypeId;
	private String enrolmentLink;
	private String tuitionFessPaymentPlan;
	private String scholarshipFinancingAssistance;
	private String website;
	private List<InstituteMedia> instituteMedias;
	private BigInteger instituteCategoryTypeId;
	private String campusType;
	private String campusName;
	private Double latitude;
	private Double longitude;
	private Integer totalStudent;
	private String openingFrom;
	private String openingTo;
	private String email;
	private String phoneNumber;
	private String address;
	private List<BigInteger> offerService;
	private List<BigInteger> accreditation;
	private List<String> intakes;
	private String createdBy;
	private String updatedBy;
	private String whatsNo;
	/**
	 * There is no use of below fields in Admin panel.
	 */
	private String aboutInfo;
	private String courseStart;

	private List<BigInteger> facultyIds;
	private List<BigInteger> levelIds;

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
	 * @return the worldRanking
	 */
	public Integer getWorldRanking() {
		return worldRanking;
	}

	/**
	 * @param worldRanking the worldRanking to set
	 */
	public void setWorldRanking(final Integer worldRanking) {
		this.worldRanking = worldRanking;
	}

	/**
	 * @return the logoUrl
	 */
	public String getLogoUrl() {
		return logoUrl;
	}

	/**
	 * @param logoUrl the logoUrl to set
	 */
	public void setLogoUrl(final String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getAvgCostOfLiving() {
		return avgCostOfLiving;
	}

	public void setAvgCostOfLiving(final String avgCostOfLiving) {
		this.avgCostOfLiving = avgCostOfLiving;
	}

	/**
	 * @return the typeId
	 */
	public BigInteger getInstituteTypeId() {
		return instituteTypeId;
	}

	public void setInstituteTypeId(final BigInteger instituteTypeId) {
		this.instituteTypeId = instituteTypeId;
	}

	/**
	 * @return the enrolment
	 */
	public String getEnrolmentLink() {
		return enrolmentLink;
	}

	/**
	 * @param enrolment the enrolment to set
	 */
	public void setEnrolmentLink(final String enrolmentLink) {
		this.enrolmentLink = enrolmentLink;
	}

	/**
	 * @return the tuitionFessPaymentPlan
	 */
	public String getTuitionFessPaymentPlan() {
		return tuitionFessPaymentPlan;
	}

	/**
	 * @param tuitionFessPaymentPlan the tuitionFessPaymentPlan to set
	 */
	public void setTuitionFessPaymentPlan(final String tuitionFessPaymentPlan) {
		this.tuitionFessPaymentPlan = tuitionFessPaymentPlan;
	}

	/**
	 * @return the scholarshipFinancingAssistance
	 */
	public String getScholarshipFinancingAssistance() {
		return scholarshipFinancingAssistance;
	}

	/**
	 * @param scholarshipFinancingAssistance the scholarshipFinancingAssistance to
	 *                                       set
	 */
	public void setScholarshipFinancingAssistance(final String scholarshipFinancingAssistance) {
		this.scholarshipFinancingAssistance = scholarshipFinancingAssistance;
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

	public List<InstituteMedia> getInstituteMedias() {
		return instituteMedias;
	}

	public void setInstituteMedias(final List<InstituteMedia> instituteMedias) {
		this.instituteMedias = instituteMedias;
	}

	public BigInteger getInstituteCategoryTypeId() {
		return instituteCategoryTypeId;
	}

	public void setInstituteCategoryTypeId(final BigInteger instituteCategoryTypeId) {
		this.instituteCategoryTypeId = instituteCategoryTypeId;
	}

	/**
	 * @return the campusType
	 */
	public String getCampusType() {
		return campusType;
	}

	/**
	 * @param campusType the campusType to set
	 */
	public void setCampusType(final String campusType) {
		this.campusType = campusType;
	}

	/**
	 * @return the campusName
	 */
	public String getCampusName() {
		return campusName;
	}

	/**
	 * @param campusName the campusName to set
	 */
	public void setCampusName(final String campusName) {
		this.campusName = campusName;
	}

	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(final Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(final Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the totalStudent
	 */
	public Integer getTotalStudent() {
		return totalStudent;
	}

	/**
	 * @param totalStudent the totalStudent to set
	 */
	public void setTotalStudent(final Integer totalStudent) {
		this.totalStudent = totalStudent;
	}

	/**
	 * @return the openingFrom
	 */
	public String getOpeningFrom() {
		return openingFrom;
	}

	/**
	 * @param openingFrom the openingFrom to set
	 */
	public void setOpeningFrom(final String openingFrom) {
		this.openingFrom = openingFrom;
	}

	/**
	 * @return the openingTo
	 */
	public String getOpeningTo() {
		return openingTo;
	}

	/**
	 * @param openingTo the openingTo to set
	 */
	public void setOpeningTo(final String openingTo) {
		this.openingTo = openingTo;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(final String address) {
		this.address = address;
	}

	/**
	 * @return the offerService
	 */
	public List<BigInteger> getOfferService() {
		return offerService;
	}

	/**
	 * @param offerService the offerService to set
	 */
	public void setOfferService(final List<BigInteger> offerService) {
		this.offerService = offerService;
	}

	/**
	 * @return the accreditation
	 */
	public List<BigInteger> getAccreditation() {
		return accreditation;
	}

	/**
	 * @param accreditation the accreditation to set
	 */
	public void setAccreditation(final List<BigInteger> accreditation) {
		this.accreditation = accreditation;
	}

	/**
	 * @return the intakes
	 */
	public List<String> getIntakes() {
		return intakes;
	}

	/**
	 * @param intakes the intakes to set
	 */
	public void setIntakes(final List<String> intakes) {
		this.intakes = intakes;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(final String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getWhatsNo() {
		return whatsNo;
	}

	/**
	 * @param whatsNo the whatsNo to set
	 */
	public void setWhatsNo(final String whatsNo) {
		this.whatsNo = whatsNo;
	}

	public String getAboutInfo() {
		return aboutInfo;
	}

	public void setAboutInfo(final String aboutInfo) {
		this.aboutInfo = aboutInfo;
	}

	public String getCourseStart() {
		return courseStart;
	}

	public void setCourseStart(final String courseStart) {
		this.courseStart = courseStart;
	}

	public List<BigInteger> getFacultyIds() {
		return facultyIds;
	}

	public void setFacultyIds(final List<BigInteger> facultyIds) {
		this.facultyIds = facultyIds;
	}

	public List<BigInteger> getLevelIds() {
		return levelIds;
	}

	public void setLevelIds(final List<BigInteger> levelIds) {
		this.levelIds = levelIds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (aboutInfo == null ? 0 : aboutInfo.hashCode());
		result = prime * result + (accreditation == null ? 0 : accreditation.hashCode());
		result = prime * result + (address == null ? 0 : address.hashCode());
		result = prime * result + (avgCostOfLiving == null ? 0 : avgCostOfLiving.hashCode());
		result = prime * result + (campusName == null ? 0 : campusName.hashCode());
		result = prime * result + (campusType == null ? 0 : campusType.hashCode());
		result = prime * result + (cityId == null ? 0 : cityId.hashCode());
		result = prime * result + (countryId == null ? 0 : countryId.hashCode());
		result = prime * result + (courseStart == null ? 0 : courseStart.hashCode());
		result = prime * result + (createdBy == null ? 0 : createdBy.hashCode());
		result = prime * result + (description == null ? 0 : description.hashCode());
		result = prime * result + (email == null ? 0 : email.hashCode());
		result = prime * result + (enrolmentLink == null ? 0 : enrolmentLink.hashCode());
		result = prime * result + (facultyIds == null ? 0 : facultyIds.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (instituteCategoryTypeId == null ? 0 : instituteCategoryTypeId.hashCode());
		result = prime * result + (instituteMedias == null ? 0 : instituteMedias.hashCode());
		result = prime * result + (instituteTypeId == null ? 0 : instituteTypeId.hashCode());
		result = prime * result + (intakes == null ? 0 : intakes.hashCode());
		result = prime * result + (latitude == null ? 0 : latitude.hashCode());
		result = prime * result + (levelIds == null ? 0 : levelIds.hashCode());
		result = prime * result + (logoUrl == null ? 0 : logoUrl.hashCode());
		result = prime * result + (longitude == null ? 0 : longitude.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (offerService == null ? 0 : offerService.hashCode());
		result = prime * result + (openingFrom == null ? 0 : openingFrom.hashCode());
		result = prime * result + (openingTo == null ? 0 : openingTo.hashCode());
		result = prime * result + (phoneNumber == null ? 0 : phoneNumber.hashCode());
		result = prime * result + (scholarshipFinancingAssistance == null ? 0 : scholarshipFinancingAssistance.hashCode());
		result = prime * result + (totalStudent == null ? 0 : totalStudent.hashCode());
		result = prime * result + (tuitionFessPaymentPlan == null ? 0 : tuitionFessPaymentPlan.hashCode());
		result = prime * result + (updatedBy == null ? 0 : updatedBy.hashCode());
		result = prime * result + (website == null ? 0 : website.hashCode());
		result = prime * result + (whatsNo == null ? 0 : whatsNo.hashCode());
		result = prime * result + (worldRanking == null ? 0 : worldRanking.hashCode());
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
		InstituteRequestDto other = (InstituteRequestDto) obj;
		if (aboutInfo == null) {
			if (other.aboutInfo != null) {
				return false;
			}
		} else if (!aboutInfo.equals(other.aboutInfo)) {
			return false;
		}
		if (accreditation == null) {
			if (other.accreditation != null) {
				return false;
			}
		} else if (!accreditation.equals(other.accreditation)) {
			return false;
		}
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (avgCostOfLiving == null) {
			if (other.avgCostOfLiving != null) {
				return false;
			}
		} else if (!avgCostOfLiving.equals(other.avgCostOfLiving)) {
			return false;
		}
		if (campusName == null) {
			if (other.campusName != null) {
				return false;
			}
		} else if (!campusName.equals(other.campusName)) {
			return false;
		}
		if (campusType == null) {
			if (other.campusType != null) {
				return false;
			}
		} else if (!campusType.equals(other.campusType)) {
			return false;
		}
		if (cityId == null) {
			if (other.cityId != null) {
				return false;
			}
		} else if (!cityId.equals(other.cityId)) {
			return false;
		}
		if (countryId == null) {
			if (other.countryId != null) {
				return false;
			}
		} else if (!countryId.equals(other.countryId)) {
			return false;
		}
		if (courseStart == null) {
			if (other.courseStart != null) {
				return false;
			}
		} else if (!courseStart.equals(other.courseStart)) {
			return false;
		}
		if (createdBy == null) {
			if (other.createdBy != null) {
				return false;
			}
		} else if (!createdBy.equals(other.createdBy)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (enrolmentLink == null) {
			if (other.enrolmentLink != null) {
				return false;
			}
		} else if (!enrolmentLink.equals(other.enrolmentLink)) {
			return false;
		}
		if (facultyIds == null) {
			if (other.facultyIds != null) {
				return false;
			}
		} else if (!facultyIds.equals(other.facultyIds)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (instituteCategoryTypeId == null) {
			if (other.instituteCategoryTypeId != null) {
				return false;
			}
		} else if (!instituteCategoryTypeId.equals(other.instituteCategoryTypeId)) {
			return false;
		}
		if (instituteMedias == null) {
			if (other.instituteMedias != null) {
				return false;
			}
		} else if (!instituteMedias.equals(other.instituteMedias)) {
			return false;
		}
		if (instituteTypeId == null) {
			if (other.instituteTypeId != null) {
				return false;
			}
		} else if (!instituteTypeId.equals(other.instituteTypeId)) {
			return false;
		}
		if (intakes == null) {
			if (other.intakes != null) {
				return false;
			}
		} else if (!intakes.equals(other.intakes)) {
			return false;
		}
		if (latitude == null) {
			if (other.latitude != null) {
				return false;
			}
		} else if (!latitude.equals(other.latitude)) {
			return false;
		}
		if (levelIds == null) {
			if (other.levelIds != null) {
				return false;
			}
		} else if (!levelIds.equals(other.levelIds)) {
			return false;
		}
		if (logoUrl == null) {
			if (other.logoUrl != null) {
				return false;
			}
		} else if (!logoUrl.equals(other.logoUrl)) {
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
		if (offerService == null) {
			if (other.offerService != null) {
				return false;
			}
		} else if (!offerService.equals(other.offerService)) {
			return false;
		}
		if (openingFrom == null) {
			if (other.openingFrom != null) {
				return false;
			}
		} else if (!openingFrom.equals(other.openingFrom)) {
			return false;
		}
		if (openingTo == null) {
			if (other.openingTo != null) {
				return false;
			}
		} else if (!openingTo.equals(other.openingTo)) {
			return false;
		}
		if (phoneNumber == null) {
			if (other.phoneNumber != null) {
				return false;
			}
		} else if (!phoneNumber.equals(other.phoneNumber)) {
			return false;
		}
		if (scholarshipFinancingAssistance == null) {
			if (other.scholarshipFinancingAssistance != null) {
				return false;
			}
		} else if (!scholarshipFinancingAssistance.equals(other.scholarshipFinancingAssistance)) {
			return false;
		}
		if (totalStudent == null) {
			if (other.totalStudent != null) {
				return false;
			}
		} else if (!totalStudent.equals(other.totalStudent)) {
			return false;
		}
		if (tuitionFessPaymentPlan == null) {
			if (other.tuitionFessPaymentPlan != null) {
				return false;
			}
		} else if (!tuitionFessPaymentPlan.equals(other.tuitionFessPaymentPlan)) {
			return false;
		}
		if (updatedBy == null) {
			if (other.updatedBy != null) {
				return false;
			}
		} else if (!updatedBy.equals(other.updatedBy)) {
			return false;
		}
		if (website == null) {
			if (other.website != null) {
				return false;
			}
		} else if (!website.equals(other.website)) {
			return false;
		}
		if (whatsNo == null) {
			if (other.whatsNo != null) {
				return false;
			}
		} else if (!whatsNo.equals(other.whatsNo)) {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InstituteRequestDto [id=").append(id).append(", name=").append(name).append(", description=").append(description).append(", cityId=")
				.append(cityId).append(", countryId=").append(countryId).append(", worldRanking=").append(worldRanking).append(", logoUrl=").append(logoUrl)
				.append(", avgCostOfLiving=").append(avgCostOfLiving).append(", instituteTypeId=").append(instituteTypeId).append(", enrolmentLink=")
				.append(enrolmentLink).append(", tuitionFessPaymentPlan=").append(tuitionFessPaymentPlan).append(", scholarshipFinancingAssistance=")
				.append(scholarshipFinancingAssistance).append(", website=").append(website).append(", instituteMedias=").append(instituteMedias)
				.append(", instituteCategoryTypeId=").append(instituteCategoryTypeId).append(", campusType=").append(campusType).append(", campusName=")
				.append(campusName).append(", latitude=").append(latitude).append(", longitude=").append(longitude).append(", totalStudent=")
				.append(totalStudent).append(", openingFrom=").append(openingFrom).append(", openingTo=").append(openingTo).append(", email=").append(email)
				.append(", phoneNumber=").append(phoneNumber).append(", address=").append(address).append(", offerService=").append(offerService)
				.append(", accreditation=").append(accreditation).append(", intakes=").append(intakes).append(", createdBy=").append(createdBy)
				.append(", updatedBy=").append(updatedBy).append(", whatsNo=").append(whatsNo).append(", aboutInfo=").append(aboutInfo).append(", courseStart=")
				.append(courseStart).append(", facultyIds=").append(facultyIds).append(", levelIds=").append(levelIds).append("]");
		return builder.toString();
	}

}
