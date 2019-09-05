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
@Table(name = "education_agent_skill")
public class EducationAgentSkill implements Serializable{
    
    /**
    *
    */
    private static final long serialVersionUID = 8492390790670110780L;
    private BigInteger id;
    private BigInteger skillId;
    private EducationAgent educationAgent;
    private String createdBy;
    private Date createdOn;
    private String updatedBy;
    private Date updatedOn;
    private String deletedBy;
    private Date deletedOn;
    
    public EducationAgentSkill() {
        super();
    }
    
    public EducationAgentSkill(BigInteger id, BigInteger skillId, EducationAgent educationAgent) {
        super();
        this.id = id;
        this.skillId = skillId;
        this.educationAgent = educationAgent;
    }
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public BigInteger getId() {
        return id;
    }
    public void setId(BigInteger id) {
        this.id = id;
    }
    
    @Column(name = "skill_id")
    public BigInteger getSkillId() {
        return skillId;
    }
    public void setSkillId(BigInteger skillId) {
        this.skillId = skillId;
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
