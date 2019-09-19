package com.seeka.app.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "education_agent_partnerships")
public class EducationAgentPartnerships implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3766742694768712352L;
    private BigInteger id;
    private Course course;
    private Institute institute;
    private Date createdOn;
    private String createdBy;
    private Date UpdatedOn;
    private String UpdatedBy;
    private Date DeletedOn;
    private Boolean isDeleted;
    private EducationAgent educationAgent;
    private Country country;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "institute_id")
    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    @Column(name = "created_on")
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Column(name = "created_by")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "updated_on")
    public Date getUpdatedOn() {
        return UpdatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        UpdatedOn = updatedOn;
    }

    @Column(name = "updated_by")
    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    @Column(name = "deleted_on")
    public Date getDeletedOn() {
        return DeletedOn;
    }

    public void setDeletedOn(Date deletedOn) {
        DeletedOn = deletedOn;
    }

    @Column(name = "is_deleted")
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "education_agent")
    public EducationAgent getEducationAgent() {
        return educationAgent;
    }

    public void setEducationAgent(EducationAgent educationAgent) {
        this.educationAgent = educationAgent;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

}
