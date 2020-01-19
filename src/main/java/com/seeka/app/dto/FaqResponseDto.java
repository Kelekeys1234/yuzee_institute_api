package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class FaqResponseDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5744814923342867841L;

	private BigInteger id;
	private String title;
	private String description;
	private Integer votes;
	private BigInteger faqCategoryId;
	private String faqCategoryName;
	private BigInteger faqSubCategoryId;
	private String faqSubCategoryName;
	private Date createdOn;
	private Date updatedOn;
	private String createdBy;
	private String updatedBy;
	private Boolean isActive;

	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Integer getVotes() {
		return votes;
	}

	public void setVotes(final Integer votes) {
		this.votes = votes;
	}

	public BigInteger getFaqCategoryId() {
		return faqCategoryId;
	}

	public void setFaqCategoryId(final BigInteger faqCategoryId) {
		this.faqCategoryId = faqCategoryId;
	}

	public String getFaqCategoryName() {
		return faqCategoryName;
	}

	public void setFaqCategoryName(final String faqCategoryName) {
		this.faqCategoryName = faqCategoryName;
	}

	public BigInteger getFaqSubCategoryId() {
		return faqSubCategoryId;
	}

	public void setFaqSubCategoryId(final BigInteger faqSubCategoryId) {
		this.faqSubCategoryId = faqSubCategoryId;
	}

	public String getFaqSubCategoryName() {
		return faqSubCategoryName;
	}

	public void setFaqSubCategoryName(final String faqSubCategoryName) {
		this.faqSubCategoryName = faqSubCategoryName;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (createdBy == null ? 0 : createdBy.hashCode());
		result = prime * result + (createdOn == null ? 0 : createdOn.hashCode());
		result = prime * result + (description == null ? 0 : description.hashCode());
		result = prime * result + (faqCategoryId == null ? 0 : faqCategoryId.hashCode());
		result = prime * result + (faqCategoryName == null ? 0 : faqCategoryName.hashCode());
		result = prime * result + (faqSubCategoryId == null ? 0 : faqSubCategoryId.hashCode());
		result = prime * result + (faqSubCategoryName == null ? 0 : faqSubCategoryName.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (isActive == null ? 0 : isActive.hashCode());
		result = prime * result + (title == null ? 0 : title.hashCode());
		result = prime * result + (updatedBy == null ? 0 : updatedBy.hashCode());
		result = prime * result + (updatedOn == null ? 0 : updatedOn.hashCode());
		result = prime * result + (votes == null ? 0 : votes.hashCode());
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
		FaqResponseDto other = (FaqResponseDto) obj;
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
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
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
		if (faqSubCategoryId == null) {
			if (other.faqSubCategoryId != null) {
				return false;
			}
		} else if (!faqSubCategoryId.equals(other.faqSubCategoryId)) {
			return false;
		}
		if (faqSubCategoryName == null) {
			if (other.faqSubCategoryName != null) {
				return false;
			}
		} else if (!faqSubCategoryName.equals(other.faqSubCategoryName)) {
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
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
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
		if (votes == null) {
			if (other.votes != null) {
				return false;
			}
		} else if (!votes.equals(other.votes)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FaqResponseDto [id=").append(id).append(", title=").append(title).append(", description=").append(description).append(", votes=")
				.append(votes).append(", faqCategoryId=").append(faqCategoryId).append(", faqCategoryName=").append(faqCategoryName)
				.append(", faqSubCategoryId=").append(faqSubCategoryId).append(", faqSubCategoryName=").append(faqSubCategoryName).append(", createdOn=")
				.append(createdOn).append(", updatedOn=").append(updatedOn).append(", createdBy=").append(createdBy).append(", updatedBy=").append(updatedBy)
				.append(", isActive=").append(isActive).append("]");
		return builder.toString();
	}

}
