package com.seeka.app.dto;

public class CurrencyConvertorRequest {

    private String fromCurrencyId;
    private String toCurrencyId;
    private Double amount;

    /**
     * @return the fromCurrencyId
     */
    public String getFromCurrencyId() {
        return fromCurrencyId;
    }

    /**
     * @param fromCurrencyId
     *            the fromCurrencyId to set
     */
    public void setFromCurrencyId(String fromCurrencyId) {
        this.fromCurrencyId = fromCurrencyId;
    }

    /**
     * @return the toCurrencyId
     */
    public String getToCurrencyId() {
        return toCurrencyId;
    }

    /**
     * @param toCurrencyId
     *            the toCurrencyId to set
     */
    public void setToCurrencyId(String toCurrencyId) {
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
