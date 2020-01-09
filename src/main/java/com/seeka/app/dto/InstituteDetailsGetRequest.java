package com.seeka.app.dto;

public class InstituteDetailsGetRequest {

    private String latitute;
    private String longitude;
    private Integer totalStudent;
    private Integer worldRanking;
    private String accreditation;
    private String email;
    private String phoneNumber;
    private String website;
    private String address;
    private String avgCostOfLiving;

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
     * @return the avgCostOfLiving
     */
    public String getAvgCostOfLiving() {
        return avgCostOfLiving;
    }

    /**
     * @param avgCostOfLiving
     *            the avgCostOfLiving to set
     */
    public void setAvgCostOfLiving(String avgCostOfLiving) {
        this.avgCostOfLiving = avgCostOfLiving;
    }

}
