package com.yuzee.app.bean;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
/*
 * @Table(name = "course_fees", uniqueConstraints
 * = @UniqueConstraint(columnNames = { "name", "course_delivery_mode_id" }, name
 * = "UK_CDMF_N_DM_ID"), indexes = {
 * 
 * @Index(name = "IDX_P_ID", columnList = "course_delivery_mode_id", unique =
 * false) })
 */
public class CourseFees implements Serializable {

	private static final long serialVersionUID = 8492390790670110780L;
	@javax.persistence.Id
	private String Id;
	@EqualsAndHashCode.Include
	private String name;

	@EqualsAndHashCode.Include
	private String currency;

	@EqualsAndHashCode.Include
	private Double amount;
	@EqualsAndHashCode.Include
	private CourseDeliveryModes courseDeliveryMode;

	private String auditFields;

}
