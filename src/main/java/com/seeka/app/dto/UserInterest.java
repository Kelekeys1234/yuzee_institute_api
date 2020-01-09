package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

public class UserInterest {

    private BigInteger userId;

    private List<BigInteger> interest;

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
     * @return the interest
     */
    public List<BigInteger> getInterest() {
        return interest;
    }

    /**
     * @param interest
     *            the interest to set
     */
    public void setInterest(List<BigInteger> interest) {
        this.interest = interest;
    }

}
