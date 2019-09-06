package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.seeka.app.bean.AgentEducationDetail;
import com.seeka.app.bean.AgentMediaDocumentation;
import com.seeka.app.bean.AgentServiceOffered;
import com.seeka.app.bean.Skill;

public class EducationAgentDto {

    private String firstName;
    private String lastName;
    private String description;
    private BigInteger city;
    private BigInteger country;
    private String phoneNumber;
    private String email;
    private String createdBy;
    private Date createdOn;
    private String updatedBy;
    private Date updatedOn;
    private String deletedBy;
    private Date deletedOn;
    private List<Skill> skill;
    private List<AgentServiceOfferedDto> agentServiceOffereds;
    private List<AgentEducationDetailDto> agentEducationDetails;
    private List<AgentMediaDocumentationDto> agentMediaDocumentations;

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

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Date getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(Date deletedOn) {
        this.deletedOn = deletedOn;
    }

    public List<Skill> getSkill() {
        return skill;
    }

    public void setSkill(List<Skill> skill) {
        this.skill = skill;
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

    public List<AgentMediaDocumentationDto> getAgentMediaDocumentations() {
        return agentMediaDocumentations;
    }

    public void setAgentMediaDocumentations(List<AgentMediaDocumentationDto> agentMediaDocumentations) {
        this.agentMediaDocumentations = agentMediaDocumentations;
    }
}
