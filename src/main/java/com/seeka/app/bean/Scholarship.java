package com.seeka.app.bean;

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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "scholarship")
public class Scholarship implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;
	@Column(name = "description")
	private String description;
	@Column(name = "scholarship_award")
	private String scholarshipAward;
	@Column(name = "country_name")
	private String countryName;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "level_id")
	private Level level;
	@Column(name = "number_of_avaliability")
	private Integer numberOfAvaliability;
	@Column(name = "currency")
	private String currency;
	@Column(name = "scholarship_amount")
	private Double scholarshipAmount;
	@Column(name = "validity")
	private String validity;
	@Column(name = "how_to_apply")
	private String howToApply;
	@Column(name = "gender")
	private String gender;
	@Column(name = "eligible_nationality")
	private String eligibleNationality;
	@Column(name = "headquaters")
	private String headquaters;
	@Column(name = "email")
	private String email;
	@Column(name = "address")
	private String address;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	private Date createdOn;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	private Date updatedOn;
	@Column(name = "created_by", length = 50)
	private String createdBy;
	@Column(name = "updated_by", length = 50)
	private String updatedBy;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted_on", length = 19)
	private Date deletedOn;
	@Column(name = "is_active")
	private Boolean isActive;

	/**
	 * extra fields for uploader
	 */
	@Column(name = "website")
	private String website;
	@Column(name = "institute_name")
	private String instituteName;
	@Column(name = "course_name")
	private String courseName;
	/**
	 * used as title
	 */
	@Column(name = "name")
	private String name;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "application_deadline", length = 19)
	private Date applicationDeadline;
	@Column(name = "content")
	private String content;
	@Column(name = "requirements")
	private String requirements;
	@Transient
	private String entityId;
	@Transient
	private String imageName;
	@Transient
	private String imageUrl;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
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

	public Level getLevel() {
		return level;
	}

	public void setLevel(final Level level) {
		this.level = level;
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

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Date getApplicationDeadline() {
		return applicationDeadline;
	}

	public void setApplicationDeadline(final Date applicationDeadline) {
		this.applicationDeadline = applicationDeadline;
	}

	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}
	
	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(final String entityId) {
		this.entityId = entityId;
	}
	
	public String getImageName() {
		return entityId;
	}
	
	public void setImageName(final String imageName) {
		this.imageName = imageName;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((address == null) ? 0 : address.hashCode());
		result = (prime * result) + ((applicationDeadline == null) ? 0 : applicationDeadline.hashCode());
		result = (prime * result) + ((content == null) ? 0 : content.hashCode());
		result = (prime * result) + ((getCountryName() == null) ? 0 : getCountryName().hashCode());
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
		result = (prime * result) + ((isActive == null) ? 0 : isActive.hashCode());
		result = (prime * result) + ((level == null) ? 0 : level.hashCode());
		result = (prime * result) + ((name == null) ? 0 : name.hashCode());
		result = (prime * result) + ((numberOfAvaliability == null) ? 0 : numberOfAvaliability.hashCode());
		result = (prime * result) + ((courseName == null) ? 0 : courseName.hashCode());
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
		Scholarship other = (Scholarship) obj;
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
		if (content == null) {
			if (other.content != null) {
				return false;
			}
		} else if (!content.equals(other.content)) {
			return false;
		}
		if (getCountryName() == null) {
			if (other.getCountryName() != null) {
				return false;
			}
		} else if (!getCountryName().equals(other.getCountryName())) {
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
		if (isActive == null) {
			if (other.isActive != null) {
				return false;
			}
		} else if (!isActive.equals(other.isActive)) {
			return false;
		}
		if (level == null) {
			if (other.level != null) {
				return false;
			}
		} else if (!level.equals(other.level)) {
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
		if (courseName == null) {
			if (other.courseName != null) {
				return false;
			}
		} else if (!courseName.equals(other.courseName)) {
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
		builder.append("Scholarship [id=").append(id).append(", courseName=").append(courseName).append(", description=").append(description)
				.append(", scholarshipAward=").append(scholarshipAward).append(", country=").append(getCountryName()).append(", level=").append(level)
				.append(", numberOfAvaliability=").append(numberOfAvaliability).append(", currency=").append(currency).append(", scholarshipAmount=")
				.append(scholarshipAmount).append(", validity=").append(validity).append(", howToApply=").append(howToApply).append(", gender=").append(gender)
				.append(", eligibleNationality=").append(eligibleNationality).append(", headquaters=").append(headquaters).append(", email=").append(email)
				.append(", address=").append(address).append(", createdOn=").append(createdOn).append(", updatedOn=").append(updatedOn).append(", createdBy=")
				.append(createdBy).append(", updatedBy=").append(updatedBy).append(", deletedOn=").append(deletedOn).append(", isActive=").append(isActive)
				.append(", website=").append(website).append(", instituteName=").append(instituteName).append(", name=").append(name).append(", applicationDeadline=")
				.append(applicationDeadline).append(", content=").append(content).append(", requirements=").append(requirements).append("]");
		return builder.toString();
	}

	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(final String requirements) {
		this.requirements = requirements;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

}
