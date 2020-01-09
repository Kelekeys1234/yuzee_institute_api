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
    private String averageCostFrom;
    private String averageCostTo;
    private BigInteger TypeId;
    private String enrolment;
    private String tuitionFessPaymentPlan;
    private String scholarshipFinancingAssistance;
    private String website;
    private List<InstituteMedia> medias;
    private BigInteger categoryTypeId;
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
    private String worldRankingType;

    /**
     * @return the id
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
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
     * @param name
     *            the name to set
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
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the cityId
     */
    public BigInteger getCityId() {
        return cityId;
    }

    /**
     * @param cityId
     *            the cityId to set
     */
    public void setCityId(BigInteger cityId) {
        this.cityId = cityId;
    }

    /**
     * @return the countryId
     */
    public BigInteger getCountryId() {
        return countryId;
    }

    /**
     * @param countryId
     *            the countryId to set
     */
    public void setCountryId(BigInteger countryId) {
        this.countryId = countryId;
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
     * @return the logoUrl
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * @param logoUrl
     *            the logoUrl to set
     */
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
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
     * @return the typeId
     */
    public BigInteger getTypeId() {
        return TypeId;
    }

    /**
     * @param typeId
     *            the typeId to set
     */
    public void setTypeId(BigInteger typeId) {
        TypeId = typeId;
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
     * @return the medias
     */
    public List<InstituteMedia> getMedias() {
        return medias;
    }

    /**
     * @param medias
     *            the medias to set
     */
    public void setMedias(List<InstituteMedia> medias) {
        this.medias = medias;
    }

    /**
     * @return the categoryTypeId
     */
    public BigInteger getCategoryTypeId() {
        return categoryTypeId;
    }

    /**
     * @param categoryTypeId
     *            the categoryTypeId to set
     */
    public void setCategoryTypeId(BigInteger categoryTypeId) {
        this.categoryTypeId = categoryTypeId;
    }

    /**
     * @return the campusType
     */
    public String getCampusType() {
        return campusType;
    }

    /**
     * @param campusType
     *            the campusType to set
     */
    public void setCampusType(String campusType) {
        this.campusType = campusType;
    }

    /**
     * @return the campusName
     */
    public String getCampusName() {
        return campusName;
    }

    /**
     * @param campusName
     *            the campusName to set
     */
    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    /**
     * @return the latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude
     *            the latitude to set
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude
     *            the longitude to set
     */
    public void setLongitude(Double longitude) {
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
     * @return the openingFrom
     */
    public String getOpeningFrom() {
        return openingFrom;
    }

    /**
     * @param openingFrom
     *            the openingFrom to set
     */
    public void setOpeningFrom(String openingFrom) {
        this.openingFrom = openingFrom;
    }

    /**
     * @return the openingTo
     */
    public String getOpeningTo() {
        return openingTo;
    }

    /**
     * @param openingTo
     *            the openingTo to set
     */
    public void setOpeningTo(String openingTo) {
        this.openingTo = openingTo;
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
     * @return the offerService
     */
    public List<BigInteger> getOfferService() {
        return offerService;
    }

    /**
     * @param offerService
     *            the offerService to set
     */
    public void setOfferService(List<BigInteger> offerService) {
        this.offerService = offerService;
    }

    /**
     * @return the accreditation
     */
    public List<BigInteger> getAccreditation() {
        return accreditation;
    }

    /**
     * @param accreditation
     *            the accreditation to set
     */
    public void setAccreditation(List<BigInteger> accreditation) {
        this.accreditation = accreditation;
    }

    /**
     * @return the intakes
     */
    public List<String> getIntakes() {
        return intakes;
    }

    /**
     * @param intakes
     *            the intakes to set
     */
    public void setIntakes(List<String> intakes) {
        this.intakes = intakes;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     *            the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy
     *            the updatedBy to set
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the worldRankingType
     */
    public String getWorldRankingType() {
        return worldRankingType;
    }

    /**
     * @param worldRankingType
     *            the worldRankingType to set
     */
    public void setWorldRankingType(String worldRankingType) {
        this.worldRankingType = worldRankingType;
    }

}
