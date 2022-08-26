package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
/*
 * @Table(name = "course_semester", uniqueConstraints
 * = @UniqueConstraint(columnNames = { "type", "name", "course_id" }, name =
 * "UK_SEMESTER_TYPE_NA_CI"), indexes = {
 * 
 * @Index(name = "IDX_COURSE", columnList = "course_id", unique = false) })
 */
public class CourseSemester implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@EqualsAndHashCode.Include
	private String type;

	@EqualsAndHashCode.Include
	private String name;

	@EqualsAndHashCode.Include
	private String description;

	private String auditFields;
	@DBRef
	private Course course;

	private String createdBy;

	private List<SemesterSubject> subjects = new ArrayList<>();

}