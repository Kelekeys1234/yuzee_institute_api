package com.seeka.app.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class ReviewQuestionsDto implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -6404309246018938368L;
	private String id;
	@NotNull(message = "studentType is Required")
	private String studentType;
	@NotNull(message = "studentCategory is Required")
	private String studentCategory;
	@NotNull(message = "questionTitle is Required")
	private String questionTitle;
	@NotNull(message = "questionCategoryId is Required")
	private String questionCategoryId;
	@NotNull(message = "question is Required")
	private String question;
	private String questionCategoryName;
	private Boolean isActive;
	private Date createdOn;
	private Date updatedOn;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getStudentType() {
		return studentType;
	}

	public void setStudentType(final String studentType) {
		this.studentType = studentType;
	}

	public String getStudentCategory() {
		return studentCategory;
	}

	public void setStudentCategory(final String studentCategory) {
		this.studentCategory = studentCategory;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(final String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getQuestionCategoryId() {
		return questionCategoryId;
	}

	public void setQuestionCategoryId(final String questionCategoryId) {
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(final Boolean isActive) {
		this.isActive = isActive;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (createdOn == null ? 0 : createdOn.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (isActive == null ? 0 : isActive.hashCode());
		result = prime * result + (question == null ? 0 : question.hashCode());
		result = prime * result + (questionCategoryId == null ? 0 : questionCategoryId.hashCode());
		result = prime * result + (questionCategoryName == null ? 0 : questionCategoryName.hashCode());
		result = prime * result + (questionTitle == null ? 0 : questionTitle.hashCode());
		result = prime * result + (studentCategory == null ? 0 : studentCategory.hashCode());
		result = prime * result + (studentType == null ? 0 : studentType.hashCode());
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
		ReviewQuestionsDto other = (ReviewQuestionsDto) obj;
		if (createdOn == null) {
			if (other.createdOn != null) {
				return false;
			}
		} else if (!createdOn.equals(other.createdOn)) {
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
		if (studentCategory == null) {
			if (other.studentCategory != null) {
				return false;
			}
		} else if (!studentCategory.equals(other.studentCategory)) {
			return false;
		}
		if (studentType == null) {
			if (other.studentType != null) {
				return false;
			}
		} else if (!studentType.equals(other.studentType)) {
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
		builder.append("ReviewQuestionsDto [id=").append(id).append(", studentType=").append(studentType).append(", studentCategory=").append(studentCategory)
				.append(", questionTitle=").append(questionTitle).append(", questionCategoryId=").append(questionCategoryId).append(", question=")
				.append(question).append(", questionCategoryName=").append(questionCategoryName).append(", isActive=").append(isActive).append(", createdOn=")
				.append(createdOn).append(", updatedOn=").append(updatedOn).append("]");
		return builder.toString();
	}

}
