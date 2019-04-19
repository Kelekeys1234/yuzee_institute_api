package com.seeka.app.dto;

import java.util.List;

import com.seeka.app.bean.City;
import com.seeka.app.bean.Level;

public class CountryDto {
	
	private Integer id;
	private String name;
	private String countryCode;
	private String imageUrl;
	private List<Level> levelList;
	private List<City> cityList;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
		imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR88bG0LmhslAUzzc2M3dZFBgd4Bb1vsymV7h2hKhvV4QRq0CgI";
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
