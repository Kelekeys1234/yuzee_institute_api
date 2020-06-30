package com.seeka.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class CourseDTOElasticSearch implements Serializable {

	private static final long serialVersionUID = 3521506877760833299L;

	private String id;
	private String name;
	private Integer worldRanking;
	private Integer stars;
	private String recognition;
	private String recognitionType;
	private String website;
	private String abbreviation;
	private String remarks;
	private String description;

	private String facultyName;
	private String instituteName;
	private String countryName;
	private String cityName;
	private String levelCode;
	private String levelName;

	private String availabilty;
	private String currency;
	private String currencyTime;
	private Double costRange;
	private String content;
	private String facultyDescription;
	private List<String> instituteImageUrl;
	private String instituteLogoUrl;
	private String latitute;
	private String longitude;

	private String openingHourFrom;
	private String openingHourTo;
	private List<Date> intake;
	private List<String> language;
	private List<CourseAddtionalInfoElasticDto> courseAdditionalInfo;
}
