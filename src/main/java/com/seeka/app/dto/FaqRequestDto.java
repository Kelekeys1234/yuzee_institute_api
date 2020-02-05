package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;

import javax.validation.constraints.NotNull;

public class FaqRequestDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5744814923342867841L;

	@NotNull(message = "title is required")
	private String title;
	@NotNull(message = "description is required")
	private String description;
	private Integer votes;
	private String faqCategoryId;
	private String faqSubCategoryId;

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

	public String getFaqCategoryId() {
		return faqCategoryId;
	}

	public void setFaqCategoryId(final String faqCategoryId) {
		this.faqCategoryId = faqCategoryId;
	}

	public String getFaqSubCategoryId() {
		return faqSubCategoryId;
	}

	public void setFaqSubCategoryId(final String faqSubCategoryId) {
		this.faqSubCategoryId = faqSubCategoryId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (description == null ? 0 : description.hashCode());
		result = prime * result + (faqCategoryId == null ? 0 : faqCategoryId.hashCode());
		result = prime * result + (faqSubCategoryId == null ? 0 : faqSubCategoryId.hashCode());
		result = prime * result + (title == null ? 0 : title.hashCode());
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
		FaqRequestDto other = (FaqRequestDto) obj;
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
		if (faqSubCategoryId == null) {
			if (other.faqSubCategoryId != null) {
				return false;
			}
		} else if (!faqSubCategoryId.equals(other.faqSubCategoryId)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
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
		builder.append("FaqRequestDto [title=").append(title).append(", description=").append(description).append(", votes=").append(votes)
				.append(", faqCategoryId=").append(faqCategoryId).append(", faqSubCategoryId=").append(faqSubCategoryId).append("]");
		return builder.toString();
	}

}
