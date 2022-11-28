package com.yuzee.app.bean;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class CourseProviderCode implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	
	private String value;
}
