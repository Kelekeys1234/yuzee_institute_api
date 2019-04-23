package com.seeka.app.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name="institute_level")
public class InstituteLevel extends RecordModifier implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
		
	@Column(name="institute_id")
	private UUID instituteId; // InstituteId
		
	@ManyToOne
	@JoinColumn(name="country_id")
	private Country countryObj; // CountryId
	
	@Column(name="city_id")
	private UUID cityId; // CityId
	
	@ManyToOne
	@JoinColumn(name="level_id")
	private Level levelObj; // LevelId
		
	@Column(name="is_active")
	private	Boolean isActive; // Is Active

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(UUID instituteId) {
		this.instituteId = instituteId;
	} 

	public Country getCountryObj() {
		return countryObj;
	}

	public void setCountryObj(Country countryObj) {
		this.countryObj = countryObj;
	}

	public UUID getCityId() {
		return cityId;
	}

	public void setCityId(UUID cityId) {
		this.cityId = cityId;
	} 

	public Level getLevelObj() {
		return levelObj;
	}

	public void setLevelObj(Level levelObj) {
		this.levelObj = levelObj;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	   	
		
	
}
