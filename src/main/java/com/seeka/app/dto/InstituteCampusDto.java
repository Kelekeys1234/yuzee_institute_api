package com.seeka.app.dto;


public class InstituteCampusDto {

    private String email;
    private String phoneNumber;
    private String address;
    private Double latitute;
    private Double longitute;
    private String openingFrom;
    private String openingTo;
    private String offerService;
    private Integer totalStudent;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitute() {
        return latitute;
    }

    public void setLatitute(Double latitute) {
        this.latitute = latitute;
    }

    public Double getLongitute() {
        return longitute;
    }

    public void setLongitute(Double longitute) {
        this.longitute = longitute;
    }

    public String getOpeningFrom() {
        return openingFrom;
    }

    public void setOpeningFrom(String openingFrom) {
        this.openingFrom = openingFrom;
    }

    public String getOpeningTo() {
        return openingTo;
    }

    public void setOpeningTo(String openingTo) {
        this.openingTo = openingTo;
    }

    public String getOfferService() {
        return offerService;
    }

    public void setOfferService(String offerService) {
        this.offerService = offerService;
    }

    public Integer getTotalStudent() {
        return totalStudent;
    }

    public void setTotalStudent(Integer totalStudent) {
        this.totalStudent = totalStudent;
    }
}
