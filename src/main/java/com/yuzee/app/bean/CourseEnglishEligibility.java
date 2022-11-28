package com.yuzee.app.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor

public class CourseEnglishEligibility implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	private String englishType;

	@EqualsAndHashCode.Include
	private Double reading;

	@EqualsAndHashCode.Include
	private Double writing;

	@EqualsAndHashCode.Include
	private Double speaking;

	@EqualsAndHashCode.Include
	private Double listening;

	@EqualsAndHashCode.Include
	private Double overall;

	@EqualsAndHashCode.Include
	private Boolean isActive;

}
