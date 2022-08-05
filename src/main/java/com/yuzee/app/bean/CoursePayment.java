package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "course")
/*
 * @Table(name = "course_payment", uniqueConstraints = @UniqueConstraint(name =
 * "UK_COURSE_PAYMENT", columnNames = { "course_id" }))
 */
public class CoursePayment implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	private String description;

	@EqualsAndHashCode.Include
	private List<CoursePaymentItem> paymentItems = new ArrayList<>();

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;
	private String auditFields;
	@DBRef
	private Course course;

	public void setAuditFields(String userId) {
		this.setUpdatedBy(userId);
		this.setUpdatedOn(new Date());
		if (StringUtils.isEmpty(userId)) {
			this.setCreatedBy(userId);
			this.setCreatedOn(new Date());
		}
	}
}