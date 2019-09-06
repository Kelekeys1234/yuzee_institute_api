package com.seeka.app.dto;

import java.util.Date;

public class AgentEducationDetailDto {

    private String course;
    private Date durationFrom;
    private Date durationTo;
    private String institute;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Date getDurationFrom() {
        return durationFrom;
    }

    public void setDurationFrom(Date durationFrom) {
        this.durationFrom = durationFrom;
    }

    public Date getDurationTo() {
        return durationTo;
    }

    public void setDurationTo(Date durationTo) {
        this.durationTo = durationTo;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

}
