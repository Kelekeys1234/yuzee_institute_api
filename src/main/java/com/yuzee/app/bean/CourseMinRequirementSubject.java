package com.yuzee.app.bean;

import java.io.Serializable;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class CourseMinRequirementSubject implements Serializable {

	private static final long serialVersionUID = 8492390790670110780L;
	
	@EqualsAndHashCode.Include
	private String name;

	@EqualsAndHashCode.Include
	private String grade;
	
	private String auditFields;
	
	@DBRef
	private CourseMinRequirement courseMinRequirement;
	public CourseMinRequirementSubject(String name, String grade) {
		super();
		this.name = name;
		this.grade = grade;
	}
	
	
}
