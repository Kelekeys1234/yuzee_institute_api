package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * @author SeekADegree
 *
 */
public class UserReviewResultDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1298861732976808001L;

	private BigInteger id;
	@NotNull(message = "userId is required")
	private BigInteger userId;
	@NotNull(message = "entityId is required")
	private BigInteger entityId;
	/**
	 * Which reviews do you want to share with us : Course or Institute
	 */
	@NotNull(message = "entityType is required")
	private String entityType;
	/**
	 * Are you current student or alumni
	 */
	@NotNull(message = "studentType is required")
	private String studentType;

	@NotNull(message = "studentCategory is required")
	private String studentCategory;
	/**
	 * Post as an anonymous
	 */
	private Boolean isAnonymous;
	private Double reviewStar;
	private String comments;
	private Boolean isActive;

	@NotNull(message = "ratings is required")
	private List<UserReviewRatingDto> ratings;

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

	public String getStudentCategory() {
		return studentCategory;
	}

	public void setStudentCategory(final String studentCategory) {
		this.studentCategory = studentCategory;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(final Boolean isActive) {
		this.isActive = isActive;
	}

	public List<UserReviewRatingDto> getRatings() {
		return ratings;
	}

	public void setRatings(final List<UserReviewRatingDto> ratings) {
		this.ratings = ratings;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (comments == null ? 0 : comments.hashCode());
		result = prime * result + (entityId == null ? 0 : entityId.hashCode());
		result = prime * result + (entityType == null ? 0 : entityType.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (isActive == null ? 0 : isActive.hashCode());
		result = prime * result + (isAnonymous == null ? 0 : isAnonymous.hashCode());
		result = prime * result + (ratings == null ? 0 : ratings.hashCode());
		result = prime * result + (reviewStar == null ? 0 : reviewStar.hashCode());
		result = prime * result + (studentCategory == null ? 0 : studentCategory.hashCode());
		result = prime * result + (studentType == null ? 0 : studentType.hashCode());
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
		UserReviewResultDto other = (UserReviewResultDto) obj;
		if (comments == null) {
			if (other.comments != null) {
				return false;
			}
		} else if (!comments.equals(other.comments)) {
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
		if (isAnonymous == null) {
			if (other.isAnonymous != null) {
				return false;
			}
		} else if (!isAnonymous.equals(other.isAnonymous)) {
			return false;
		}
		if (ratings == null) {
			if (other.ratings != null) {
				return false;
			}
		} else if (!ratings.equals(other.ratings)) {
			return false;
		}
		if (reviewStar == null) {
			if (other.reviewStar != null) {
				return false;
			}
		} else if (!reviewStar.equals(other.reviewStar)) {
			return false;
		}
		if (studentCategory == null) {
			if (other.studentCategory != null) {
				return false;
			}
		} else if (!studentCategory.equals(other.studentCategory)) {
			return false;
		}
		if (studentType == null) {
			if (other.studentType != null) {
				return false;
			}
		} else if (!studentType.equals(other.studentType)) {
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
		builder.append("UserReviewResultDto [id=").append(id).append(", userId=").append(userId).append(", entityId=").append(entityId).append(", entityType=")
				.append(entityType).append(", studentType=").append(studentType).append(", studentCategory=").append(studentCategory).append(", isAnonymous=")
				.append(isAnonymous).append(", reviewStar=").append(reviewStar).append(", comments=").append(comments).append(", isActive=").append(isActive)
				.append(", ratings=").append(ratings).append("]");
		return builder.toString();
	}

}
