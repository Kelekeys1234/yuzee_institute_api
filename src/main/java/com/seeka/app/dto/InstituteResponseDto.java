package com.seeka.app.dto;import java.math.BigInteger;

public class InstituteResponseDto {

    private BigInteger instituteId;
    private String instituteName;
    private String instituteLogoUrl;
    private String instituteImageUrl;
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

    public BigInteger getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(BigInteger instituteId) {
        this.instituteId = instituteId;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getInstituteLogoUrl() {
        return instituteLogoUrl;
    }

    public void setInstituteLogoUrl(String instituteLogoUrl) {
        this.instituteLogoUrl = instituteLogoUrl;
    }

    public String getInstituteImageUrl() {
        return instituteImageUrl;
    }

    public void setInstituteImageUrl(String instituteImageUrl) {
        this.instituteImageUrl = instituteImageUrl;
    }

    public String getWorldRanking() {
        return worldRanking;
    }

    public void setWorldRanking(String worldRanking) {
        this.worldRanking = worldRanking;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(Integer totalCourses) {
        this.totalCourses = totalCourses;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public String getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(String openingHour) {
        this.openingHour = openingHour;
    }

    public String getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(String closingHour) {
        this.closingHour = closingHour;
    }

    public Integer getTotalNoOfStudents() {
        return totalNoOfStudents;
    }

    public void setTotalNoOfStudents(Integer totalNoOfStudents) {
        this.totalNoOfStudents = totalNoOfStudents;
    }

    public String getLatitute() {
        return latitute;
    }

    public void setLatitute(String latitute) {
        this.latitute = latitute;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getInterPhoneNumber() {
        return interPhoneNumber;
    }

    public void setInterPhoneNumber(String interPhoneNumber) {
        this.interPhoneNumber = interPhoneNumber;
    }

    public String getInterEmail() {
        return interEmail;
    }

    public void setInterEmail(String interEmail) {
        this.interEmail = interEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVisaRequirement() {
        if (null == visaRequirement) {
            visaRequirement = "";
        }
        return visaRequirement;
    }

    public void setVisaRequirement(String visaRequirement) {
        this.visaRequirement = visaRequirement;
    }

    public String getTotalAvailableJobs() {
        if (null == totalAvailableJobs) {
            totalAvailableJobs = "0";
        }
        return totalAvailableJobs;
    }

    public void setTotalAvailableJobs(String totalAvailableJobs) {
        this.totalAvailableJobs = totalAvailableJobs;
    }

    public BigInteger getCityId() {
        return cityId;
    }

    public void setCityId(BigInteger cityId) {
        this.cityId = cityId;
    }

    public BigInteger getCountryId() {
        return countryId;
    }

    public void setCountryId(BigInteger countryId) {
        this.countryId = countryId;
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
}
