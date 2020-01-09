package com.seeka.app.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigInteger;
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
 * CountryEnglishEligibility generated by hbm2java
 */
@Entity
@Table(name = "country_english_eligibility")
public class CountryEnglishEligibility implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8884333439508471297L;
	private BigInteger id;
	private Country country;
	private String englishType;
	private Double reading;
	private Double writing;
	private Double speaking;
	private Double listening;
	private Double overall;
	private Boolean isActive;
	private Date createdOn;
	private Date updatedOn;
	private Date deletedOn;
	private String createdBy;
	private String updatedBy;
	private Boolean isDeleted;

	public CountryEnglishEligibility() {
	}

	public CountryEnglishEligibility(Country country) {
		this.country = country;
	}

	public CountryEnglishEligibility(Country country, String englishType, Double reading, Double writing,
			Double speaking, Double listening, Double overall, Boolean isActive, Date createdOn, Date updatedOn,
			Date deletedOn, String createdBy, String updatedBy, Boolean isDeleted) {
		this.country = country;
		this.englishType = englishType;
		this.reading = reading;
		this.writing = writing;
		this.speaking = speaking;
		this.listening = listening;
		this.overall = overall;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.deletedOn = deletedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.isDeleted = isDeleted;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public BigInteger getId() {
		return this.id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "country_id", nullable = false)
	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Column(name = "english_type", length = 250)
	public String getEnglishType() {
		return this.englishType;
	}

	public void setEnglishType(String englishType) {
		this.englishType = englishType;
	}

	@Column(name = "reading", precision = 10)
	public Double getReading() {
		return this.reading;
	}

	public void setReading(Double reading) {
		this.reading = reading;
	}

	@Column(name = "writing", precision = 10)
	public Double getWriting() {
		return this.writing;
	}

	public void setWriting(Double writing) {
		this.writing = writing;
	}

	@Column(name = "speaking", precision = 10)
	public Double getSpeaking() {
		return this.speaking;
	}

	public void setSpeaking(Double speaking) {
		this.speaking = speaking;
	}

	@Column(name = "listening", precision = 10)
	public Double getListening() {
		return this.listening;
	}

	public void setListening(Double listening) {
		this.listening = listening;
	}

	@Column(name = "overall", precision = 10)
	public Double getOverall() {
		return this.overall;
	}

	public void setOverall(Double overall) {
		this.overall = overall;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	public Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted_on", length = 19)
	public Date getDeletedOn() {
		return this.deletedOn;
	}

	public void setDeletedOn(Date deletedOn) {
		this.deletedOn = deletedOn;
	}

	@Column(name = "created_by", length = 50)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "updated_by", length = 50)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "is_deleted")
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
		result = prime * result + ((deletedOn == null) ? 0 : deletedOn.hashCode());
		result = prime * result + ((englishType == null) ? 0 : englishType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isActive == null) ? 0 : isActive.hashCode());
		result = prime * result + ((isDeleted == null) ? 0 : isDeleted.hashCode());
		result = prime * result + ((listening == null) ? 0 : listening.hashCode());
		result = prime * result + ((overall == null) ? 0 : overall.hashCode());
		result = prime * result + ((reading == null) ? 0 : reading.hashCode());
		result = prime * result + ((speaking == null) ? 0 : speaking.hashCode());
		result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result + ((updatedOn == null) ? 0 : updatedOn.hashCode());
		result = prime * result + ((writing == null) ? 0 : writing.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CountryEnglishEligibility other = (CountryEnglishEligibility) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdOn == null) {
			if (other.createdOn != null)
				return false;
		} else if (!createdOn.equals(other.createdOn))
			return false;
		if (deletedOn == null) {
			if (other.deletedOn != null)
				return false;
		} else if (!deletedOn.equals(other.deletedOn))
			return false;
		if (englishType == null) {
			if (other.englishType != null)
				return false;
		} else if (!englishType.equals(other.englishType))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isActive == null) {
			if (other.isActive != null)
				return false;
		} else if (!isActive.equals(other.isActive))
			return false;
		if (isDeleted == null) {
			if (other.isDeleted != null)
				return false;
		} else if (!isDeleted.equals(other.isDeleted))
			return false;
		if (listening == null) {
			if (other.listening != null)
				return false;
		} else if (!listening.equals(other.listening))
			return false;
		if (overall == null) {
			if (other.overall != null)
				return false;
		} else if (!overall.equals(other.overall))
			return false;
		if (reading == null) {
			if (other.reading != null)
				return false;
		} else if (!reading.equals(other.reading))
			return false;
		if (speaking == null) {
			if (other.speaking != null)
				return false;
		} else if (!speaking.equals(other.speaking))
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		if (updatedOn == null) {
			if (other.updatedOn != null)
				return false;
		} else if (!updatedOn.equals(other.updatedOn))
			return false;
		if (writing == null) {
			if (other.writing != null)
				return false;
		} else if (!writing.equals(other.writing))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CountryEnglishEligibility [id=").append(id).append(", country=").append(country)
				.append(", englishType=").append(englishType).append(", reading=").append(reading).append(", writing=")
				.append(writing).append(", speaking=").append(speaking).append(", listening=").append(listening)
				.append(", overall=").append(overall).append(", isActive=").append(isActive).append(", createdOn=")
				.append(createdOn).append(", updatedOn=").append(updatedOn).append(", deletedOn=").append(deletedOn)
				.append(", createdBy=").append(createdBy).append(", updatedBy=").append(updatedBy)
				.append(", isDeleted=").append(isDeleted).append("]");
		return builder.toString();
	}

}
