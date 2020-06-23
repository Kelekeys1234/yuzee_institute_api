package com.seeka.app.dto;

import java.util.Date;

import lombok.Data;

@Data
public class InstituteFilterDto {

    private String cityName;
    private String countryName;
    private String instituteId;
    private String instituteTypeId;
    private Date durationDate;
    private Integer maxSizePerPage;
    private Integer pageNumber;
    private Integer worldRanking;
    private String datePosted;
}
