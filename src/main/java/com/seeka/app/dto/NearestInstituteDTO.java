package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class NearestInstituteDTO implements Serializable {

	private static final long serialVersionUID = 3205327956325930806L;
	private BigInteger instituteId;
	private String instituteName;
	private Integer totalCourseCount;
	private Double minPriceRange;
	private Double maxPriceRange;
	private Double latitute;
	private Double longitude;
	private List<StorageDto> instituteLogoImages;

	public BigInteger getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(final BigInteger instituteId) {
		this.instituteId = instituteId;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(final String instituteName) {
		this.instituteName = instituteName;
	}

	public Integer getTotalCourseCount() {
		return totalCourseCount;
	}

	public void setTotalCourseCount(final Integer totalCourseCount) {
		this.totalCourseCount = totalCourseCount;
	}

	public Double getMinPriceRange() {
		return minPriceRange;
	}

	public void setMinPriceRange(final Double minPriceRange) {
		this.minPriceRange = minPriceRange;
	}

	public Double getMaxPriceRange() {
		return maxPriceRange;
	}

	public void setMaxPriceRange(final Double maxPriceRange) {
		this.maxPriceRange = maxPriceRange;
	}

	public List<StorageDto> getInstituteLogoImages() {
		return instituteLogoImages;
	}

	public void setInstituteLogoImages(final List<StorageDto> instituteLogoImages) {
		this.instituteLogoImages = instituteLogoImages;
	}

	public Double getLatitute() {
		return latitute;
	}

	public void setLatitute(final Double latitute) {
		this.latitute = latitute;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(final Double longitude) {
		this.longitude = longitude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (instituteId == null ? 0 : instituteId.hashCode());
		result = prime * result + (instituteLogoImages == null ? 0 : instituteLogoImages.hashCode());
		result = prime * result + (instituteName == null ? 0 : instituteName.hashCode());
		result = prime * result + (latitute == null ? 0 : latitute.hashCode());
		result = prime * result + (longitude == null ? 0 : longitude.hashCode());
		result = prime * result + (maxPriceRange == null ? 0 : maxPriceRange.hashCode());
		result = prime * result + (minPriceRange == null ? 0 : minPriceRange.hashCode());
		result = prime * result + (totalCourseCount == null ? 0 : totalCourseCount.hashCode());
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
		NearestInstituteDTO other = (NearestInstituteDTO) obj;
		if (instituteId == null) {
			if (other.instituteId != null) {
				return false;
			}
		} else if (!instituteId.equals(other.instituteId)) {
			return false;
		}
		if (instituteLogoImages == null) {
			if (other.instituteLogoImages != null) {
				return false;
			}
		} else if (!instituteLogoImages.equals(other.instituteLogoImages)) {
			return false;
		}
		if (instituteName == null) {
			if (other.instituteName != null) {
				return false;
			}
		} else if (!instituteName.equals(other.instituteName)) {
			return false;
		}
		if (latitute == null) {
			if (other.latitute != null) {
				return false;
			}
		} else if (!latitute.equals(other.latitute)) {
			return false;
		}
		if (longitude == null) {
			if (other.longitude != null) {
				return false;
			}
		} else if (!longitude.equals(other.longitude)) {
			return false;
		}
		if (maxPriceRange == null) {
			if (other.maxPriceRange != null) {
				return false;
			}
		} else if (!maxPriceRange.equals(other.maxPriceRange)) {
			return false;
		}
		if (minPriceRange == null) {
			if (other.minPriceRange != null) {
				return false;
			}
		} else if (!minPriceRange.equals(other.minPriceRange)) {
			return false;
		}
		if (totalCourseCount == null) {
			if (other.totalCourseCount != null) {
				return false;
			}
		} else if (!totalCourseCount.equals(other.totalCourseCount)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NearestInstituteDTO [instituteId=").append(instituteId).append(", instituteName=").append(instituteName).append(", totalCourseCount=")
				.append(totalCourseCount).append(", minPriceRange=").append(minPriceRange).append(", maxPriceRange=").append(maxPriceRange)
				.append(", latitute=").append(latitute).append(", longitude=").append(longitude).append(", instituteLogoImages=").append(instituteLogoImages)
				.append("]");
		return builder.toString();
	}

}
