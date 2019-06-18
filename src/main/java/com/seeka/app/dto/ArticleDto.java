package com.seeka.app.dto;import java.math.BigInteger;

public class ArticleDto {
    private BigInteger id;
    private String heading;
    private String content;
    private BigInteger category;
    private BigInteger subcategory;
    private CategoryDto categoryobj;
    private SubCategoryDto subcategoryobj;
    private String link;
    private String imageUrl;
    private BigInteger country;
    private BigInteger city;
    private BigInteger faculty;
    private BigInteger institute;
    private BigInteger courses;
    private String gender;
    private String status;
    private BigInteger userCity;
    private BigInteger userCountry;

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
    public BigInteger getCountry() {
        return country;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(BigInteger country) {
        this.country = country;
    }

    /**
     * @return the city
     */
    public BigInteger getCity() {
        return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(BigInteger city) {
        this.city = city;
    }

    /**
     * @return the faculty
     */
    public BigInteger getFaculty() {
        return faculty;
    }

    /**
     * @param faculty
     *            the faculty to set
     */
    public void setFaculty(BigInteger faculty) {
        this.faculty = faculty;
    }

    /**
     * @return the courses
     */
    public BigInteger getCourses() {
        return courses;
    }

    /**
     * @param courses
     *            the courses to set
     */
    public void setCourses(BigInteger courses) {
        this.courses = courses;
    }

    /**
     * @return the institute
     */
    public BigInteger getInstitute() {
        return institute;
    }

    /**
     * @param institute
     *            the institute to set
     */
    public void setInstitute(BigInteger institute) {
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
    public BigInteger getUserCity() {
        return userCity;
    }

    /**
     * @param userCity
     *            the userCity to set
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
     * @param userCountry
     *            the userCountry to set
     */
    public void setUserCountry(BigInteger userCountry) {
        this.userCountry = userCountry;
    }
}
