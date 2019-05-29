package com.seeka.app.dto;

import java.util.List;
import java.util.UUID;

public class ArticleDto2 {
    private UUID id;
    private String heading;
    private String content;
    private UUID category;
    private UUID subcategory;
    private CategoryDto categoryobj;
    private SubCategoryDto subcategoryobj;
    private String link;
    private String imageUrl;
    private List<UUID> country;
    private List<UUID> city;
    private List<UUID> faculty;
    private List<UUID> institute;
    private List<UUID> courses;
    private List<String> gender;
    private String status;
    private UUID userCity;
    private UUID userCountry;
    private String compnayName;
    private String companyWebsite;
    private Boolean recArticleType;
    private Boolean ceekaArticleType;
    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(UUID id) {
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
    public UUID getCategory() {
        return category;
    }
    /**
     * @param category the category to set
     */
    public void setCategory(UUID category) {
        this.category = category;
    }
    /**
     * @return the subcategory
     */
    public UUID getSubcategory() {
        return subcategory;
    }
    /**
     * @param subcategory the subcategory to set
     */
    public void setSubcategory(UUID subcategory) {
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
    public List<UUID> getCountry() {
        return country;
    }
    /**
     * @param country the country to set
     */
    public void setCountry(List<UUID> country) {
        this.country = country;
    }
    /**
     * @return the city
     */
    public List<UUID> getCity() {
        return city;
    }
    /**
     * @param city the city to set
     */
    public void setCity(List<UUID> city) {
        this.city = city;
    }
    /**
     * @return the faculty
     */
    public List<UUID> getFaculty() {
        return faculty;
    }
    /**
     * @param faculty the faculty to set
     */
    public void setFaculty(List<UUID> faculty) {
        this.faculty = faculty;
    }
    /**
     * @return the institute
     */
    public List<UUID> getInstitute() {
        return institute;
    }
    /**
     * @param institute the institute to set
     */
    public void setInstitute(List<UUID> institute) {
        this.institute = institute;
    }
    /**
     * @return the courses
     */
    public List<UUID> getCourses() {
        return courses;
    }
    /**
     * @param courses the courses to set
     */
    public void setCourses(List<UUID> courses) {
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
    public UUID getUserCity() {
        return userCity;
    }
    /**
     * @param userCity the userCity to set
     */
    public void setUserCity(UUID userCity) {
        this.userCity = userCity;
    }
    /**
     * @return the userCountry
     */
    public UUID getUserCountry() {
        return userCountry;
    }
    /**
     * @param userCountry the userCountry to set
     */
    public void setUserCountry(UUID userCountry) {
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
}