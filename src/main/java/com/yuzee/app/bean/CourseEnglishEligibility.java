package com.yuzee.app.bean;

import java.io.Serializable;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "CourseEnglishEligibility")
/*
 * @Table(name = "course_english_eligibility", uniqueConstraints
 * = @UniqueConstraint(columnNames = { "course_id", "english_type" }, name =
 * "UK_COURSE_ID_ENGLISH_TYPE"), indexes = {
 * 
 * @Index(name = "IDX_COURSE_ID", columnList = "course_id", unique = false) })
 */
public class CourseEnglishEligibility implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
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
	@DBRef
	private Course course;

	private String auditFields;

	private String createdBy;

	public CourseEnglishEligibility(String englishType, Double reading, Double writing, Double speaking,
			Double listening, Double overall, Boolean isActive, Course course, String auditFields, String createdBy) {
		super();

		this.englishType = englishType;
		this.reading = reading;
		this.writing = writing;
		this.speaking = speaking;
		this.listening = listening;
		this.overall = overall;
		this.isActive = isActive;
		this.course = course;
		this.auditFields = auditFields;
		this.createdBy = createdBy;
	}

}
