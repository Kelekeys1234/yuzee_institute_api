package com.yuzee.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString
@EqualsAndHashCode
@Table(name = "course_min_requirement", indexes = { @Index (name = "IDX_COURSE_ID", columnList="course_id", unique = false)})
public class CourseMinRequirement implements Serializable {

	private static final long serialVersionUID = 6903674843134844883L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;

	@Column(name = "country_name", nullable = false)
	private String countryName;

	@Column(name = "system_id", nullable = false)
	private String system;

	@Column(name = "subject", nullable = false)
	private String subject;

	@Column(name = "grade", nullable = false)
	private String grade;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private Course course;
}
