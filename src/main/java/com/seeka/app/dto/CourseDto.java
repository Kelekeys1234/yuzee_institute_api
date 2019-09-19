package com.seeka.app.dto;

import java.math.BigInteger;

public class CourseDto {

    private BigInteger id;
    private BigInteger levelId;
    private String name;
    private String language;
    private String languageShortKey;
    private String cost;
    private String duration;
    private String durationTime;
    private Double domasticFee;
    private Double internationalFee;
    private String worldRanking;
    private String stars;
    private String facultyName;
    private String levelName;
    private String costOfLiving;
    private String description;
    private String delivery;
    private String intakeDate;
    private String remarks;

    public String getLanguageShortKey() {
        return languageShortKey;
    }

    public void setLanguageShortKey(final String languageShortKey) {
        this.languageShortKey = languageShortKey;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(final String cost) {
        this.cost = cost;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(final String duration) {
        this.duration = duration;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(final String durationTime) {
        this.durationTime = durationTime;
    }

    public String getWorldRanking() {
        return worldRanking;
    }

    public void setWorldRanking(final String worldRanking) {
        this.worldRanking = worldRanking;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(final String stars) {
        this.stars = stars;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(final String facultyName) {
        this.facultyName = facultyName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(final String levelName) {
        this.levelName = levelName;
    }

    public String getCostOfLiving() {
        return costOfLiving;
    }

    public void setCostOfLiving(final String costOfLiving) {
        this.costOfLiving = costOfLiving;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDelivery() {
        if (null == delivery) {
            delivery = "";
        }
        return delivery;
    }

    public void setDelivery(final String delivery) {
        this.delivery = delivery;
    }

    public String getIntakeDate() {
        if (null == intakeDate) {
            intakeDate = "";
        }
        return intakeDate;
    }

    public void setIntakeDate(final String intakeDate) {
        this.intakeDate = intakeDate;
    }

    public BigInteger getLevelId() {
        return levelId;
    }

    public void setLevelId(final BigInteger levelId) {
        this.levelId = levelId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the domasticFee
     */
    public Double getDomasticFee() {
        return domasticFee;
    }

    /**
     * @param domasticFee
     *            the domasticFee to set
     */
    public void setDomasticFee(Double domasticFee) {
        this.domasticFee = domasticFee;
    }

    /**
     * @return the internationalFee
     */
    public Double getInternationalFee() {
        return internationalFee;
    }

    /**
     * @param internationalFee
     *            the internationalFee to set
     */
    public void setInternationalFee(Double internationalFee) {
        this.internationalFee = internationalFee;
    }

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language
     *            the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

}
