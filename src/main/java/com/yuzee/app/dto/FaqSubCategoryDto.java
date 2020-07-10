package com.yuzee.app.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class FaqSubCategoryDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -4031303934629241617L;
	private String id;
	@NotNull(message = "name is required")
	private String name;
	private Date createdOn;
	private Date updatedOn;
	private String createdBy;
	private String updatedBy;
	private Boolean isActive;
	@NotNull(message = "faqCategoryId is required")
	private String faqCategoryId;
	private String faqCategoryName;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(final Boolean isActive) {
		this.isActive = isActive;
	}

	public String getFaqCategoryId() {
		return faqCategoryId;
	}

	public void setFaqCategoryId(final String faqCategoryId) {
		this.faqCategoryId = faqCategoryId;
	}

	public String getFaqCategoryName() {
		return faqCategoryName;
	}

	public void setFaqCategoryName(final String faqCategoryName) {
		this.faqCategoryName = faqCategoryName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (createdBy == null ? 0 : createdBy.hashCode());
		result = prime * result + (createdOn == null ? 0 : createdOn.hashCode());
		result = prime * result + (faqCategoryId == null ? 0 : faqCategoryId.hashCode());
		result = prime * result + (faqCategoryName == null ? 0 : faqCategoryName.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (isActive == null ? 0 : isActive.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
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
		FaqSubCategoryDto other = (FaqSubCategoryDto) obj;
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
		if (faqCategoryId == null) {
			if (other.faqCategoryId != null) {
				return false;
			}
		} else if (!faqCategoryId.equals(other.faqCategoryId)) {
			return false;
		}
		if (faqCategoryName == null) {
			if (other.faqCategoryName != null) {
				return false;
			}
		} else if (!faqCategoryName.equals(other.faqCategoryName)) {
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
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
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
		builder.append("FaqSubCategoryDto [id=").append(id).append(", name=").append(name).append(", createdOn=").append(createdOn).append(", updatedOn=")
				.append(updatedOn).append(", createdBy=").append(createdBy).append(", updatedBy=").append(updatedBy).append(", isActive=").append(isActive)
				.append(", faqCategoryId=").append(faqCategoryId).append(", faqCategoryName=").append(faqCategoryName).append("]");
		return builder.toString();
	}

}
