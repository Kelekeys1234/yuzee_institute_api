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
 * @Table(name = "semeter_subject", uniqueConstraints
 * = @UniqueConstraint(columnNames = { "name", "course_semester_id" }, name =
 * "UK_COURSE_SEMESTER_NA"), indexes = {
 * 
 * @Index(name = "IDX_COURSE", columnList = "course_semester_id", unique =
 * false) })
 */
public class SemesterSubject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
	private String name;

	@EqualsAndHashCode.Include
	private String description;

	public SemesterSubject(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	

}