package com.seeka.app.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigInteger;

// Generated 7 Jun, 2019 2:45:49 PM by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Institute generated by hbm2java
 */
@Entity
@Table(name = "institute")
public class Institute implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7598262998124127162L;
    private BigInteger id;
    private City city;
    private Country country;
    private InstituteType instituteType;
    private String name;
    private Boolean isActive;
    private Date createdOn;
    private Date updatedOn;
    private Date deletedOn;
    private String createdBy;
    private String updatedBy;
    private Boolean isDeleted;

    private String instituteLogoUrl;
    private String instituteImageUrl;
    private InstituteDetails instituteDetailsObj;
    private List<InstituteService> serviceList;
    private String description;

    private Integer worldRanking;
    private String accreditation;
    private String website;
    private String address;
    private Integer totalStudent;
    private Double latitute;
    private Double longitude;
    private String email;
    private String avgCostOfLiving;
    private String campus;
    private String phoneNumber;
    private String campusType;
    private String lastUpdated;
    private String openingFrom;
    private String openingTo;
    private String campusName;
    private InstituteCategoryType instituteCategoryType;
    
    private String enrolment;
    private String tuitionFessPaymentPlan;
    private String scholarshipFinancingAssistance;
    
    private Integer courseCount;

    @Transient
    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Institute() {
    }

    public Institute(City city, Country country, InstituteType instituteType, String name, Boolean isActive, Date createdOn, Date updatedOn, Date deletedOn, String createdBy,
                    String updatedBy, Boolean isDeleted) {
        this.city = city;
        this.country = country;
        this.instituteType = instituteType;
        this.name = name;
        this.isActive = isActive;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.deletedOn = deletedOn;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.isDeleted = isDeleted;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public BigInteger getId() {
        return this.id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "institute_type_id")
    public InstituteType getInstituteType() {
        return this.instituteType;
    }

    public void setInstituteType(InstituteType instituteType) {
        this.instituteType = instituteType;
    }

    @Column(name = "name", length = 145)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "is_active")
    public Boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", length = 19)
    public Date getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on", length = 19)
    public Date getUpdatedOn() {
        return this.updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_on", length = 19)
    public Date getDeletedOn() {
        return this.deletedOn;
    }

    public void setDeletedOn(Date deletedOn) {
        this.deletedOn = deletedOn;
    }

    @Column(name = "created_by", length = 50)
    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "updated_by", length = 50)
    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Column(name = "is_deleted")
    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return the instituteLogoUrl
     */
    @Transient
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
     * @return the instituteImageUrl
     */
    @Transient
    public String getInstituteImageUrl() {
        return instituteImageUrl;
    }

    /**
     * @param instituteImageUrl
     *            the instituteImageUrl to set
     */
    public void setInstituteImageUrl(String instituteImageUrl) {
        this.instituteImageUrl = instituteImageUrl;
    }

    /**
     * @return the instituteDetailsObj
     */
    @Transient
    public InstituteDetails getInstituteDetailsObj() {
        return instituteDetailsObj;
    }

    /**
     * @param instituteDetailsObj
     *            the instituteDetailsObj to set
     */
    public void setInstituteDetailsObj(InstituteDetails instituteDetailsObj) {
        this.instituteDetailsObj = instituteDetailsObj;
    }

    /**
     * @return the serviceList
     */
    @Transient
    public List<InstituteService> getServiceList() {
        return serviceList;
    }

    /**
     * @param serviceList
     *            the serviceList to set
     */
    public void setServiceList(List<InstituteService> serviceList) {
        this.serviceList = serviceList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
        result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
        result = prime * result + ((deletedOn == null) ? 0 : deletedOn.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((instituteDetailsObj == null) ? 0 : instituteDetailsObj.hashCode());
        result = prime * result + ((instituteImageUrl == null) ? 0 : instituteImageUrl.hashCode());
        result = prime * result + ((instituteLogoUrl == null) ? 0 : instituteLogoUrl.hashCode());
        result = prime * result + ((instituteType == null) ? 0 : instituteType.hashCode());
        result = prime * result + ((isActive == null) ? 0 : isActive.hashCode());
        result = prime * result + ((isDeleted == null) ? 0 : isDeleted.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((serviceList == null) ? 0 : serviceList.hashCode());
        result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
        result = prime * result + ((updatedOn == null) ? 0 : updatedOn.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Institute other = (Institute) obj;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (createdBy == null) {
            if (other.createdBy != null)
                return false;
        } else if (!createdBy.equals(other.createdBy))
            return false;
        if (createdOn == null) {
            if (other.createdOn != null)
                return false;
        } else if (!createdOn.equals(other.createdOn))
            return false;
        if (deletedOn == null) {
            if (other.deletedOn != null)
                return false;
        } else if (!deletedOn.equals(other.deletedOn))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (instituteDetailsObj == null) {
            if (other.instituteDetailsObj != null)
                return false;
        } else if (!instituteDetailsObj.equals(other.instituteDetailsObj))
            return false;
        if (instituteImageUrl == null) {
            if (other.instituteImageUrl != null)
                return false;
        } else if (!instituteImageUrl.equals(other.instituteImageUrl))
            return false;
        if (instituteLogoUrl == null) {
            if (other.instituteLogoUrl != null)
                return false;
        } else if (!instituteLogoUrl.equals(other.instituteLogoUrl))
            return false;
        if (instituteType == null) {
            if (other.instituteType != null)
                return false;
        } else if (!instituteType.equals(other.instituteType))
            return false;
        if (isActive == null) {
            if (other.isActive != null)
                return false;
        } else if (!isActive.equals(other.isActive))
            return false;
        if (isDeleted == null) {
            if (other.isDeleted != null)
                return false;
        } else if (!isDeleted.equals(other.isDeleted))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (serviceList == null) {
            if (other.serviceList != null)
                return false;
        } else if (!serviceList.equals(other.serviceList))
            return false;
        if (updatedBy == null) {
            if (other.updatedBy != null)
                return false;
        } else if (!updatedBy.equals(other.updatedBy))
            return false;
        if (updatedOn == null) {
            if (other.updatedOn != null)
                return false;
        } else if (!updatedOn.equals(other.updatedOn))
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Institute [id=" + id + ", city=" + city + ", country=" + country + ", instituteType=" + instituteType + ", name=" + name + ", isActive=" + isActive + ", createdOn="
                        + createdOn + ", updatedOn=" + updatedOn + ", deletedOn=" + deletedOn + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", isDeleted=" + isDeleted
                        + ", instituteLogoUrl=" + instituteLogoUrl + ", instituteImageUrl=" + instituteImageUrl + ", instituteDetailsObj=" + instituteDetailsObj + ", serviceList="
                        + serviceList + "]";
    }

    /**
     * @return the description
     */
    @Column(name = "description")
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
     * @return the worldRanking
     */
    @Column(name = "world_ranking")
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
    @Column(name = "accreditation")
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
     * @return the website
     */
    @Column(name = "website")
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
    @Column(name = "address")
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
     * @return the totalStudent
     */
    @Column(name = "total_student")
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
     * @return the latitute
     */
    @Column(name = "latitute")
    public Double getLatitute() {
        return latitute;
    }

    /**
     * @param latitute
     *            the latitute to set
     */
    public void setLatitute(Double latitute) {
        this.latitute = latitute;
    }

    /**
     * @return the longitude
     */
    @Column(name = "longitute")
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
     * @return the email
     */
    @Column(name = "email")
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
     * @return the avgCostOfLiving
     */
    @Column(name = "avg_cost_of_living")
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

    /**
     * @return the campus
     */
    @Column(name = "campus")
    public String getCampus() {
        return campus;
    }

    /**
     * @param campus
     *            the campus to set
     */
    public void setCampus(String campus) {
        this.campus = campus;
    }

    /**
     * @return the phoneNumber
     */
    @Column(name = "phone_number")
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
     * @return the campusType
     */
    @Column(name = "campus_type")
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

    @Column(name = "opening_from")
    public String getOpeningFrom() {
        return openingFrom;
    }

    public void setOpeningFrom(String openingFrom) {
        this.openingFrom = openingFrom;
    }

    @Column(name = "opening_to")
    public String getOpeningTo() {
        return openingTo;
    }

    public void setOpeningTo(String openingTo) {
        this.openingTo = openingTo;
    }

    @Column(name = "campus_name")
    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    /**
     * @return the instituteCategoryType
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "institute_category_type_id")
    public InstituteCategoryType getInstituteCategoryType() {
        return instituteCategoryType;
    }

    /**
     * @param instituteCategoryType
     *            the instituteCategoryType to set
     */
    public void setInstituteCategoryType(InstituteCategoryType instituteCategoryType) {
        this.instituteCategoryType = instituteCategoryType;
    }

    /**
     * @return the enrolment
     */
    @Column(name = "enrolment_link")
    public String getEnrolment() {
        return enrolment;
    }

    /**
     * @param enrolment the enrolment to set
     */
    public void setEnrolment(String enrolment) {
        this.enrolment = enrolment;
    }

    /**
     * @return the tuitionFessPaymentPlan
     */
    @Column(name = "ttion_fees_p_plan")
    public String getTuitionFessPaymentPlan() {
        return tuitionFessPaymentPlan;
    }

    /**
     * @param tuitionFessPaymentPlan the tuitionFessPaymentPlan to set
     */
    public void setTuitionFessPaymentPlan(String tuitionFessPaymentPlan) {
        this.tuitionFessPaymentPlan = tuitionFessPaymentPlan;
    }

    /**
     * @return the scholarshipFinancingAssistance
     */
    @Column(name = "scholarship_financing_assistance")
    public String getScholarshipFinancingAssistance() {
        return scholarshipFinancingAssistance;
    }

    /**
     * @param scholarshipFinancingAssistance the scholarshipFinancingAssistance to set
     */
    public void setScholarshipFinancingAssistance(String scholarshipFinancingAssistance) {
        this.scholarshipFinancingAssistance = scholarshipFinancingAssistance;
    }

    /**
     * @return the courseCount
     */
    @Transient
    public Integer getCourseCount() {
        return courseCount;
    }

    /**
     * @param courseCount the courseCount to set
     */
    public void setCourseCount(Integer courseCount) {
        this.courseCount = courseCount;
    }

}