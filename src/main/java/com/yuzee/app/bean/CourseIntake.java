package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.yuzee.common.lib.enumeration.IntakeType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
/*
 * @Table(name = "course_intake", uniqueConstraints
 * = @UniqueConstraint(columnNames = { "course_id" }, name = "UK_COURSE_ID"))
 */
public class CourseIntake implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@EqualsAndHashCode.Include
	private IntakeType type;

	private List<Date> dates;
	private String auditFields;
	@DBRef
	private Course course;

}
