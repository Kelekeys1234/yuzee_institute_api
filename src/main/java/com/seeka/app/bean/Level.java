package com.seeka.app.bean;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="level")
public class Level extends RecordModifier implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@Column(name="name")
	private String name; //type text
	
	@Column(name="level_key")
	private String levelKey; //type description
		
	@Column(name="description")
	private String description; // Description
	 
	@JsonIgnore
	@Column(name="is_active")
	private	Boolean isActive; // Is Active
	
	@Transient
	private UUID countryId;
	
	@Transient
	private List<Faculty> facultyList;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevelKey() {
		return levelKey;
	}

	public void setLevelKey(String levelKey) {
		this.levelKey = levelKey;
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

	public List<Faculty> getFacultyList() {
		return facultyList;
	}

	public void setFacultyList(List<Faculty> facultyList) {
		this.facultyList = facultyList;
	}

	public UUID getCountryId() {
		return countryId;
	}

	public void setCountryId(UUID countryId) {
		this.countryId = countryId;
	} 
	
}
