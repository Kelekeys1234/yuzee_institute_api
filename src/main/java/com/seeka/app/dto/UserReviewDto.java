package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class UserReviewDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1298861732976808001L;

	private BigInteger id;
	private BigInteger userId;
	private BigInteger levelId;
	private BigInteger entityId;
	/**
	 * Which reviews do you want to share with us : Course or Institute
	 */
	private String entityType;
	/**
	 * Are you current student or alumni
	 */
	private String studentType;
	/**
	 * student name
	 */
	private String fullName;
	/**
	 * Post as an anonymous
	 */
	private Boolean isAnonymous;
	private Double reviewStar;
	private String comments;

	/**
	 * Review category / title
	 */
	private Double overallUniversityRating;
	private Double jobProspects;
	private Double courseAndLectures;
	private Double studentUnion;
	private Double institutionFacilities;
	private Double cityLife;
	private Double clubsAndSocieties;
	private Double studentSupport;

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

	public BigInteger getLevelId() {
		return levelId;
	}

	public void setLevelId(final BigInteger levelId) {
		this.levelId = levelId;
	}

	public BigInteger getEntityId() {
		return entityId;
	}

	public void setEntityId(final BigInteger entityId) {
		this.entityId = entityId;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (cityLife == null ? 0 : cityLife.hashCode());
		result = prime * result + (clubsAndSocieties == null ? 0 : clubsAndSocieties.hashCode());
		result = prime * result + (comments == null ? 0 : comments.hashCode());
		result = prime * result + (courseAndLectures == null ? 0 : courseAndLectures.hashCode());
		result = prime * result + (entityId == null ? 0 : entityId.hashCode());
		result = prime * result + (entityType == null ? 0 : entityType.hashCode());
		result = prime * result + (fullName == null ? 0 : fullName.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (institutionFacilities == null ? 0 : institutionFacilities.hashCode());
		result = prime * result + (isAnonymous == null ? 0 : isAnonymous.hashCode());
		result = prime * result + (jobProspects == null ? 0 : jobProspects.hashCode());
		result = prime * result + (levelId == null ? 0 : levelId.hashCode());
		result = prime * result + (overallUniversityRating == null ? 0 : overallUniversityRating.hashCode());
		result = prime * result + (reviewStar == null ? 0 : reviewStar.hashCode());
		result = prime * result + (studentSupport == null ? 0 : studentSupport.hashCode());
		result = prime * result + (studentType == null ? 0 : studentType.hashCode());
		result = prime * result + (studentUnion == null ? 0 : studentUnion.hashCode());
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
		UserReviewDto other = (UserReviewDto) obj;
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
		if (levelId == null) {
			if (other.levelId != null) {
				return false;
			}
		} else if (!levelId.equals(other.levelId)) {
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
		builder.append("UserReviewDto [id=").append(id).append(", userId=").append(userId).append(", levelId=").append(levelId).append(", entityId=")
				.append(entityId).append(", entityType=").append(entityType).append(", studentType=").append(studentType).append(", fullName=").append(fullName)
				.append(", isAnonymous=").append(isAnonymous).append(", reviewStar=").append(reviewStar).append(", comments=").append(comments)
				.append(", overallUniversityRating=").append(overallUniversityRating).append(", jobProspects=").append(jobProspects)
				.append(", courseAndLectures=").append(courseAndLectures).append(", studentUnion=").append(studentUnion).append(", institutionFacilities=")
				.append(institutionFacilities).append(", cityLife=").append(cityLife).append(", clubsAndSocieties=").append(clubsAndSocieties)
				.append(", studentSupport=").append(studentSupport).append("]");
		return builder.toString();
	}

}
