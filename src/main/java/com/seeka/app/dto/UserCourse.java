package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

public class UserCourse {

    private BigInteger userId;

    private List<BigInteger> courses;

    private String createdBy;
    private String updatedBy;

    /**
     * @return the userId
     */
    public BigInteger getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    /**
     * @return the courses
     */
    public List<BigInteger> getCourses() {
        return courses;
    }

    /**
     * @param courses
     *            the courses to set
     */
    public void setCourses(List<BigInteger> courses) {
        this.courses = courses;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     *            the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy
     *            the updatedBy to set
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

}
