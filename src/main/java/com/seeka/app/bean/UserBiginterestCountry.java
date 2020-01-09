package com.seeka.app.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigInteger;

// Generated 7 Jun, 2019 2:45:49 PM by Hibernate Tools 4.3.1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * UserBiginterestCountry generated by hbm2java
 */
@Entity
@Table(name = "user_biginterest_country")
public class UserBiginterestCountry implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5961885660772909758L;
    private BigInteger id;
    private String country;
    private BigInteger userInfo;
    private Date createdOn;
    private Date updatedOn;
    private Date deletedOn;
    private String createdBy;
    private String updatedBy;
    private Boolean isDeleted;

    public UserBiginterestCountry() {
    }

    public UserBiginterestCountry(String country, BigInteger userInfo) {
        this.country = country;
        this.userInfo = userInfo;
    }

    public UserBiginterestCountry(String country, BigInteger userInfo, Date createdOn, Date updatedOn, Date deletedOn, String createdBy, String updatedBy, Boolean isDeleted) {
        this.country = country;
        this.userInfo = userInfo;
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

    @Column(name = "country_name", length = 45)
    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "user_id", length = 45)
    public BigInteger getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(BigInteger userInfo) {
        this.userInfo = userInfo;
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

}
