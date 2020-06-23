package com.seeka.app.dto;

import lombok.Data;

@Data
public class InstituteDetailsGetRequest {
    private String latitude;
    private String longitude;
    private Integer totalStudent;
    private Integer worldRanking;
    private String accreditation;
    private String email;
    private String phoneNumber;
    private String website;
    private String address;
    private String avgCostOfLiving;
}
