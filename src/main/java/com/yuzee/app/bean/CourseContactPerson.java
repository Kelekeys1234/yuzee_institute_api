package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@Entity
//@Table(name = "course_contact_person", uniqueConstraints = @UniqueConstraint(columnNames = { "course_id",
//		"user_id" }, name = "UK_CCP_COURSE_ID_USER_ID"), indexes = {
//				@Index(name = "IDX_COURSE_ID", columnList = "course_id", unique = false) })
@Data
@NoArgsConstructor
@ToString(exclude = "course")
public class CourseContactPerson implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id

	private String id;

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;

	@DBRef(lazy = true)
	private Course course;

	@Column(name = "user_id", nullable = false)
	private String userId;

	public void setAuditFields(String userId) {
		this.setUpdatedBy(userId);
		this.setUpdatedOn(new Date());
		if (StringUtils.isEmpty(id)) {
			this.setCreatedBy(userId);
			this.setCreatedOn(new Date());
		}
	}
}
