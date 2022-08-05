package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "course_Delevering_mode")
/*
 * @Table(name = "course_delivery_modes", uniqueConstraints
 * = @UniqueConstraint(columnNames = { "course_id", "study_mode",
 * "delivery_type", "is_government_eligible", "accessibility", "duration",
 * "duration_time" }, name = "UK_CDM_CI_SM_DT_GE_AC_DU_DT"), indexes = {
 * 
 * @Index(name = "IDX_COURSE_ID", columnList = "course_id", unique = false) })
 */
public class CourseDeliveryModes implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@EqualsAndHashCode.Include
	private String deliveryType;

	@EqualsAndHashCode.Include
	private Double duration;

	@EqualsAndHashCode.Include
	private String durationTime;

	@EqualsAndHashCode.Include
	private String studyMode;
	private String CreatedBy;
	private Boolean isGovernmentEligible;

	private String accessibility;
	@DBRef(lazy = true)
	private Course course;
	private String AuditFields;
	@EqualsAndHashCode.Include
	private List<CourseFees> fees = new ArrayList<>();

	@EqualsAndHashCode.Include
	private List<CourseDeliveryModeFunding> fundings = new ArrayList<>();

}
