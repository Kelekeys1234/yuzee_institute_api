package com.yuzee.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ScholarshipElasticDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3487477078664375741L;

	private String id;

	private String name;

	private String countryName;

	private String instituteName;

	private String levelName;

	private Double amount;

	private String description;

	private String website;

	private String content;

	private Date applicationDeadline;

	private String levelCode;

	private String currency;

	private String eligibleNationality;

	private List<ScholarshipIntakeDto> intakes;

	private List<String> languages;

}
