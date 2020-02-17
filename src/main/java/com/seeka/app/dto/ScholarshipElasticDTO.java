package com.seeka.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ScholarshipElasticDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3487477078664375741L;
	private String id;
	private String name;
	private String countryName;
	private String instituteName;
	private String offeredBy;
	private String levelName;
	private Double amount;
	private String description;
	private String website;
	private String content;
	private Date applicationDeadline;
	private String levelCode;

	private String currency;
	private String eligibleNationality;
	private List<String> intake;
	private List<String> languages;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	public String getEligibleNationality() {
		return eligibleNationality;
	}

	public void setEligibleNationality(final String eligibleNationality) {
		this.eligibleNationality = eligibleNationality;
	}

	public List<String> getIntake() {
		return intake;
	}

	public void setIntake(final List<String> intake) {
		this.intake = intake;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(final List<String> languages) {
		this.languages = languages;
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

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(final String countryName) {
		this.countryName = countryName;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(final String instituteName) {
		this.instituteName = instituteName;
	}

	public String getOfferedBy() {
		return offeredBy;
	}

	public void setOfferedBy(final String offeredBy) {
		this.offeredBy = offeredBy;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(final String levelName) {
		this.levelName = levelName;
	}

	public Date getApplicationDeadline() {
		return applicationDeadline;
	}

	public void setApplicationDeadline(final Date applicationDeadline) {
		this.applicationDeadline = applicationDeadline;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(final String website) {
		this.website = website;
	}

	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ScholarshipElasticDTO [id=").append(id).append(", name=").append(name).append(", countryName=").append(countryName)
				.append(", instituteName=").append(instituteName).append(", offeredBy=").append(offeredBy).append(", levelName=").append(levelName)
				.append(", amount=").append(amount).append(", description=").append(description).append(", website=").append(website).append(", content=")
				.append(content).append(", applicationDeadline=").append(applicationDeadline).append(", levelCode=").append(levelCode).append(", currency=")
				.append(currency).append(", eligibleNationality=").append(eligibleNationality).append(", intake=").append(intake).append(", languages=")
				.append(languages).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((amount == null) ? 0 : amount.hashCode());
		result = (prime * result) + ((applicationDeadline == null) ? 0 : applicationDeadline.hashCode());
		result = (prime * result) + ((content == null) ? 0 : content.hashCode());
		result = (prime * result) + ((countryName == null) ? 0 : countryName.hashCode());
		result = (prime * result) + ((currency == null) ? 0 : currency.hashCode());
		result = (prime * result) + ((description == null) ? 0 : description.hashCode());
		result = (prime * result) + ((eligibleNationality == null) ? 0 : eligibleNationality.hashCode());
		result = (prime * result) + ((id == null) ? 0 : id.hashCode());
		result = (prime * result) + ((instituteName == null) ? 0 : instituteName.hashCode());
		result = (prime * result) + ((intake == null) ? 0 : intake.hashCode());
		result = (prime * result) + ((languages == null) ? 0 : languages.hashCode());
		result = (prime * result) + ((levelCode == null) ? 0 : levelCode.hashCode());
		result = (prime * result) + ((levelName == null) ? 0 : levelName.hashCode());
		result = (prime * result) + ((name == null) ? 0 : name.hashCode());
		result = (prime * result) + ((offeredBy == null) ? 0 : offeredBy.hashCode());
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
		ScholarshipElasticDTO other = (ScholarshipElasticDTO) obj;
		if (amount == null) {
			if (other.amount != null) {
				return false;
			}
		} else if (!amount.equals(other.amount)) {
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
		if (countryName == null) {
			if (other.countryName != null) {
				return false;
			}
		} else if (!countryName.equals(other.countryName)) {
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
		if (intake == null) {
			if (other.intake != null) {
				return false;
			}
		} else if (!intake.equals(other.intake)) {
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
		if (offeredBy == null) {
			if (other.offeredBy != null) {
				return false;
			}
		} else if (!offeredBy.equals(other.offeredBy)) {
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

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(final String levelCode) {
		this.levelCode = levelCode;
	}

}
