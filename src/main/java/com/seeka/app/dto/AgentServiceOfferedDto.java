package com.seeka.app.dto;

import java.math.BigInteger;

import com.seeka.app.bean.Service;

public class AgentServiceOfferedDto {

    private Service service;
    private Double amount;
    private BigInteger country;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public BigInteger getCountry() {
        return country;
    }

    public void setCountry(BigInteger country) {
        this.country = country;
    }

}
