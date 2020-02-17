package com.seeka.app.dto;

import java.util.List;

public class GradeDto {

    private String countryId;
    private String educationSystemId;
    private List<String> subjectGrades;

    /**
     * @return the countryId
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     * @param countryId
     *            the countryId to set
     */
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    /**
     * @return the educationSystemId
     */
    public String getEducationSystemId() {
        return educationSystemId;
    }

    /**
     * @param educationSystemId
     *            the educationSystemId to set
     */
    public void setEducationSystemId(String educationSystemId) {
        this.educationSystemId = educationSystemId;
    }

    /**
     * @return the subjectGrades
     */
    public List<String> getSubjectGrades() {
        return subjectGrades;
    }

    /**
     * @param subjectGrades
     *            the subjectGrades to set
     */
    public void setSubjectGrades(List<String> subjectGrades) {
        this.subjectGrades = subjectGrades;
    }

}
