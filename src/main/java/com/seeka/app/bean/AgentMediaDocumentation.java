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
@Table(name = "agent_media_documentation")
public class AgentMediaDocumentation implements Serializable{
    
    /**
    *
    */
    private static final long serialVersionUID = 8492390790670110780L;
    private BigInteger id;
    private String documentName;
    private String type;
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
    

    @Column(name = "document_name")
    public String getDocumentName() {
        return documentName;
    }
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
    
    @Column(name = "type")
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
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
