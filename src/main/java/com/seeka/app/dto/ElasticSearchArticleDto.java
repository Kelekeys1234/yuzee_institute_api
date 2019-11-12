package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.seeka.app.bean.Category;
import com.seeka.app.bean.City;
import com.seeka.app.bean.Country;
import com.seeka.app.bean.Course;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.SubCategory;

public class ElasticSearchArticleDto {

    private BigInteger id;
    private String addType;
    private String heading;
    private String content;
    private String url;
    private String imagepath;
    private BigInteger type;
    private Boolean active;
    private Date deletedOn;
    private Date createdAt;
    private BigInteger shared;
    private BigInteger reviewed;
    private BigInteger likes;
    private String link;
    private Date updatedAt;
    private String articleType;
    private String companyName;
    private String companyWebsite;
    private Integer totalCount;
    
    private String author;
    private Date postDate;
    private Date expireyDate;
    private boolean enabled;
    private boolean featured;
    private String notes;

    private String websiteUrl;
    private boolean seekaRecommended;
    private String tags;
    private String status;

    private String category;
    private String subCategory;
    private String country;
    private String city;
    private String faculty;
    private String institute;
    private String courses;
    private String gender;
    private City userCity;
    private Country userCountry;

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
     * @return the addType
     */
    public String getAddType() {
        return addType;
    }

    /**
     * @param addType
     *            the addType to set
     */
    public void setAddType(String addType) {
        this.addType = addType;
    }

    /**
     * @return the heading
     */
    public String getHeading() {
        return heading;
    }

    /**
     * @param heading
     *            the heading to set
     */
    public void setHeading(String heading) {
        this.heading = heading;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the imagepath
     */
    public String getImagepath() {
        return imagepath;
    }

    /**
     * @param imagepath
     *            the imagepath to set
     */
    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    /**
     * @return the type
     */
    public BigInteger getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(BigInteger type) {
        this.type = type;
    }

    /**
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active
     *            the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * @return the deletedOn
     */
    public Date getDeletedOn() {
        return deletedOn;
    }

    /**
     * @param deletedOn
     *            the deletedOn to set
     */
    public void setDeletedOn(Date deletedOn) {
        this.deletedOn = deletedOn;
    }

    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     *            the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the shared
     */
    public BigInteger getShared() {
        return shared;
    }

    /**
     * @param shared
     *            the shared to set
     */
    public void setShared(BigInteger shared) {
        this.shared = shared;
    }

    /**
     * @return the reviewed
     */
    public BigInteger getReviewed() {
        return reviewed;
    }

    /**
     * @param reviewed
     *            the reviewed to set
     */
    public void setReviewed(BigInteger reviewed) {
        this.reviewed = reviewed;
    }

    /**
     * @return the likes
     */
    public BigInteger getLikes() {
        return likes;
    }

    /**
     * @param likes
     *            the likes to set
     */
    public void setLikes(BigInteger likes) {
        this.likes = likes;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link
     *            the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return the updatedAt
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt
     *            the updatedAt to set
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return the articleType
     */
    public String getArticleType() {
        return articleType;
    }

    /**
     * @param articleType
     *            the articleType to set
     */
    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName
     *            the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the companyWebsite
     */
    public String getCompanyWebsite() {
        return companyWebsite;
    }

    /**
     * @param companyWebsite
     *            the companyWebsite to set
     */
    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    /**
     * @return the totalCount
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount
     *            the totalCount to set
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author
     *            the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the postDate
     */
    public Date getPostDate() {
        return postDate;
    }

    /**
     * @param postDate
     *            the postDate to set
     */
    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    /**
     * @return the expireyDate
     */
    public Date getExpireyDate() {
        return expireyDate;
    }

    /**
     * @param expireyDate
     *            the expireyDate to set
     */
    public void setExpireyDate(Date expireyDate) {
        this.expireyDate = expireyDate;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     *            the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the featured
     */
    public boolean isFeatured() {
        return featured;
    }

    /**
     * @param featured
     *            the featured to set
     */
    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes
     *            the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return the websiteUrl
     */
    public String getWebsiteUrl() {
        return websiteUrl;
    }

    /**
     * @param websiteUrl
     *            the websiteUrl to set
     */
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    /**
     * @return the seekaRecommended
     */
    public boolean isSeekaRecommended() {
        return seekaRecommended;
    }

    /**
     * @param seekaRecommended
     *            the seekaRecommended to set
     */
    public void setSeekaRecommended(boolean seekaRecommended) {
        this.seekaRecommended = seekaRecommended;
    }

    /**
     * @return the tags
     */
    public String getTags() {
        return tags;
    }

    /**
     * @param tags
     *            the tags to set
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
        this.status = status;
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

	public String getCourses() {
		return courses;
	}

	public void setCourses(String courses) {
		this.courses = courses;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
     * @return the userCity
     */
    public City getUserCity() {
        return userCity;
    }

    /**
     * @param userCity
     *            the userCity to set
     */
    public void setUserCity(City userCity) {
        this.userCity = userCity;
    }

    /**
     * @return the userCountry
     */
    public Country getUserCountry() {
        return userCountry;
    }

    /**
     * @param userCountry
     *            the userCountry to set
     */
    public void setUserCountry(Country userCountry) {
        this.userCountry = userCountry;
    }

}