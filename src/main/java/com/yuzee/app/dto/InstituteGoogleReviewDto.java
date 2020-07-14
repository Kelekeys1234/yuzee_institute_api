package com.yuzee.app.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InstituteGoogleReviewDto implements Serializable {

	private static final long serialVersionUID = -6061441361067943776L;

	@JsonProperty("user_name")
	@NotBlank(message = "user_name should not be blank")
	private String userName;
	
	@JsonProperty("date_of_posting")
	private Date dateOfPosting;
	
	@JsonProperty("review_star")
	@NotNull(message = "review_star should not be blank")
	private Double reviewStar;
	
	@JsonProperty("review_comment")
	@NotBlank(message = "review_comment should not be blank")
	private String reviewComment;
	
	@JsonProperty("institute_name")
	@NotBlank(message = "institute_name should not be blank")
	private String instituteName;
	
	@JsonProperty("country_name")
	@NotBlank(message = "country_name should not be blank")
	private String countryName;
	
	@JsonProperty("institute_id")
	@NotBlank(message = "institute_id should not be blank")
	private String instituteId;
}
