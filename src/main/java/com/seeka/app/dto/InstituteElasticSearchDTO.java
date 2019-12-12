package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

public class InstituteElasticSearchDTO {

	private BigInteger id;
	private String countryName;
	private String cityName;
	private String instituteTypeName;
	private String name;
	private Integer worldRanking;
	private String latitude;
	private String longitude;
	private String description;
	private String campus;
	private String levelCode;
	private Integer stars;
	private List<String> facultyNames;
	private List<String> intakes;

	public List<String> getIntakes() {
		return intakes;
	}

	public void setIntakes(final List<String> intakes) {
		this.intakes = intakes;
	}

	public List<String> getFacultyNames() {
		return facultyNames;
	}

	public void setFacultyNames(final List<String> facultyNames) {
		this.facultyNames = facultyNames;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(final String countryName) {
		this.countryName = countryName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(final String cityName) {
		this.cityName = cityName;
	}

	public String getInstituteTypeName() {
		return instituteTypeName;
	}

	public void setInstituteTypeName(final String instituteTypeName) {
		this.instituteTypeName = instituteTypeName;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Integer getWorldRanking() {
		return worldRanking;
	}

	public void setWorldRanking(final Integer worldRanking) {
		this.worldRanking = worldRanking;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(final String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(final String longitude) {
		this.longitude = longitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(final String campus) {
		this.campus = campus;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(final String levelCode) {
		this.levelCode = levelCode;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(final Integer stars) {
		this.stars = stars;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InstituteElasticSearchDTO [id=").append(id).append(", countryName=").append(countryName).append(", cityName=").append(cityName)
				.append(", instituteTypeName=").append(instituteTypeName).append(", name=").append(name).append(", worldRanking=").append(worldRanking)
				.append(", latitude=").append(latitude).append(", longitude=").append(longitude).append(", description=").append(description)
				.append(", campus=").append(campus).append(", levelCode=").append(levelCode).append(", stars=").append(stars).append(", facultyNames=")
				.append(facultyNames).append(", intakes=").append(intakes).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((campus == null) ? 0 : campus.hashCode());
		result = (prime * result) + ((cityName == null) ? 0 : cityName.hashCode());
		result = (prime * result) + ((countryName == null) ? 0 : countryName.hashCode());
		result = (prime * result) + ((description == null) ? 0 : description.hashCode());
		result = (prime * result) + ((facultyNames == null) ? 0 : facultyNames.hashCode());
		result = (prime * result) + ((id == null) ? 0 : id.hashCode());
		result = (prime * result) + ((instituteTypeName == null) ? 0 : instituteTypeName.hashCode());
		result = (prime * result) + ((intakes == null) ? 0 : intakes.hashCode());
		result = (prime * result) + ((latitude == null) ? 0 : latitude.hashCode());
		result = (prime * result) + ((levelCode == null) ? 0 : levelCode.hashCode());
		result = (prime * result) + ((longitude == null) ? 0 : longitude.hashCode());
		result = (prime * result) + ((name == null) ? 0 : name.hashCode());
		result = (prime * result) + ((stars == null) ? 0 : stars.hashCode());
		result = (prime * result) + ((worldRanking == null) ? 0 : worldRanking.hashCode());
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
		InstituteElasticSearchDTO other = (InstituteElasticSearchDTO) obj;
		if (campus == null) {
			if (other.campus != null) {
				return false;
			}
		} else if (!campus.equals(other.campus)) {
			return false;
		}
		if (cityName == null) {
			if (other.cityName != null) {
				return false;
			}
		} else if (!cityName.equals(other.cityName)) {
			return false;
		}
		if (countryName == null) {
			if (other.countryName != null) {
				return false;
			}
		} else if (!countryName.equals(other.countryName)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (facultyNames == null) {
			if (other.facultyNames != null) {
				return false;
			}
		} else if (!facultyNames.equals(other.facultyNames)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (instituteTypeName == null) {
			if (other.instituteTypeName != null) {
				return false;
			}
		} else if (!instituteTypeName.equals(other.instituteTypeName)) {
			return false;
		}
		if (intakes == null) {
			if (other.intakes != null) {
				return false;
			}
		} else if (!intakes.equals(other.intakes)) {
			return false;
		}
		if (latitude == null) {
			if (other.latitude != null) {
				return false;
			}
		} else if (!latitude.equals(other.latitude)) {
			return false;
		}
		if (levelCode == null) {
			if (other.levelCode != null) {
				return false;
			}
		} else if (!levelCode.equals(other.levelCode)) {
			return false;
		}
		if (longitude == null) {
			if (other.longitude != null) {
				return false;
			}
		} else if (!longitude.equals(other.longitude)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (stars == null) {
			if (other.stars != null) {
				return false;
			}
		} else if (!stars.equals(other.stars)) {
			return false;
		}
		if (worldRanking == null) {
			if (other.worldRanking != null) {
				return false;
			}
		} else if (!worldRanking.equals(other.worldRanking)) {
			return false;
		}
		return true;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
		this.id = id;
	}

}
