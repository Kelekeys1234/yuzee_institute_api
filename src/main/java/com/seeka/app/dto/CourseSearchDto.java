package com.seeka.app.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class CourseSearchDto {
	
	private String searchKey;
	private String courseName;
	private Boolean isProfileSearch;
	private List<String> courseKeys;
	private List<Integer> levelIds;
	private List<Integer> facultyIds;
	private List<Integer> countryIds;
	private List<Integer> serviceIds;
	private Double minCost;
	private Double maxCost;
	private Integer minDuration;
	private Integer maxDuration;
	private CourseSearchFilterDto sortingObj;
	
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	 
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Boolean getIsProfileSearch() {
		return isProfileSearch;
	}
	public void setIsProfileSearch(Boolean isProfileSearch) {
		this.isProfileSearch = isProfileSearch;
	}
	public List<String> getCourseKeys() {
		return courseKeys;
	}
	public void setCourseKeys(List<String> courseKeys) {
		this.courseKeys = courseKeys;
	}
	public List<Integer> getLevelIds() {
		return levelIds;
	}
	public void setLevelIds(List<Integer> levelIds) {
		this.levelIds = levelIds;
	}
	public List<Integer> getFacultyIds() {
		return facultyIds;
	}
	public void setFacultyIds(List<Integer> facultyIds) {
		this.facultyIds = facultyIds;
	}
	public List<Integer> getCountryIds() {
		return countryIds;
	}
	public void setCountryIds(List<Integer> countryIds) {
		this.countryIds = countryIds;
	}
	public List<Integer> getServiceIds() {
		return serviceIds;
	}
	public void setServiceIds(List<Integer> serviceIds) {
		this.serviceIds = serviceIds;
	}
	public Double getMinCost() {
		return minCost;
	}
	public void setMinCost(Double minCost) {
		this.minCost = minCost;
	}
	public Double getMaxCost() {
		return maxCost;
	}
	public void setMaxCost(Double maxCost) {
		this.maxCost = maxCost;
	}
	public Integer getMinDuration() {
		return minDuration;
	}
	public void setMinDuration(Integer minDuration) {
		this.minDuration = minDuration;
	}
	public Integer getMaxDuration() {
		return maxDuration;
	}
	public void setMaxDuration(Integer maxDuration) {
		this.maxDuration = maxDuration;
	}

	public CourseSearchFilterDto getSortingObj() {
		return sortingObj;
	}
	public void setSortingObj(CourseSearchFilterDto sortingObj) {
		this.sortingObj = sortingObj;
	}
	public static void main(String[] args) {
		
		CourseSearchFilterDto filter = new CourseSearchFilterDto();
		filter.setDuration("ASC");
		filter.setLocation("ASC");
		filter.setPrice("ASC");
		filter.setRecognition("ASC");
		
		CourseSearchDto dto = new CourseSearchDto();
		
		List<Integer> ids = new ArrayList<>();
		ids.add(2001);
		ids.add(201);
		
		List<String> names = new ArrayList<>();
		names.add("3-D Dimentional");
		names.add("Graphic Design");
		
		dto.setCountryIds(ids);
		dto.setCourseKeys(names);
		dto.setCourseName("");
		dto.setFacultyIds(ids);
		dto.setSortingObj(filter);
		dto.setIsProfileSearch(false);
		dto.setLevelIds(ids);
		dto.setMaxCost(500000.00);
		dto.setMinCost(0.00);
		dto.setMinDuration(1);
		dto.setMaxDuration(10);
		dto.setSearchKey("");
		dto.setServiceIds(ids);
		
		Gson gson = new Gson();
		String request = gson.toJson(dto);
		System.out.println(request);
	}
}
