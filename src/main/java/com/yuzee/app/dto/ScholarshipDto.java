package com.yuzee.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ScholarshipDto implements Serializable {

	private static final long serialVersionUID = 2633639341414502096L;

	@JsonProperty("scholarship_id")
	private String id;

	@JsonProperty("name")
	@NotBlank(message = "name should not be blank")
	private String name;

	@JsonProperty("description")
	private String description;

	@JsonProperty("scholarship_award")
	private String scholarshipAward;

	@JsonProperty("country_name")
	@NotBlank(message = "country_name should not be blank")
	private String countryName;

	@JsonProperty("level_id")
	@NotBlank(message = "level_id should not be blank")
	private String levelId;

	@JsonProperty("number_of_avaliability")
	private Integer numberOfAvaliability;

	@JsonProperty("currency")
	private String currency;

	@JsonProperty("scholarship_amount")
	private Double scholarshipAmount;

	@JsonProperty("validity")
	private String validity;

	@JsonProperty("how_to_apply")
	private String howToApply;

	@JsonProperty("gender")
	private String gender;

	@JsonProperty("eligible_nationality")
	private List<String> eligibleNationality;

	@JsonProperty("headquaters")
	private String headquaters;

	@JsonProperty("email")
	private String email;

	@JsonProperty("address")
	private String address;

	@JsonProperty("website")
	private String website;

	@JsonProperty("institute_id")
	@NotBlank(message = "institute_id should not be blank")
	private String instituteId;
	
	@JsonProperty("institute_name")
	@NotBlank(message = "institute_name should not be blank")
	private String instituteName;

	@JsonProperty("course_id")
	@NotBlank(message = "course_id should not be blank")
	private String courseId;

	@JsonProperty("application_deadline")
	private Date applicationDeadline;

	@JsonProperty("intakes")
	private List<String> intakes;

	@JsonProperty("languages")
	private List<String> languages;

	@JsonProperty("requirements")
	private String requirements;

	@JsonProperty("level_name")
	private String levelName;

	@JsonProperty("level_code")
	private String levelCode;
}
