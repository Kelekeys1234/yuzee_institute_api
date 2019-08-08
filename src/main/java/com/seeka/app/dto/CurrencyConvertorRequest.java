package com.seeka.app.dto;

import java.math.BigInteger;

public class CurrencyConvertorRequest {

    private BigInteger fromCurrencyId;
    private BigInteger toCurrencyId;
    private Double amount;

    /**
     * @return the fromCurrencyId
     */
    public BigInteger getFromCurrencyId() {
        return fromCurrencyId;
    }

    /**
     * @param fromCurrencyId
     *            the fromCurrencyId to set
     */
    public void setFromCurrencyId(BigInteger fromCurrencyId) {
        this.fromCurrencyId = fromCurrencyId;
    }

    /**
     * @return the toCurrencyId
     */
    public BigInteger getToCurrencyId() {
        return toCurrencyId;
    }

    /**
     * @param toCurrencyId
     *            the toCurrencyId to set
     */
    public void setToCurrencyId(BigInteger toCurrencyId) {
        this.toCurrencyId = toCurrencyId;
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

}
