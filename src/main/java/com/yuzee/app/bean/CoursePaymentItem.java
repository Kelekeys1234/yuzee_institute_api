package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
/*@Table(name = "course_payment_item", uniqueConstraints = @UniqueConstraint(columnNames = { "name",
		"course_payment_id" }, name = "UK_COURSE_PAYMENT_N_P_ID"), indexes = {
				@Index(name = "IDX_P_ID", columnList = "course_payment_id", unique = false) }) */
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
