package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.yuzee.common.lib.enumeration.IntakeType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CourseIntake implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	private IntakeType type;

	private List<Date> dates;

}
