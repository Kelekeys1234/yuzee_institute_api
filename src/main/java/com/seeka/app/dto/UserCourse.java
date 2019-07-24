package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

public class UserCourse {
    
    private BigInteger userId;
    
    private List<BigInteger> courses;

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
    

}
