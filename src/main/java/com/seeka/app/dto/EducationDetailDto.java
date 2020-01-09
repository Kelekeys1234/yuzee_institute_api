package com.seeka.app.dto;

import java.math.BigInteger;

public class EducationDetailDto {

    private BigInteger eduCountryId;
    private BigInteger eduSystemId;
    private String eduInstitue;
    private String gpaScore;
    private String isEnglishMedium;
    private String englishLevel;
    private String eduSysScore;
    private String eduLevel;
    
    private String educationSystemName;
    private String educationCountryName;

    /**
     * @return the eduCountryId
     */
    public BigInteger getEduCountryId() {
        return eduCountryId;
    }

    /**
     * @param eduCountryId
     *            the eduCountryId to set
     */
    public void setEduCountryId(BigInteger eduCountryId) {
        this.eduCountryId = eduCountryId;
    }

    /**
     * @return the eduSystemId
     */
    public BigInteger getEduSystemId() {
        return eduSystemId;
    }

    /**
     * @param eduSystemId
     *            the eduSystemId to set
     */
    public void setEduSystemId(BigInteger eduSystemId) {
        this.eduSystemId = eduSystemId;
    }

    /**
     * @return the eduInstitue
     */
    public String getEduInstitue() {
        return eduInstitue;
    }

    /**
     * @param eduInstitue
     *            the eduInstitue to set
     */
    public void setEduInstitue(String eduInstitue) {
        this.eduInstitue = eduInstitue;
    }

    /**
     * @return the gpaScore
     */
    public String getGpaScore() {
        return gpaScore;
    }

    /**
     * @param gpaScore
     *            the gpaScore to set
     */
    public void setGpaScore(String gpaScore) {
        this.gpaScore = gpaScore;
    }

    /**
     * @return the isEnglishMedium
     */
    public String getIsEnglishMedium() {
        return isEnglishMedium;
    }

    /**
     * @param isEnglishMedium
     *            the isEnglishMedium to set
     */
    public void setIsEnglishMedium(String isEnglishMedium) {
        this.isEnglishMedium = isEnglishMedium;
    }

    /**
     * @return the englishLevel
     */
    public String getEnglishLevel() {
        return englishLevel;
    }

    /**
     * @param englishLevel
     *            the englishLevel to set
     */
    public void setEnglishLevel(String englishLevel) {
        this.englishLevel = englishLevel;
    }

    /**
     * @return the eduSysScore
     */
    public String getEduSysScore() {
        return eduSysScore;
    }

    /**
     * @param eduSysScore
     *            the eduSysScore to set
     */
    public void setEduSysScore(String eduSysScore) {
        this.eduSysScore = eduSysScore;
    }

    /**
     * @return the eduLevel
     */
    public String getEduLevel() {
        return eduLevel;
    }

    /**
     * @param eduLevel
     *            the eduLevel to set
     */
    public void setEduLevel(String eduLevel) {
        this.eduLevel = eduLevel;
    }

    /**
     * @return the educationSystemName
     */
    public String getEducationSystemName() {
        return educationSystemName;
    }

    /**
     * @param educationSystemName the educationSystemName to set
     */
    public void setEducationSystemName(String educationSystemName) {
        this.educationSystemName = educationSystemName;
    }

    /**
     * @return the educationCountryName
     */
    public String getEducationCountryName() {
        return educationCountryName;
    }

    /**
     * @param educationCountryName the educationCountryName to set
     */
    public void setEducationCountryName(String educationCountryName) {
        this.educationCountryName = educationCountryName;
    }
}
