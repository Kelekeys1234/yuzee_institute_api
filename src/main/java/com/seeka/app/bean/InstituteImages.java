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
 * InstituteImages generated by hbm2java
 */
@Entity
@Table(name = "institute_images")
public class InstituteImages implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7478538284327710921L;
	private BigInteger id;
	private Institute institute;
	private int imageIndex;
	private String imageName;
	private String subCategory;
	private String description;
	private Boolean isActive;
	private Date createdOn;
	private Date updatedOn;
	private Date deletedOn;
	private String createdBy;
	private String updatedBy;
	private Boolean isDeleted;

	public InstituteImages() {
	}

	public InstituteImages(final Institute institute, final int imageIndex) {
		this.institute = institute;
		this.imageIndex = imageIndex;
	}

	public InstituteImages(final Institute institute, final int imageIndex, final String imageName, final String subCategory, final String description,
			final Boolean isActive, final Date createdOn, final Date updatedOn, final Date deletedOn, final String createdBy, final String updatedBy,
			final Boolean isDeleted) {
		this.institute = institute;
		this.imageIndex = imageIndex;
		this.imageName = imageName;
		this.subCategory = subCategory;
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
	@JoinColumn(name = "institute_id", nullable = false)
	public Institute getInstitute() {
		return this.institute;
	}

	public void setInstitute(final Institute institute) {
		this.institute = institute;
	}

	@Column(name = "image_index", nullable = false)
	public int getImageIndex() {
		return this.imageIndex;
	}

	public void setImageIndex(final int imageIndex) {
		this.imageIndex = imageIndex;
	}

	@Column(name = "image_name", length = 100)
	public String getImageName() {
		return this.imageName;
	}

	public void setImageName(final String imageName) {
		this.imageName = imageName;
	}

	@Column(name = "sub_category", length = 500)
	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(final String subCategory) {
		this.subCategory = subCategory;
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
		result = prime * result + (createdBy == null ? 0 : createdBy.hashCode());
		result = prime * result + (createdOn == null ? 0 : createdOn.hashCode());
		result = prime * result + (deletedOn == null ? 0 : deletedOn.hashCode());
		result = prime * result + (description == null ? 0 : description.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + imageIndex;
		result = prime * result + (imageName == null ? 0 : imageName.hashCode());
		result = prime * result + (institute == null ? 0 : institute.hashCode());
		result = prime * result + (isActive == null ? 0 : isActive.hashCode());
		result = prime * result + (isDeleted == null ? 0 : isDeleted.hashCode());
		result = prime * result + (subCategory == null ? 0 : subCategory.hashCode());
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
		InstituteImages other = (InstituteImages) obj;
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
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (imageIndex != other.imageIndex) {
			return false;
		}
		if (imageName == null) {
			if (other.imageName != null) {
				return false;
			}
		} else if (!imageName.equals(other.imageName)) {
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
		if (subCategory == null) {
			if (other.subCategory != null) {
				return false;
			}
		} else if (!subCategory.equals(other.subCategory)) {
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
		builder.append("InstituteImages [id=").append(id).append(", institute=").append(institute).append(", imageIndex=").append(imageIndex)
				.append(", imageName=").append(imageName).append(", subCategory=").append(subCategory).append(", description=").append(description)
				.append(", isActive=").append(isActive).append(", createdOn=").append(createdOn).append(", updatedOn=").append(updatedOn).append(", deletedOn=")
				.append(deletedOn).append(", createdBy=").append(createdBy).append(", updatedBy=").append(updatedBy).append(", isDeleted=").append(isDeleted)
				.append("]");
		return builder.toString();
	}

}
