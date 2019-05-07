package com.seeka.app.dto;

import java.util.List;
import java.util.UUID;

import com.seeka.app.bean.City;
import com.seeka.app.bean.Level;
import com.seeka.app.util.CDNServerUtil;

public class CountryDto {
	
	private UUID id;
	private String name;
	private String countryCode;
	private String imageUrl;
	private List<Level> levelList;
	private List<City> cityList;
	
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
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getImageUrl() {
		if(name != null && !name.isEmpty()) {
			imageUrl = CDNServerUtil.getCountryImageUrl(name.trim());
		}
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public List<Level> getLevelList() {
		return levelList;
	}
	public void setLevelList(List<Level> levelList) {
		this.levelList = levelList;
	}
	public List<City> getCityList() {
		return cityList;
	}
	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	} 
}
