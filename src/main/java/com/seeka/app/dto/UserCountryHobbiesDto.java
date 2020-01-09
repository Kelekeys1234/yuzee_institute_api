package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

public class UserCountryHobbiesDto {

    private BigInteger userId;

    private List<BigInteger> hobbies;

    private List<String> country;

    /**
     * @return the userId
     */
    public BigInteger getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    /**
     * @return the hobbies
     */
    public List<BigInteger> getHobbies() {
        return hobbies;
    }

    /**
     * @param hobbies
     *            the hobbies to set
     */
    public void setHobbies(List<BigInteger> hobbies) {
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
