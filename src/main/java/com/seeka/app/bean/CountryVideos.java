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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "country_videos")
public class CountryVideos implements java.io.Serializable {

    private BigInteger id;
    private Country country;
    private String youTubeChannel;
    private String youTubeId1;
    private String youTubeId2;
    private String youTubeId3;
    private String youTubeId4;
    private Date createdOn;
    private Date updatedOn;
    private String createdBy;
    private String updatedBy;

    private String countryName;

    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public BigInteger getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(BigInteger id) {
        this.id = id;
    }

    /**
     * @return the youTubeChannel
     */
    @Column(name = "youtube_channel")
    public String getYouTubeChannel() {
        return youTubeChannel;
    }

    /**
     * @param youTubeChannel
     *            the youTubeChannel to set
     */
    public void setYouTubeChannel(String youTubeChannel) {
        this.youTubeChannel = youTubeChannel;
    }

    /**
     * @return the youTubeId1
     */
    @Column(name = "youtube_id_1")
    public String getYouTubeId1() {
        return youTubeId1;
    }

    /**
     * @param youTubeId1
     *            the youTubeId1 to set
     */
    public void setYouTubeId1(String youTubeId1) {
        this.youTubeId1 = youTubeId1;
    }

    /**
     * @return the youTubeId2
     */
    @Column(name = "youtube_id_2")
    public String getYouTubeId2() {
        return youTubeId2;
    }

    /**
     * @param youTubeId2
     *            the youTubeId2 to set
     */
    public void setYouTubeId2(String youTubeId2) {
        this.youTubeId2 = youTubeId2;
    }

    /**
     * @return the youTubeId3
     */
    @Column(name = "youtube_id_3")
    public String getYouTubeId3() {
        return youTubeId3;
    }

    /**
     * @param youTubeId3
     *            the youTubeId3 to set
     */
    public void setYouTubeId3(String youTubeId3) {
        this.youTubeId3 = youTubeId3;
    }

    /**
     * @return the youTubeId4
     */
    @Column(name = "youtube_id_4")
    public String getYouTubeId4() {
        return youTubeId4;
    }

    /**
     * @param youTubeId4
     *            the youTubeId4 to set
     */
    public void setYouTubeId4(String youTubeId4) {
        this.youTubeId4 = youTubeId4;
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

    /**
     * @return the country
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
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
     * @return the countryName
     */
    @Transient
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName
     *            the countryName to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
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
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((countryName == null) ? 0 : countryName.hashCode());
        result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
        result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
        result = prime * result + ((updatedOn == null) ? 0 : updatedOn.hashCode());
        result = prime * result + ((youTubeChannel == null) ? 0 : youTubeChannel.hashCode());
        result = prime * result + ((youTubeId1 == null) ? 0 : youTubeId1.hashCode());
        result = prime * result + ((youTubeId2 == null) ? 0 : youTubeId2.hashCode());
        result = prime * result + ((youTubeId3 == null) ? 0 : youTubeId3.hashCode());
        result = prime * result + ((youTubeId4 == null) ? 0 : youTubeId4.hashCode());
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
        CountryVideos other = (CountryVideos) obj;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (countryName == null) {
            if (other.countryName != null)
                return false;
        } else if (!countryName.equals(other.countryName))
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
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
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
        if (youTubeChannel == null) {
            if (other.youTubeChannel != null)
                return false;
        } else if (!youTubeChannel.equals(other.youTubeChannel))
            return false;
        if (youTubeId1 == null) {
            if (other.youTubeId1 != null)
                return false;
        } else if (!youTubeId1.equals(other.youTubeId1))
            return false;
        if (youTubeId2 == null) {
            if (other.youTubeId2 != null)
                return false;
        } else if (!youTubeId2.equals(other.youTubeId2))
            return false;
        if (youTubeId3 == null) {
            if (other.youTubeId3 != null)
                return false;
        } else if (!youTubeId3.equals(other.youTubeId3))
            return false;
        if (youTubeId4 == null) {
            if (other.youTubeId4 != null)
                return false;
        } else if (!youTubeId4.equals(other.youTubeId4))
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
        return "CountryVideos [id=" + id + ", country=" + country + ", youTubeChannel=" + youTubeChannel + ", youTubeId1=" + youTubeId1 + ", youTubeId2=" + youTubeId2
                        + ", youTubeId3=" + youTubeId3 + ", youTubeId4=" + youTubeId4 + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", createdBy=" + createdBy
                        + ", updatedBy=" + updatedBy + ", countryName=" + countryName + "]";
    }
}