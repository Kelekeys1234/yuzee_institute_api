package com.seeka.app.dto;

import java.util.List;

public class UserCourseCompare {
    
    private String userId;
    private List<String> courseId;
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * @return the courseId
     */
    public List<String> getCourseId() {
        return courseId;
    }
    /**
     * @param courseId the courseId to set
     */
    public void setCourseId(List<String> courseId) {
        this.courseId = courseId;
    }

}
