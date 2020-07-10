package com.yuzee.app.dto;

import java.util.List;

public class UserHobbies {

    private String userId;

    private List<String> hobbies;

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
     * @return the hobbies
     */
    public List<String> getHobbies() {
        return hobbies;
    }

    /**
     * @param hobbies
     *            the hobbies to set
     */
    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

}
