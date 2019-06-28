package com.seeka.app.bean;

import static javax.persistence.GenerationType.IDENTITY;

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
@Table(name = "institute", catalog = "seeka_dev_v5")
public class Institute implements java.io.Serializable {

    private BigInteger id;
    private City city;
    private Country country;
    private InstituteType instituteType;
    private String name;
    private Integer worldRanking;
    private String accredited;
    private String intPhNum;
    private String intEmails;
    private String website;
    private Integer insImgCnt;
    private Integer TNumOfStu;
    private String latitude;
    private String longitude;
    private String address;
    private String description;
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

    public Institute() {
    }

    public Institute(City city, Country country, InstituteType instituteType, String name, Integer worldRanking, String accredited, String intPhNum, String intEmails,
                    String website, Integer insImgCnt, Integer TNumOfStu, String latitude, String longitude, String address, String description, Boolean isActive, Date createdOn,
                    Date updatedOn, Date deletedOn, String createdBy, String updatedBy, Boolean isDeleted) {
        this.city = city;
        this.country = country;
        this.instituteType = instituteType;
        this.name = name;
        this.worldRanking = worldRanking;
        this.accredited = accredited;
        this.intPhNum = intPhNum;
        this.intEmails = intEmails;
        this.website = website;
        this.insImgCnt = insImgCnt;
        this.TNumOfStu = TNumOfStu;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.description = description;
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

    @Column(name = "world_ranking")
    public Integer getWorldRanking() {
        return this.worldRanking;
    }

    public void setWorldRanking(Integer worldRanking) {
        this.worldRanking = worldRanking;
    }

    @Column(name = "accredited", length = 20)
    public String getAccredited() {
        return this.accredited;
    }

    public void setAccredited(String accredited) {
        this.accredited = accredited;
    }

    @Column(name = "int_ph_num", length = 100)
    public String getIntPhNum() {
        return this.intPhNum;
    }

    public void setIntPhNum(String intPhNum) {
        this.intPhNum = intPhNum;
    }

    @Column(name = "int_emails", length = 250)
    public String getIntEmails() {
        return this.intEmails;
    }

    public void setIntEmails(String intEmails) {
        this.intEmails = intEmails;
    }

    @Column(name = "website", length = 500)
    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Column(name = "ins_img_cnt")
    public Integer getInsImgCnt() {
        return this.insImgCnt;
    }

    public void setInsImgCnt(Integer insImgCnt) {
        this.insImgCnt = insImgCnt;
    }

    @Column(name = "t_num_of_stu")
    public Integer getTNumOfStu() {
        return this.TNumOfStu;
    }

    public void setTNumOfStu(Integer TNumOfStu) {
        this.TNumOfStu = TNumOfStu;
    }

    @Column(name = "latitude", length = 100)
    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Column(name = "longitude", length = 100)
    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Column(name = "address", length = 500)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "description", length = 500)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((TNumOfStu == null) ? 0 : TNumOfStu.hashCode());
        result = prime * result + ((accredited == null) ? 0 : accredited.hashCode());
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
        result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
        result = prime * result + ((deletedOn == null) ? 0 : deletedOn.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((insImgCnt == null) ? 0 : insImgCnt.hashCode());
        result = prime * result + ((instituteDetailsObj == null) ? 0 : instituteDetailsObj.hashCode());
        result = prime * result + ((instituteImageUrl == null) ? 0 : instituteImageUrl.hashCode());
        result = prime * result + ((instituteLogoUrl == null) ? 0 : instituteLogoUrl.hashCode());
        result = prime * result + ((instituteType == null) ? 0 : instituteType.hashCode());
        result = prime * result + ((intEmails == null) ? 0 : intEmails.hashCode());
        result = prime * result + ((intPhNum == null) ? 0 : intPhNum.hashCode());
        result = prime * result + ((isActive == null) ? 0 : isActive.hashCode());
        result = prime * result + ((isDeleted == null) ? 0 : isDeleted.hashCode());
        result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
        result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((serviceList == null) ? 0 : serviceList.hashCode());
        result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
        result = prime * result + ((updatedOn == null) ? 0 : updatedOn.hashCode());
        result = prime * result + ((website == null) ? 0 : website.hashCode());
        result = prime * result + ((worldRanking == null) ? 0 : worldRanking.hashCode());
        return result;
    }

    /* (non-Javadoc)
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
        if (TNumOfStu == null) {
            if (other.TNumOfStu != null)
                return false;
        } else if (!TNumOfStu.equals(other.TNumOfStu))
            return false;
        if (accredited == null) {
            if (other.accredited != null)
                return false;
        } else if (!accredited.equals(other.accredited))
            return false;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
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
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (insImgCnt == null) {
            if (other.insImgCnt != null)
                return false;
        } else if (!insImgCnt.equals(other.insImgCnt))
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
        if (intEmails == null) {
            if (other.intEmails != null)
                return false;
        } else if (!intEmails.equals(other.intEmails))
            return false;
        if (intPhNum == null) {
            if (other.intPhNum != null)
                return false;
        } else if (!intPhNum.equals(other.intPhNum))
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
        if (latitude == null) {
            if (other.latitude != null)
                return false;
        } else if (!latitude.equals(other.latitude))
            return false;
        if (longitude == null) {
            if (other.longitude != null)
                return false;
        } else if (!longitude.equals(other.longitude))
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
        if (website == null) {
            if (other.website != null)
                return false;
        } else if (!website.equals(other.website))
            return false;
        if (worldRanking == null) {
            if (other.worldRanking != null)
                return false;
        } else if (!worldRanking.equals(other.worldRanking))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Institute [id=" + id + ", city=" + city + ", country=" + country + ", instituteType=" + instituteType + ", name=" + name + ", worldRanking=" + worldRanking
                        + ", accredited=" + accredited + ", intPhNum=" + intPhNum + ", intEmails=" + intEmails + ", website=" + website + ", insImgCnt=" + insImgCnt
                        + ", TNumOfStu=" + TNumOfStu + ", latitude=" + latitude + ", longitude=" + longitude + ", address=" + address + ", description=" + description
                        + ", isActive=" + isActive + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", deletedOn=" + deletedOn + ", createdBy=" + createdBy
                        + ", updatedBy=" + updatedBy + ", isDeleted=" + isDeleted + ", instituteLogoUrl=" + instituteLogoUrl + ", instituteImageUrl=" + instituteImageUrl
                        + ", instituteDetailsObj=" + instituteDetailsObj + ", serviceList=" + serviceList + "]";
    }

}
