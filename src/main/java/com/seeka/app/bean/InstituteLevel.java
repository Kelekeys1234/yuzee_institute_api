package com.seeka.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="institute_level")
public class InstituteLevel extends RecordModifier implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
		
	@Column(name="institute_id")
	private Integer instituteId; // InstituteId
		
	@ManyToOne
	@JoinColumn(name="country_id")
	private Country countryId; // CountryId
	
	@Column(name="city_id")
	private Integer cityId; // CityId
	
	@ManyToOne
	@JoinColumn(name="level_id")
	private Level levelId; // LevelId
		
	@Column(name="is_active")
	private	Boolean isActive; // Is Active

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(Integer instituteId) {
		this.instituteId = instituteId;
	}

	public Country getCountryId() {
		return countryId;
	}

	public void setCountryId(Country countryId) {
		this.countryId = countryId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Level getLevelId() {
		return levelId;
	}

	public void setLevelId(Level levelId) {
		this.levelId = levelId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	   	
		
	
}
