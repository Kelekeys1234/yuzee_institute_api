package com.yuzee.app.bean;

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
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "course_funding", uniqueConstraints = @UniqueConstraint(columnNames = { "course_id",
		"funding_name_id" }, name = "UK_COURSE_ID_FUNDING_ID"), indexes = {
				@Index(name = "IDX_FUNDING_COURSE_ID", columnList = "course_id", unique = false) })
@Data
@NoArgsConstructor
@ToString(exclude = "course")
@EqualsAndHashCode
public class CourseFunding {

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

	@Column(name = "funding_name_id", nullable = false)
	private String fundingNameId;
	
	public void setAuditFields(String userId, CourseFunding existingCourseFunding) {
		this.setUpdatedBy(userId);
		this.setUpdatedOn(new Date());
		if (existingCourseFunding != null) {
			this.setCreatedBy(existingCourseFunding.getCreatedBy());
			this.setCreatedOn(existingCourseFunding.getCreatedOn());
		}else {
			this.setCreatedBy(userId);
			this.setCreatedOn(new Date());
		}
	}
}
