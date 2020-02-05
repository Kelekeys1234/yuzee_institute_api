package com.seeka.app.bean;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.CascadeType;
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
@Table(name = "user_review_rating")
public class UserReviewRating implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 8527758588935720411L;
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_review_id", nullable = false)
	private UserReview userReview;
	@Column(name = "review_question_id", nullable = false)
	private String reviewQuestionId;
	@Column(name = "rating", nullable = false, precision = 4)
	private Double rating;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public UserReview getUserReview() {
		return userReview;
	}

	public void setUserReview(final UserReview userReview) {
		this.userReview = userReview;
	}

	public String getReviewQuestionId() {
		return reviewQuestionId;
	}

	public void setReviewQuestionId(final String reviewQuestionId) {
		this.reviewQuestionId = reviewQuestionId;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(final Double rating) {
		this.rating = rating;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (reviewQuestionId == null ? 0 : reviewQuestionId.hashCode());
		result = prime * result + (rating == null ? 0 : rating.hashCode());
		result = prime * result + (userReview == null ? 0 : userReview.hashCode());
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
		UserReviewRating other = (UserReviewRating) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (reviewQuestionId == null) {
			if (other.reviewQuestionId != null) {
				return false;
			}
		} else if (!reviewQuestionId.equals(other.reviewQuestionId)) {
			return false;
		}
		if (rating == null) {
			if (other.rating != null) {
				return false;
			}
		} else if (!rating.equals(other.rating)) {
			return false;
		}
		if (userReview == null) {
			if (other.userReview != null) {
				return false;
			}
		} else if (!userReview.equals(other.userReview)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserReviewRating [id=").append(id).append(", userReview=").append(userReview).append(", reviewQuestionId=").append(reviewQuestionId)
				.append(", rating=").append(rating).append("]");
		return builder.toString();
	}

}
