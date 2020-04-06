package com.seeka.app.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserDto {

	private String id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;
	private Date dob;
	private String countryOrgin;
	private String citizenship;
	private String email;
	private String username;
	private String mobileNo;
	private String skypeId;
	private String userEduInfo;
	private String dobStr;
	private String signUpType;
	private String socialAccountId;
	private String userType;
	private String whattsappNo;
	private String city;
	private String state;
	private String postalCode;
	private String address;
	private String imageName;
	private String imageURL;
	private String aboutMe;
	@JsonIgnore
	UserEducationProfileResponseDto userEducationProfileResponseDto =  null;
	@JsonIgnore
	UserAchivementsProfileResponseDto userAchivementsProfileResponseDto = null;
	@JsonIgnore
	private List<SkillProfileResponseDto> skills = new ArrayList<SkillProfileResponseDto>();
	@JsonIgnore
	List<UserEnglishQualificationProfileResponseDto> listOfUserEnglishQualificationProfileResponseDto = null;
	
	public UserDto() {

	}
}
