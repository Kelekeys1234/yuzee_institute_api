package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data
@ToString(exclude = "course")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Document(collection = "CoursePrerequisite")
public class CoursePrerequisite implements Serializable {

	private static final long serialVersionUID = 8492390790670110780L;

	@Id
	private String id;

	@EqualsAndHashCode.Include
	private String description;

	@DBRef(lazy = true)
	private Course course;

	private Date createdOn;

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
