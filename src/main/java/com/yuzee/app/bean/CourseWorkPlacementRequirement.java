package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
/*@Table(name = "course_work_placement_requirement", indexes = {
		@Index(name = "IDX_COURSE_ID", columnList = "course_id", unique = false) }) */
public class CourseWorkPlacementRequirement implements Serializable {

	private static final long serialVersionUID = 8492390790670110780L;

	@EqualsAndHashCode.Include
	private String description;

	private String durationType;

	private Double duration;

	@EqualsAndHashCode.Include
	private List<String> fields;

}
