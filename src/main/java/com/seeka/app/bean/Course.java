package com.seeka.app.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigInteger;

// Generated 7 Jun, 2019 2:45:49 PM by Hibernate Tools 4.3.1

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

/**
 * Course generated by hbm2java
 */
@Entity
@Table(name = "course")
public class Course implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8492390790670110780L;
	private BigInteger id;
	private City city;
	private Country country;
	private Faculty faculty;
	private Institute institute;
	private String name;
	private String worldRanking;
	private String stars;
	private String recognition;
	private String recognitionType;
	private String duration;
	private String durationTime;
	private String website;
	private String courseLang;
	private String abbreviation;
	private Date recDate;
	private String remarks;
	private String description;
	private Boolean isActive;
	private Date createdOn;
	private Date updatedOn;
	private Date deletedOn;
	private String createdBy;
	private String updatedBy;
	private Boolean isDeleted;
	private Integer cId;
	private String levelCode;

	public Course() {
	}

	public Course(City city, Country country, Faculty faculty, Institute institute, String name, String worldRanking,
			String stars, String recognition, String recognitionType, String duration, String durationTime,
			String website, String courseLang, String abbreviation, Date recDate, String remarks, String description,
			Boolean isActive, Date createdOn, Date updatedOn, Date deletedOn, String createdBy, String updatedBy,
			Boolean isDeleted) {
		this.city = city;
		this.country = country;
		this.faculty = faculty;
		this.institute = institute;
		this.name = name;
		this.worldRanking = worldRanking;
		this.stars = stars;
		this.recognition = recognition;
		this.recognitionType = recognitionType;
		this.duration = duration;
		this.durationTime = durationTime;
		this.website = website;
		this.courseLang = courseLang;
		this.abbreviation = abbreviation;
		this.recDate = recDate;
		this.remarks = remarks;
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
	@JoinColumn(name = "faculty_id")
	public Faculty getFaculty() {
		return this.faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "institute_id")
	public Institute getInstitute() {
		return this.institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	@Column(name = "name", length = 245)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "world_ranking", length = 50)
	public String getWorldRanking() {
		return this.worldRanking;
	}

	public void setWorldRanking(String worldRanking) {
		this.worldRanking = worldRanking;
	}

	@Column(name = "stars", length = 50)
	public String getStars() {
		return this.stars;
	}

	public void setStars(String stars) {
		this.stars = stars;
	}

	@Column(name = "recognition", length = 250)
	public String getRecognition() {
		return this.recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

	@Column(name = "recognition_type", length = 250)
	public String getRecognitionType() {
		return this.recognitionType;
	}

	public void setRecognitionType(String recognitionType) {
		this.recognitionType = recognitionType;
	}

	@Column(name = "duration", length = 250)
	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Column(name = "duration_time", length = 250)
	public String getDurationTime() {
		return this.durationTime;
	}

	public void setDurationTime(String durationTime) {
		this.durationTime = durationTime;
	}

	@Column(name = "website", length = 500)
	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Column(name = "course_lang", length = 45)
	public String getCourseLang() {
		return this.courseLang;
	}

	public void setCourseLang(String courseLang) {
		this.courseLang = courseLang;
	}

	@Column(name = "abbreviation", length = 500)
	public String getAbbreviation() {
		return this.abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "rec_date", length = 19)
	public Date getRecDate() {
		return this.recDate;
	}

	public void setRecDate(Date recDate) {
		this.recDate = recDate;
	}

	@Column(name = "remarks", length = 500)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abbreviation == null) ? 0 : abbreviation.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((courseLang == null) ? 0 : courseLang.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
		result = prime * result + ((deletedOn == null) ? 0 : deletedOn.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + ((durationTime == null) ? 0 : durationTime.hashCode());
		result = prime * result + ((faculty == null) ? 0 : faculty.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((institute == null) ? 0 : institute.hashCode());
		result = prime * result + ((isActive == null) ? 0 : isActive.hashCode());
		result = prime * result + ((isDeleted == null) ? 0 : isDeleted.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((recDate == null) ? 0 : recDate.hashCode());
		result = prime * result + ((recognition == null) ? 0 : recognition.hashCode());
		result = prime * result + ((recognitionType == null) ? 0 : recognitionType.hashCode());
		result = prime * result + ((remarks == null) ? 0 : remarks.hashCode());
		result = prime * result + ((stars == null) ? 0 : stars.hashCode());
		result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result + ((updatedOn == null) ? 0 : updatedOn.hashCode());
		result = prime * result + ((website == null) ? 0 : website.hashCode());
		result = prime * result + ((worldRanking == null) ? 0 : worldRanking.hashCode());
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
		Course other = (Course) obj;
		if (abbreviation == null) {
			if (other.abbreviation != null)
				return false;
		} else if (!abbreviation.equals(other.abbreviation))
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
		if (courseLang == null) {
			if (other.courseLang != null)
				return false;
		} else if (!courseLang.equals(other.courseLang))
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
		if (duration == null) {
			if (other.duration != null)
				return false;
		} else if (!duration.equals(other.duration))
			return false;
		if (durationTime == null) {
			if (other.durationTime != null)
				return false;
		} else if (!durationTime.equals(other.durationTime))
			return false;
		if (faculty == null) {
			if (other.faculty != null)
				return false;
		} else if (!faculty.equals(other.faculty))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (institute == null) {
			if (other.institute != null)
				return false;
		} else if (!institute.equals(other.institute))
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
		if (recDate == null) {
			if (other.recDate != null)
				return false;
		} else if (!recDate.equals(other.recDate))
			return false;
		if (recognition == null) {
			if (other.recognition != null)
				return false;
		} else if (!recognition.equals(other.recognition))
			return false;
		if (recognitionType == null) {
			if (other.recognitionType != null)
				return false;
		} else if (!recognitionType.equals(other.recognitionType))
			return false;
		if (remarks == null) {
			if (other.remarks != null)
				return false;
		} else if (!remarks.equals(other.remarks))
			return false;
		if (stars == null) {
			if (other.stars != null)
				return false;
		} else if (!stars.equals(other.stars))
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Course [id=").append(id).append(", city=").append(city).append(", country=").append(country)
				.append(", faculty=").append(faculty).append(", institute=").append(institute).append(", name=")
				.append(name).append(", worldRanking=").append(worldRanking).append(", stars=").append(stars)
				.append(", recognition=").append(recognition).append(", recognitionType=").append(recognitionType)
				.append(", duration=").append(duration).append(", durationTime=").append(durationTime)
				.append(", website=").append(website).append(", courseLang=").append(courseLang)
				.append(", abbreviation=").append(abbreviation).append(", recDate=").append(recDate)
				.append(", remarks=").append(remarks).append(", description=").append(description).append(", isActive=")
				.append(isActive).append(", createdOn=").append(createdOn).append(", updatedOn=").append(updatedOn)
				.append(", deletedOn=").append(deletedOn).append(", createdBy=").append(createdBy)
				.append(", updatedBy=").append(updatedBy).append(", isDeleted=").append(isDeleted).append("]");
		return builder.toString();
	}

    /**
     * @return the cId
     */
	@Column(name = "c_id")
    public Integer getcId() {
        return cId;
    }

    /**
     * @param cId the cId to set
     */
    public void setcId(Integer cId) {
        this.cId = cId;
    }

    /**
     * @return the levelCode
     */
    @Column(name = "level_code")
    public String getLevelCode() {
        return levelCode;
    }

    /**
     * @param levelCode the levelCode to set
     */
    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

}
