package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

public class ArticleResponseDetailsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2548163101026500580L;
	
	private BigInteger id;
    private String heading;
    private String content;
    private BigInteger categoryId;
    private String categoryName;
    private BigInteger subcategoryId;
    private String subcategoryName;
	private String link;
    private String imageUrl;
    private BigInteger countryId;
    private String countryName;
    private BigInteger cityId;
    private String cityName;
    private BigInteger facultyId;
    private String facutyName;
    private BigInteger instituteId;
    private String instituteName;
    private BigInteger courseId;
    private String courseName;
    private String status;
   	private String imagepath;
	private String author;
	private Date postDate;
	private Date expireDate;
	private Boolean enabled;
	private Boolean published;
	private String websiteUrl;
	private String url;
	private String tags;	
	
	private List<ArticleUserDemographicDto> userDemographic;

	
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getPublished() {
		return published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}
	
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
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


	public List<ArticleUserDemographicDto> getUserDemographic() {
		return userDemographic;
	}

	public void setUserDemographic(List<ArticleUserDemographicDto> userDemographic) {
		this.userDemographic = userDemographic;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public BigInteger getCountryId() {
		return countryId;
	}

	public void setCountryId(BigInteger countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public BigInteger getCityId() {
		return cityId;
	}

	public void setCityId(BigInteger cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public BigInteger getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(BigInteger facultyId) {
		this.facultyId = facultyId;
	}

	public String getFacutyName() {
		return facutyName;
	}

	public void setFacutyName(String facutyName) {
		this.facutyName = facutyName;
	}

	public BigInteger getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(BigInteger instituteId) {
		this.instituteId = instituteId;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public BigInteger getCourseId() {
		return courseId;
	}

	public void setCourseId(BigInteger courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

    public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSubcategoryName() {
		return subcategoryName;
	}

	public void setSubcategoryName(String subcategoryName) {
		this.subcategoryName = subcategoryName;
	}
	
}
