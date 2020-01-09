package com.seeka.app.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigInteger;

// Generated 7 Jun, 2019 2:45:49 PM by Hibernate Tools 4.3.1

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

/**
 *
 * @author SeekADegree
 *
 */
@Entity
@Table(name = "user_watch_course")
public class UserWatchCourse implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2268386819742613960L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private BigInteger id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;
	@Column(name = "user_id", nullable = false)
	private BigInteger userId;
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

	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(final Course course) {
		this.course = course;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(final BigInteger userId) {
		this.userId = userId;
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
		result = prime * result + (course == null ? 0 : course.hashCode());
		result = prime * result + (createdBy == null ? 0 : createdBy.hashCode());
		result = prime * result + (createdOn == null ? 0 : createdOn.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (updatedBy == null ? 0 : updatedBy.hashCode());
		result = prime * result + (updatedOn == null ? 0 : updatedOn.hashCode());
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
		UserWatchCourse other = (UserWatchCourse) obj;
		if (course == null) {
			if (other.course != null) {
				return false;
			}
		} else if (!course.equals(other.course)) {
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
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
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
		builder.append("UserWatchCourse [id=").append(id).append(", course=").append(course).append(", userId=").append(userId).append(", createdOn=")
				.append(createdOn).append(", updatedOn=").append(updatedOn).append(", createdBy=").append(createdBy).append(", updatedBy=").append(updatedBy)
				.append("]");
		return builder.toString();
	}

}
