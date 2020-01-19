package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class EducationAOLevelSubjectDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 473560159928318998L;
	private BigInteger subjectId;
	private String subjectName;
	private String grade;

	public BigInteger getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(final BigInteger subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(final String subjectName) {
		this.subjectName = subjectName;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(final String grade) {
		this.grade = grade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (grade == null ? 0 : grade.hashCode());
		result = prime * result + (subjectId == null ? 0 : subjectId.hashCode());
		result = prime * result + (subjectName == null ? 0 : subjectName.hashCode());
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
		EducationAOLevelSubjectDto other = (EducationAOLevelSubjectDto) obj;
		if (grade == null) {
			if (other.grade != null) {
				return false;
			}
		} else if (!grade.equals(other.grade)) {
			return false;
		}
		if (subjectId == null) {
			if (other.subjectId != null) {
				return false;
			}
		} else if (!subjectId.equals(other.subjectId)) {
			return false;
		}
		if (subjectName == null) {
			if (other.subjectName != null) {
				return false;
			}
		} else if (!subjectName.equals(other.subjectName)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EducationAOLevelSubjectDto [subjectId=").append(subjectId).append(", subjectName=").append(subjectName).append(", grade=").append(grade)
				.append("]");
		return builder.toString();
	}

}
