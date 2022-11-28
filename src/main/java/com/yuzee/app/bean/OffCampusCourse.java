package com.yuzee.app.bean;

import java.io.Serializable;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document("off_campus_course")

public class OffCampusCourse implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@EqualsAndHashCode.Include
	private String title;

	@EqualsAndHashCode.Include
	private Double latitude;

	@EqualsAndHashCode.Include
	private Double longitude;

	@EqualsAndHashCode.Include
	private Double adminFee;

	@EqualsAndHashCode.Include
	private Double materialFee;

	@EqualsAndHashCode.Include
	private String address;

	@EqualsAndHashCode.Include
	private String countryName;

	@EqualsAndHashCode.Include
	private String cityName;

	@EqualsAndHashCode.Include
	private String stateName;

	@EqualsAndHashCode.Include
	private String postalCode;

	@EqualsAndHashCode.Include
	private Boolean locationHelpRequired;

	@EqualsAndHashCode.Include
	private Boolean skipLocation;

	@EqualsAndHashCode.Include
	private String referenceCourseId;
	@EqualsAndHashCode.Include
	private Date startDate;
	@EqualsAndHashCode.Include
	private Date endDate;
	@DBRef
	private Course course;

}