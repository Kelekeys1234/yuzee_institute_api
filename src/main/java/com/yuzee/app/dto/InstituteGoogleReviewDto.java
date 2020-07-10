package com.yuzee.app.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InstituteGoogleReviewDto implements Serializable {

	private static final long serialVersionUID = -6061441361067943776L;

	@JsonProperty("user_name")
	private String userName;
	
	@JsonProperty("date_of_posting")
	private Date dateOfPosting;
	
	@JsonProperty("review_star")
	private Double reviewStar;
	
	@JsonProperty("review_comment")
	private String reviewComment;
	
	@JsonProperty("institute_name")
	private String instituteName;
	
	@JsonProperty("country_name")
	private String countryName;
	
	@JsonProperty("institute_id")
	private String instituteId;
	
	@JsonProperty("country_id")
	private String countryId;
}
