package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class UserReviewRatingDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 8831276275076190678L;

	private BigInteger id;
	private BigInteger reviewQuestionId;
	private Double rating;
	private String questionTitle;
	private BigInteger questionCategoryId;
	private String question;
	private String questionCategoryName;

	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
		this.id = id;
	}

	public BigInteger getReviewQuestionId() {
		return reviewQuestionId;
	}

	public void setReviewQuestionId(final BigInteger reviewQuestionId) {
		this.reviewQuestionId = reviewQuestionId;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(final Double rating) {
		this.rating = rating;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(final String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public BigInteger getQuestionCategoryId() {
		return questionCategoryId;
	}

	public void setQuestionCategoryId(final BigInteger questionCategoryId) {
		this.questionCategoryId = questionCategoryId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(final String question) {
		this.question = question;
	}

	public String getQuestionCategoryName() {
		return questionCategoryName;
	}

	public void setQuestionCategoryName(final String questionCategoryName) {
		this.questionCategoryName = questionCategoryName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (question == null ? 0 : question.hashCode());
		result = prime * result + (questionCategoryId == null ? 0 : questionCategoryId.hashCode());
		result = prime * result + (questionCategoryName == null ? 0 : questionCategoryName.hashCode());
		result = prime * result + (questionTitle == null ? 0 : questionTitle.hashCode());
		result = prime * result + (rating == null ? 0 : rating.hashCode());
		result = prime * result + (reviewQuestionId == null ? 0 : reviewQuestionId.hashCode());
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
		UserReviewRatingDto other = (UserReviewRatingDto) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (question == null) {
			if (other.question != null) {
				return false;
			}
		} else if (!question.equals(other.question)) {
			return false;
		}
		if (questionCategoryId == null) {
			if (other.questionCategoryId != null) {
				return false;
			}
		} else if (!questionCategoryId.equals(other.questionCategoryId)) {
			return false;
		}
		if (questionCategoryName == null) {
			if (other.questionCategoryName != null) {
				return false;
			}
		} else if (!questionCategoryName.equals(other.questionCategoryName)) {
			return false;
		}
		if (questionTitle == null) {
			if (other.questionTitle != null) {
				return false;
			}
		} else if (!questionTitle.equals(other.questionTitle)) {
			return false;
		}
		if (rating == null) {
			if (other.rating != null) {
				return false;
			}
		} else if (!rating.equals(other.rating)) {
			return false;
		}
		if (reviewQuestionId == null) {
			if (other.reviewQuestionId != null) {
				return false;
			}
		} else if (!reviewQuestionId.equals(other.reviewQuestionId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserReviewRatingDto [id=").append(id).append(", reviewQuestionId=").append(reviewQuestionId).append(", rating=").append(rating)
				.append(", questionTitle=").append(questionTitle).append(", questionCategoryId=").append(questionCategoryId).append(", question=")
				.append(question).append(", questionCategoryName=").append(questionCategoryName).append("]");
		return builder.toString();
	}

}
