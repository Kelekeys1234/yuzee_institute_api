package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author SeekADegree
 *
 */
public class EnrollmentImageDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -4469057908292528438L;
	private BigInteger id;
	private String imageType;
	private String imageName;
	private String imageURL;

	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
		this.id = id;
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

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(final String imageURL) {
		this.imageURL = imageURL;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (imageName == null ? 0 : imageName.hashCode());
		result = prime * result + (imageType == null ? 0 : imageType.hashCode());
		result = prime * result + (imageURL == null ? 0 : imageURL.hashCode());
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
		EnrollmentImageDto other = (EnrollmentImageDto) obj;
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
		if (imageURL == null) {
			if (other.imageURL != null) {
				return false;
			}
		} else if (!imageURL.equals(other.imageURL)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EnrollmentImageDto [id=").append(id).append(", imageType=").append(imageType).append(", imageName=").append(imageName)
				.append(", imageURL=").append(imageURL).append("]");
		return builder.toString();
	}

}
