package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.Country;
import com.seeka.app.bean.Course;

public class CourseMinRequirementDto {

    private BigInteger country;
    private BigInteger system;
    private List<String> subject;
    private List<String> grade;
    private BigInteger course;
    public BigInteger getCountry() {
        return country;
    }
    public void setCountry(BigInteger country) {
        this.country = country;
    }
    public BigInteger getSystem() {
        return system;
    }
    public void setSystem(BigInteger system) {
        this.system = system;
    }
    public List<String> getSubject() {
        return subject;
    }
    public void setSubject(List<String> subject) {
        this.subject = subject;
    }
    public List<String> getGrade() {
        return grade;
    }
    public void setGrade(List<String> grade) {
        this.grade = grade;
    }
    public BigInteger getCourse() {
        return course;
    }
    public void setCourse(BigInteger course) {
        this.course = course;
    }
    
}
