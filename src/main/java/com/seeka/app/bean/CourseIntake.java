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
@Table(name = "course_intake")
public class CourseIntake implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 6549666149894604387L;
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;
	@Column(name = "intake_date")
	private Date intakeDates;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private Course course;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public Date getIntakeDates() {
		return intakeDates;
	}

	public void setIntakeDates(final Date intakeDates) {
		this.intakeDates = intakeDates;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(final Course course) {
		this.course = course;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (course == null ? 0 : course.hashCode());
		result = (prime * result) + (id == null ? 0 : id.hashCode());
		result = (prime * result) + (intakeDates == null ? 0 : intakeDates.hashCode());
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
		CourseIntake other = (CourseIntake) obj;
		if (course == null) {
			if (other.course != null) {
				return false;
			}
		} else if (!course.equals(other.course)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (intakeDates == null) {
			if (other.intakeDates != null) {
				return false;
			}
		} else if (!intakeDates.equals(other.intakeDates)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CourseIntake [id=").append(id).append(", name=").append(intakeDates).append(", course=").append(course).append("]");
		return builder.toString();
	}

}
