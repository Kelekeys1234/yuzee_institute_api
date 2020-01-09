package com.seeka.app.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * @author SeekADegree
 *
 */
@Entity
@Table(name = "question_category")
public class QuestionCategroy implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5699019798373814995L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private BigInteger id;

	@Column(name = "category_name", length = 250)
	@NotNull(message = "categoryName is required")
	private String categoryName;

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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(final String categoryName) {
		this.categoryName = categoryName;
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
		result = prime * result + (categoryName == null ? 0 : categoryName.hashCode());
		result = prime * result + (createdBy == null ? 0 : createdBy.hashCode());
		result = prime * result + (createdOn == null ? 0 : createdOn.hashCode());
		result = prime * result + (deletedBy == null ? 0 : deletedBy.hashCode());
		result = prime * result + (deletedOn == null ? 0 : deletedOn.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (isActive == null ? 0 : isActive.hashCode());
		result = prime * result + (updatedBy == null ? 0 : updatedBy.hashCode());
		result = prime * result + (updatedOn == null ? 0 : updatedOn.hashCode());
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
		QuestionCategroy other = (QuestionCategroy) obj;
		if (categoryName == null) {
			if (other.categoryName != null) {
				return false;
			}
		} else if (!categoryName.equals(other.categoryName)) {
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
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QuestionCategroy [id=").append(id).append(", categoryName=").append(categoryName).append(", isActive=").append(isActive)
				.append(", createdOn=").append(createdOn).append(", updatedOn=").append(updatedOn).append(", deletedOn=").append(deletedOn)
				.append(", createdBy=").append(createdBy).append(", updatedBy=").append(updatedBy).append(", deletedBy=").append(deletedBy).append("]");
		return builder.toString();
	}

}
