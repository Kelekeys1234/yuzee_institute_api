package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class InstituteResponseDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4673759147405801959L;
	private BigInteger id;
	private String name;
	private Integer worldRanking;
	private String location;
	private Integer totalCourses;
	private Integer totalCount;
	private BigInteger cityId;
	private BigInteger countryId;
	private String website;
	private String aboutUs;
	private String openingFrom;
	private String openingTo;
	private Integer totalStudent;
	private String latitute;
	private String longitude;
	private String phoneNumber;
	private String email;
	private String address;
	private String visaRequirement;
	private String totalAvailableJobs;
	private String countryName;
	private String cityName;
	private Date updatedOn;
	private String instituteType;
	private String campusType;
	private List<StorageDto> storageList;
	private Boolean isActive;
	private Integer stars;
	public Integer domesticRanking;

	public Integer getDomesticRanking() {
		return domesticRanking;
	}

	public void setDomesticRanking(final Integer domesticRanking) {
		this.domesticRanking = domesticRanking;
	}

	public Integer getWorldRanking() {
		return worldRanking;
	}

	public void setWorldRanking(final Integer worldRanking) {
		this.worldRanking = worldRanking;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(final String location) {
		this.location = location;
	}

	public Integer getTotalCourses() {
		return totalCourses;
	}

	public void setTotalCourses(final Integer totalCourses) {
		this.totalCourses = totalCourses;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(final Integer totalCount) {
		this.totalCount = totalCount;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(final String website) {
		this.website = website;
	}

	public String getAboutUs() {
		return aboutUs;
	}

	public void setAboutUs(final String aboutUs) {
		this.aboutUs = aboutUs;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getVisaRequirement() {
		if (null == visaRequirement) {
			visaRequirement = "";
		}
		return visaRequirement;
	}

	public void setVisaRequirement(final String visaRequirement) {
		this.visaRequirement = visaRequirement;
	}

	public String getTotalAvailableJobs() {
		if (null == totalAvailableJobs) {
			totalAvailableJobs = "0";
		}
		return totalAvailableJobs;
	}

	public void setTotalAvailableJobs(final String totalAvailableJobs) {
		this.totalAvailableJobs = totalAvailableJobs;
	}

	public BigInteger getCityId() {
		return cityId;
	}

	public void setCityId(final BigInteger cityId) {
		this.cityId = cityId;
	}

	public BigInteger getCountryId() {
		return countryId;
	}

	public void setCountryId(final BigInteger countryId) {
		this.countryId = countryId;
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

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(final Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getInstituteType() {
		return instituteType;
	}

	public void setInstituteType(final String instituteType) {
		this.instituteType = instituteType;
	}

	public String getCampusType() {
		return campusType;
	}

	public void setCampusType(final String campusType) {
		this.campusType = campusType;
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

	public List<StorageDto> getStorageList() {
		return storageList;
	}

	public void setStorageList(final List<StorageDto> storageList) {
		this.storageList = storageList;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(final Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(final Integer stars) {
		this.stars = stars;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (aboutUs == null ? 0 : aboutUs.hashCode());
		result = prime * result + (address == null ? 0 : address.hashCode());
		result = prime * result + (campusType == null ? 0 : campusType.hashCode());
		result = prime * result + (cityId == null ? 0 : cityId.hashCode());
		result = prime * result + (cityName == null ? 0 : cityName.hashCode());
		result = prime * result + (countryId == null ? 0 : countryId.hashCode());
		result = prime * result + (countryName == null ? 0 : countryName.hashCode());
		result = prime * result + (email == null ? 0 : email.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (instituteType == null ? 0 : instituteType.hashCode());
		result = prime * result + (isActive == null ? 0 : isActive.hashCode());
		result = prime * result + (latitute == null ? 0 : latitute.hashCode());
		result = prime * result + (location == null ? 0 : location.hashCode());
		result = prime * result + (longitude == null ? 0 : longitude.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (openingFrom == null ? 0 : openingFrom.hashCode());
		result = prime * result + (openingTo == null ? 0 : openingTo.hashCode());
		result = prime * result + (phoneNumber == null ? 0 : phoneNumber.hashCode());
		result = prime * result + (stars == null ? 0 : stars.hashCode());
		result = prime * result + (storageList == null ? 0 : storageList.hashCode());
		result = prime * result + (totalAvailableJobs == null ? 0 : totalAvailableJobs.hashCode());
		result = prime * result + (totalCount == null ? 0 : totalCount.hashCode());
		result = prime * result + (totalCourses == null ? 0 : totalCourses.hashCode());
		result = prime * result + (totalStudent == null ? 0 : totalStudent.hashCode());
		result = prime * result + (updatedOn == null ? 0 : updatedOn.hashCode());
		result = prime * result + (visaRequirement == null ? 0 : visaRequirement.hashCode());
		result = prime * result + (website == null ? 0 : website.hashCode());
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
		InstituteResponseDto other = (InstituteResponseDto) obj;
		if (aboutUs == null) {
			if (other.aboutUs != null) {
				return false;
			}
		} else if (!aboutUs.equals(other.aboutUs)) {
			return false;
		}
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
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
		if (cityName == null) {
			if (other.cityName != null) {
				return false;
			}
		} else if (!cityName.equals(other.cityName)) {
			return false;
		}
		if (countryId == null) {
			if (other.countryId != null) {
				return false;
			}
		} else if (!countryId.equals(other.countryId)) {
			return false;
		}
		if (countryName == null) {
			if (other.countryName != null) {
				return false;
			}
		} else if (!countryName.equals(other.countryName)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (instituteType == null) {
			if (other.instituteType != null) {
				return false;
			}
		} else if (!instituteType.equals(other.instituteType)) {
			return false;
		}
		if (isActive == null) {
			if (other.isActive != null) {
				return false;
			}
		} else if (!isActive.equals(other.isActive)) {
			return false;
		}
		if (latitute == null) {
			if (other.latitute != null) {
				return false;
			}
		} else if (!latitute.equals(other.latitute)) {
			return false;
		}
		if (location == null) {
			if (other.location != null) {
				return false;
			}
		} else if (!location.equals(other.location)) {
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
		if (stars == null) {
			if (other.stars != null) {
				return false;
			}
		} else if (!stars.equals(other.stars)) {
			return false;
		}
		if (storageList == null) {
			if (other.storageList != null) {
				return false;
			}
		} else if (!storageList.equals(other.storageList)) {
			return false;
		}
		if (totalAvailableJobs == null) {
			if (other.totalAvailableJobs != null) {
				return false;
			}
		} else if (!totalAvailableJobs.equals(other.totalAvailableJobs)) {
			return false;
		}
		if (totalCount == null) {
			if (other.totalCount != null) {
				return false;
			}
		} else if (!totalCount.equals(other.totalCount)) {
			return false;
		}
		if (totalCourses == null) {
			if (other.totalCourses != null) {
				return false;
			}
		} else if (!totalCourses.equals(other.totalCourses)) {
			return false;
		}
		if (totalStudent == null) {
			if (other.totalStudent != null) {
				return false;
			}
		} else if (!totalStudent.equals(other.totalStudent)) {
			return false;
		}
		if (updatedOn == null) {
			if (other.updatedOn != null) {
				return false;
			}
		} else if (!updatedOn.equals(other.updatedOn)) {
			return false;
		}
		if (visaRequirement == null) {
			if (other.visaRequirement != null) {
				return false;
			}
		} else if (!visaRequirement.equals(other.visaRequirement)) {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InstituteResponseDto [id=").append(id).append(", name=").append(name).append(", worldRanking=").append(worldRanking)
				.append(", location=").append(location).append(", totalCourses=").append(totalCourses).append(", totalCount=").append(totalCount)
				.append(", cityId=").append(cityId).append(", countryId=").append(countryId).append(", website=").append(website).append(", aboutUs=")
				.append(aboutUs).append(", openingFrom=").append(openingFrom).append(", openingTo=").append(openingTo).append(", totalStudent=")
				.append(totalStudent).append(", latitute=").append(latitute).append(", longitude=").append(longitude).append(", phoneNumber=")
				.append(phoneNumber).append(", email=").append(email).append(", address=").append(address).append(", visaRequirement=").append(visaRequirement)
				.append(", totalAvailableJobs=").append(totalAvailableJobs).append(", countryName=").append(countryName).append(", cityName=").append(cityName)
				.append(", updatedOn=").append(updatedOn).append(", instituteType=").append(instituteType).append(", campusType=").append(campusType)
				.append(", storageList=").append(storageList).append(", isActive=").append(isActive).append(", stars=").append(stars).append("]");
		return builder.toString();
	}

}
