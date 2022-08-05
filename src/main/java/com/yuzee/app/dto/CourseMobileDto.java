package com.yuzee.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class CourseMobileDto {

	@JsonProperty("course_id")
	private String courseId;

	@NotBlank(message = "{course_name.is_required}")
	@JsonProperty("course_name")
	private String courseName;

	@NotBlank(message = "{course_description.is_required}")
	@JsonProperty("course_description")
	private String courseDescription;

	@NotBlank(message = "{faculty_id.is_required}")
	@JsonProperty("faculty_id")
	private String facultyId;

	@JsonProperty("faculty_name")
	private String facultyName;

	@NotNull(message = "{gpa_required.is_required}")
	@JsonProperty("gpa_required")
	private Double gpaRequired;

	@NotNull(message = "{usd_domestic_fee.is_required}")
	@JsonProperty("usd_domestic_fee")
	private Double usdDomesticFee;

	@NotNull(message = "{usd_international_fee.is_required}")
	@JsonProperty("usd_international_fee")
	private Double usdInternationalFee;

	@NotNull(message = "{duration.is_required}")
	@JsonProperty("duration")
	private Double duration;

	@NotBlank(message = "{duration_unit.is_required}")
	@JsonProperty("duration_unit")
	private String durationUnit;

	public CourseMobileDto(String courseId, @NotBlank(message = "{course_name.is_required}") String courseName,
			@NotBlank(message = "{course_description.is_required}") String courseDescription,
			@NotBlank(message = "{faculty_id.is_required}") String facultyId, String facultyName,
			@NotNull(message = "{gpa_required.is_required}") Double gpaRequired,
			@NotNull(message = "{usd_domestic_fee.is_required}") Double usdDomesticFee,
			@NotNull(message = "{usd_international_fee.is_required}") Double usdInternationalFee,
			@NotNull(message = "{duration.is_required}") Double duration,
			@NotBlank(message = "{duration_unit.is_required}") String durationUnit) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseDescription = courseDescription;
		this.facultyId = facultyId;
		this.facultyName = facultyName;
		this.gpaRequired = gpaRequired;
		this.usdDomesticFee = usdDomesticFee;
		this.usdInternationalFee = usdInternationalFee;
		this.duration = duration;
		this.durationUnit = durationUnit;
	}

}
