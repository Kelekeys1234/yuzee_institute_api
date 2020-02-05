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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "institute_google_review")
public class InstituteGoogleReview implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -961510910091914896L;
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "institute_id", nullable = false)
	private Institute institute;
	@Column(name = "user_name", nullable = false)
	private String userName;
	@Column(name = "date_of_posting", nullable = true)
	private Date dateOfPosting;
	@Column(name = "review_star", nullable = false)
	private Double reviewStar;
	@Column(name = "review_comment", nullable = true)
	private String reviewComment;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "country_id")
	private Country country;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	private Date createdOn;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	private Date updatedOn;
	@Column(name = "created_by", length = 50)
	private String createdBy;
	@Column(name = "updated_by", length = 50)
	private String updatedBy;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(final Institute institute) {
		this.institute = institute;
	}

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

	public Country getCountry() {
		return country;
	}

	public void setCountry(final Country country) {
		this.country = country;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (country == null ? 0 : country.hashCode());
		result = prime * result + (createdBy == null ? 0 : createdBy.hashCode());
		result = prime * result + (createdOn == null ? 0 : createdOn.hashCode());
		result = prime * result + (dateOfPosting == null ? 0 : dateOfPosting.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (institute == null ? 0 : institute.hashCode());
		result = prime * result + (reviewComment == null ? 0 : reviewComment.hashCode());
		result = prime * result + (reviewStar == null ? 0 : reviewStar.hashCode());
		result = prime * result + (updatedBy == null ? 0 : updatedBy.hashCode());
		result = prime * result + (updatedOn == null ? 0 : updatedOn.hashCode());
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
		InstituteGoogleReview other = (InstituteGoogleReview) obj;
		if (country == null) {
			if (other.country != null) {
				return false;
			}
		} else if (!country.equals(other.country)) {
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
		if (dateOfPosting == null) {
			if (other.dateOfPosting != null) {
				return false;
			}
		} else if (!dateOfPosting.equals(other.dateOfPosting)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (institute == null) {
			if (other.institute != null) {
				return false;
			}
		} else if (!institute.equals(other.institute)) {
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
		builder.append("InstituteGoogleReview [id=").append(id).append(", institute=").append(institute).append(", userName=").append(userName)
				.append(", dateOfPosting=").append(dateOfPosting).append(", reviewStar=").append(reviewStar).append(", reviewComment=").append(reviewComment)
				.append(", country=").append(country).append(", createdOn=").append(createdOn).append(", updatedOn=").append(updatedOn).append(", createdBy=")
				.append(createdBy).append(", updatedBy=").append(updatedBy).append("]");
		return builder.toString();
	}

}
