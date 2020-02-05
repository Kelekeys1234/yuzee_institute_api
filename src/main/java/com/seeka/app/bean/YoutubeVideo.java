package com.seeka.app.bean;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "youtube_video")
public class YoutubeVideo implements java.io.Serializable {

    private String youtubeVideoId;
    private BigInteger id;
    private String type;
    private String videoTitle;
    private String description;
    private String vedioId;
    private String url;
    private Date createdOn;
    private Date updatedOn;
    private String createdBy;
    private String updatedBy;
    private String name;
    private String uploadDate;

    /**
     * @return the youtubeVideoId
     */
    @Id
    @GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "youtubeVideoId", columnDefinition = "uniqueidentifier")
    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }

    /**
     * @param youtubeVideoId
     *            the youtubeVideoId to set
     */
    public void setYoutubeVideoId(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
    }

    /**
     * @return the id
     */
    @Column(name = "id")
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
     * @return the type
     */
    @Column(name = "type")
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the videoTitle
     */
    @Column(name = "video_title")
    public String getVideoTitle() {
        return videoTitle;
    }

    /**
     * @param videoTitle
     *            the videoTitle to set
     */
    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
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
     * @return the vedioId
     */
    @Column(name = "video_id")
    public String getVedioId() {
        return vedioId;
    }

    /**
     * @param vedioId
     *            the vedioId to set
     */
    public void setVedioId(String vedioId) {
        this.vedioId = vedioId;
    }

    /**
     * @return the url
     */
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
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
     * @return the name
     */
    @Transient
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
     * @return the uploadDate
     */
    @Column(name = "upload_date")
    public String getUploadDate() {
        return uploadDate;
    }

    /**
     * @param uploadDate
     *            the uploadDate to set
     */
    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
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
        result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
        result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
        result = prime * result + ((updatedOn == null) ? 0 : updatedOn.hashCode());
        result = prime * result + ((uploadDate == null) ? 0 : uploadDate.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + ((vedioId == null) ? 0 : vedioId.hashCode());
        result = prime * result + ((videoTitle == null) ? 0 : videoTitle.hashCode());
        result = prime * result + ((youtubeVideoId == null) ? 0 : youtubeVideoId.hashCode());
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
        YoutubeVideo other = (YoutubeVideo) obj;
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
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
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
        if (uploadDate == null) {
            if (other.uploadDate != null)
                return false;
        } else if (!uploadDate.equals(other.uploadDate))
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        if (vedioId == null) {
            if (other.vedioId != null)
                return false;
        } else if (!vedioId.equals(other.vedioId))
            return false;
        if (videoTitle == null) {
            if (other.videoTitle != null)
                return false;
        } else if (!videoTitle.equals(other.videoTitle))
            return false;
        if (youtubeVideoId == null) {
            if (other.youtubeVideoId != null)
                return false;
        } else if (!youtubeVideoId.equals(other.youtubeVideoId))
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
        return "YoutubeVideo [youtubeVideoId=" + youtubeVideoId + ", id=" + id + ", type=" + type + ", videoTitle=" + videoTitle + ", description=" + description + ", vedioId="
                        + vedioId + ", url=" + url + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", name="
                        + name + ", uploadDate=" + uploadDate + "]";
    }
}