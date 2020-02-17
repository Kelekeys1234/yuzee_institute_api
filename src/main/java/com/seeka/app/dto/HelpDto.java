package com.seeka.app.dto;

public class HelpDto {

	private String id;
	private String title;
	private String categoryId;
	private String subCategoryId;
	private String description;
	private String createdBy;
	private String updatedBy;
	private Boolean isQuestioning;
	private String createdUser;
	private String Status;
	private String assignedUser;
	private String createdOn;
	private Boolean isArchive;

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(final String categoryId) {
		this.categoryId = categoryId;
	}

	public String getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(final String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
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

	public Boolean getIsQuestioning() {
		return isQuestioning;
	}

	public void setIsQuestioning(final Boolean isQuestioning) {
		this.isQuestioning = isQuestioning;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the createdUser
	 */
	public String getCreatedUser() {
		return createdUser;
	}

	/**
	 * @param createdUser the createdUser to set
	 */
	public void setCreatedUser(final String createdUser) {
		this.createdUser = createdUser;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return Status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(final String status) {
		Status = status;
	}

	/**
	 * @return the assignedUser
	 */
	public String getAssignedUser() {
		return assignedUser;
	}

	/**
	 * @param assignedUser the assignedUser to set
	 */
	public void setAssignedUser(final String assignedUser) {
		this.assignedUser = assignedUser;
	}

	/**
	 * @return the createdOn
	 */
	public String getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(final String createdOn) {
		this.createdOn = createdOn;
	}

	public Boolean getIsArchive() {
		return isArchive;
	}

	public void setIsArchive(final Boolean isArchive) {
		this.isArchive = isArchive;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (Status == null ? 0 : Status.hashCode());
		result = prime * result + (assignedUser == null ? 0 : assignedUser.hashCode());
		result = prime * result + (categoryId == null ? 0 : categoryId.hashCode());
		result = prime * result + (createdBy == null ? 0 : createdBy.hashCode());
		result = prime * result + (createdOn == null ? 0 : createdOn.hashCode());
		result = prime * result + (createdUser == null ? 0 : createdUser.hashCode());
		result = prime * result + (description == null ? 0 : description.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (isArchive == null ? 0 : isArchive.hashCode());
		result = prime * result + (isQuestioning == null ? 0 : isQuestioning.hashCode());
		result = prime * result + (subCategoryId == null ? 0 : subCategoryId.hashCode());
		result = prime * result + (title == null ? 0 : title.hashCode());
		result = prime * result + (updatedBy == null ? 0 : updatedBy.hashCode());
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
		HelpDto other = (HelpDto) obj;
		if (Status == null) {
			if (other.Status != null) {
				return false;
			}
		} else if (!Status.equals(other.Status)) {
			return false;
		}
		if (assignedUser == null) {
			if (other.assignedUser != null) {
				return false;
			}
		} else if (!assignedUser.equals(other.assignedUser)) {
			return false;
		}
		if (categoryId == null) {
			if (other.categoryId != null) {
				return false;
			}
		} else if (!categoryId.equals(other.categoryId)) {
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
		if (createdUser == null) {
			if (other.createdUser != null) {
				return false;
			}
		} else if (!createdUser.equals(other.createdUser)) {
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
		if (isArchive == null) {
			if (other.isArchive != null) {
				return false;
			}
		} else if (!isArchive.equals(other.isArchive)) {
			return false;
		}
		if (isQuestioning == null) {
			if (other.isQuestioning != null) {
				return false;
			}
		} else if (!isQuestioning.equals(other.isQuestioning)) {
			return false;
		}
		if (subCategoryId == null) {
			if (other.subCategoryId != null) {
				return false;
			}
		} else if (!subCategoryId.equals(other.subCategoryId)) {
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
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HelpDto [id=").append(id).append(", title=").append(title).append(", categoryId=").append(categoryId).append(", subCategoryId=")
				.append(subCategoryId).append(", description=").append(description).append(", createdBy=").append(createdBy).append(", updatedBy=")
				.append(updatedBy).append(", isQuestioning=").append(isQuestioning).append(", createdUser=").append(createdUser).append(", Status=")
				.append(Status).append(", assignedUser=").append(assignedUser).append(", createdOn=").append(createdOn).append(", isArchive=").append(isArchive)
				.append("]");
		return builder.toString();
	}

}
