package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

public class UserCourseCompare {
    
    private BigInteger userId;
    private List<BigInteger> courseId;
    /**
     * @return the userId
     */
    public BigInteger getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }
    /**
     * @return the courseId
     */
    public List<BigInteger> getCourseId() {
        return courseId;
    }
    /**
     * @param courseId the courseId to set
     */
    public void setCourseId(List<BigInteger> courseId) {
        this.courseId = courseId;
    }

}
