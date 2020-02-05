package com.seeka.app.bean;

import java.io.Serializable;
import java.math.BigInteger;

// Generated 7 Jun, 2019 2:45:49 PM by Hibernate Tools 4.3.1

import java.util.Date;

import javax.persistence.CascadeType;
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

import org.hibernate.annotations.GenericGenerator;

/**
 * UserEducationDetails generated by hbm2java
 */
@Entity
@Table(name = "user_education_details")
public class UserEducationDetails implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6557054387264945982L;
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;
	@Column(name = "user_id", nullable = false)
	private String userId;
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "education_country_id", nullable = false)
	private Country country;
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "education_system_id", nullable = false)
	private EducationSystem educationSystem;
	@Column(name = "education_institue")
	private String educationInstitue;
	@Column(name = "gpa_score")
	private String gpaScore;
	@Column(name = "is_english_medium")
	private Boolean isEnglishMedium;
	@Column(name = "english_level")
	private String englishLevel;
	@Column(name = "edu_sys_score")
	private String eduSysScore;
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "education_level_id", nullable = false)
	private Level level;
	@Column(name = "is_active")
	private Boolean isActive;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	private Date createdOn;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	private Date updatedOn;
	@Column(name = "created_by", length = 50)
	private String createdBy;
	@Column(name = "updated_by", length = 50)
	private String updatedBy;
	@Column(name = "deleted_by", length = 50)
	private String deletedBy;
	@Column(name = "deleted_on")
	private Date deletedOn;
	@Column(name = "from_duration")
	private Integer fromDuration;
	@Column(name = "to_duration")
	private Integer toDuration;
	@Column(name = "education_qualification")
	private String educationQualification;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(final Country country) {
		this.country = country;
	}

	public EducationSystem getEducationSystem() {
		return educationSystem;
	}

	public void setEducationSystem(final EducationSystem educationSystem) {
		this.educationSystem = educationSystem;
	}

	public String getEducationInstitue() {
		return educationInstitue;
	}

	public void setEducationInstitue(final String educationInstitue) {
		this.educationInstitue = educationInstitue;
	}

	public String getGpaScore() {
		return gpaScore;
	}

	public void setGpaScore(final String gpaScore) {
		this.gpaScore = gpaScore;
	}

	public Boolean getIsEnglishMedium() {
		return isEnglishMedium;
	}

	public void setIsEnglishMedium(final Boolean isEnglishMedium) {
		this.isEnglishMedium = isEnglishMedium;
	}

	public String getEnglishLevel() {
		return englishLevel;
	}

	public void setEnglishLevel(final String englishLevel) {
		this.englishLevel = englishLevel;
	}

	public String getEduSysScore() {
		return eduSysScore;
	}

	public void setEduSysScore(final String eduSysScore) {
		this.eduSysScore = eduSysScore;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(final Level level) {
		this.level = level;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(final Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(final Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(final Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(final String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(final String deletedBy) {
		this.deletedBy = deletedBy;
	}

	public Date getDeletedOn() {
		return deletedOn;
	}

	public void setDeletedOn(final Date deletedOn) {
		this.deletedOn = deletedOn;
	}

	public Integer getFromDuration() {
		return fromDuration;
	}

	public void setFromDuration(final Integer fromDuration) {
		this.fromDuration = fromDuration;
	}

	public Integer getToDuration() {
		return toDuration;
	}

	public void setToDuration(final Integer toDuration) {
		this.toDuration = toDuration;
	}

	public String getEducationQualification() {
		return educationQualification;
	}

	public void setEducationQualification(final String educationQualification) {
		this.educationQualification = educationQualification;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (country == null ? 0 : country.hashCode());
		result = prime * result + (createdBy == null ? 0 : createdBy.hashCode());
		result = prime * result + (createdOn == null ? 0 : createdOn.hashCode());
		result = prime * result + (deletedBy == null ? 0 : deletedBy.hashCode());
		result = prime * result + (deletedOn == null ? 0 : deletedOn.hashCode());
		result = prime * result + (eduSysScore == null ? 0 : eduSysScore.hashCode());
		result = prime * result + (educationInstitue == null ? 0 : educationInstitue.hashCode());
		result = prime * result + (educationQualification == null ? 0 : educationQualification.hashCode());
		result = prime * result + (educationSystem == null ? 0 : educationSystem.hashCode());
		result = prime * result + (englishLevel == null ? 0 : englishLevel.hashCode());
		result = prime * result + (fromDuration == null ? 0 : fromDuration.hashCode());
		result = prime * result + (gpaScore == null ? 0 : gpaScore.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (isActive == null ? 0 : isActive.hashCode());
		result = prime * result + (isEnglishMedium == null ? 0 : isEnglishMedium.hashCode());
		result = prime * result + (level == null ? 0 : level.hashCode());
		result = prime * result + (toDuration == null ? 0 : toDuration.hashCode());
		result = prime * result + (updatedBy == null ? 0 : updatedBy.hashCode());
		result = prime * result + (updatedOn == null ? 0 : updatedOn.hashCode());
		result = prime * result + (userId == null ? 0 : userId.hashCode());
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
		UserEducationDetails other = (UserEducationDetails) obj;
		if (country == null) {
			if (other.country != null) {
				return false;
			}
		} else if (!country.equals(other.country)) {
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
		if (deletedBy == null) {
			if (other.deletedBy != null) {
				return false;
			}
		} else if (!deletedBy.equals(other.deletedBy)) {
			return false;
		}
		if (deletedOn == null) {
			if (other.deletedOn != null) {
				return false;
			}
		} else if (!deletedOn.equals(other.deletedOn)) {
			return false;
		}
		if (eduSysScore == null) {
			if (other.eduSysScore != null) {
				return false;
			}
		} else if (!eduSysScore.equals(other.eduSysScore)) {
			return false;
		}
		if (educationInstitue == null) {
			if (other.educationInstitue != null) {
				return false;
			}
		} else if (!educationInstitue.equals(other.educationInstitue)) {
			return false;
		}
		if (educationQualification == null) {
			if (other.educationQualification != null) {
				return false;
			}
		} else if (!educationQualification.equals(other.educationQualification)) {
			return false;
		}
		if (educationSystem == null) {
			if (other.educationSystem != null) {
				return false;
			}
		} else if (!educationSystem.equals(other.educationSystem)) {
			return false;
		}
		if (englishLevel == null) {
			if (other.englishLevel != null) {
				return false;
			}
		} else if (!englishLevel.equals(other.englishLevel)) {
			return false;
		}
		if (fromDuration == null) {
			if (other.fromDuration != null) {
				return false;
			}
		} else if (!fromDuration.equals(other.fromDuration)) {
			return false;
		}
		if (gpaScore == null) {
			if (other.gpaScore != null) {
				return false;
			}
		} else if (!gpaScore.equals(other.gpaScore)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (isActive == null) {
			if (other.isActive != null) {
				return false;
			}
		} else if (!isActive.equals(other.isActive)) {
			return false;
		}
		if (isEnglishMedium == null) {
			if (other.isEnglishMedium != null) {
				return false;
			}
		} else if (!isEnglishMedium.equals(other.isEnglishMedium)) {
			return false;
		}
		if (level == null) {
			if (other.level != null) {
				return false;
			}
		} else if (!level.equals(other.level)) {
			return false;
		}
		if (toDuration == null) {
			if (other.toDuration != null) {
				return false;
			}
		} else if (!toDuration.equals(other.toDuration)) {
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
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserEducationDetails [id=").append(id).append(", userId=").append(userId).append(", country=").append(country)
				.append(", educationSystem=").append(educationSystem).append(", educationInstitue=").append(educationInstitue).append(", gpaScore=")
				.append(gpaScore).append(", isEnglishMedium=").append(isEnglishMedium).append(", englishLevel=").append(englishLevel).append(", eduSysScore=")
				.append(eduSysScore).append(", level=").append(level).append(", isActive=").append(isActive).append(", createdOn=").append(createdOn)
				.append(", updatedOn=").append(updatedOn).append(", createdBy=").append(createdBy).append(", updatedBy=").append(updatedBy)
				.append(", deletedBy=").append(deletedBy).append(", deletedOn=").append(deletedOn).append(", fromDuration=").append(fromDuration)
				.append(", toDuration=").append(toDuration).append(", educationQualification=").append(educationQualification).append("]");
		return builder.toString();
	}

}