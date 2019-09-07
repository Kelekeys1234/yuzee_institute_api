package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.Date;

public class AgentEducationDetailDto {

    private String course;
    private BigInteger durationFrom;
    private BigInteger durationTo;
    private String institute;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public BigInteger getDurationFrom() {
        return durationFrom;
    }

    public void setDurationFrom(BigInteger durationFrom) {
        this.durationFrom = durationFrom;
    }

    public BigInteger getDurationTo() {
        return durationTo;
    }

    public void setDurationTo(BigInteger durationTo) {
        this.durationTo = durationTo;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

}
