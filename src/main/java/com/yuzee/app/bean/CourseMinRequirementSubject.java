package com.yuzee.app.bean;

import java.io.Serializable;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
/*
 * @Table(name = "course_min_requirement_subject", uniqueConstraints
 * = @UniqueConstraint(columnNames = { "name", "course_min_requirement_id" },
 * name = "UK_COURSE_NA_CN_CN"), indexes = {
 * 
 * @Index(name = "IDX_CMRS", columnList = "course_min_requirement_id", unique =
 * false) })
 */
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
