package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

public class GradeDto {

    private BigInteger countryId;
    private BigInteger educationSystemId;
    private List<String> subjectGrades;

    /**
     * @return the countryId
     */
    public BigInteger getCountryId() {
        return countryId;
    }

    /**
     * @param countryId
     *            the countryId to set
     */
    public void setCountryId(BigInteger countryId) {
        this.countryId = countryId;
    }

    /**
     * @return the educationSystemId
     */
    public BigInteger getEducationSystemId() {
        return educationSystemId;
    }

    /**
     * @param educationSystemId
     *            the educationSystemId to set
     */
    public void setEducationSystemId(BigInteger educationSystemId) {
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
