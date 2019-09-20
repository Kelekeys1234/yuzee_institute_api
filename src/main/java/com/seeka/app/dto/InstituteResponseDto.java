package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class InstituteResponseDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4673759147405801959L;
	private BigInteger id;
	private String name;
	private String worldRanking;
	private String stars;
	private String location;
	private Integer totalCourses;
	private Integer totalCount;
	private BigInteger cityId;
	private BigInteger countryId;
	private String website;
	private String aboutUs;
	private String openingHour;
	private String closingHour;
	private Integer totalNoOfStudents;
	private String latitute;
	private String longitude;
	private String interPhoneNumber;
	private String interEmail;
	private String address;
	private String visaRequirement;
	private String totalAvailableJobs;
	private String countryName;
	private String cityName;
	private String logoImage;
	private List<ImageResponseDto> images;

	public String getWorldRanking() {
		return worldRanking;
	}

	public void setWorldRanking(final String worldRanking) {
		this.worldRanking = worldRanking;
	}

	public String getStars() {
		return stars;
	}

	public void setStars(final String stars) {
		this.stars = stars;
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

	public String getOpeningHour() {
		return openingHour;
	}

	public void setOpeningHour(final String openingHour) {
		this.openingHour = openingHour;
	}

	public String getClosingHour() {
		return closingHour;
	}

	public void setClosingHour(final String closingHour) {
		this.closingHour = closingHour;
	}

	public Integer getTotalNoOfStudents() {
		return totalNoOfStudents;
	}

	public void setTotalNoOfStudents(final Integer totalNoOfStudents) {
		this.totalNoOfStudents = totalNoOfStudents;
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

	public String getInterPhoneNumber() {
		return interPhoneNumber;
	}

	public void setInterPhoneNumber(final String interPhoneNumber) {
		this.interPhoneNumber = interPhoneNumber;
	}

	public String getInterEmail() {
		return interEmail;
	}

	public void setInterEmail(final String interEmail) {
		this.interEmail = interEmail;
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

	public String getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(final String logoImage) {
		this.logoImage = logoImage;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the images
     */
    public List<ImageResponseDto> getImages() {
        return images;
    }

    /**
     * @param images the images to set
     */
    public void setImages(List<ImageResponseDto> images) {
        this.images = images;
    }

}
