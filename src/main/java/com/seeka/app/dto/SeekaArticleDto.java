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
	private BigInteger category;
	private BigInteger subcategory;
	private String author;
	private Timestamp postDate;
	private Timestamp expireDate;
	private boolean enabled;
	private boolean published;
	private String websiteUrl;
	private String url;
	private String tags;
	private BigInteger country;
	private BigInteger city;
	private BigInteger faculty;
	private BigInteger institute;
	private BigInteger courses;

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

	public BigInteger getCategory() {
		return category;
	}

	public void setCategory(BigInteger category) {
		this.category = category;
	}

	public BigInteger getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(BigInteger subcategory) {
		this.subcategory = subcategory;
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

	public BigInteger getCountry() {
		return country;
	}

	public void setCountry(BigInteger country) {
		this.country = country;
	}

	public BigInteger getCity() {
		return city;
	}

	public void setCity(BigInteger city) {
		this.city = city;
	}

	public BigInteger getFaculty() {
		return faculty;
	}

	public void setFaculty(BigInteger faculty) {
		this.faculty = faculty;
	}

	public BigInteger getInstitute() {
		return institute;
	}

	public void setInstitute(BigInteger institute) {
		this.institute = institute;
	}

	public BigInteger getCourses() {
		return courses;
	}

	public void setCourses(BigInteger courses) {
		this.courses = courses;
	}

	public List<String> getUserDemographicString() {
		return userDemographicString;
	}

	public void setUserDemographicString(List<String> userDemographicString) {
		this.userDemographicString = userDemographicString;
	}

	
	
}
