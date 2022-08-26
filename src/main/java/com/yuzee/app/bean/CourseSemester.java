package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
/*@Table(name = "course_semester", uniqueConstraints = @UniqueConstraint(columnNames = { "type", "name",
		"course_id" }, name = "UK_SEMESTER_TYPE_NA_CI"), indexes = {
				@Index(name = "IDX_COURSE", columnList = "course_id", unique = false) }) */
public class CourseSemester implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	private String type;

	@EqualsAndHashCode.Include
	private String name;

	@EqualsAndHashCode.Include
	private String description;

	private List<SemesterSubject> subjects = new ArrayList<>();
	
}