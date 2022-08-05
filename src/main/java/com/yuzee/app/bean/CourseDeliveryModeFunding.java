package com.yuzee.app.bean;

import java.io.Serializable;

import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
/*
 * @Table(name = "course_delivery_mode_funding", uniqueConstraints
 * = @UniqueConstraint(columnNames = { "name", "funding_name_id",
 * "course_delivery_mode_id" }, name = "UK_CDMF_N_FN_DM_ID"), indexes = {
 * 
 * @Index(name = "IDX_D_ID", columnList = "course_delivery_mode_id", unique =
 * false) })
 */
public class CourseDeliveryModeFunding implements Serializable {

	private static final long serialVersionUID = 8492390790670110780L;
	@Id
	private String id;
	@EqualsAndHashCode.Include
	private String name;

	@EqualsAndHashCode.Include
	private String fundingNameId;

	@EqualsAndHashCode.Include
	private String currency;

	@EqualsAndHashCode.Include
	private Double amount;

	@EqualsAndHashCode.Include
	private CourseDeliveryModes courseDeliveryMode;
	@EqualsAndHashCode.Include
	private String auditFields;

}
