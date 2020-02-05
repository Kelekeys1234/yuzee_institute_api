package com.seeka.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

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
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "entity_id")
	private String entityId;

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
	 * Student is international or Domestic
	 */
	@Column(name = "student_category", nullable = false, length = 20)
	private String studentCategory;

	/**
	 * Post as an anonymous
	 */
	@Column(name = "is_anonymous")
	private Boolean isAnonymous;
	@Column(name = "review_star", nullable = false, precision = 4)
	private Double reviewStar;
	@Column(name = "comments", length = 500)
	private String comments;

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

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(final String entityId) {
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
		result = prime * result + (comments == null ? 0 : comments.hashCode());
		result = prime * result + (createdBy == null ? 0 : createdBy.hashCode());
		result = prime * result + (createdOn == null ? 0 : createdOn.hashCode());
		result = prime * result + (deletedBy == null ? 0 : deletedBy.hashCode());
		result = prime * result + (deletedOn == null ? 0 : deletedOn.hashCode());
		result = prime * result + (entityId == null ? 0 : entityId.hashCode());
		result = prime * result + (entityType == null ? 0 : entityType.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (isActive == null ? 0 : isActive.hashCode());
		result = prime * result + (isAnonymous == null ? 0 : isAnonymous.hashCode());
		result = prime * result + (reviewStar == null ? 0 : reviewStar.hashCode());
		result = prime * result + (studentCategory == null ? 0 : studentCategory.hashCode());
		result = prime * result + (studentType == null ? 0 : studentType.hashCode());
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
		if (comments == null) {
			if (other.comments != null) {
				return false;
			}
		} else if (!comments.equals(other.comments)) {
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
		builder.append("UserReview [id=").append(id).append(", userId=").append(userId).append(", entityId=").append(entityId).append(", entityType=")
				.append(entityType).append(", studentType=").append(studentType).append(", studentCategory=").append(studentCategory).append(", isAnonymous=")
				.append(isAnonymous).append(", reviewStar=").append(reviewStar).append(", comments=").append(comments).append(", isActive=").append(isActive)
				.append(", createdOn=").append(createdOn).append(", updatedOn=").append(updatedOn).append(", deletedOn=").append(deletedOn)
				.append(", createdBy=").append(createdBy).append(", updatedBy=").append(updatedBy).append(", deletedBy=").append(deletedBy).append("]");
		return builder.toString();
	}

}
