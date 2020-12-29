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

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString
@EqualsAndHashCode
@Table(name = "course_language", uniqueConstraints = @UniqueConstraint(columnNames = { "course_id",
"language" }, name = "UK_COURSE_ID_LANGUAGE"), indexes = {
		@Index(name = "IDX_COURSE_ID", columnList = "course_id", unique = false)})
public class CourseLanguage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;
	
	@Column(name = "language", nullable = false)
	private String language;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	private Date updatedOn;

	@Column(name = "created_by", length = 50)
	private String createdBy;

	@Column(name = "updated_by", length = 50)
	private String updatedBy;
	
	public void setAuditFields(String userId, CourseLanguage existingCourseLanguage) {
		this.setUpdatedBy(userId);
		this.setUpdatedOn(new Date());
		if (existingCourseLanguage != null) {
			this.setCreatedBy(existingCourseLanguage.getCreatedBy());
			this.setCreatedOn(existingCourseLanguage.getCreatedOn());
		}else {
			this.setCreatedBy(userId);
			this.setCreatedOn(new Date());
		}
	}
}
