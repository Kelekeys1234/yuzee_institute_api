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
import javax.persistence.Transient;

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
	private Level level;

	private String availbilty;
	private String partFull;
	private String studyMode;
	private String intake;
	private String fileUrl;
	private String grades;
	private String contact;
	private String openingHour;
	private String campusLocation;
	private String jobFullTime;
	private String jobPartTime;
	private String courseLink;
	private String domesticFee;
	private String internationalFee;
	private String currency;
	private String currencyTime;
	private String usdInternationFee;
	private String usdDomasticFee;
	private Double costRange;

	public Course() {
	}

	public Course(final City city, final Country country, final Faculty faculty, final Institute institute, final String name, final String worldRanking,
			final String stars, final String recognition, final String recognitionType, final String duration, final String durationTime, final String website,
			final String courseLang, final String abbreviation, final Date recDate, final String remarks, final String description, final Boolean isActive,
			final Date createdOn, final Date updatedOn, final Date deletedOn, final String createdBy, final String updatedBy, final Boolean isDeleted) {
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

	public void setId(final BigInteger id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "city_id")
	public City getCity() {
		return this.city;
	}

	public void setCity(final City city) {
		this.city = city;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "country_id")
	public Country getCountry() {
		return this.country;
	}

	public void setCountry(final Country country) {
		this.country = country;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "faculty_id")
	public Faculty getFaculty() {
		return this.faculty;
	}

	public void setFaculty(final Faculty faculty) {
		this.faculty = faculty;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "institute_id")
	public Institute getInstitute() {
		return this.institute;
	}

	public void setInstitute(final Institute institute) {
		this.institute = institute;
	}

	@Column(name = "name", length = 245)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Column(name = "world_ranking", length = 50)
	public String getWorldRanking() {
		return this.worldRanking;
	}

	public void setWorldRanking(final String worldRanking) {
		this.worldRanking = worldRanking;
	}

	@Column(name = "stars", length = 50)
	public String getStars() {
		return this.stars;
	}

	public void setStars(final String stars) {
		this.stars = stars;
	}

	@Column(name = "recognition", length = 250)
	public String getRecognition() {
		return this.recognition;
	}

	public void setRecognition(final String recognition) {
		this.recognition = recognition;
	}

	@Column(name = "recognition_type", length = 250)
	public String getRecognitionType() {
		return this.recognitionType;
	}

	public void setRecognitionType(final String recognitionType) {
		this.recognitionType = recognitionType;
	}

	@Column(name = "duration", length = 250)
	public String getDuration() {
		return this.duration;
	}

	public void setDuration(final String duration) {
		this.duration = duration;
	}

	@Column(name = "duration_time", length = 250)
	public String getDurationTime() {
		return this.durationTime;
	}

	public void setDurationTime(final String durationTime) {
		this.durationTime = durationTime;
	}

	@Column(name = "website", length = 500)
	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(final String website) {
		this.website = website;
	}

	@Column(name = "course_lang", length = 45)
	public String getCourseLang() {
		return this.courseLang;
	}

	public void setCourseLang(final String courseLang) {
		this.courseLang = courseLang;
	}

	@Column(name = "abbreviation", length = 500)
	public String getAbbreviation() {
		return this.abbreviation;
	}

	public void setAbbreviation(final String abbreviation) {
		this.abbreviation = abbreviation;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "rec_date", length = 19)
	public Date getRecDate() {
		return this.recDate;
	}

	public void setRecDate(final Date recDate) {
		this.recDate = recDate;
	}

	@Column(name = "remarks", length = 500)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(final String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "description", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(final Boolean isActive) {
		this.isActive = isActive;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(final Date createdOn) {
		this.createdOn = createdOn;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	public Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(final Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted_on", length = 19)
	public Date getDeletedOn() {
		return this.deletedOn;
	}

	public void setDeletedOn(final Date deletedOn) {
		this.deletedOn = deletedOn;
	}

	@Column(name = "created_by", length = 50)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "updated_by", length = 50)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(final String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "is_deleted")
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(final Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (abbreviation == null ? 0 : abbreviation.hashCode());
		result = prime * result + (cId == null ? 0 : cId.hashCode());
		result = prime * result + (city == null ? 0 : city.hashCode());
		result = prime * result + (country == null ? 0 : country.hashCode());
		result = prime * result + (courseLang == null ? 0 : courseLang.hashCode());
		result = prime * result + (createdBy == null ? 0 : createdBy.hashCode());
		result = prime * result + (createdOn == null ? 0 : createdOn.hashCode());
		result = prime * result + (deletedOn == null ? 0 : deletedOn.hashCode());
		result = prime * result + (description == null ? 0 : description.hashCode());
		result = prime * result + (duration == null ? 0 : duration.hashCode());
		result = prime * result + (durationTime == null ? 0 : durationTime.hashCode());
		result = prime * result + (faculty == null ? 0 : faculty.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (institute == null ? 0 : institute.hashCode());
		result = prime * result + (isActive == null ? 0 : isActive.hashCode());
		result = prime * result + (isDeleted == null ? 0 : isDeleted.hashCode());
		result = prime * result + (level == null ? 0 : level.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (recDate == null ? 0 : recDate.hashCode());
		result = prime * result + (recognition == null ? 0 : recognition.hashCode());
		result = prime * result + (recognitionType == null ? 0 : recognitionType.hashCode());
		result = prime * result + (remarks == null ? 0 : remarks.hashCode());
		result = prime * result + (stars == null ? 0 : stars.hashCode());
		result = prime * result + (updatedBy == null ? 0 : updatedBy.hashCode());
		result = prime * result + (updatedOn == null ? 0 : updatedOn.hashCode());
		result = prime * result + (website == null ? 0 : website.hashCode());
		result = prime * result + (worldRanking == null ? 0 : worldRanking.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Course other = (Course) obj;
		if (abbreviation == null) {
			if (other.abbreviation != null) {
				return false;
			}
		} else if (!abbreviation.equals(other.abbreviation)) {
			return false;
		}
		if (cId == null) {
			if (other.cId != null) {
				return false;
			}
		} else if (!cId.equals(other.cId)) {
			return false;
		}
		if (city == null) {
			if (other.city != null) {
				return false;
			}
		} else if (!city.equals(other.city)) {
			return false;
		}
		if (country == null) {
			if (other.country != null) {
				return false;
			}
		} else if (!country.equals(other.country)) {
			return false;
		}
		if (courseLang == null) {
			if (other.courseLang != null) {
				return false;
			}
		} else if (!courseLang.equals(other.courseLang)) {
			return false;
		}
		if (createdBy == null) {
			if (other.createdBy != null) {
				return false;
			}
		} else if (!createdBy.equals(other.createdBy)) {
			return false;
		}
		if (createdOn == null) {
			if (other.createdOn != null) {
				return false;
			}
		} else if (!createdOn.equals(other.createdOn)) {
			return false;
		}
		if (deletedOn == null) {
			if (other.deletedOn != null) {
				return false;
			}
		} else if (!deletedOn.equals(other.deletedOn)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (duration == null) {
			if (other.duration != null) {
				return false;
			}
		} else if (!duration.equals(other.duration)) {
			return false;
		}
		if (durationTime == null) {
			if (other.durationTime != null) {
				return false;
			}
		} else if (!durationTime.equals(other.durationTime)) {
			return false;
		}
		if (faculty == null) {
			if (other.faculty != null) {
				return false;
			}
		} else if (!faculty.equals(other.faculty)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (institute == null) {
			if (other.institute != null) {
				return false;
			}
		} else if (!institute.equals(other.institute)) {
			return false;
		}
		if (isActive == null) {
			if (other.isActive != null) {
				return false;
			}
		} else if (!isActive.equals(other.isActive)) {
			return false;
		}
		if (isDeleted == null) {
			if (other.isDeleted != null) {
				return false;
			}
		} else if (!isDeleted.equals(other.isDeleted)) {
			return false;
		}
		if (level == null) {
			if (other.level != null) {
				return false;
			}
		} else if (!level.equals(other.level)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (recDate == null) {
			if (other.recDate != null) {
				return false;
			}
		} else if (!recDate.equals(other.recDate)) {
			return false;
		}
		if (recognition == null) {
			if (other.recognition != null) {
				return false;
			}
		} else if (!recognition.equals(other.recognition)) {
			return false;
		}
		if (recognitionType == null) {
			if (other.recognitionType != null) {
				return false;
			}
		} else if (!recognitionType.equals(other.recognitionType)) {
			return false;
		}
		if (remarks == null) {
			if (other.remarks != null) {
				return false;
			}
		} else if (!remarks.equals(other.remarks)) {
			return false;
		}
		if (stars == null) {
			if (other.stars != null) {
				return false;
			}
		} else if (!stars.equals(other.stars)) {
			return false;
		}
		if (updatedBy == null) {
			if (other.updatedBy != null) {
				return false;
			}
		} else if (!updatedBy.equals(other.updatedBy)) {
			return false;
		}
		if (updatedOn == null) {
			if (other.updatedOn != null) {
				return false;
			}
		} else if (!updatedOn.equals(other.updatedOn)) {
			return false;
		}
		if (website == null) {
			if (other.website != null) {
				return false;
			}
		} else if (!website.equals(other.website)) {
			return false;
		}
		if (worldRanking == null) {
			if (other.worldRanking != null) {
				return false;
			}
		} else if (!worldRanking.equals(other.worldRanking)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Course [id=").append(id).append(", city=").append(city).append(", country=").append(country).append(", faculty=").append(faculty)
				.append(", institute=").append(institute).append(", name=").append(name).append(", worldRanking=").append(worldRanking).append(", stars=")
				.append(stars).append(", recognition=").append(recognition).append(", recognitionType=").append(recognitionType).append(", duration=")
				.append(duration).append(", durationTime=").append(durationTime).append(", website=").append(website).append(", courseLang=").append(courseLang)
				.append(", abbreviation=").append(abbreviation).append(", recDate=").append(recDate).append(", remarks=").append(remarks)
				.append(", description=").append(description).append(", isActive=").append(isActive).append(", createdOn=").append(createdOn)
				.append(", updatedOn=").append(updatedOn).append(", deletedOn=").append(deletedOn).append(", createdBy=").append(createdBy)
				.append(", updatedBy=").append(updatedBy).append(", isDeleted=").append(isDeleted).append(", cId=").append(cId).append(", level=").append(level)
				.append("]");
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
	public void setcId(final Integer cId) {
		this.cId = cId;
	}

	/**
	 * @return the levelCode
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "level_id")

	public Level getLevel() {
		return level;
	}

	public void setLevel(final Level level) {
		this.level = level;
	}

	@Column(name = "availbilty", length = 250)
	public String getAvailbilty() {
		return this.availbilty;
	}

	public void setAvailbilty(final String availbilty) {
		this.availbilty = availbilty;
	}

	@Column(name = "part_full", length = 50)
	public String getPartFull() {
		return this.partFull;
	}

	public void setPartFull(final String partFull) {
		this.partFull = partFull;
	}

	@Column(name = "study_mode", length = 50)
	public String getStudyMode() {
		return this.studyMode;
	}

	public void setStudyMode(final String studyMode) {
		this.studyMode = studyMode;
	}

	@Column(name = "currency", length = 100)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	@Column(name = "currency_time", length = 250)
	public String getCurrencyTime() {
		return this.currencyTime;
	}

	public void setCurrencyTime(final String currencyTime) {
		this.currencyTime = currencyTime;
	}

	/**
	 * @return the usdInternationFee
	 */
	@Column(name = "usd_international_fee")
	public String getUsdInternationFee() {
		return usdInternationFee;
	}

	/**
	 * @param usdInternationFee the usdInternationFee to set
	 */
	public void setUsdInternationFee(final String usdInternationFee) {
		this.usdInternationFee = usdInternationFee;
	}

	/**
	 * @return the usdDomasticFee
	 */
	@Column(name = "usd_domestic_fee")
	public String getUsdDomasticFee() {
		return usdDomasticFee;
	}

	/**
	 * @param usdDomasticFee the usdDomasticFee to set
	 */
	public void setUsdDomasticFee(final String usdDomasticFee) {
		this.usdDomasticFee = usdDomasticFee;
	}

	/**
	 * @return the intake
	 */
	@Column(name = "intake")
	public String getIntake() {
		return intake;
	}

	/**
	 * @param intake the intake to set
	 */
	public void setIntake(final String intake) {
		this.intake = intake;
	}

	/**
	 * @return the fileUrl
	 */
	@Transient
	public String getFileUrl() {
		return fileUrl;
	}

	/**
	 * @param fileUrl the fileUrl to set
	 */
	public void setFileUrl(final String fileUrl) {
		this.fileUrl = fileUrl;
	}

	/**
	 * @return the grades
	 */
	@Transient
	public String getGrades() {
		return grades;
	}

	/**
	 * @param grades the grades to set
	 */
	public void setGrades(final String grades) {
		this.grades = grades;
	}

	/**
	 * @return the contact
	 */
	@Transient
	public String getContact() {
		return contact;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(final String contact) {
		this.contact = contact;
	}

	/**
	 * @return the openingHour
	 */
	@Transient
	public String getOpeningHour() {
		return openingHour;
	}

	/**
	 * @param openingHour the openingHour to set
	 */
	public void setOpeningHour(final String openingHour) {
		this.openingHour = openingHour;
	}

	/**
	 * @return the campusLocation
	 */
	@Column(name = "campus_location")
	public String getCampusLocation() {
		return campusLocation;
	}

	/**
	 * @param campusLocation the campusLocation to set
	 */
	public void setCampusLocation(final String campusLocation) {
		this.campusLocation = campusLocation;
	}

	/**
	 * @return the jobFullTime
	 */
	@Transient
	public String getJobFullTime() {
		return jobFullTime;
	}

	/**
	 * @param jobFullTime the jobFullTime to set
	 */
	public void setJobFullTime(final String jobFullTime) {
		this.jobFullTime = jobFullTime;
	}

	/**
	 * @return the jobPartTime
	 */
	@Transient
	public String getJobPartTime() {
		return jobPartTime;
	}

	/**
	 * @param jobPartTime the jobPartTime to set
	 */
	public void setJobPartTime(final String jobPartTime) {
		this.jobPartTime = jobPartTime;
	}

	/**
	 * @return the courseLink
	 */
	@Column(name = "course_link")
	public String getCourseLink() {
		return courseLink;
	}

	/**
	 * @param courseLink the courseLink to set
	 */
	public void setCourseLink(final String courseLink) {
		this.courseLink = courseLink;
	}

	/**
	 * @return the domesticFee
	 */
	@Column(name = "domestic_fee")
	public String getDomesticFee() {
		return domesticFee;
	}

	/**
	 * @param domesticFee the domesticFee to set
	 */
	public void setDomesticFee(final String domesticFee) {
		this.domesticFee = domesticFee;
	}

	/**
	 * @return the internationalFee
	 */
	@Column(name = "international_fee")
	public String getInternationalFee() {
		return internationalFee;
	}

	/**
	 * @param internationalFee the internationalFee to set
	 */
	public void setInternationalFee(final String internationalFee) {
		this.internationalFee = internationalFee;
	}

	@Column(name = "cost_range", precision = 18, scale = 3)
	public Double getCostRange() {
		return this.costRange;
	}

	public void setCostRange(final Double costRange) {
		this.costRange = costRange;
	}

}
