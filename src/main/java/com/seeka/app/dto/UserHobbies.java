package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

public class UserHobbies {

    private BigInteger userId;

    private List<BigInteger> hobbies;

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

}
