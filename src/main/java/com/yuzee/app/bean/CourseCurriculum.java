package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CourseCurriculum implements Serializable {

	private static final long serialVersionUID = 8970688340243493406L;

	private String name;

	private Boolean isActive;

}
