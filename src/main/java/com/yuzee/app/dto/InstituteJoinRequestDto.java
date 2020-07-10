package com.yuzee.app.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstituteJoinRequestDto {

	@JsonProperty("institute_join_request_id")
	private String instituteJoinRequestId;
	
	@JsonProperty("user_id")
	private String userId;
	
	@NotBlank(message = "institute_country should not be blank")
	@JsonProperty("institute_country")
	private String instituteCountry;
	
	@NotBlank(message = "institute_name should not be blank")
	@JsonProperty("institute_name")
	private String instituteName;
	
	@NotBlank(message = "institute_type should not be blank")
	@JsonProperty("institute_type")
	private String instituteType;
	
	@NotBlank(message = "type should not be blank")
	@JsonProperty("type")
	private String type;
	
	@NotBlank(message = "first_name should not be blank")
	@JsonProperty("first_name")
	private String firstName;
	
	@NotBlank(message = "last_name should not be blank")
	@JsonProperty("last_name")
	private String lastName;
	
	@NotBlank(message = "title should not be blank")
	@JsonProperty("title")
	private String title;
	
	@NotBlank(message = "email should not be blank")
	@JsonProperty("email")
	private String email;
	
	@NotBlank(message = "phone_number should not be blank")
	@JsonProperty("phone_number")
	private String phoneNumber;
	
	@NotBlank(message = "management_name should not be blank")
	@JsonProperty("management_name")
	private String managementName;
	
	@NotBlank(message = "management_email should not be blank")
	@JsonProperty("management_email")
	private String managementEmail;
	
	@NotBlank(message = "management_phone_number should not be blank")
	@JsonProperty("management_phone_number")
	private String managementPhoneNumber;
	
	@JsonProperty("certificates")
	List<StorageDto> certificates = new ArrayList<StorageDto>();
}
