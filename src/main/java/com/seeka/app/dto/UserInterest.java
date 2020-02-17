package com.seeka.app.dto;

import java.util.List;

public class UserInterest {

    private String userId;

    private List<String> interest;

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the interest
     */
    public List<String> getInterest() {
        return interest;
    }

    /**
     * @param interest
     *            the interest to set
     */
    public void setInterest(List<String> interest) {
        this.interest = interest;
    }

}
