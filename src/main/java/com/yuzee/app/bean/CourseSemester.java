package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class CourseSemester implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
    private String courseSemesterId;
	
	@EqualsAndHashCode.Include
	private String type;

	@EqualsAndHashCode.Include
	private String name;

	@EqualsAndHashCode.Include
	private String description;

	private List<SemesterSubject> subjects = new ArrayList<>();
	
}