package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "course")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Document(collection = "CourseLanguage")
//@Table(name = "course_language", uniqueConstraints = @UniqueConstraint(columnNames = { "course_id",
//		"language" }, name = "UK_COURSE_ID_LANGUAGE"), indexes = {
//				@Index(name = "IDX_COURSE_ID", columnList = "course_id", unique = false) })
public class CourseLanguage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id

	private String id;

	private String language;

	@DBRef(lazy = true)
	private Course course;

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;

	public void setAuditFields(String userId) {
		this.setUpdatedBy(userId);
		this.setUpdatedOn(new Date());
		if (StringUtils.isEmpty(id)) {
			this.setCreatedBy(userId);
			this.setCreatedOn(new Date());
		}
	}

	public CourseLanguage(String language, Course course, Date createdOn, Date updatedOn, String createdBy,
			String updatedBy) {
		super();
		this.language = language;
		this.course = course;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}
}
