package com.seeka.app.dto;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class SeekaArticleDto {

	private BigInteger id;
	private String heading;
	private String content;
	private String imagepath;
	private BigInteger categoryId;
	private BigInteger subcategoryId;
	private String author;
	private Timestamp postDate;
	private Timestamp expireDate;
	private boolean enabled;
	private boolean published;
	private String websiteUrl;
	private String url;
	private String tags;
	private BigInteger countryId;
	private BigInteger cityId;
	private BigInteger facultyId;
	private BigInteger instituteId;
	private BigInteger courseId;

	private List<String> userDemographicString;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
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

	public BigInteger getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(BigInteger categoryId) {
		this.categoryId = categoryId;
	}

	public BigInteger getSubcategoryId() {
		return subcategoryId;
	}

	public void setSubcategoryId(BigInteger subcategoryId) {
		this.subcategoryId = subcategoryId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Timestamp getPostDate() {
		return postDate;
	}

	public void setPostDate(Timestamp postDate) {
		this.postDate = postDate;
	}

	public Timestamp getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Timestamp expireDate) {
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

	public BigInteger getCountryId() {
		return countryId;
	}

	public void setCountryId(BigInteger country) {
		this.countryId = country;
	}

	public BigInteger getCityId() {
		return cityId;
	}

	public void setCityId(BigInteger city) {
		this.cityId = city;
	}

	public BigInteger getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(BigInteger faculty) {
		this.facultyId = faculty;
	}

	public BigInteger getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(BigInteger institute) {
		this.instituteId = institute;
	}

	public BigInteger getCourseId() {
		return courseId;
	}

	public void setCourseId(BigInteger courses) {
		this.courseId = courses;
	}

	public List<String> getUserDemographicString() {
		return userDemographicString;
	}

	public void setUserDemographicString(List<String> userDemographicString) {
		this.userDemographicString = userDemographicString;
	}

	
	
}
