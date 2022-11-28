package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CourseWorkExperienceRequirement implements Serializable {

	private static final long serialVersionUID = 8492390790670110780L;

	@EqualsAndHashCode.Include
	private String description;

	@EqualsAndHashCode.Include
	private String durationType;

	@EqualsAndHashCode.Include
	private Double duration;
	
	@EqualsAndHashCode.Include
	private List<String> fields;


}
