package com.yuzee.app.bean;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
/*@Table(name = "off_campus_course", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "title" }, name = "UK_OFF_CAMPUS_TITLE"),
		@UniqueConstraint(columnNames = { "course_id" }, name = "UK_COURSE") }) */
public class OffCampusCourse implements Serializable {

	private static final long serialVersionUID = 1L;

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
	
}