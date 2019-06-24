package com.seeka.app.bean;import java.math.BigInteger;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public class RecordModifier implements Serializable {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@JsonIgnore
	@Column(name="created_on")
	private	Date createdOn; // Created On
	
	@JsonIgnore
	@Column(name="updated_on")
	private	Date updatedOn; // Updated On
	
	@JsonIgnore
	@Column(name="deleted_on")
	private	Date deletedOn; // Deleted On
	
	@JsonIgnore
	@Column(name="created_by")
	private String createdBy; // Created By
	
	@JsonIgnore
	@Column(name="updated_by")
	private String updatedBy; // Updated By
	
	@JsonIgnore
	@Column(name="is_deleted")
	private Boolean isDeleted; // Is Deleted

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Date getDeletedOn() {
		return deletedOn;
	}

	public void setDeletedOn(Date deletedOn) {
		this.deletedOn = deletedOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	 
	
	 
}
