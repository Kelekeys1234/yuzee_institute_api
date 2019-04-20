package com.seeka.app.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="faculty")
public class Faculty extends RecordModifier implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id	
	@Column(name="id")
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name="level_id")
	private Level levelObj; // levelId
	
	@Column(name="name")
	private String name; //Faculty Name
	
	@Column(name="description")
	private String description; //Description
	
	@JsonIgnore
	@Column(name="is_active")
	private	Boolean isActive; // Is Active
	
	@Transient
	private UUID levelId;
	
	@Transient
	private UUID countryId;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	} 

	public Level getLevelObj() {
		return levelObj;
	}

	public void setLevelObj(Level levelObj) {
		this.levelObj = levelObj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public UUID getLevelId() {
		return levelId;
	}

	public void setLevelId(UUID levelId) {
		this.levelId = levelId;
	}

	public UUID getCountryId() {
		return countryId;
	}

	public void setCountryId(UUID countryId) {
		this.countryId = countryId;
	}
    
}
