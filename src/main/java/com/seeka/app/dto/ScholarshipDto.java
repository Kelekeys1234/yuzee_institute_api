package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class ScholarshipDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2633639341414502096L;
	private BigInteger id;
	private String name;
	private String offeredBy;
	private String description;
	private String scholarshipAward;
	private BigInteger countryId;
	private BigInteger levelId;
	private Integer numberOfAvaliability;
	private String currency;
	private Double scholarshipAmount;
	private String validity;
	private String howToApply;
	private String gender;
	private String eligibleNationality;
	private String headquaters;
	private String email;
	private String address;
	private String website;
	private BigInteger instituteId;
	private Date applicationDeadline;
	private List<String> intakes;
	private List<String> languages;
	private String requirements;

	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
		this.id = id;
	}

	public String getOfferedBy() {
		return offeredBy;
	}

	public void setOfferedBy(final String offeredBy) {
		this.offeredBy = offeredBy;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getScholarshipAward() {
		return scholarshipAward;
	}

	public void setScholarshipAward(final String scholarshipAward) {
		this.scholarshipAward = scholarshipAward;
	}

	public BigInteger getCountryId() {
		return countryId;
	}

	public void setCountryId(final BigInteger countryId) {
		this.countryId = countryId;
	}

	public BigInteger getLevelId() {
		return levelId;
	}

	public void setLevelId(final BigInteger levelId) {
		this.levelId = levelId;
	}

	public Integer getNumberOfAvaliability() {
		return numberOfAvaliability;
	}

	public void setNumberOfAvaliability(final Integer numberOfAvaliability) {
		this.numberOfAvaliability = numberOfAvaliability;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	public Double getScholarshipAmount() {
		return scholarshipAmount;
	}

	public void setScholarshipAmount(final Double scholarshipAmount) {
		this.scholarshipAmount = scholarshipAmount;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(final String validity) {
		this.validity = validity;
	}

	public String getHowToApply() {
		return howToApply;
	}

	public void setHowToApply(final String howToApply) {
		this.howToApply = howToApply;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(final String gender) {
		this.gender = gender;
	}

	public String getEligibleNationality() {
		return eligibleNationality;
	}

	public void setEligibleNationality(final String eligibleNationality) {
		this.eligibleNationality = eligibleNationality;
	}

	public String getHeadquaters() {
		return headquaters;
	}

	public void setHeadquaters(final String headquaters) {
		this.headquaters = headquaters;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(final String website) {
		this.website = website;
	}

	public BigInteger getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(final BigInteger instituteId) {
		this.instituteId = instituteId;
	}

	public Date getApplicationDeadline() {
		return applicationDeadline;
	}

	public void setApplicationDeadline(final Date applicationDeadline) {
		this.applicationDeadline = applicationDeadline;
	}

	public List<String> getIntakes() {
		return intakes;
	}

	public void setIntakes(final List<String> intakes) {
		this.intakes = intakes;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(final List<String> languages) {
		this.languages = languages;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((address == null) ? 0 : address.hashCode());
		result = (prime * result) + ((applicationDeadline == null) ? 0 : applicationDeadline.hashCode());
		result = (prime * result) + ((countryId == null) ? 0 : countryId.hashCode());
		result = (prime * result) + ((currency == null) ? 0 : currency.hashCode());
		result = (prime * result) + ((description == null) ? 0 : description.hashCode());
		result = (prime * result) + ((eligibleNationality == null) ? 0 : eligibleNationality.hashCode());
		result = (prime * result) + ((email == null) ? 0 : email.hashCode());
		result = (prime * result) + ((gender == null) ? 0 : gender.hashCode());
		result = (prime * result) + ((headquaters == null) ? 0 : headquaters.hashCode());
		result = (prime * result) + ((howToApply == null) ? 0 : howToApply.hashCode());
		result = (prime * result) + ((instituteId == null) ? 0 : instituteId.hashCode());
		result = (prime * result) + ((intakes == null) ? 0 : intakes.hashCode());
		result = (prime * result) + ((languages == null) ? 0 : languages.hashCode());
		result = (prime * result) + ((levelId == null) ? 0 : levelId.hashCode());
		result = (prime * result) + ((name == null) ? 0 : name.hashCode());
		result = (prime * result) + ((numberOfAvaliability == null) ? 0 : numberOfAvaliability.hashCode());
		result = (prime * result) + ((offeredBy == null) ? 0 : offeredBy.hashCode());
		result = (prime * result) + ((requirements == null) ? 0 : requirements.hashCode());
		result = (prime * result) + ((scholarshipAmount == null) ? 0 : scholarshipAmount.hashCode());
		result = (prime * result) + ((scholarshipAward == null) ? 0 : scholarshipAward.hashCode());
		result = (prime * result) + ((validity == null) ? 0 : validity.hashCode());
		result = (prime * result) + ((website == null) ? 0 : website.hashCode());
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
		ScholarshipDto other = (ScholarshipDto) obj;
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (applicationDeadline == null) {
			if (other.applicationDeadline != null) {
				return false;
			}
		} else if (!applicationDeadline.equals(other.applicationDeadline)) {
			return false;
		}
		if (countryId == null) {
			if (other.countryId != null) {
				return false;
			}
		} else if (!countryId.equals(other.countryId)) {
			return false;
		}
		if (currency == null) {
			if (other.currency != null) {
				return false;
			}
		} else if (!currency.equals(other.currency)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (eligibleNationality == null) {
			if (other.eligibleNationality != null) {
				return false;
			}
		} else if (!eligibleNationality.equals(other.eligibleNationality)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (gender == null) {
			if (other.gender != null) {
				return false;
			}
		} else if (!gender.equals(other.gender)) {
			return false;
		}
		if (headquaters == null) {
			if (other.headquaters != null) {
				return false;
			}
		} else if (!headquaters.equals(other.headquaters)) {
			return false;
		}
		if (howToApply == null) {
			if (other.howToApply != null) {
				return false;
			}
		} else if (!howToApply.equals(other.howToApply)) {
			return false;
		}
		if (instituteId == null) {
			if (other.instituteId != null) {
				return false;
			}
		} else if (!instituteId.equals(other.instituteId)) {
			return false;
		}
		if (intakes == null) {
			if (other.intakes != null) {
				return false;
			}
		} else if (!intakes.equals(other.intakes)) {
			return false;
		}
		if (languages == null) {
			if (other.languages != null) {
				return false;
			}
		} else if (!languages.equals(other.languages)) {
			return false;
		}
		if (levelId == null) {
			if (other.levelId != null) {
				return false;
			}
		} else if (!levelId.equals(other.levelId)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (numberOfAvaliability == null) {
			if (other.numberOfAvaliability != null) {
				return false;
			}
		} else if (!numberOfAvaliability.equals(other.numberOfAvaliability)) {
			return false;
		}
		if (offeredBy == null) {
			if (other.offeredBy != null) {
				return false;
			}
		} else if (!offeredBy.equals(other.offeredBy)) {
			return false;
		}
		if (requirements == null) {
			if (other.requirements != null) {
				return false;
			}
		} else if (!requirements.equals(other.requirements)) {
			return false;
		}
		if (scholarshipAmount == null) {
			if (other.scholarshipAmount != null) {
				return false;
			}
		} else if (!scholarshipAmount.equals(other.scholarshipAmount)) {
			return false;
		}
		if (scholarshipAward == null) {
			if (other.scholarshipAward != null) {
				return false;
			}
		} else if (!scholarshipAward.equals(other.scholarshipAward)) {
			return false;
		}
		if (validity == null) {
			if (other.validity != null) {
				return false;
			}
		} else if (!validity.equals(other.validity)) {
			return false;
		}
		if (website == null) {
			if (other.website != null) {
				return false;
			}
		} else if (!website.equals(other.website)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ScholarshipDto [name=").append(name).append(", offeredBy=").append(offeredBy).append(", description=").append(description)
				.append(", scholarshipAward=").append(scholarshipAward).append(", countryId=").append(countryId).append(", levelId=").append(levelId)
				.append(", numberOfAvaliability=").append(numberOfAvaliability).append(", currency=").append(currency).append(", scholarshipAmount=")
				.append(scholarshipAmount).append(", validity=").append(validity).append(", howToApply=").append(howToApply).append(", gender=").append(gender)
				.append(", eligibleNationality=").append(eligibleNationality).append(", headquaters=").append(headquaters).append(", email=").append(email)
				.append(", address=").append(address).append(", website=").append(website).append(", instituteId=").append(instituteId)
				.append(", applicationDeadline=").append(applicationDeadline).append(", intakes=").append(intakes).append(", languages=").append(languages)
				.append(", requirements=").append(requirements).append("]");
		return builder.toString();
	}

	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(final String requirements) {
		this.requirements = requirements;
	}

}
