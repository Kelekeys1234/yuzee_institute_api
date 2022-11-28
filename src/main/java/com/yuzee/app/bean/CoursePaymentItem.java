package com.yuzee.app.bean;

import java.io.Serializable;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
