package com.seeka.app.dto;

import java.util.Date;
import java.util.List;

public class ArticlesDto {

	private String id;
	private String heading;
	private String content;
	private String imagepath;
	private String categoryId;
	private String subcategoryId;
	private String author;
	private Date postDate;
	private Date expireDate;
	private boolean enabled;
	private boolean published;
	private String websiteUrl;
	private String url;
	private String tags;
	private String countryName;
	private String cityId;
	private String facultyId;
	private String instituteId;
	private String courseId;

	private List<ArticleUserDemographicDto> userDemographic;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getSubcategoryId() {
		return subcategoryId;
	}

	public void setSubcategoryId(String subcategoryId) {
		this.subcategoryId = subcategoryId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String city) {
		this.cityId = city;
	}

	public String getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(String faculty) {
		this.facultyId = faculty;
	}

	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(String institute) {
		this.instituteId = institute;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courses) {
		this.courseId = courses;
	}

	public List<ArticleUserDemographicDto> getUserDemographic() {
		return userDemographic;
	}

	public void setUserDemographic(List<ArticleUserDemographicDto> userDemographic) {
		this.userDemographic = userDemographic;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}



	
	
}
