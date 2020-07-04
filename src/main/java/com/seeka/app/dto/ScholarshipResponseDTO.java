package com.seeka.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ScholarshipResponseDTO implements Serializable {

	private static final long serialVersionUID = -6660498982386504039L;
	
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
	
	@JsonProperty("scholarship_amount")
	private Double scholarshipAmount;
	
	@JsonProperty("validity")
	private String validity;
	
	@JsonProperty("how_to_apply")
	private String howToApply;
	
	@JsonProperty("gender")
	private String gender;
	
	@JsonProperty("eligible_nationality")
	private String eligibleNationality;
	
	@JsonProperty("headquaters")
	private String headquaters;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("address")
	private String address;
	
	@JsonProperty("created_on")
	private Date createdOn;
	
	@JsonProperty("updated_on")
	private Date updatedOn;
	
	@JsonProperty("created_by")
	private String createdBy;
	
	@JsonProperty("updated_by")
	private String updatedBy;
	
	@JsonProperty("deleted_on")
	private Date deletedOn;
	
	@JsonProperty("is_active")
	private Boolean isActive;
	
	@JsonProperty("website")
	private String website;
	
	@JsonProperty("application_deadline")
	private Date applicationDeadline;
	
	@JsonProperty("intakes")
	private List<String> intakes;
	
	@JsonProperty("languages")
	private List<String> languages;
	
	@JsonProperty("level_name")
	private String levelName;
	
	@JsonProperty("currency")
	private String currency;
	
	@JsonProperty("institute_name")
	@NotBlank(message = "institute_name should not be blank")
	private String instituteName;
	
	@JsonProperty("course_name")
	@NotBlank(message = "course_name should not be blank")
	private String courseName;
	
	@JsonProperty("requirements")
	private String requirements;
	
	@JsonProperty("level_code")
	private String levelCode;
	
	@JsonProperty("files")
	private List<MediaDto> files;
	
	/**
	 * This field is added just to keep the response from elastic search and normal
	 * search same.
	 */
	@JsonProperty("intake")
	private String intake;
}
