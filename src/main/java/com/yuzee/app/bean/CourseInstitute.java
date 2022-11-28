package com.yuzee.app.bean;

import java.io.Serializable;

import java.util.Date; 
import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(exclude = { "sourceCourse", "sourceInstitute", "destinationCourse", "destinationInstitute" })
@EqualsAndHashCode
@Document(collection = "course_institute")
public class CourseInstitute implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5639931588147850985L;

	@Id

	private String id;
	@DBRef(lazy = true)
	private Course sourceCourse;

	@DBRef(lazy = true)
	private Course destinationCourse;

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
}
