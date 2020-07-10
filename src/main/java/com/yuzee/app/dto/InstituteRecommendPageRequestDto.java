package com.yuzee.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstituteRecommendPageRequestDto {

	@JsonProperty("institute_recommend_page_request_id")
	private String instituteRecommendRequestId;
	
	@NotBlank(message = "institute_name should not be blank")
	@JsonProperty("institute_name")
	private String instituteName;
	
	@NotNull(message = "is_working should not be blank")
	@JsonProperty("is_working")
	private Boolean isWorking;
	
	@NotNull(message = "knowing_someone_working should not be blank")
	@JsonProperty("knowing_someone_working")
	private Boolean knowingSomeoneWorking;
	
	@NotBlank(message = "name should not be blank")
	@JsonProperty("name")
	private String name;
	
	@NotBlank(message = "email should not be blank")
	@JsonProperty("email")
	private String email;
	
	@NotBlank(message = "phone_number should not be blank")
	@JsonProperty("phone_number")
	private String phoneNumber;
	
	@NotBlank(message = "title should not be blank")
	@JsonProperty("title")
	private String title;
	
	@NotBlank(message = "address_institute should not be blank")
	@JsonProperty("address_institute")
	private String addressInstitute;
	
	@NotBlank(message = "website should not be blank")
	@JsonProperty("website")
	private String website;

}
