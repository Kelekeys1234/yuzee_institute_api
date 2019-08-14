package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class UserMyCourseRequestDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5780245895720098894L;
	private BigInteger userId;
	private BigInteger courseId;

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(final BigInteger userId) {
		this.userId = userId;
	}

	public BigInteger getCourseId() {
		return courseId;
	}

	public void setCourseId(final BigInteger courseId) {
		this.courseId = courseId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (courseId == null ? 0 : courseId.hashCode());
		result = prime * result + (userId == null ? 0 : userId.hashCode());
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
		UserMyCourseRequestDto other = (UserMyCourseRequestDto) obj;
		if (courseId == null) {
			if (other.courseId != null) {
				return false;
			}
		} else if (!courseId.equals(other.courseId)) {
			return false;
		}
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserCourseRequestDto [userId=").append(userId).append(", courseId=").append(courseId).append("]");
		return builder.toString();
	}

}
