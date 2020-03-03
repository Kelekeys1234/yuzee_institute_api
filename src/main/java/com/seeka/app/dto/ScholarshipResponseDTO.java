package com.seeka.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ScholarshipResponseDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6660498982386504039L;
	private String id;
	private String name;
	private String description;
	private String scholarshipAward;
	private String countryId;
	private String levelId;
	private Integer numberOfAvaliability;
	private Double scholarshipAmount;
	private String validity;
	private String howToApply;
	private String gender;
	private String eligibleNationality;
	private String headquaters;
	private String email;
	private String address;
	private Date createdOn;
	private Date updatedOn;
	private String createdBy;
	private String updatedBy;
	private Date deletedOn;
	private Boolean isActive;
	private String website;
	private Date applicationDeadline;
	private List<String> intakes;
	private List<String> languages;
	private String countryName;
	private String levelName;
	private String currency;
	private String instituteName;
	private String courseName;
	private String requirements;
	private String levelCode;
	/**
	 * This field is added just to keep the response from elastic search and normal
	 * search same.
	 */
	private String intake;

	public String getIntake() {
		return intake;
	}

	public void setIntake(final String intake) {
		this.intake = intake;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(final String levelCode) {
		this.levelCode = levelCode;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
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

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(final String countryId) {
		this.countryId = countryId;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(final String levelId) {
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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(final Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(final Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(final String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getDeletedOn() {
		return deletedOn;
	}

	public void setDeletedOn(final Date deletedOn) {
		this.deletedOn = deletedOn;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(final Boolean isActive) {
		this.isActive = isActive;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(final String website) {
		this.website = website;
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

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(final String countryName) {
		this.countryName = countryName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(final String levelName) {
		this.levelName = levelName;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(final String instituteName) {
		this.instituteName = instituteName;
	}
	
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(final String courseName) {
		this.courseName = courseName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((address == null) ? 0 : address.hashCode());
		result = (prime * result) + ((applicationDeadline == null) ? 0 : applicationDeadline.hashCode());
		result = (prime * result) + ((countryId == null) ? 0 : countryId.hashCode());
		result = (prime * result) + ((countryName == null) ? 0 : countryName.hashCode());
		result = (prime * result) + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = (prime * result) + ((createdOn == null) ? 0 : createdOn.hashCode());
		result = (prime * result) + ((currency == null) ? 0 : currency.hashCode());
		result = (prime * result) + ((deletedOn == null) ? 0 : deletedOn.hashCode());
		result = (prime * result) + ((description == null) ? 0 : description.hashCode());
		result = (prime * result) + ((eligibleNationality == null) ? 0 : eligibleNationality.hashCode());
		result = (prime * result) + ((email == null) ? 0 : email.hashCode());
		result = (prime * result) + ((gender == null) ? 0 : gender.hashCode());
		result = (prime * result) + ((headquaters == null) ? 0 : headquaters.hashCode());
		result = (prime * result) + ((howToApply == null) ? 0 : howToApply.hashCode());
		result = (prime * result) + ((id == null) ? 0 : id.hashCode());
		result = (prime * result) + ((instituteName == null) ? 0 : instituteName.hashCode());
		result = (prime * result) + ((courseName == null) ? 0 : courseName.hashCode());
		result = (prime * result) + ((intake == null) ? 0 : intake.hashCode());
		result = (prime * result) + ((intakes == null) ? 0 : intakes.hashCode());
		result = (prime * result) + ((isActive == null) ? 0 : isActive.hashCode());
		result = (prime * result) + ((languages == null) ? 0 : languages.hashCode());
		result = (prime * result) + ((levelCode == null) ? 0 : levelCode.hashCode());
		result = (prime * result) + ((levelId == null) ? 0 : levelId.hashCode());
		result = (prime * result) + ((levelName == null) ? 0 : levelName.hashCode());
		result = (prime * result) + ((name == null) ? 0 : name.hashCode());
		result = (prime * result) + ((numberOfAvaliability == null) ? 0 : numberOfAvaliability.hashCode());
		result = (prime * result) + ((requirements == null) ? 0 : requirements.hashCode());
		result = (prime * result) + ((scholarshipAmount == null) ? 0 : scholarshipAmount.hashCode());
		result = (prime * result) + ((scholarshipAward == null) ? 0 : scholarshipAward.hashCode());
		result = (prime * result) + ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = (prime * result) + ((updatedOn == null) ? 0 : updatedOn.hashCode());
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
		ScholarshipResponseDTO other = (ScholarshipResponseDTO) obj;
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
		if (countryName == null) {
			if (other.countryName != null) {
				return false;
			}
		} else if (!countryName.equals(other.countryName)) {
			return false;
		}
		if (createdBy == null) {
			if (other.createdBy != null) {
				return false;
			}
		} else if (!createdBy.equals(other.createdBy)) {
			return false;
		}
		if (createdOn == null) {
			if (other.createdOn != null) {
				return false;
			}
		} else if (!createdOn.equals(other.createdOn)) {
			return false;
		}
		if (currency == null) {
			if (other.currency != null) {
				return false;
			}
		} else if (!currency.equals(other.currency)) {
			return false;
		}
		if (deletedOn == null) {
			if (other.deletedOn != null) {
				return false;
			}
		} else if (!deletedOn.equals(other.deletedOn)) {
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
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (instituteName == null) {
			if (other.instituteName != null) {
				return false;
			}
		} else if (!instituteName.equals(other.instituteName)) {
			return false;
		}
		if (courseName == null) {
			if (other.courseName != null) {
				return false;
			}
		} else if (!courseName.equals(other.courseName)) {
			return false;
		}
		if (intake == null) {
			if (other.intake != null) {
				return false;
			}
		} else if (!intake.equals(other.intake)) {
			return false;
		}
		if (intakes == null) {
			if (other.intakes != null) {
				return false;
			}
		} else if (!intakes.equals(other.intakes)) {
			return false;
		}
		if (isActive == null) {
			if (other.isActive != null) {
				return false;
			}
		} else if (!isActive.equals(other.isActive)) {
			return false;
		}
		if (languages == null) {
			if (other.languages != null) {
				return false;
			}
		} else if (!languages.equals(other.languages)) {
			return false;
		}
		if (levelCode == null) {
			if (other.levelCode != null) {
				return false;
			}
		} else if (!levelCode.equals(other.levelCode)) {
			return false;
		}
		if (levelId == null) {
			if (other.levelId != null) {
				return false;
			}
		} else if (!levelId.equals(other.levelId)) {
			return false;
		}
		if (levelName == null) {
			if (other.levelName != null) {
				return false;
			}
		} else if (!levelName.equals(other.levelName)) {
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
		if (updatedBy == null) {
			if (other.updatedBy != null) {
				return false;
			}
		} else if (!updatedBy.equals(other.updatedBy)) {
			return false;
		}
		if (updatedOn == null) {
			if (other.updatedOn != null) {
				return false;
			}
		} else if (!updatedOn.equals(other.updatedOn)) {
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
		builder.append("ScholarshipResponseDTO [id=").append(id).append(", name=").append(name)
				.append(", description=").append(description).append(", scholarshipAward=").append(scholarshipAward).append(", countryId=").append(countryId)
				.append(", levelId=").append(levelId).append(", numberOfAvaliability=").append(numberOfAvaliability).append(", scholarshipAmount=")
				.append(scholarshipAmount).append(", validity=").append(validity).append(", howToApply=").append(howToApply).append(", gender=").append(gender)
				.append(", eligibleNationality=").append(eligibleNationality).append(", headquaters=").append(headquaters).append(", email=").append(email)
				.append(", address=").append(address).append(", createdOn=").append(createdOn).append(", updatedOn=").append(updatedOn).append(", createdBy=")
				.append(createdBy).append(", updatedBy=").append(updatedBy).append(", deletedOn=").append(deletedOn).append(", isActive=").append(isActive)
				.append(", website=").append(website).append(", courseName=").append(courseName).append(", applicationDeadline=").append(applicationDeadline)
				.append(", intakes=").append(intakes).append(", languages=").append(languages).append(", countryName=").append(countryName)
				.append(", levelName=").append(levelName).append(", currency=").append(currency).append(", instituteName=").append(instituteName)
				.append(", requirements=").append(requirements).append(", levelCode=").append(levelCode).append(", intake=").append(intake).append("]");
		return builder.toString();
	}

	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(final String requirements) {
		this.requirements = requirements;
	}

}
