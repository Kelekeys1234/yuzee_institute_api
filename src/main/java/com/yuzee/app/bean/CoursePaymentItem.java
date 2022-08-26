package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
/*
 * @Table(name = "course_payment_item", uniqueConstraints
 * = @UniqueConstraint(columnNames = { "name", "course_payment_id" }, name =
 * "UK_COURSE_PAYMENT_N_P_ID"), indexes = {
 * 
 * @Index(name = "IDX_P_ID", columnList = "course_payment_id", unique = false)
 * })
 */
public class CoursePaymentItem implements Serializable {

	private static final long serialVersionUID = 8492390790670110780L;
	
	@EqualsAndHashCode.Include
	private String name;

	@EqualsAndHashCode.Include
	private Double amount;

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;

}
