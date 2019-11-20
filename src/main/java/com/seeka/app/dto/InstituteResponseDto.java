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

}
