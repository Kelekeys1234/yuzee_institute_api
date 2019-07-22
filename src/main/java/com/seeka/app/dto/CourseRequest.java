package com.seeka.app.dto;

import java.math.BigInteger;

import com.seeka.app.bean.CourseEnglishEligibility;

public class CourseRequest {
    
    private BigInteger courseId;
    private Integer cId;
    private BigInteger instituteId;
    private BigInteger cityId;
    private BigInteger countryId;
    private BigInteger facultyId;
    private String name;
    private String description;
    private String duration;
    private String intake;
    private String languaige;
    private String domasticFee;
    private String internationalFee;
    private String grades;
    private CourseEnglishEligibility englishEligibility;
    private String partTime;
    private String fullTime;
    private String documentUrl;
    private String contact;
    private String openingHourFrom;
    private String openingHourTo;
    private String campusLocation;
    private String website;
    private String courseLink;
    /**
     * @return the courseId
     */
    public BigInteger getCourseId() {
        return courseId;
    }
    /**
     * @param courseId the courseId to set
     */
    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }
    /**
     * @return the cId
     */
    public Integer getcId() {
        return cId;
    }
    /**
     * @param cId the cId to set
     */
    public void setcId(Integer cId) {
        this.cId = cId;
    }
    /**
     * @return the instituteId
     */
    public BigInteger getInstituteId() {
        return instituteId;
    }
    /**
     * @param instituteId the instituteId to set
     */
    public void setInstituteId(BigInteger instituteId) {
        this.instituteId = instituteId;
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
    public void setCityId(BigInteger cityId) {
        this.cityId = cityId;
    }
    /**
     * @return the facultyId
     */
    public BigInteger getFacultyId() {
        return facultyId;
    }
    /**
     * @param facultyId the facultyId to set
     */
    public void setFacultyId(BigInteger facultyId) {
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
    public void setName(String name) {
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
    public void setDescription(String description) {
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
    public void setDuration(String duration) {
        this.duration = duration;
    }
    /**
     * @return the intake
     */
    public String getIntake() {
        return intake;
    }
    /**
     * @param intake the intake to set
     */
    public void setIntake(String intake) {
        this.intake = intake;
    }
    /**
     * @return the languaige
     */
    public String getLanguaige() {
        return languaige;
    }
    /**
     * @param languaige the languaige to set
     */
    public void setLanguaige(String languaige) {
        this.languaige = languaige;
    }
    /**
     * @return the domasticFee
     */
    public String getDomasticFee() {
        return domasticFee;
    }
    /**
     * @param domasticFee the domasticFee to set
     */
    public void setDomasticFee(String domasticFee) {
        this.domasticFee = domasticFee;
    }
    /**
     * @return the internationalFee
     */
    public String getInternationalFee() {
        return internationalFee;
    }
    /**
     * @param internationalFee the internationalFee to set
     */
    public void setInternationalFee(String internationalFee) {
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
    public void setGrades(String grades) {
        this.grades = grades;
    }
    /**
     * @return the englishEligibility
     */
    public CourseEnglishEligibility getEnglishEligibility() {
        return englishEligibility;
    }
    /**
     * @param englishEligibility the englishEligibility to set
     */
    public void setEnglishEligibility(CourseEnglishEligibility englishEligibility) {
        this.englishEligibility = englishEligibility;
    }
    /**
     * @return the partTime
     */
    public String getPartTime() {
        return partTime;
    }
    /**
     * @param partTime the partTime to set
     */
    public void setPartTime(String partTime) {
        this.partTime = partTime;
    }
    /**
     * @return the fullTime
     */
    public String getFullTime() {
        return fullTime;
    }
    /**
     * @param fullTime the fullTime to set
     */
    public void setFullTime(String fullTime) {
        this.fullTime = fullTime;
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
    public void setDocumentUrl(String documentUrl) {
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
    public void setOpeningHourFrom(String openingHourFrom) {
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
    public void setOpeningHourTo(String openingHourTo) {
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
    public void setCampusLocation(String campusLocation) {
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
    public void setWebsite(String website) {
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
    public void setContact(String contact) {
        this.contact = contact;
    }
    /**
     * @return the courseLink
     */
    public String getCourseLink() {
        return courseLink;
    }
    /**
     * @param courseLink the courseLink to set
     */
    public void setCourseLink(String courseLink) {
        this.courseLink = courseLink;
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
    public void setCountryId(BigInteger countryId) {
        this.countryId = countryId;
    }

}
