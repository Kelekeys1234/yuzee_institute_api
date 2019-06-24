package com.seeka.app.bean;import java.math.BigInteger;


import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="seeka_coupons")
public class SeekaCoupons extends RecordModifier implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private BigInteger id;
		
	@Column(name = "name")
	private String name;
	
	@Column(name = "type")  
	private String type;
	
	@Column(name = "status")  
	private String status;
	
	@Column(name = "quantity")  
	private String quantity;
	
	@Column(name = "value")  
	private String value;
	
	@Column(name = "code")  
	private String code;
	
	@Column(name = "startdate")  
	private String startdate;
	
	@Column(name = "expirationdate")  
	private String expirationdate;
	
	@Column(name = "used")  
	private String used;
	
	@Column(name = "description")  
	private String description;
	
	@Column(name = "user_id")  
	private String userid;
	
	@Column(name = "createddate")  
	private String createddate;
	
	@Column(name = "active")  
	private String active;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getUsed() {
		return used;
	}

	public void setUsed(String used) {
		this.used = used;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getCreateddate() {
		return createddate;
	}

	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	
	
	
	

}
