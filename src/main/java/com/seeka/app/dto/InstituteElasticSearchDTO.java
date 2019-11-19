package com.seeka.app.dto;

import java.math.BigInteger;

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
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getInstituteTypeName() {
		return instituteTypeName;
	}
	public void setInstituteTypeName(String instituteTypeName) {
		this.instituteTypeName = instituteTypeName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getWorldRanking() {
		return worldRanking;
	}
	public void setWorldRanking(Integer worldRanking) {
		this.worldRanking = worldRanking;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCampus() {
		return campus;
	}
	public void setCampus(String campus) {
		this.campus = campus;
	}
	public String getLevelCode() {
		return levelCode;
	}
	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
	public Integer getStars() {
		return stars;
	}
	public void setStars(Integer stars) {
		this.stars = stars;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InstituteElasticSearchDTO [countryName=").append(countryName).append(", cityName=")
				.append(cityName).append(", instituteTypeName=").append(instituteTypeName).append(", name=")
				.append(name).append(", worldRanking=").append(worldRanking).append(", latitude=").append(latitude)
				.append(", longitude=").append(longitude).append(", description=").append(description)
				.append(", campus=").append(campus).append(", levelCode=").append(levelCode).append(", stars=")
				.append(stars).append("]");
		return builder.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((campus == null) ? 0 : campus.hashCode());
		result = prime * result + ((cityName == null) ? 0 : cityName.hashCode());
		result = prime * result + ((countryName == null) ? 0 : countryName.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((instituteTypeName == null) ? 0 : instituteTypeName.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((levelCode == null) ? 0 : levelCode.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((stars == null) ? 0 : stars.hashCode());
		result = prime * result + ((worldRanking == null) ? 0 : worldRanking.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstituteElasticSearchDTO other = (InstituteElasticSearchDTO) obj;
		if (campus == null) {
			if (other.campus != null)
				return false;
		} else if (!campus.equals(other.campus))
			return false;
		if (cityName == null) {
			if (other.cityName != null)
				return false;
		} else if (!cityName.equals(other.cityName))
			return false;
		if (countryName == null) {
			if (other.countryName != null)
				return false;
		} else if (!countryName.equals(other.countryName))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (instituteTypeName == null) {
			if (other.instituteTypeName != null)
				return false;
		} else if (!instituteTypeName.equals(other.instituteTypeName))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (levelCode == null) {
			if (other.levelCode != null)
				return false;
		} else if (!levelCode.equals(other.levelCode))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (stars == null) {
			if (other.stars != null)
				return false;
		} else if (!stars.equals(other.stars))
			return false;
		if (worldRanking == null) {
			if (other.worldRanking != null)
				return false;
		} else if (!worldRanking.equals(other.worldRanking))
			return false;
		return true;
	}
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
    
}
