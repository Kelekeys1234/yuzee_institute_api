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
//@Table(name = "course_prerequisite", uniqueConstraints = @UniqueConstraint(columnNames = { "course_id", "description" }, 
//	   	 name = "UK_NA_CN_CN"), indexes = {@Index(name = "IDX_COURSE_ID", columnList = "course_id", unique = false) })
@Document(collection = "CoursePrerequisite")
public class CoursePrerequisite implements Serializable {

	private static final long serialVersionUID = 8492390790670110780L;

	@Id
//	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
//	@GeneratedValue(generator = "generator")
//	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;

	@EqualsAndHashCode.Include
//	@Column(name = "description", nullable = false)
	private String description;

//	
	@DBRef(lazy = true)
	private Course course;

	private Date createdOn;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "updated_on", length = 19)
	private Date updatedOn;

	private String createdBy;

	private String updatedBy;

	public CoursePrerequisite(String description, Course course, Date createdOn, Date updatedOn, String createdBy,
			String updatedBy) {
		super();
		this.description = description;
		this.course = course;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}

	public void setAuditFields(String userId) {
		this.setUpdatedBy(userId);
		this.setUpdatedOn(new Date());
		if (StringUtils.isEmpty(id)) {
			this.setCreatedBy(userId);
			this.setCreatedOn(new Date());
		}
	}
}
