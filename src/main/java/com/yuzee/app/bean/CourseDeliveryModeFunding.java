package com.yuzee.app.bean;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CourseDeliveryModeFunding implements Serializable {

	private static final long serialVersionUID = 8492390790670110780L;

	@EqualsAndHashCode.Include
	private String name;

	@EqualsAndHashCode.Include
	private String fundingNameId;

	@EqualsAndHashCode.Include
	private String currency;

	@EqualsAndHashCode.Include
	private Double amount;

}
