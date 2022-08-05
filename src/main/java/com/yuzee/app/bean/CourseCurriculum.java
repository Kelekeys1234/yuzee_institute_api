package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
//@Entity
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@Table(name = "course_curriculum",uniqueConstraints = @UniqueConstraint(columnNames = { "name" }, name = "UK_CURRICULUM_NAME"))
public class CourseCurriculum implements Serializable {

	private static final long serialVersionUID = 8970688340243493406L;

	private String name;

	private Boolean isActive;

	private Date createdOn;
	private Date updatedOn;
	private String createdBy;
	private String updatedBy;

}
