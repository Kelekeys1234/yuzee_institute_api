package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "course")
/*@Table(name = "course_payment", uniqueConstraints = @UniqueConstraint(name = "UK_COURSE_PAYMENT", columnNames = {
		"course_id" })) */
public class CoursePayment implements Serializable {

	private static final long serialVersionUID = 1L;
	@EqualsAndHashCode.Include
    private String CoursePaymentId;
	@EqualsAndHashCode.Include
	private String description;

	@EqualsAndHashCode.Include
	private List<CoursePaymentItem> paymentItems = new ArrayList<>();

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;


	public void setAuditFields(String userId) {
		this.setUpdatedBy(userId);
		this.setUpdatedOn(new Date());
			this.setCreatedBy(userId);
			this.setCreatedOn(new Date());


	}
}