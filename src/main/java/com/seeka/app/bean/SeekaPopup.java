package com.seeka.app.bean;import java.math.BigInteger;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="seeka_popups")
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

	
	
}
