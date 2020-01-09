package com.seeka.app.bean;

import java.math.BigInteger;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "seeka_popups")
public class SeekaPopup extends RecordModifier implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private BigInteger id;

	@Column(name = "title")
	private String title;

	@Column(name = "details")
	private String details;

	@Column(name = "image")
	private String image;

	@Column(name = "countries")
	private String countries;

	@Column(name = "citizenship")
	private String citizenship;

	@Column(name = "type")
	private String type;

	@Column(name = "startdate")
	private String startdate;

	@Column(name = "expirationdate")
	private String expirationdate;

	@Column(name = "createddate")
	private String createddate;

	@Column(name = "status")
	private String status;

	@Column(name = "user_id")
	private String userid;

	@Column(name = "active")
	private String active;

	@Column(name = "deleted")
	private String deleted;

	@Column(name = "buttontext")
	private String buttontext;

	@Column(name = "url")
	private String url;

	@Column(name = "amount")
	private String amount;

	@Column(name = "currency_type")
	private String currencyType;

	@Column(name = "icon")
	private String icon;

	@Column(name = "currency_code")
	private String currencyCode;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCountries() {
		return countries;
	}

	public void setCountries(String countries) {
		this.countries = countries;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getExpirationdate() {
		return expirationdate;
	}

	public void setExpirationdate(String expirationdate) {
		this.expirationdate = expirationdate;
	}

	public String getCreateddate() {
		return createddate;
	}

	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return userid;
	}

	public void setUser_id(String userid) {
		this.userid = userid;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getButtontext() {
		return buttontext;
	}

	public void setButtontext(String buttontext) {
		this.buttontext = buttontext;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((buttontext == null) ? 0 : buttontext.hashCode());
		result = prime * result + ((citizenship == null) ? 0 : citizenship.hashCode());
		result = prime * result + ((countries == null) ? 0 : countries.hashCode());
		result = prime * result + ((createddate == null) ? 0 : createddate.hashCode());
		result = prime * result + ((currencyCode == null) ? 0 : currencyCode.hashCode());
		result = prime * result + ((currencyType == null) ? 0 : currencyType.hashCode());
		result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + ((expirationdate == null) ? 0 : expirationdate.hashCode());
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((startdate == null) ? 0 : startdate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SeekaPopup other = (SeekaPopup) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (buttontext == null) {
			if (other.buttontext != null)
				return false;
		} else if (!buttontext.equals(other.buttontext))
			return false;
		if (citizenship == null) {
			if (other.citizenship != null)
				return false;
		} else if (!citizenship.equals(other.citizenship))
			return false;
		if (countries == null) {
			if (other.countries != null)
				return false;
		} else if (!countries.equals(other.countries))
			return false;
		if (createddate == null) {
			if (other.createddate != null)
				return false;
		} else if (!createddate.equals(other.createddate))
			return false;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
			return false;
		if (currencyType == null) {
			if (other.currencyType != null)
				return false;
		} else if (!currencyType.equals(other.currencyType))
			return false;
		if (deleted == null) {
			if (other.deleted != null)
				return false;
		} else if (!deleted.equals(other.deleted))
			return false;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (expirationdate == null) {
			if (other.expirationdate != null)
				return false;
		} else if (!expirationdate.equals(other.expirationdate))
			return false;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (startdate == null) {
			if (other.startdate != null)
				return false;
		} else if (!startdate.equals(other.startdate))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SeekaPopup [id=").append(id).append(", title=").append(title).append(", details=")
				.append(details).append(", image=").append(image).append(", countries=").append(countries)
				.append(", citizenship=").append(citizenship).append(", type=").append(type).append(", startdate=")
				.append(startdate).append(", expirationdate=").append(expirationdate).append(", createddate=")
				.append(createddate).append(", status=").append(status).append(", userid=").append(userid)
				.append(", active=").append(active).append(", deleted=").append(deleted).append(", buttontext=")
				.append(buttontext).append(", url=").append(url).append(", amount=").append(amount)
				.append(", currencyType=").append(currencyType).append(", icon=").append(icon).append(", currencyCode=")
				.append(currencyCode).append("]");
		return builder.toString();
	}

}
