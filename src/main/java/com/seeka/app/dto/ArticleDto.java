package com.seeka.app.dto;

import java.util.UUID;

public class ArticleDto {
    private UUID id;
    private String heading;
    private String content;
    private UUID category;
    private UUID subcategory;
    private CategoryDto categoryobj;
    private SubCategoryDto subcategoryobj;
    private String link;
    private String imageUrl;
    private UUID country;
    private UUID city;
    private UUID faculty;
    private UUID institute;
    private UUID courses;
    private String gender;
    private String status;
    private UUID userCity;
    private UUID userCountry;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public UUID getCategory() {
        return category;
    }

    public void setCategory(UUID category) {
        this.category = category;
    }

    public UUID getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(UUID subcategory) {
        this.subcategory = subcategory;
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

    /**
     * @return the country
     */
    public UUID getCountry() {
        return country;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(UUID country) {
        this.country = country;
    }

    /**
     * @return the city
     */
    public UUID getCity() {
        return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(UUID city) {
        this.city = city;
    }

    /**
     * @return the faculty
     */
    public UUID getFaculty() {
        return faculty;
    }

    /**
     * @param faculty
     *            the faculty to set
     */
    public void setFaculty(UUID faculty) {
        this.faculty = faculty;
    }

    /**
     * @return the courses
     */
    public UUID getCourses() {
        return courses;
    }

    /**
     * @param courses
     *            the courses to set
     */
    public void setCourses(UUID courses) {
        this.courses = courses;
    }

    /**
     * @return the institute
     */
    public UUID getInstitute() {
        return institute;
    }

    /**
     * @param institute
     *            the institute to set
     */
    public void setInstitute(UUID institute) {
        this.institute = institute;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender
     *            the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
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

    /**
     * @return the categoryobj
     */
    public CategoryDto getCategoryobj() {
        return categoryobj;
    }

    /**
     * @param categoryobj
     *            the categoryobj to set
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
     * @param subcategoryobj
     *            the subcategoryobj to set
     */
    public void setSubcategoryobj(SubCategoryDto subcategoryobj) {
        this.subcategoryobj = subcategoryobj;
    }

    /**
     * @return the userCity
     */
    public UUID getUserCity() {
        return userCity;
    }

    /**
     * @param userCity
     *            the userCity to set
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
     * @param userCountry
     *            the userCountry to set
     */
    public void setUserCountry(UUID userCountry) {
        this.userCountry = userCountry;
    }
}
