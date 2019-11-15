package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class ArticleElasticSearchDto implements Serializable{

	
    /**
	 * 
	 */
	private static final long serialVersionUID = -3270095176718095747L;
	private BigInteger id;
    private String heading;
    private String content;
    private String category;
    private String subCategory;
    private String author;
    private String tags;
    private String country;
    private String city;
    private String faculty;
    private String institute;
    private String course;

    private String gender;
    private String demographicCity;
    // private Country demographicCountry;

    /**
     * @return the id
     */
    public BigInteger getId() {
        return id;
    }

	/**
     * @param id
     *            the id to set
     */
    public void setId(BigInteger id) {
        this.id = id;
    }
    
    /**
     * 
     * @return
     */
    public String getDemographicCity() {
		return demographicCity;
	}

	public void setDemographicCity(String demographicCity) {
		this.demographicCity = demographicCity;
	}

//	public Country getDemographicCountry() {
//		return demographicCountry;
//	}
//
//	public void setDemographicCountry(Country demographicCountry) {
//		this.demographicCountry = demographicCountry;
//	}

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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String courses) {
		this.course = courses;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
}