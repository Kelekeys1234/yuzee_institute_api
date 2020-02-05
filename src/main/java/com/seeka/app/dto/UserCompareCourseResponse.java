package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

public class UserCompareCourseResponse {

    private String userCourseCompareId;

    private List<CourseRequest> courses;

    /**
     * @return the userCourseCompareId
     */
    public String getUserCourseCompareId() {
        return userCourseCompareId;
    }

    /**
     * @param userCourseCompareId
     *            the userCourseCompareId to set
     */
    public void setUserCourseCompareId(String userCourseCompareId) {
        this.userCourseCompareId = userCourseCompareId;
    }

    /**
     * @return the courses
     */
    public List<CourseRequest> getCourses() {
        return courses;
    }

    /**
     * @param courses
     *            the courses to set
     */
    public void setCourses(List<CourseRequest> courses) {
        this.courses = courses;
    }

}
