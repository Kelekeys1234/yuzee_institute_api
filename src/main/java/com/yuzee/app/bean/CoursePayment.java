package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "course")
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