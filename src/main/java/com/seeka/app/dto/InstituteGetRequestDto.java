package com.seeka.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class InstituteGetRequestDto {
    private String id;
    private String countryName;
    private String cityName;
    private String instituteType;
    private String name;
    private List<String> instituteYoutubes;
    private Integer courseCount;
    private Double latitude;
    private Double longitude;
    private Integer totalStudent;
    private Integer worldRanking;
    private String accreditation;
    private String email;
    private String phoneNumber;
    private String website;
    private String address;
    private String avgCostOfLiving;
    private String description;

}