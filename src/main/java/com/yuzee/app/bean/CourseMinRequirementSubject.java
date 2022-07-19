package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
/*@Table(name = "course_min_requirement_subject", uniqueConstraints = @UniqueConstraint(columnNames = { "name",
		"course_min_requirement_id" }, name = "UK_COURSE_NA_CN_CN"), indexes = {
				@Index(name = "IDX_CMRS", columnList = "course_min_requirement_id", unique = false) }) */
public class CourseMinRequirementSubject implements Serializable {

	private static final long serialVersionUID = 8492390790670110780L;

	@EqualsAndHashCode.Include
	private String name;

	@EqualsAndHashCode.Include
	private String grade;
}
