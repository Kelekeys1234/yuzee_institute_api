package com.seeka.app.dto;

import java.util.List;

public class UserCountryHobbiesDto {

    private String userId;

    private List<String> hobbies;

    private List<String> country;

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

    /**
     * @return the country
     */
    public List<String> getCountry() {
        return country;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(List<String> country) {
        this.country = country;
    }

}
