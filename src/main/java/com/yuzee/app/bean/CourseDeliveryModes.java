package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
/*@Table(name = "course_delivery_modes", uniqueConstraints = @UniqueConstraint(columnNames = { "course_id", "study_mode",
		"delivery_type", "is_government_eligible", "accessibility", "duration", "duration_time" }, name = "UK_CDM_CI_SM_DT_GE_AC_DU_DT"), indexes = {
				@Index(name = "IDX_COURSE_ID", columnList = "course_id", unique = false) }) */
public class CourseDeliveryModes implements Serializable{

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	private String deliveryType;

	@EqualsAndHashCode.Include
	private Double duration;

	@EqualsAndHashCode.Include
	private String durationTime;

	@EqualsAndHashCode.Include
	private String studyMode;

	private Boolean isGovernmentEligible;

	private String accessibility;

	@EqualsAndHashCode.Include
	private List<CourseFees> fees = new ArrayList<>();

	@EqualsAndHashCode.Include
	private List<CourseDeliveryModeFunding> fundings = new ArrayList<>();

}
