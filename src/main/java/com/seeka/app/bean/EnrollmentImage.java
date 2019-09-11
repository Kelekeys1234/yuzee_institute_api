package com.seeka.app.bean;

import static javax.persistence.GenerationType.IDENTITY;

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

@Entity
@Table(name = "enrollment_image")
public class EnrollmentImage implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -4469057908292528438L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private BigInteger id;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "enrollment_id", nullable = false)
	private Enrollment enrollment;
	@Column(name = "image_type", nullable = false)
	private String imageType;
	@Column(name = "image_name", nullable = false)
	private String imageName;

	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
		this.id = id;
	}

	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(final Enrollment enrollment) {
		this.enrollment = enrollment;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(final String imageType) {
		this.imageType = imageType;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(final String imageName) {
		this.imageName = imageName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (enrollment == null ? 0 : enrollment.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (imageName == null ? 0 : imageName.hashCode());
		result = prime * result + (imageType == null ? 0 : imageType.hashCode());
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
		EnrollmentImage other = (EnrollmentImage) obj;
		if (enrollment == null) {
			if (other.enrollment != null) {
				return false;
			}
		} else if (!enrollment.equals(other.enrollment)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (imageName == null) {
			if (other.imageName != null) {
				return false;
			}
		} else if (!imageName.equals(other.imageName)) {
			return false;
		}
		if (imageType == null) {
			if (other.imageType != null) {
				return false;
			}
		} else if (!imageType.equals(other.imageType)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EnrollmentImage [id=").append(id).append(", enrollment=").append(enrollment).append(", imageType=").append(imageType)
				.append(", imageName=").append(imageName).append("]");
		return builder.toString();
	}

}
