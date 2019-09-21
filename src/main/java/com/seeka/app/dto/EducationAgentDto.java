package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

public class EducationAgentDto {

    private String firstName;
    private String lastName;
    private String description;
    private BigInteger city;
    private BigInteger country;
    private String phoneNumber;
    private String email;
    private String createdBy;
    private String updatedBy;
    private String deletedBy;
    private List<BigInteger> skill;
    private List<String> accomplishment;
    private List<AgentServiceOfferedDto> agentServiceOffereds;
    private List<AgentEducationDetailDto> agentEducationDetails;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigInteger getCity() {
        return city;
    }

    public void setCity(BigInteger city) {
        this.city = city;
    }

    public BigInteger getCountry() {
        return country;
    }

    public void setCountry(BigInteger country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public List<BigInteger> getSkill() {
        return skill;
    }

    public void setSkill(List<BigInteger> skill) {
        this.skill = skill;
    }

    public List<String> getAccomplishment() {
        return accomplishment;
    }

    public void setAccomplishment(List<String> accomplishment) {
        this.accomplishment = accomplishment;
    }

    public List<AgentServiceOfferedDto> getAgentServiceOffereds() {
        return agentServiceOffereds;
    }

    public void setAgentServiceOffereds(List<AgentServiceOfferedDto> agentServiceOffereds) {
        this.agentServiceOffereds = agentServiceOffereds;
    }

    public List<AgentEducationDetailDto> getAgentEducationDetails() {
        return agentEducationDetails;
    }

    public void setAgentEducationDetails(List<AgentEducationDetailDto> agentEducationDetails) {
        this.agentEducationDetails = agentEducationDetails;
    }
}