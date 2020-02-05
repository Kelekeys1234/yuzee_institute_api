package com.seeka.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "faq")
public class Faq implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5744814923342867841L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;
	@Column(name = "title")
	private String title;
	@Column(name = "description")
	private String description;
	@Column(name = "votes")
	private Integer votes;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "faq_category_id")
	private FaqCategory faqCategory;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "faq_sub_category_id")
	private FaqSubCategory faqSubCategory;
	@Column(name = "created_on")
	private Date createdOn;
	@Column(name = "updated_on")
	private Date updatedOn;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "updated_by")
	private String updatedBy;
	@Column(name = "is_active")
	private Boolean isActive;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
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

	public FaqCategory getFaqCategory() {
		return faqCategory;
	}

	public void setFaqCategory(final FaqCategory faqCategory) {
		this.faqCategory = faqCategory;
	}

	public FaqSubCategory getFaqSubCategory() {
		return faqSubCategory;
	}

	public void setFaqSubCategory(final FaqSubCategory faqSubCategory) {
		this.faqSubCategory = faqSubCategory;
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
		result = prime * result + (faqCategory == null ? 0 : faqCategory.hashCode());
		result = prime * result + (faqSubCategory == null ? 0 : faqSubCategory.hashCode());
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
		Faq other = (Faq) obj;
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
		if (faqCategory == null) {
			if (other.faqCategory != null) {
				return false;
			}
		} else if (!faqCategory.equals(other.faqCategory)) {
			return false;
		}
		if (faqSubCategory == null) {
			if (other.faqSubCategory != null) {
				return false;
			}
		} else if (!faqSubCategory.equals(other.faqSubCategory)) {
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
		builder.append("Faq [id=").append(id).append(", title=").append(title).append(", description=").append(description).append(", votes=").append(votes)
				.append(", faqCategory=").append(faqCategory).append(", faqSubCategory=").append(faqSubCategory).append(", createdOn=").append(createdOn)
				.append(", updatedOn=").append(updatedOn).append(", createdBy=").append(createdBy).append(", updatedBy=").append(updatedBy)
				.append(", isActive=").append(isActive).append("]");
		return builder.toString();
	}

}
