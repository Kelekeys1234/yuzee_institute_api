package com.seeka.app.dto;

import java.math.BigInteger;

public class AgentServiceOfferedDto {

    private BigInteger service;
    private Double amount;
    private BigInteger currency;

    /**
     * @return the service
     */
    public BigInteger getService() {
        return service;
    }

    /**
     * @param service
     *            the service to set
     */
    public void setService(BigInteger service) {
        this.service = service;
    }

    /**
     * @return the amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * @return the currency
     */
    public BigInteger getCurrency() {
        return currency;
    }

    /**
     * @param currency
     *            the currency to set
     */
    public void setCurrency(BigInteger currency) {
        this.currency = currency;
    }

}
