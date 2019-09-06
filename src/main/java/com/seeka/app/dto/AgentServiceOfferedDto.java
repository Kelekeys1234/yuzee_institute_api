package com.seeka.app.dto;

import com.seeka.app.bean.Service;

public class AgentServiceOfferedDto {

    private Service service;
    private Double amount;
    
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
    
    
}
