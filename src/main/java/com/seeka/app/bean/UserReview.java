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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author SeekADegree
 *
 */
@Entity
@Table(name = "user_review")
public class UserReview implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5699019798373814995L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private BigInteger id;

	@Column(name = "user_id")
	private BigInteger userId;

	@Column(name = "entity_id")
	private BigInteger entityId;

	/**
	 * What is your level of education
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "level_id")
	private Level level;

	/**
	 * Which reviews do you want to share with us : Course or Institute
	 */
	@Column(name = "entity_type", length = 250)
	private String entityType;
	/**
	 * Are you current student or alumni
	 */
	@Column(name = "student_type", length = 250)
	private String studentType;
	/**
	 * student name
	 */
	@Column(name = "full_name", length = 90)
	private String fullName;
	/**
	 * Post as an anonymous
	 */
	@Column(name = "is_anonymous")
	private Boolean isAnonymous;
	@Column(name = "review_star", nullable = false, precision = 4)
	private Double reviewStar;
	@Column(name = "comments", length = 500)
	private String comments;

	/**
	 * Review category / title
	 */
	@Column(name = "overall_university_rating", nullable = false, precision = 4)
	private Double overallUniversityRating;
	@Column(name = "job_prospects", nullable = false, precision = 4)
	private Double jobProspects;
	@Column(name = "course_and_lectures", nullable = false, precision = 4)
	private Double courseAndLectures;
	@Column(name = "student_union", nullable = false, precision = 4)
	private Double studentUnion;
	@Column(name = "institution_facilities", nullable = false, precision = 4)
	private Double institutionFacilities;
	@Column(name = "city_life", nullable = false, precision = 4)
	private Double cityLife;
	@Column(name = "clubs_and_societies", nullable = false, precision = 4)
	private Double clubsAndSocieties;
	@Column(name = "student_support", nullable = false, precision = 4)
	private Double studentSupport;

	@Column(name = "is_active")
	private Boolean isActive;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	private Date createdOn;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	private Date updatedOn;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted_on", length = 19)
	private Date deletedOn;
	@Column(name = "created_by", length = 50)
	private String createdBy;
	@Column(name = "updated_by", length = 50)
	private String updatedBy;
	@Column(name = "deleted_by", length = 50)
	private String deletedBy;

	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
		this.id = id;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(final BigInteger userId) {
		this.userId = userId;
	}

	public BigInteger getEntityId() {
		return entityId;
	}

	public void setEntityId(final BigInteger entityId) {
		this.entityId = entityId;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(final Level level) {
		this.level = level;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(final String entityType) {
		this.entityType = entityType;
	}

	public String getStudentType() {
		return studentType;
	}

	public void setStudentType(final String studentType) {
		this.studentType = studentType;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	public Boolean getIsAnonymous() {
		return isAnonymous;
	}

	public void setIsAnonymous(final Boolean isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	public Double getReviewStar() {
		return reviewStar;
	}

	public void setReviewStar(final Double reviewStar) {
		this.reviewStar = reviewStar;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public Double getOverallUniversityRating() {
		return overallUniversityRating;
	}

	public void setOverallUniversityRating(final Double overallUniversityRating) {
		this.overallUniversityRating = overallUniversityRating;
	}

	public Double getJobProspects() {
		return jobProspects;
	}

	public void setJobProspects(final Double jobProspects) {
		this.jobProspects = jobProspects;
	}

	public Double getCourseAndLectures() {
		return courseAndLectures;
	}

	public void setCourseAndLectures(final Double courseAndLectures) {
		this.courseAndLectures = courseAndLectures;
	}

	public Double getStudentUnion() {
		return studentUnion;
	}

	public void setStudentUnion(final Double studentUnion) {
		this.studentUnion = studentUnion;
	}

	public Double getInstitutionFacilities() {
		return institutionFacilities;
	}

	public void setInstitutionFacilities(final Double institutionFacilities) {
		this.institutionFacilities = institutionFacilities;
	}

	public Double getCityLife() {
		return cityLife;
	}

	public void setCityLife(final Double cityLife) {
		this.cityLife = cityLife;
	}

	public Double getClubsAndSocieties() {
		return clubsAndSocieties;
	}

	public void setClubsAndSocieties(final Double clubsAndSocieties) {
		this.clubsAndSocieties = clubsAndSocieties;
	}

	public Double getStudentSupport() {
		return studentSupport;
	}

	public void setStudentSupport(final Double studentSupport) {
		this.studentSupport = studentSupport;
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

	public Date getDeletedOn() {
		return deletedOn;
	}

	public void setDeletedOn(final Date deletedOn) {
		this.deletedOn = deletedOn;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (cityLife == null ? 0 : cityLife.hashCode());
		result = prime * result + (clubsAndSocieties == null ? 0 : clubsAndSocieties.hashCode());
		result = prime * result + (comments == null ? 0 : comments.hashCode());
		result = prime * result + (courseAndLectures == null ? 0 : courseAndLectures.hashCode());
		result = prime * result + (createdBy == null ? 0 : createdBy.hashCode());
		result = prime * result + (createdOn == null ? 0 : createdOn.hashCode());
		result = prime * result + (deletedBy == null ? 0 : deletedBy.hashCode());
		result = prime * result + (deletedOn == null ? 0 : deletedOn.hashCode());
		result = prime * result + (entityId == null ? 0 : entityId.hashCode());
		result = prime * result + (entityType == null ? 0 : entityType.hashCode());
		result = prime * result + (fullName == null ? 0 : fullName.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (institutionFacilities == null ? 0 : institutionFacilities.hashCode());
		result = prime * result + (isActive == null ? 0 : isActive.hashCode());
		result = prime * result + (isAnonymous == null ? 0 : isAnonymous.hashCode());
		result = prime * result + (jobProspects == null ? 0 : jobProspects.hashCode());
		result = prime * result + (level == null ? 0 : level.hashCode());
		result = prime * result + (overallUniversityRating == null ? 0 : overallUniversityRating.hashCode());
		result = prime * result + (reviewStar == null ? 0 : reviewStar.hashCode());
		result = prime * result + (studentSupport == null ? 0 : studentSupport.hashCode());
		result = prime * result + (studentType == null ? 0 : studentType.hashCode());
		result = prime * result + (studentUnion == null ? 0 : studentUnion.hashCode());
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
		UserReview other = (UserReview) obj;
		if (cityLife == null) {
			if (other.cityLife != null) {
				return false;
			}
		} else if (!cityLife.equals(other.cityLife)) {
			return false;
		}
		if (clubsAndSocieties == null) {
			if (other.clubsAndSocieties != null) {
				return false;
			}
		} else if (!clubsAndSocieties.equals(other.clubsAndSocieties)) {
			return false;
		}
		if (comments == null) {
			if (other.comments != null) {
				return false;
			}
		} else if (!comments.equals(other.comments)) {
			return false;
		}
		if (courseAndLectures == null) {
			if (other.courseAndLectures != null) {
				return false;
			}
		} else if (!courseAndLectures.equals(other.courseAndLectures)) {
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
		if (entityId == null) {
			if (other.entityId != null) {
				return false;
			}
		} else if (!entityId.equals(other.entityId)) {
			return false;
		}
		if (entityType == null) {
			if (other.entityType != null) {
				return false;
			}
		} else if (!entityType.equals(other.entityType)) {
			return false;
		}
		if (fullName == null) {
			if (other.fullName != null) {
				return false;
			}
		} else if (!fullName.equals(other.fullName)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (institutionFacilities == null) {
			if (other.institutionFacilities != null) {
				return false;
			}
		} else if (!institutionFacilities.equals(other.institutionFacilities)) {
			return false;
		}
		if (isActive == null) {
			if (other.isActive != null) {
				return false;
			}
		} else if (!isActive.equals(other.isActive)) {
			return false;
		}
		if (isAnonymous == null) {
			if (other.isAnonymous != null) {
				return false;
			}
		} else if (!isAnonymous.equals(other.isAnonymous)) {
			return false;
		}
		if (jobProspects == null) {
			if (other.jobProspects != null) {
				return false;
			}
		} else if (!jobProspects.equals(other.jobProspects)) {
			return false;
		}
		if (level == null) {
			if (other.level != null) {
				return false;
			}
		} else if (!level.equals(other.level)) {
			return false;
		}
		if (overallUniversityRating == null) {
			if (other.overallUniversityRating != null) {
				return false;
			}
		} else if (!overallUniversityRating.equals(other.overallUniversityRating)) {
			return false;
		}
		if (reviewStar == null) {
			if (other.reviewStar != null) {
				return false;
			}
		} else if (!reviewStar.equals(other.reviewStar)) {
			return false;
		}
		if (studentSupport == null) {
			if (other.studentSupport != null) {
				return false;
			}
		} else if (!studentSupport.equals(other.studentSupport)) {
			return false;
		}
		if (studentType == null) {
			if (other.studentType != null) {
				return false;
			}
		} else if (!studentType.equals(other.studentType)) {
			return false;
		}
		if (studentUnion == null) {
			if (other.studentUnion != null) {
				return false;
			}
		} else if (!studentUnion.equals(other.studentUnion)) {
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
		builder.append("UserReview [id=").append(id).append(", userId=").append(userId).append(", entityId=").append(entityId).append(", level=").append(level)
				.append(", entityType=").append(entityType).append(", studentType=").append(studentType).append(", fullName=").append(fullName)
				.append(", isAnonymous=").append(isAnonymous).append(", reviewStar=").append(reviewStar).append(", comments=").append(comments)
				.append(", overallUniversityRating=").append(overallUniversityRating).append(", jobProspects=").append(jobProspects)
				.append(", courseAndLectures=").append(courseAndLectures).append(", studentUnion=").append(studentUnion).append(", institutionFacilities=")
				.append(institutionFacilities).append(", cityLife=").append(cityLife).append(", clubsAndSocieties=").append(clubsAndSocieties)
				.append(", studentSupport=").append(studentSupport).append(", isActive=").append(isActive).append(", createdOn=").append(createdOn)
				.append(", updatedOn=").append(updatedOn).append(", deletedOn=").append(deletedOn).append(", createdBy=").append(createdBy)
				.append(", updatedBy=").append(updatedBy).append(", deletedBy=").append(deletedBy).append("]");
		return builder.toString();
	}

}
