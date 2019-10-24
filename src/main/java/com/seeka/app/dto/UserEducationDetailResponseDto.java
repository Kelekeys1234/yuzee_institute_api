package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class UserEducationDetailResponseDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4119989833427065164L;
	private BigInteger educationCountryId;
	private BigInteger educationSystemId;
	private BigInteger educationLevelId;
	private String educationInstitue;
	private String gpaScore;
	private Boolean isEnglishMedium;
	private String englishLevel;
	private String eduSysScore;
	private Integer fromDuration;
	private Integer toDuration;
	private String eduLevel;
	private String educationSystemName;
	private String educationCountryName;

	public BigInteger getEducationCountryId() {
		return educationCountryId;
	}

	public void setEducationCountryId(final BigInteger educationCountryId) {
		this.educationCountryId = educationCountryId;
	}

	public BigInteger getEducationSystemId() {
		return educationSystemId;
	}

	public void setEducationSystemId(final BigInteger educationSystemId) {
		this.educationSystemId = educationSystemId;
	}

	public BigInteger getEducationLevelId() {
		return educationLevelId;
	}

	public void setEducationLevelId(final BigInteger educationLevelId) {
		this.educationLevelId = educationLevelId;
	}

	public String getEducationInstitue() {
		return educationInstitue;
	}

	public void setEducationInstitue(final String educationInstitue) {
		this.educationInstitue = educationInstitue;
	}

	public String getGpaScore() {
		return gpaScore;
	}

	public void setGpaScore(final String gpaScore) {
		this.gpaScore = gpaScore;
	}

	public Boolean getIsEnglishMedium() {
		return isEnglishMedium;
	}

	public void setIsEnglishMedium(final Boolean isEnglishMedium) {
		this.isEnglishMedium = isEnglishMedium;
	}

	public String getEnglishLevel() {
		return englishLevel;
	}

	public void setEnglishLevel(final String englishLevel) {
		this.englishLevel = englishLevel;
	}

	public String getEduSysScore() {
		return eduSysScore;
	}

	public void setEduSysScore(final String eduSysScore) {
		this.eduSysScore = eduSysScore;
	}

	public Integer getFromDuration() {
		return fromDuration;
	}

	public void setFromDuration(final Integer fromDuration) {
		this.fromDuration = fromDuration;
	}

	public Integer getToDuration() {
		return toDuration;
	}

	public void setToDuration(final Integer toDuration) {
		this.toDuration = toDuration;
	}

	public String getEduLevel() {
		return eduLevel;
	}

	public void setEduLevel(final String eduLevel) {
		this.eduLevel = eduLevel;
	}

	public String getEducationSystemName() {
		return educationSystemName;
	}

	public void setEducationSystemName(final String educationSystemName) {
		this.educationSystemName = educationSystemName;
	}

	public String getEducationCountryName() {
		return educationCountryName;
	}

	public void setEducationCountryName(final String educationCountryName) {
		this.educationCountryName = educationCountryName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (eduLevel == null ? 0 : eduLevel.hashCode());
		result = prime * result + (eduSysScore == null ? 0 : eduSysScore.hashCode());
		result = prime * result + (educationCountryId == null ? 0 : educationCountryId.hashCode());
		result = prime * result + (educationCountryName == null ? 0 : educationCountryName.hashCode());
		result = prime * result + (educationInstitue == null ? 0 : educationInstitue.hashCode());
		result = prime * result + (educationLevelId == null ? 0 : educationLevelId.hashCode());
		result = prime * result + (educationSystemId == null ? 0 : educationSystemId.hashCode());
		result = prime * result + (educationSystemName == null ? 0 : educationSystemName.hashCode());
		result = prime * result + (englishLevel == null ? 0 : englishLevel.hashCode());
		result = prime * result + (fromDuration == null ? 0 : fromDuration.hashCode());
		result = prime * result + (gpaScore == null ? 0 : gpaScore.hashCode());
		result = prime * result + (isEnglishMedium == null ? 0 : isEnglishMedium.hashCode());
		result = prime * result + (toDuration == null ? 0 : toDuration.hashCode());
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
		UserEducationDetailResponseDto other = (UserEducationDetailResponseDto) obj;
		if (eduLevel == null) {
			if (other.eduLevel != null) {
				return false;
			}
		} else if (!eduLevel.equals(other.eduLevel)) {
			return false;
		}
		if (eduSysScore == null) {
			if (other.eduSysScore != null) {
				return false;
			}
		} else if (!eduSysScore.equals(other.eduSysScore)) {
			return false;
		}
		if (educationCountryId == null) {
			if (other.educationCountryId != null) {
				return false;
			}
		} else if (!educationCountryId.equals(other.educationCountryId)) {
			return false;
		}
		if (educationCountryName == null) {
			if (other.educationCountryName != null) {
				return false;
			}
		} else if (!educationCountryName.equals(other.educationCountryName)) {
			return false;
		}
		if (educationInstitue == null) {
			if (other.educationInstitue != null) {
				return false;
			}
		} else if (!educationInstitue.equals(other.educationInstitue)) {
			return false;
		}
		if (educationLevelId == null) {
			if (other.educationLevelId != null) {
				return false;
			}
		} else if (!educationLevelId.equals(other.educationLevelId)) {
			return false;
		}
		if (educationSystemId == null) {
			if (other.educationSystemId != null) {
				return false;
			}
		} else if (!educationSystemId.equals(other.educationSystemId)) {
			return false;
		}
		if (educationSystemName == null) {
			if (other.educationSystemName != null) {
				return false;
			}
		} else if (!educationSystemName.equals(other.educationSystemName)) {
			return false;
		}
		if (englishLevel == null) {
			if (other.englishLevel != null) {
				return false;
			}
		} else if (!englishLevel.equals(other.englishLevel)) {
			return false;
		}
		if (fromDuration == null) {
			if (other.fromDuration != null) {
				return false;
			}
		} else if (!fromDuration.equals(other.fromDuration)) {
			return false;
		}
		if (gpaScore == null) {
			if (other.gpaScore != null) {
				return false;
			}
		} else if (!gpaScore.equals(other.gpaScore)) {
			return false;
		}
		if (isEnglishMedium == null) {
			if (other.isEnglishMedium != null) {
				return false;
			}
		} else if (!isEnglishMedium.equals(other.isEnglishMedium)) {
			return false;
		}
		if (toDuration == null) {
			if (other.toDuration != null) {
				return false;
			}
		} else if (!toDuration.equals(other.toDuration)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EducationDetailRequestDto [educationCountryId=").append(educationCountryId).append(", educationSystemId=").append(educationSystemId)
				.append(", educationLevelId=").append(educationLevelId).append(", educationInstitue=").append(educationInstitue).append(", gpaScore=")
				.append(gpaScore).append(", isEnglishMedium=").append(isEnglishMedium).append(", englishLevel=").append(englishLevel).append(", eduSysScore=")
				.append(eduSysScore).append(", fromDuration=").append(fromDuration).append(", toDuration=").append(toDuration).append(", eduLevel=")
				.append(eduLevel).append(", educationSystemName=").append(educationSystemName).append(", educationCountryName=").append(educationCountryName)
				.append("]");
		return builder.toString();
	}

}
