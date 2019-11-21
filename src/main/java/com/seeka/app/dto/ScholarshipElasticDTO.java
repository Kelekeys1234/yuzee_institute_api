package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class ScholarshipElasticDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3487477078664375741L;
	private BigInteger id;
	private String name;
    private String countryName;
    private String instituteName;
    private String cityName;
    private String offeredBy;
    private String levelName;
    private String applicationDeadlineString;
    private Double amount;
    private String description;
   	private String website;
   	private String content;
   	private Date applicationDeadline;
   	private String levelCode;
   	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getOfferedBy() {
		return offeredBy;
	}
	public void setOfferedBy(String offeredBy) {
		this.offeredBy = offeredBy;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public Date getApplicationDeadline() {
		return applicationDeadline;
	}
	public void setApplicationDeadline(Date applicationDeadline) {
		this.applicationDeadline = applicationDeadline;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ScholarshipElasticDTO [id=").append(id).append(", name=").append(name).append(", countryName=")
				.append(countryName).append(", instituteName=").append(instituteName).append(", cityName=")
				.append(cityName).append(", offeredBy=").append(offeredBy).append(", levelName=").append(levelName)
				.append(", applicationDeadline=").append(applicationDeadline).append(", amount=").append(amount)
				.append(", description=").append(description).append(", website=").append(website).append(", content=")
				.append(content).append("]");
		return builder.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((applicationDeadline == null) ? 0 : applicationDeadline.hashCode());
		result = prime * result + ((cityName == null) ? 0 : cityName.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((countryName == null) ? 0 : countryName.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((instituteName == null) ? 0 : instituteName.hashCode());
		result = prime * result + ((levelName == null) ? 0 : levelName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((offeredBy == null) ? 0 : offeredBy.hashCode());
		result = prime * result + ((website == null) ? 0 : website.hashCode());
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
		ScholarshipElasticDTO other = (ScholarshipElasticDTO) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (applicationDeadline == null) {
			if (other.applicationDeadline != null)
				return false;
		} else if (!applicationDeadline.equals(other.applicationDeadline))
			return false;
		if (cityName == null) {
			if (other.cityName != null)
				return false;
		} else if (!cityName.equals(other.cityName))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (instituteName == null) {
			if (other.instituteName != null)
				return false;
		} else if (!instituteName.equals(other.instituteName))
			return false;
		if (levelName == null) {
			if (other.levelName != null)
				return false;
		} else if (!levelName.equals(other.levelName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (offeredBy == null) {
			if (other.offeredBy != null)
				return false;
		} else if (!offeredBy.equals(other.offeredBy))
			return false;
		if (website == null) {
			if (other.website != null)
				return false;
		} else if (!website.equals(other.website))
			return false;
		return true;
	}
	public String getApplicationDeadlineString() {
		return applicationDeadlineString;
	}
	public void setApplicationDeadlineString(String applicationDeadlineString) {
		this.applicationDeadlineString = applicationDeadlineString;
	}
	public String getLevelCode() {
		return levelCode;
	}
	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
	
	
}
