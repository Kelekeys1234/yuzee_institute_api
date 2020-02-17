package com.seeka.app.dto;

import java.util.List;

public class InstituteGetByIdRequestDto {

    private String instituteId;
    private String instituteName;
    private String description;
    private String cityId;
    private String countryId;

    private String latitute;
    private String longitude;
    private Integer totalStudent;
    private Integer worldRanking;
    private String accreditation;
    private String instituteLogoUrl;
    private String averageCostFrom;
    private String averageCostTo;
    private String instituteTypeId;
    private String openingHour;
    private String closingHour;
    private String enrolment;
    private String tuitionFessPaymentPlan;
    private String scholarshipFinancingAssistance;
    private String email;
    private String phoneNumber;
    private String website;
    private String address;
    private List<InstituteMedia> instituteMedias;
    private List<InstituteCampusDto> instituteCampus;
    private String instituteCategoryTypeId;
    private String campusType;

    /**
     * @return the instituteId
     */
    public String getInstituteId() {
        return instituteId;
    }

    /**
     * @param instituteId
     *            the instituteId to set
     */
    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    /**
     * @return the instituteName
     */
    public String getInstituteName() {
        return instituteName;
    }

    /**
     * @param instituteName
     *            the instituteName to set
     */
    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the cityId
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * @param cityId
     *            the cityId to set
     */
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    /**
     * @return the countryId
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     * @param countryId
     *            the countryId to set
     */
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    /**
     * @return the latitute
     */
    public String getLatitute() {
        return latitute;
    }

    /**
     * @param latitute
     *            the latitute to set
     */
    public void setLatitute(String latitute) {
        this.latitute = latitute;
    }

    /**
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude
     *            the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the totalStudent
     */
    public Integer getTotalStudent() {
        return totalStudent;
    }

    /**
     * @param totalStudent
     *            the totalStudent to set
     */
    public void setTotalStudent(Integer totalStudent) {
        this.totalStudent = totalStudent;
    }

    /**
     * @return the worldRanking
     */
    public Integer getWorldRanking() {
        return worldRanking;
    }

    /**
     * @param worldRanking
     *            the worldRanking to set
     */
    public void setWorldRanking(Integer worldRanking) {
        this.worldRanking = worldRanking;
    }

    /**
     * @return the accreditation
     */
    public String getAccreditation() {
        return accreditation;
    }

    /**
     * @param accreditation
     *            the accreditation to set
     */
    public void setAccreditation(String accreditation) {
        this.accreditation = accreditation;
    }

    /**
     * @return the instituteLogoUrl
     */
    public String getInstituteLogoUrl() {
        return instituteLogoUrl;
    }

    /**
     * @param instituteLogoUrl
     *            the instituteLogoUrl to set
     */
    public void setInstituteLogoUrl(String instituteLogoUrl) {
        this.instituteLogoUrl = instituteLogoUrl;
    }

    /**
     * @return the averageCostFrom
     */
    public String getAverageCostFrom() {
        return averageCostFrom;
    }

    /**
     * @param averageCostFrom
     *            the averageCostFrom to set
     */
    public void setAverageCostFrom(String averageCostFrom) {
        this.averageCostFrom = averageCostFrom;
    }

    /**
     * @return the averageCostTo
     */
    public String getAverageCostTo() {
        return averageCostTo;
    }

    /**
     * @param averageCostTo
     *            the averageCostTo to set
     */
    public void setAverageCostTo(String averageCostTo) {
        this.averageCostTo = averageCostTo;
    }

    /**
     * @return the instituteTypeId
     */
    public String getInstituteTypeId() {
        return instituteTypeId;
    }

    /**
     * @param instituteTypeId
     *            the instituteTypeId to set
     */
    public void setInstituteTypeId(String instituteTypeId) {
        this.instituteTypeId = instituteTypeId;
    }

    /**
     * @return the openingHour
     */
    public String getOpeningHour() {
        return openingHour;
    }

    /**
     * @param openingHour
     *            the openingHour to set
     */
    public void setOpeningHour(String openingHour) {
        this.openingHour = openingHour;
    }

    /**
     * @return the closingHour
     */
    public String getClosingHour() {
        return closingHour;
    }

    /**
     * @param closingHour
     *            the closingHour to set
     */
    public void setClosingHour(String closingHour) {
        this.closingHour = closingHour;
    }

    /**
     * @return the enrolment
     */
    public String getEnrolment() {
        return enrolment;
    }

    /**
     * @param enrolment
     *            the enrolment to set
     */
    public void setEnrolment(String enrolment) {
        this.enrolment = enrolment;
    }

    /**
     * @return the tuitionFessPaymentPlan
     */
    public String getTuitionFessPaymentPlan() {
        return tuitionFessPaymentPlan;
    }

    /**
     * @param tuitionFessPaymentPlan
     *            the tuitionFessPaymentPlan to set
     */
    public void setTuitionFessPaymentPlan(String tuitionFessPaymentPlan) {
        this.tuitionFessPaymentPlan = tuitionFessPaymentPlan;
    }

    /**
     * @return the scholarshipFinancingAssistance
     */
    public String getScholarshipFinancingAssistance() {
        return scholarshipFinancingAssistance;
    }

    /**
     * @param scholarshipFinancingAssistance
     *            the scholarshipFinancingAssistance to set
     */
    public void setScholarshipFinancingAssistance(String scholarshipFinancingAssistance) {
        this.scholarshipFinancingAssistance = scholarshipFinancingAssistance;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber
     *            the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website
     *            the website to set
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the instituteMedias
     */
    public List<InstituteMedia> getInstituteMedias() {
        return instituteMedias;
    }

    /**
     * @param instituteMedias
     *            the instituteMedias to set
     */
    public void setInstituteMedias(List<InstituteMedia> instituteMedias) {
        this.instituteMedias = instituteMedias;
    }

    public List<InstituteCampusDto> getInstituteCampus() {
        return instituteCampus;
    }

    public void setInstituteCampus(List<InstituteCampusDto> instituteCampus) {
        this.instituteCampus = instituteCampus;
    }

    /**
     * @return the instituteCategoryTypeId
     */
    public String getInstituteCategoryTypeId() {
        return instituteCategoryTypeId;
    }

    /**
     * @param instituteCategoryTypeId the instituteCategoryTypeId to set
     */
    public void setInstituteCategoryTypeId(String instituteCategoryTypeId) {
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
    public void setCampusType(String campusType) {
        this.campusType = campusType;
    }

}
