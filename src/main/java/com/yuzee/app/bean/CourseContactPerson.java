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
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "course_contact_person", uniqueConstraints = @UniqueConstraint(columnNames = { "course_id",
		"user_id" }, name = "UK_CCP_COURSE_ID_USER_ID"), indexes = {
				@Index(name = "IDX_COURSE_ID", columnList = "course_id", unique = false) })
@Data
@NoArgsConstructor
@ToString(exclude = "course")
public class CourseContactPerson implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "GUID", strategy = "org.hibernate.id.GUIDGenerator")
	@GeneratedValue(generator = "GUID")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", nullable = false)
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
