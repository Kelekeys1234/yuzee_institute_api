package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.seeka.app.bean.City;
import com.seeka.app.bean.Country;
import com.seeka.app.bean.InstituteType;

public class InstituteGetRequestDto {

    private String id;
    private City city;
    private Country country;
    private InstituteType instituteType;
    private String name;
    private List<InstituteDetailsGetRequest> instituteDetails;
    private List<String> instituteYoutubes;

    private Boolean isActive;
    private Date createdOn;
    private Date updatedOn;
    private String createdBy;
    private String updatedBy;
    private String lastUpdated;
    private Integer courseCount;
    private String campusType;
    

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the city
     */
    public City getCity() {
        return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * @return the country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * @return the instituteType
     */
    public InstituteType getInstituteType() {
        return instituteType;
    }

    /**
     * @param instituteType
     *            the instituteType to set
     */
    public void setInstituteType(InstituteType instituteType) {
        this.instituteType = instituteType;
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
     * @return the instituteDetails
     */
    public List<InstituteDetailsGetRequest> getInstituteDetails() {
        return instituteDetails;
    }

    /**
     * @param instituteDetails
     *            the instituteDetails to set
     */
    public void setInstituteDetails(List<InstituteDetailsGetRequest> instituteDetails) {
        this.instituteDetails = instituteDetails;
    }

    /**
     * @return the instituteYoutubes
     */
    public List<String> getInstituteYoutubes() {
        return instituteYoutubes;
    }

    /**
     * @param instituteYoutubes
     *            the instituteYoutubes to set
     */
    public void setInstituteYoutubes(List<String> instituteYoutubes) {
        this.instituteYoutubes = instituteYoutubes;
    }

    /**
     * @return the isActive
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * @param isActive
     *            the isActive to set
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return the createdOn
     */
    public Date getCreatedOn() {
        return createdOn;
    }

    /**
     * @param createdOn
     *            the createdOn to set
     */
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * @return the updatedOn
     */
    public Date getUpdatedOn() {
        return updatedOn;
    }

    /**
     * @param updatedOn
     *            the updatedOn to set
     */
    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
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
     * @return the courseCount
     */
    public Integer getCourseCount() {
        return courseCount;
    }

    /**
     * @param courseCount the courseCount to set
     */
    public void setCourseCount(Integer courseCount) {
        this.courseCount = courseCount;
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