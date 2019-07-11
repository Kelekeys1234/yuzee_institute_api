package com.seeka.app.dto;import java.math.BigInteger;

import java.util.List;

public class ArticleDto2 {
    private BigInteger id;
    private String heading;
    private String content;
    private BigInteger category;
    private BigInteger subcategory;
    private CategoryDto categoryobj;
    private SubCategoryDto subcategoryobj;
    private String link;
    private String imageUrl;
    private List<BigInteger> country;
    private List<BigInteger> city;
    private List<BigInteger> faculty;
    private List<BigInteger> institute;
    private List<BigInteger> courses;
    private List<String> gender;
    private String status;
    private BigInteger userCity;
    private BigInteger userCountry;
    private String compnayName;
    private String companyWebsite;
    private Boolean recArticleType;
    private Boolean ceekaArticleType;
    
    private String author;
    private String postDate;
    private String expireyDate;
    private boolean enabled;
    private boolean featured;
    private String notes;
    
    /**
     * @return the id
     */
    public BigInteger getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(BigInteger id) {
        this.id = id;
    }
    /**
     * @return the heading
     */
    public String getHeading() {
        return heading;
    }
    /**
     * @param heading the heading to set
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
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * @return the category
     */
    public BigInteger getCategory() {
        return category;
    }
    /**
     * @param category the category to set
     */
    public void setCategory(BigInteger category) {
        this.category = category;
    }
    /**
     * @return the subcategory
     */
    public BigInteger getSubcategory() {
        return subcategory;
    }
    /**
     * @param subcategory the subcategory to set
     */
    public void setSubcategory(BigInteger subcategory) {
        this.subcategory = subcategory;
    }
    /**
     * @return the categoryobj
     */
    public CategoryDto getCategoryobj() {
        return categoryobj;
    }
    /**
     * @param categoryobj the categoryobj to set
     */
    public void setCategoryobj(CategoryDto categoryobj) {
        this.categoryobj = categoryobj;
    }
    /**
     * @return the subcategoryobj
     */
    public SubCategoryDto getSubcategoryobj() {
        return subcategoryobj;
    }
    /**
     * @param subcategoryobj the subcategoryobj to set
     */
    public void setSubcategoryobj(SubCategoryDto subcategoryobj) {
        this.subcategoryobj = subcategoryobj;
    }
    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }
    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }
    /**
     * @return the imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }
    /**
     * @param imageUrl the imageUrl to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    /**
     * @return the country
     */
    public List<BigInteger> getCountry() {
        return country;
    }
    /**
     * @param country the country to set
     */
    public void setCountry(List<BigInteger> country) {
        this.country = country;
    }
    /**
     * @return the city
     */
    public List<BigInteger> getCity() {
        return city;
    }
    /**
     * @param city the city to set
     */
    public void setCity(List<BigInteger> city) {
        this.city = city;
    }
    /**
     * @return the faculty
     */
    public List<BigInteger> getFaculty() {
        return faculty;
    }
    /**
     * @param faculty the faculty to set
     */
    public void setFaculty(List<BigInteger> faculty) {
        this.faculty = faculty;
    }
    /**
     * @return the institute
     */
    public List<BigInteger> getInstitute() {
        return institute;
    }
    /**
     * @param institute the institute to set
     */
    public void setInstitute(List<BigInteger> institute) {
        this.institute = institute;
    }
    /**
     * @return the courses
     */
    public List<BigInteger> getCourses() {
        return courses;
    }
    /**
     * @param courses the courses to set
     */
    public void setCourses(List<BigInteger> courses) {
        this.courses = courses;
    }
    /**
     * @return the gender
     */
    public List<String> getGender() {
        return gender;
    }
    /**
     * @param gender the gender to set
     */
    public void setGender(List<String> gender) {
        this.gender = gender;
    }
    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * @return the userCity
     */
    public BigInteger getUserCity() {
        return userCity;
    }
    /**
     * @param userCity the userCity to set
     */
    public void setUserCity(BigInteger userCity) {
        this.userCity = userCity;
    }
    /**
     * @return the userCountry
     */
    public BigInteger getUserCountry() {
        return userCountry;
    }
    /**
     * @param userCountry the userCountry to set
     */
    public void setUserCountry(BigInteger userCountry) {
        this.userCountry = userCountry;
    }
    /**
     * @return the compnayName
     */
    public String getCompnayName() {
        return compnayName;
    }
    /**
     * @param compnayName the compnayName to set
     */
    public void setCompnayName(String compnayName) {
        this.compnayName = compnayName;
    }
    /**
     * @return the companyWebsite
     */
    public String getCompanyWebsite() {
        return companyWebsite;
    }
    /**
     * @param companyWebsite the companyWebsite to set
     */
    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }
    /**
     * @return the recArticleType
     */
    public Boolean getRecArticleType() {
        return recArticleType;
    }
    /**
     * @param recArticleType the recArticleType to set
     */
    public void setRecArticleType(Boolean recArticleType) {
        this.recArticleType = recArticleType;
    }
    /**
     * @return the ceekaArticleType
     */
    public Boolean getCeekaArticleType() {
        return ceekaArticleType;
    }
    /**
     * @param ceekaArticleType the ceekaArticleType to set
     */
    public void setCeekaArticleType(Boolean ceekaArticleType) {
        this.ceekaArticleType = ceekaArticleType;
    }
    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }
    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    /**
     * @return the postDate
     */
    public String getPostDate() {
        return postDate;
    }
    /**
     * @param postDate the postDate to set
     */
    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }
    /**
     * @return the expireyDate
     */
    public String getExpireyDate() {
        return expireyDate;
    }
    /**
     * @param expireyDate the expireyDate to set
     */
    public void setExpireyDate(String expireyDate) {
        this.expireyDate = expireyDate;
    }
    /**
     * @return the enabled
     */
    public boolean getEnabled() {
        return enabled;
    }
    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    /**
     * @return the featured
     */
    public boolean getFeatured() {
        return featured;
    }
    /**
     * @param featured the featured to set
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
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
}