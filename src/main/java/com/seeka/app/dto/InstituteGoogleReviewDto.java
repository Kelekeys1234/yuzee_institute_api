package com.seeka.app.dto;

import java.io.Serializable;
import java.util.Date;

public class InstituteGoogleReviewDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6061441361067943776L;

	private String userName;
	private Date dateOfPosting;
	private Double reviewStar;
	private String reviewComment;
	private String instituteName;
	private String countryName;
	private String instituteId;
	private String countryId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public Date getDateOfPosting() {
		return dateOfPosting;
	}

	public void setDateOfPosting(final Date dateOfPosting) {
		this.dateOfPosting = dateOfPosting;
	}

	public Double getReviewStar() {
		return reviewStar;
	}

	public void setReviewStar(final Double reviewStar) {
		this.reviewStar = reviewStar;
	}

	public String getReviewComment() {
		return reviewComment;
	}

	public void setReviewComment(final String reviewComment) {
		this.reviewComment = reviewComment;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(final String instituteName) {
		this.instituteName = instituteName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(final String countryName) {
		this.countryName = countryName;
	}

	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(final String instituteId) {
		this.instituteId = instituteId;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(final String countryId) {
		this.countryId = countryId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (countryId == null ? 0 : countryId.hashCode());
		result = prime * result + (countryName == null ? 0 : countryName.hashCode());
		result = prime * result + (dateOfPosting == null ? 0 : dateOfPosting.hashCode());
		result = prime * result + (instituteId == null ? 0 : instituteId.hashCode());
		result = prime * result + (instituteName == null ? 0 : instituteName.hashCode());
		result = prime * result + (reviewComment == null ? 0 : reviewComment.hashCode());
		result = prime * result + (reviewStar == null ? 0 : reviewStar.hashCode());
		result = prime * result + (userName == null ? 0 : userName.hashCode());
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
		InstituteGoogleReviewDto other = (InstituteGoogleReviewDto) obj;
		if (countryId == null) {
			if (other.countryId != null) {
				return false;
			}
		} else if (!countryId.equals(other.countryId)) {
			return false;
		}
		if (countryName == null) {
			if (other.countryName != null) {
				return false;
			}
		} else if (!countryName.equals(other.countryName)) {
			return false;
		}
		if (dateOfPosting == null) {
			if (other.dateOfPosting != null) {
				return false;
			}
		} else if (!dateOfPosting.equals(other.dateOfPosting)) {
			return false;
		}
		if (instituteId == null) {
			if (other.instituteId != null) {
				return false;
			}
		} else if (!instituteId.equals(other.instituteId)) {
			return false;
		}
		if (instituteName == null) {
			if (other.instituteName != null) {
				return false;
			}
		} else if (!instituteName.equals(other.instituteName)) {
			return false;
		}
		if (reviewComment == null) {
			if (other.reviewComment != null) {
				return false;
			}
		} else if (!reviewComment.equals(other.reviewComment)) {
			return false;
		}
		if (reviewStar == null) {
			if (other.reviewStar != null) {
				return false;
			}
		} else if (!reviewStar.equals(other.reviewStar)) {
			return false;
		}
		if (userName == null) {
			if (other.userName != null) {
				return false;
			}
		} else if (!userName.equals(other.userName)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReviewDto [userName=").append(userName).append(", dateOfPosting=").append(dateOfPosting).append(", reviewStar=").append(reviewStar)
				.append(", reviewComment=").append(reviewComment).append(", instituteName=").append(instituteName).append(", countryName=").append(countryName)
				.append(", instituteId=").append(instituteId).append(", countryId=").append(countryId).append("]");
		return builder.toString();
	}

}
