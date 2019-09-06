package com.seeka.app.bean;

import static javax.persistence.GenerationType.IDENTITY;

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
@Table(name = "agent_education_detail")
public class AgentEducationDetail {

    /**
    *
    */
    private BigInteger id;
    private String course;
    private Date durationFrom;
    private Date durationTo;
    private String institute;
    private EducationAgent educationAgent;
    private String createdBy;
    private Date createdOn;
    private String updatedBy;
    private Date updatedOn;
    private String deletedBy;
    private Date deletedOn;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Column(name = "course")
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Column(name = "duration_from")
    public Date getDurationFrom() {
        return durationFrom;
    }

    public void setDurationFrom(Date durationFrom) {
        this.durationFrom = durationFrom;
    }

    @Column(name = "duration_to")
    public Date getDurationTo() {
        return durationTo;
    }

    public void setDurationTo(Date durationTo) {
        this.durationTo = durationTo;
    }

    @Column(name = "institute")
    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "education_agent")
    public EducationAgent getEducationAgent() {
        return educationAgent;
    }

    public void setEducationAgent(EducationAgent educationAgent) {
        this.educationAgent = educationAgent;
    }

    @Column(name = "created_by")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "created_on")
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Column(name = "updated_by")
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Column(name = "updated_on")
    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Column(name = "deleted_by")
    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    @Column(name = "deleted_on")
    public Date getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(Date deletedOn) {
        this.deletedOn = deletedOn;
    }

}
