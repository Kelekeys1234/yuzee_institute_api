package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
